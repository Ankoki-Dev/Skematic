package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.efnilite.skematic.util.FaweUtil;
import com.sk89q.worldedit.command.tool.InvalidToolBindException;
import org.bukkit.entity.Player;

@Name("Fawe - Brush size")
@Description("Get the brush size of players.")
@Since("2.0")
public class ExprBrushSize extends SimplePropertyExpression<Player, Number> {

    static {
        register(ExprBrushSize.class, Number.class, "[fawe] brush size[s]", "players");
    }

    @Override
    public Number convert(Player player) {
        if (player == null) {
            Skript.error("Player is null");
            return 0;
        }
        try {
            return FaweUtil.getLocalSession(player).getBrushTool(FaweUtil.getPlayer(player)).getSize();
        } catch (InvalidToolBindException e) {
            Skript.error("Invalid tool bind (ExprBrushSize.class)");
            return 0;
        }
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    protected String getPropertyName() {
        return "brush size";
    }
}
