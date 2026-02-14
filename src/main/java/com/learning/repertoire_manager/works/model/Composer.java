package com.learning.repertoire_manager.works.model;

import java.time.LocalDate;
import java.util.UUID;

public interface Composer {
    UUID getId();
    String getName();
    String getShortName();
    String getEpoch();
    LocalDate getBirth();
    LocalDate getDeath();
}