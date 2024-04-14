package com.example.spaceship.model;

import java.util.Map;

public record Field(Dimension areaSize, Map<String, Area> areas) {

    public Area getArea(Vector objectPosition) {
        return getArea(objectPosition, 0);
    }

    public Area getArea(Vector objectPosition, int offset) {
        var index = new StringBuilder();
        for (int i = 0; i < areaSize.getDimensionCount(); i++) {
            index.append((objectPosition.getCoordinate(i) + offset) / (areaSize.getDimensionSize(i)));
            index.append(":");
        }
        return areas.get(index.toString());
    }

}
