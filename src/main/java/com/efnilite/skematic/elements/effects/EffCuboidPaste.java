package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.Skematic;
import com.efnilite.skematic.object.FaweOptions;
import com.efnilite.skematic.object.FaweSchematic;
import com.efnilite.skematic.util.FaweUtil;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.HashSet;

@Name("Fawe - Paste cuboidregion")
@Description("Paste a cuboidregion at a location. This can be the player's selection, etc.")
@Examples("paste player's selection at player's location ignoring air and ignoring biomes and ignoring entities")
@Since("2.1")
public class EffCuboidPaste extends Effect {

    private Expression<CuboidRegion> cuboid;
    private Expression<Location> location;
    private Expression<FaweOptions> options;

    static {
        Skript.registerEffect(EffCuboidPaste.class, "paste fawe %fawe cuboidregions% at %location% [ignoring %-pasteoptions%]");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        cuboid = (Expression<CuboidRegion>) expressions[0];
        location = (Expression<Location>) expressions[1];
        options = (Expression<FaweOptions>) expressions[2];

        return true;
    }

    @Override
    protected void execute(Event e) {
        CuboidRegion cuboid = this.cuboid.getSingle(e);
        Location location = this.location.getSingle(e);
        FaweOptions[] options = this.options != null ? this.options.getArray(e) : new FaweOptions[] { FaweOptions.DEFAULT };

        if (cuboid == null || location == null) {
            return;
        }

        World world = location.getWorld();

        if (world == null) {
            Skematic.error("World is null (" + getClass().getName() + ") - be sure to set the world of a location!");
            return;
        }

        HashSet<FaweOptions> optionsSet = new HashSet<>(Arrays.asList(options));
        new FaweSchematic(cuboid).paste(FaweUtil.getWorld(world.getName()), FaweUtil.toVector(location), optionsSet);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "paste " + cuboid.toString(e, debug) + " at " + location.toString(e, debug) + (options != null ? " with options " + options.toString(e, debug) : "");
    }
}
