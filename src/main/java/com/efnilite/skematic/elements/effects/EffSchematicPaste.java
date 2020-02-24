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
import com.efnilite.skematic.object.FaweOptions;
import com.efnilite.skematic.object.FaweSchematic;
import com.efnilite.skematic.util.FaweUtil;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;
import org.bukkit.event.Event;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Name("Fawe - Paste schematic")
@Description("Paste a schematic at a location with or without using air")
@Examples("paste skematic \"plugins/WorldEdit/skematic.schematic\" at player excluding air")
@Since("1.0")
public class EffSchematicPaste extends Effect {

    private Expression<?> schematic;
    private Expression<Location> location;
    private Expression<FaweOptions> options;

    static {
        Skript.registerEffect(EffSchematicPaste.class, "paste [the] fawe s(ch|k)em[atic] %strings/schematics% at %location% [ignoring %-pasteoptions%]");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        schematic = expressions[0];
        location = (Expression<Location>) expressions[1];
        options = (Expression<FaweOptions>) expressions[2];

        return true;
    }

    @Override
    protected void execute(Event e) {
        Location location = this.location.getSingle(e);
        FaweOptions[] options = this.options != null ? this.options.getArray(e) : new FaweOptions[] { FaweOptions.DEFAULT };

        if (this.schematic == null || location == null || options == null) {
            return;
        }

        FaweSchematic schematic;
        if (this.schematic.getSingle(e) instanceof String) {
            String file = (String) this.schematic.getSingle(e);
            if (FaweSchematic.getSchematics().containsKey(file)) {
                schematic = FaweSchematic.get(file);
            } else if (Paths.get(file).toFile().exists()) {
                schematic = new FaweSchematic(new File(file));
            } else {
                Skript.error("FaweSchematic " + file + " doesn't exist!");
                return;
            }
        } else if (this.schematic.getSingle(e) instanceof FaweSchematic) {
            schematic = (FaweSchematic) this.schematic.getSingle(e);
        } else {
            return;
        }

        Set<FaweOptions> optionsSet = new HashSet<>(Arrays.asList(options));

        BlockVector3 vector = FaweUtil.toVector(location);
        schematic.paste(FaweUtil.getWorld(location.getWorld()), vector, optionsSet);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "paste schematic " + schematic.toString(e, debug) + " at " + location.toString(e, debug);
    }
}
