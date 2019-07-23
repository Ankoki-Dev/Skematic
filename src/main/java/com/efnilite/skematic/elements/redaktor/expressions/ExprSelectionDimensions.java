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
import org.bukkit.event.Event;

@Name("Redaktor - Dimensions")
@Description("Gets the dimensions of a selection")
@Since("3.0")
public class ExprSelectionDimensions extends SimpleExpression<Number> {

    private int mark;
    private Expression<CuboidSelection> cuboid;

    static {
        Skript.registerExpression(ExprSelectionDimensions.class, Number.class, ExpressionType.PROPERTY,
                "[the] (cuboid|redaktor) selection (1¦length|2¦height|3¦width|4¦volume) of %cuboidselections%",
                "[the] %cuboidselections%'s (cuboid|we|worldedit)[ ]region (1¦length|2¦height|3¦width|4¦volume)");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        mark = parseResult.mark;
        cuboid = (Expression<CuboidSelection>) expressions[0];

        return true;
    }

    @Override
    protected Number[] get(Event e) {
        CuboidSelection cuboid = this.cuboid.getSingle(e);

        if (cuboid == null) {
            return null;
        }

        Dimensions dimensions = cuboid.getDimensions();

        switch (mark) {
            case 1:
                return new Number[]{dimensions.getLength()};
            case 2:
                return new Number[]{dimensions.getHeight()};
            case 3:
                return new Number[]{dimensions.getWidth()};
            case 4:
                return new Number[]{dimensions.getVolume()};
            default:
                return new Number[]{0};
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "dimensions of " + cuboid.toString(e, debug);
    }
}