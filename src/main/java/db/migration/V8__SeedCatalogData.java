package db.migration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class V8__SeedCatalogData extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        Connection connection = context.getConnection();
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getClass().getResourceAsStream("/data/works-by-composer.min.json")) {
            if (is == null) {
                throw new RuntimeException("Could not find works-by-composer.min.json");
            }

            JsonNode root = mapper.readTree(is);
            JsonNode composers = root.get("composers");

            for (JsonNode composer : composers) {
                UUID composerId = UUID.randomUUID();

                // Insert Composer
                try (PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO catalog_composer (id, name, short_name, epoch, birth, death) VALUES (?, ?, ?, ?, ?, ?)")) {
                    ps.setObject(1, composerId);
                    ps.setString(2, composer.get("name").asText());
                    ps.setString(3, composer.get("short_name").asText());
                    ps.setString(4, composer.get("epoch").asText());
                    ps.setDate(5, Date.valueOf(composer.get("birth").asText()));

                    if (composer.get("death").isNull()) {
                        ps.setNull(6, java.sql.Types.DATE);
                    } else {
                        ps.setDate(6, Date.valueOf(composer.get("death").asText()));
                    }
                    ps.executeUpdate();
                }

                for (JsonNode work : composer.get("works")) {
                    UUID workId = UUID.randomUUID();

                    // Insert Work
                    try (PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO catalog_work (id, composer_id, title, classification) VALUES (?, ?, ?, ?)")) {
                        ps.setObject(1, workId);
                        ps.setObject(2, composerId);
                        ps.setString(3, work.get("title").asText());
                        ps.setString(4, work.get("classification").asText());
                        ps.executeUpdate();
                    }

                    // Insert Catalog Identifiers (i.e. ["TWV.1:1536", "TWV.1:1537", ...])
                    if (work.has("catalog_numbers")) {
                        for (JsonNode catalogNumber : work.get("catalog_numbers")) {
                            String value = catalogNumber.asText();

                            try (PreparedStatement ps = connection.prepareStatement(
                                    "INSERT INTO catalog_number (work_id, value) VALUES (?, ?)")) {
                                ps.setObject(1, workId);
                                ps.setString(2, value);
                                ps.executeUpdate();
                            }
                        }
                    }

                    // Insert Settings
                    for (JsonNode settingNode : work.get("settings")) {
                        UUID settingId = UUID.randomUUID();

                        try (PreparedStatement ps = connection.prepareStatement(
                                "INSERT INTO work_setting (id, work_id, name) VALUES (?, ?, ?)")) {
                            ps.setObject(1, settingId);
                            ps.setObject(2, workId);
                            ps.setString(3, "Standard");
                            ps.executeUpdate();
                        }

                        // Prepare instrumentation "seats"
                        for (JsonNode instrumentationSlot : settingNode.get("instrumentation")) {
                            UUID slotId = UUID.randomUUID();

                            try (PreparedStatement ps = connection.prepareStatement(
                                    "INSERT INTO instrumentation_slot (id, work_setting_id) VALUES (?, ?)")) {
                                ps.setObject(1, slotId);
                                ps.setObject(2, settingId);
                                ps.executeUpdate();
                            }

                            // Insert Instrumentation alternatives for a seat
                            for (JsonNode alternative : instrumentationSlot.get("alternatives")) {
                                String instrumentationName = alternative.get("name").asText();
                                int quantity = alternative.get("quantity").asInt(1);
                                UUID instrumentationId = lookupInstrumentationId(connection, instrumentationName);

                                if (instrumentationId != null) {
                                    try (PreparedStatement ps = connection.prepareStatement(
                                            "INSERT INTO instrumentation_alternative (instrumentation_slot_id, instrumentation_id, quantity) VALUES (?, ?, ?)")) {
                                        ps.setObject(1, slotId);
                                        ps.setObject(2, instrumentationId);
                                        ps.setInt(3, quantity);
                                        ps.executeUpdate();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private UUID lookupInstrumentationId(Connection conn, String name) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement("SELECT id FROM instrumentation WHERE name = ?")) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return (UUID) rs.getObject("id");
                }
            }
        }

        throw new RuntimeException("Instrumentation not found: " + name);
    }
}