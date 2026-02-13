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

public class V6__SeedCatalogData extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        Connection connection = context.getConnection();
        ObjectMapper mapper = new ObjectMapper();

        // 1. Load the JSON file from resources
        try (InputStream is = getClass().getResourceAsStream("/data/works-by-composer.min.json")) {
            if (is == null) {
                throw new RuntimeException("Could not find works-by-composer.min.json in resources/data/");
            }

            JsonNode root = mapper.readTree(is);
            JsonNode composers = root.get("composers");

            for (JsonNode composer : composers) {
                UUID composerId = UUID.randomUUID();

                // 2. Insert Composer
                try (PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO catalog_composers (id, name, short_name, epoch, birth, death) VALUES (?, ?, ?, ?, ?, ?)")) {
                    ps.setObject(1, composerId);
                    ps.setString(2, composer.get("complete_name").asText());
                    ps.setString(3, composer.get("name").asText());
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

                    // 3. Insert Work
                    try (PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO catalog_works (id, composer_id, title, subtitle) VALUES (?, ?, ?, ?)")) {
                        ps.setObject(1, workId);
                        ps.setObject(2, composerId);
                        ps.setString(3, work.get("title").asText());
                        ps.setString(4, work.get("subtitle").asText());
                        ps.executeUpdate();
                    }

                    for (JsonNode instr : work.get("instrumentation")) {
                        String instrName = instr.get("name").asText();

                        // 4. Find Instrumentation ID by Name (Lookup)
                        UUID instrumentationId = null;
                        try (PreparedStatement ps = connection.prepareStatement(
                                "SELECT id FROM instrumentation WHERE name = ?")) {
                            ps.setString(1, instrName);
                            try (ResultSet rs = ps.executeQuery()) {
                                if (rs.next()) {
                                    instrumentationId = (UUID) rs.getObject("id");
                                }
                            }
                        }

                        // 5. Insert Link (if instrument found)
                        if (instrumentationId != null) {
                            try (PreparedStatement ps = connection.prepareStatement(
                                    "INSERT INTO catalog_work_instrumentation (work_id, instrumentation_id, rank, quantity) VALUES (?, ?, ?, ?)")) {
                                ps.setObject(1, workId);
                                ps.setObject(2, instrumentationId);
                                ps.setString(3, instr.get("rank").asText(""));
                                ps.setInt(4, instr.get("quantity").asInt(1));
                                ps.executeUpdate();
                            }
                        }
                    }
                }
            }
        }
    }
}