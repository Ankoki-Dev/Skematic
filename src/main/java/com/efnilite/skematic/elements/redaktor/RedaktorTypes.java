package com.efnilite.skematic.elements.redaktor;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import com.efnilite.redaktor.selection.CuboidSelection;

public class RedaktorTypes {

    public RedaktorTypes() {
        Classes.registerClass(new ClassInfo<>(CuboidSelection.class, "cuboidregion")
                .user("cuboidselections?")
                .name("CuboidSelection")
                .description("A virtual region selection.")
                .since("1.0")
                .parser(new Parser<CuboidSelection>() {

                    @Override
                    public CuboidSelection parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(CuboidSelection o, int flags) {
                        return o.toString();
                    }

                    @Override
                    public String toVariableNameString(CuboidSelection o) {
                        return "cuboidregion:" + o.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "cuboidregion:.+";
                    }
                }));
    }
}
