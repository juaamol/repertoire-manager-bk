package com.learning.repertoire_manager.works.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
public class InstrumentationTreeDto {
    private UUID id;
    private String name;
    private Integer level;
    private boolean matched;
    private List<InstrumentationTreeDto> children;

    public void addChild(InstrumentationTreeDto child) {
        this.children.add(child);
    }
}