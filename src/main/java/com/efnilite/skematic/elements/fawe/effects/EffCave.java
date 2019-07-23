package com.efnilite.skematic.elements.fawe.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.util.FaweUtil;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Fawe - Cavify")
@Description("Generate caves in a cuboidregion.")
@Since("1.5")
public class EffCave extends Effect {

    private Expression<CuboidRegion> cuboid;

    static {
        Skript.registerEffect(EffCave.class, "cav(e|ify) %fawe cuboidregions%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        cuboid = (Expression<CuboidRegion>) expressions[0];

        return true;
    }

    @Override
    protected void execute(Event e) {
        CuboidRegion cuboid = this.cuboid.getSingle(e);

        if (cuboid == null) {
            return;
        }

        EditSession session = FaweUtil.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        try {
            session.addCaves(cuboid);
        } catch (WorldEditException ex) {
            ex.printStackTrace();
        }
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "cavify " + cuboid.toString(e, debug);
    }
}
