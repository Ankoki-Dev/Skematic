package com.efnilite.skematic.elements.fawe;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.EnumUtils;
import com.efnilite.skematic.elements.fawe.object.FaweOptions;
import com.efnilite.skematic.elements.fawe.object.FaweSchematic;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;

public class FaweTypes {

    public FaweTypes() {
        Classes.registerClass(new ClassInfo<>(CuboidRegion.class, "fawe cuboidregion")
                .user("fawe cuboidregions?")
                .name("Fawe Cuboidregion")
                .description("A virtual region selection.")
                .since("1.0")
                .parser(new Parser<CuboidRegion>() {

                    @Override
                    public CuboidRegion parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(CuboidRegion o, int flags) {
                        return o.toString();
                    }

                    @Override
                    public String toVariableNameString(CuboidRegion o) {
                        return "cuboidregion:" + o.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "cuboidregion:.+";
                    }
                }));

        Classes.registerClass(new ClassInfo<>(FaweSchematic.class, "fawe schematic")
                .user("fawe schematics?")
                .name("Fawe FaweSchematic")
                .description("A fawe schematic file.")
                .since("2.1")
                .parser(new Parser<FaweSchematic>() {

                    @Override
                    public FaweSchematic parse(final String s, final ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(final ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(final FaweSchematic o, int flags) {
                        return o.getFile().getPath();
                    }

                    @Override
                    public String toVariableNameString(final FaweSchematic o) {
                        return "schematic:" + o.getFile().getPath();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "schematic:.+";
                    }
                }));

        EnumUtils<FaweOptions> pasteOptions = new EnumUtils<>(FaweOptions.class, "fawe pasteoption");
        Classes.registerClass(new ClassInfo<>(FaweOptions.class, "fawe pasteoption")
                .defaultExpression(new EventValueExpression<>(FaweOptions.class))
                .user("fawe pasteoptions?")
                .name("Fawe Paste Options")
                .description("Options to change how you paste a fawe schematic/cuboidregion. Current options: air, entities and biomes")
                .since("2.1")
                .parser(new Parser<FaweOptions>() {

                    @Override
                    public FaweOptions parse(String s, ParseContext context) {
                        return pasteOptions.parse(s);
                    }

                    @Override
                    public String toString(FaweOptions o, int flags) {
                        return pasteOptions.toString(o, flags);
                    }

                    @Override
                    public String toVariableNameString(FaweOptions o) {
                        return "pasteoptions:" + o.name();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "pasteoptions:.+";
                    }
                }));

        EnumUtils<BuiltInClipboardFormat> clipboardFormats = new EnumUtils<>(BuiltInClipboardFormat.class, "fawe schematicformat");
        Classes.registerClass(new ClassInfo<>(ClipboardFormat.class, "fawe schematicformat")
                .defaultExpression(new EventValueExpression<>(BuiltInClipboardFormat.class))
                .user("fawe schematicformats?")
                .name("Fawe FaweSchematic Format")
                .description("The format fawe schematics are saved in.")
                .since("2.0")
                .parser(new Parser<BuiltInClipboardFormat>() {

                    @Override
                    public BuiltInClipboardFormat parse(String s, ParseContext context) {
                        return clipboardFormats.parse(s);
                    }

                    @Override
                    public String toString(BuiltInClipboardFormat o, int flags) {
                        return clipboardFormats.toString(o, flags);
                    }

                    @Override
                    public String toVariableNameString(BuiltInClipboardFormat o) {
                        return "clipboardformat:" + o.getName();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "clipboardformat:.+";
                    }

                }));
    }
}
