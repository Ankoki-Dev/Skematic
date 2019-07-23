package com.efnilite.skematic.elements.fawe.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.boydti.fawe.object.FawePlayer;
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
        FawePlayer fPlayer = FaweUtil.getPlayer(player);
        try {
            return fPlayer.getSession().getBrushTool(fPlayer.toWorldEditPlayer()).getSize();
        } catch (InvalidToolBindException e) {
            return null;
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
