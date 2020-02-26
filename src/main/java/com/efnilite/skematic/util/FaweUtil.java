package com.efnilite.skematic.util;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BaseBlock;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class FaweUtil {

    /**
     * Gets a WorldEdit world from the world's name
     *
     * @param   world
     *          The name of the world
     *
     * @return  the world
     */
    public static World getWorld(org.bukkit.World world) {
        if (world == null) {
            Skript.error("World is null - be sure to set the world of a location!");
            throw new IllegalStateException("World is null");
        }

        return FaweAPI.getWorld(world.getName());
    }

    /**
     * Gets a Fawe player from a Bukkit player
     *
     * @param   player
     *          The Bukkit player
     *
     * @return  a FawePlayer
     */
    public static com.sk89q.worldedit.entity.Player getPlayer(Player player) {
        return WorldEditPlugin.getInstance().getCachedPlayer(player);
    }

    /**
     * Gets the editsession of a player
     *
     * @param   player
     *          The player of the editsession
     *
     * @return  the editsession
     */
    public static EditSession getEditSession(Player player) {
        if (player == null) {
            Skript.error("Player is null");
            throw new IllegalStateException("World is null");
        }

        return WorldEditPlugin.getInstance().getSession(player).createEditSession(getPlayer(player));
    }

    /**
     * Gets the local session of a player
     *
     * @param   player
     *          The player of the local session
     *
     * @return  the local session
     */
    public static LocalSession getLocalSession(Player player) {
        if (player == null) {
            Skript.error("Player is null");
            throw new IllegalStateException("World is null");
        }

        return WorldEditPlugin.getInstance().getSession(player);
    }

    /**
     * Gets the editsession of a world
     *
     * @param   world
     *          The world the editsession is going to be in
     *
     * @return  the editsession
     */
    public static EditSession getEditSession(org.bukkit.World world) {
        return getEditSession(getWorld(world));
    }

    /**
     * Gets the editsession of a world
     *
     * @param   world
     *          The world the editsession is going to be in
     *
     * @return  the editsession
     */
    public static EditSession getEditSession(World world) {
        if (world == null) {
            Skript.error("World is null - be sure to set the world of a location!");
            throw new IllegalStateException("World is null");
        }

        return FaweAPI.getEditSessionBuilder(world).autoQueue(true).build();
    }

    /**
     * Turns a location into a vector
     *
     * @param   location
     *          The location
     *
     * @return  The vector of the location
     */
    public static BlockVector3 toVector(Location location) {
        return BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    /**
     * Parses a pattern
     *
     * @param   blocks
     *          The itemtypes that the pattern has to contain
     *
     * @return  The parsed pattern from param blocks
     */
    public static Pattern parsePattern(ItemType[] blocks) {
        RandomPattern parsedPattern = new RandomPattern();
        for (ItemType b : blocks) {
            if (b.getRandom().getType().isBlock()) {
                parsedPattern.add(new BlockPattern(new BaseBlock(b.getRandom().getType().getId(), b.getRandom().getDurability())), 50);
            }
        }
        return parsedPattern;
    }
}
