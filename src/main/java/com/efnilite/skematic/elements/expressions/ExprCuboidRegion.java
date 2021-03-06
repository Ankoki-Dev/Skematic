package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.util.FaweUtil;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.Event;

@Name("Fawe - Create region")
@Description("Create a virtual region (for saving schematics, etc.)")
@Examples("set {_cool region} to a new fawe cuboidregion from {_location} to {_location-2}")
@Since("2.0")
public class ExprCuboidRegion extends SimpleExpression<CuboidRegion> {

    private Expression<?> location1;
    private Expression<?> location2;
    private static CuboidRegion last;

    static {
        Skript.registerExpression(ExprCuboidRegion.class, CuboidRegion.class, ExpressionType.PROPERTY,
                "[a] [new] (we|wordedit|fawe) [cuboid]region from %locations/blocks% to %location/blocks%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        location1 = expressions[0];
        location2 = expressions[1];

        return true;
    }

    protected CuboidRegion[] get(Event e) {
        Location location1;
        Location location2;

        if (this.location1.getSingle(e) == null || this.location2.getSingle(e) == null) {
            return null;
        }

        if (this.location1.getSingle(e) instanceof Block && this.location2.getSingle(e) instanceof Block) {
            location1 = ((Block) this.location1.getSingle(e)).getLocation();
            location2 = ((Block) this.location2.getSingle(e)).getLocation();
        } else if (this.location1.getSingle(e) instanceof Location && this.location2.getSingle(e) instanceof Location) {
            location1 = (Location) this.location1.getSingle(e);
            location2 = (Location) this.location2.getSingle(e);
        } else {
            return null;
        }

        if (location1 == null || location2 == null) {
            return null;
        }

        World world = location1.getWorld() == null ? location2.getWorld() : location1.getWorld();

        last = new CuboidRegion(FaweUtil.getWorld(world),
                FaweUtil.toVector(location1),
                FaweUtil.toVector(location2));
        return new CuboidRegion[] { last };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends CuboidRegion> getReturnType() {
        return CuboidRegion.class;
    }

    public static CuboidRegion[] getLastCuboidRegion() {
        return new CuboidRegion[] { last };
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create a region from " + this.location1.toString(e, debug) + " to " + this.location2.toString(e, debug);
    }
}
