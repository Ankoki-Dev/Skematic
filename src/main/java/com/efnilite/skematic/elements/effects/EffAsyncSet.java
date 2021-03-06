package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.util.FaweUtil;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.Event;

@Name("Fawe - Async Set")
@Description("Set a block at a location using FAWE's API.")
@Since("2.3")
public class EffAsyncSet extends Effect {

    private Expression<?> location;
    private Expression<ItemType> blocks;

    static {
        Skript.registerEffect(EffAsyncSet.class, "set [the] fawe block[s] at %locations/blocks% to %itemtype%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        location = expressions[0];
        blocks = (Expression<ItemType>) expressions[1];

        return true;
    }

    @Override
    protected void execute(Event e) {
        Location location;
        ItemType[] blocks = this.blocks.getArray(e);

        if (this.location.getSingle(e) == null || blocks == null) {
            return;
        }

        if (this.location.getSingle(e) instanceof Block) {
            location = ((Block) this.location.getSingle(e)).getLocation();
        } else if (this.location.getSingle(e) instanceof Location) {
            location = (Location) this.location.getSingle(e);
        } else {
            return;
        }

        EditSession session = FaweUtil.getEditSession(location.getWorld());
        session.setBlock(FaweUtil.toVector(location), FaweUtil.parsePattern(blocks));
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "set " + location.toString(e, debug) + " to " + blocks.toString(e, debug);
    }
}
