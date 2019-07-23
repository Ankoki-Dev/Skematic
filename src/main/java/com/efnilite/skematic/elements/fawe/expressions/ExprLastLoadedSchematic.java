package com.efnilite.skematic.elements.fawe.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.elements.fawe.object.FaweSchematic;
import com.efnilite.skematic.elements.fawe.object.SchematicLoader;
import org.bukkit.event.Event;

@Name("Fawe - Last loaded schematic")
@Description("Get the lastly loaded schematic")
@Since("2.0")
public class ExprLastLoadedSchematic extends SimpleExpression<FaweSchematic> {

    static {
        Skript.registerExpression(ExprLastLoadedSchematic.class, FaweSchematic.class, ExpressionType.PROPERTY,
                "[the] last[ly] loaded fawe s(k|ch)ematic");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    protected FaweSchematic[] get(Event e) {
        return new FaweSchematic[] { SchematicLoader.getLastLoaded() };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends FaweSchematic> getReturnType() {
        return FaweSchematic.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "last loaded schematic";
    }
}
