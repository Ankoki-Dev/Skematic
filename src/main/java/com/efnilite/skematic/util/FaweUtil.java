package com.efnilite.skematic.util;

import ch.njol.skript.aliases.ItemType;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.FawePlayer;
import com.boydti.fawe.object.schematic.Schematic;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BaseBlock;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

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
    public static World getWorld(String world) {
        return FaweAPI.getWorld(world);
    }

    /**
     * Gets a Fawe player from a Bukkit player
     *
     * @param   player
     *          The Bukkit player
     *
     * @return  a FawePlayer
     */
    public static FawePlayer getPlayer(Player player) {
        return FaweAPI.wrapPlayer(player);
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
        return FaweAPI.getEditSessionBuilder(getWorld(world.getName())).autoQueue(true).build();
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

    /**
     * Wraps a file to a FaweSchematic
     *
     * @param   file
     *          The file to be changed to a FaweSchematic
     *
     * @return  a FaweSchematic instance
     */
    public static Schematic toSchematic(File file) {
        try {
            Schematic schematic = FaweAPI.load(file);

            if (schematic.getClipboard() == null) {
                return null;
            }

            return new Schematic(schematic.getClipboard());
        } catch (IOException e) {
            return null;
        }
    }
}
