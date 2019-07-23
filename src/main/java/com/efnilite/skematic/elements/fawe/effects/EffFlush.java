package com.efnilite.skematic.elements.fawe.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.util.FaweUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Fawe - Flush Queue")
@Description("Flush the queue of a player - clear it.")
@Examples("flush player's queue")
@Since("1.0")
public class EffFlush extends Effect {

    private Expression<Player> player;

    static {
        Skript.registerEffect(EffFlush.class, "(flush|dequeue) [the] (fawe queue of %player%|%player%'s fawe queue)");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        player = (Expression<Player>) expressions[0];

        return true;
    }

    @Override
    protected void execute(Event e) {
        Player player = this.player.getSingle(e);

        if (player == null) {
            return;
        }

        FaweUtil.getPlayer(player).getNewEditSession().flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "flush queue of " + player.toString(e, debug);
    }
}
