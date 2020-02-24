package com.efnilite.skematic.elements.effects;

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
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.event.Event;

@Name("Fawe - Regenerate")
@Description("Regenerate a cuboidregion.")
@Since("2.0")
public class EffRegenerate extends Effect {

    private Expression<CuboidRegion> cuboid;

    static {
        Skript.registerEffect(EffRegenerate.class, "regenerate [cuboid[(-| )region]] %fawe cuboidregions%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        cuboid = (Expression<CuboidRegion>) expressions[0];

        return true;
    }

    @Override
    protected void execute(Event e) {
        CuboidRegion cuboid = this.cuboid.getSingle(e);

        if (cuboid == null || cuboid.getWorld() == null) {
            return;
        }

        EditSession session = FaweUtil.getEditSession(cuboid.getWorld());
        session.regenerate(cuboid);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "regenerate " + cuboid.toString(e, debug);
    }
}
