package com.efnilite.skematic.elements.fawe.object;

import java.util.HashMap;

public class SchematicLoader {

    private static HashMap<String, FaweSchematic> schematics = new HashMap<>();
    private static FaweSchematic lastLoaded;

    public static void add(String alias, FaweSchematic schematic) {
        schematics.put(alias, schematic);
        lastLoaded = schematic;
    }

    public static FaweSchematic get(String alias) {
        return schematics.get(alias);
    }

    public static void remove(String alias) {
        schematics.remove(alias);
    }

    public static HashMap<String, FaweSchematic> getSchematics() {
        return schematics;
    }

    public static FaweSchematic getLastLoaded() {
        return lastLoaded;
    }
}
