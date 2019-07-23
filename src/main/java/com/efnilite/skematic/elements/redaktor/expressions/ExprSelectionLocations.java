package com.efnilite.skematic.elements.redaktor.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.selection.Dimensions;
import com.efnilite.redaktor.util.Util;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Redaktor - Dimensions")
@Description("Gets the dimensions of a selection")
@Since("3.0")
public class ExprSelectionLocations extends SimpleExpression<Location> {

    private int mark;
    private Expression<CuboidSelection> cuboid;

    static {
        Skript.registerExpression(ExprSelectionLocations.class, Location.class, ExpressionType.PROPERTY,
                "[the] (1¦min[(imum|imal)]|2¦max[(imum|imal)]) location of %cuboidselections%",
                "[the] %cuboidselections%'s (1¦min[(imum|imal)]|2¦max[(imum|imal)]) location");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        mark = parseResult.mark;
        cuboid = (Expression<CuboidSelection>) expressions[0];

        return true;
    }

    @Override
    protected Location[] get(Event e) {
        CuboidSelection cuboid = this.cuboid.getSingle(e);

        if (cuboid == null) {
            return null;
        }

        switch (mark) {
            case 1:
                return new Location[] { cuboid.getMinimumPoint() };
            case 2:
                return new Location[] { cuboid.getMaximumPoint() };
            default:
                return new Location[] { Util.zero() };
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Location> getReturnType() {
        return Location.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "dimensions of " + cuboid.toString(e, debug);
    }
}