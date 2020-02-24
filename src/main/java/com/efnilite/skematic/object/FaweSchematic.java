package com.efnilite.skematic.object;

import ch.njol.skript.Skript;
import com.boydti.fawe.object.clipboard.ReadOnlyClipboard;
import com.boydti.fawe.object.schematic.Schematic;
import com.efnilite.skematic.util.FaweUtil;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * A wrapper class for schematics
 */
public class FaweSchematic {

    /**
     * The loaded schematics
     */
    private static HashMap<String, FaweSchematic> schematics = new HashMap<>();

    /**
     * The last loaded schematic
     */
    private static FaweSchematic lastLoaded;

    /**
     * The file
     */
    private File file;

    /**
     * The clipboard
     */
    private Clipboard clipboard;

    /**
     * Creates a new instance
     *
     * @param   file
     *          The file
     */
    public FaweSchematic(File file) {
        this.file = file;
        Schematic schematic = FaweUtil.toSchematic(file);
        if (schematic == null) {
            Skript.error("Invalid file with new instance");
            return;
        }
        this.clipboard = schematic.getClipboard();
    }

    /**
     * Creates a new instance
     *
     * @param   cuboid
     *          The cuboid
     */
    public FaweSchematic(CuboidRegion cuboid) {
        this.file = null;
        this.clipboard = new BlockArrayClipboard(cuboid, (Clipboard) ReadOnlyClipboard.of(cuboid.getWorld(), cuboid));
    }

    /**
     * Pastes the schematic
     *
     * @param   world
     *          The world
     *
     * @param   vector
     *          The location
     *
     * @param   options
     *          The {@link FaweOptions} used for pasting
     */
    public void paste(World world, BlockVector3 vector, Set<FaweOptions> options) {
        org.bukkit.World w = Bukkit.getWorld(world.getName());
        if (w == null) {
            Skript.error("World is null (" + getClass().getName() + ") - be sure to set the world of a location!");
            return;
        }
        EditSession session = FaweUtil.getEditSession(w);
        Operation operation = new ClipboardHolder(clipboard)
                .createPaste(session.getRegionExtent())
                .to(vector)
                .ignoreAirBlocks(!options.contains(FaweOptions.AIR))
                .copyEntities(!options.contains(FaweOptions.ENTITIES))
                .build();
        Operations.completeLegacy(operation);

        session.flushQueue();
    }

    public static void add(String alias, FaweSchematic schematic) {
        schematics.put(alias, schematic);
        lastLoaded = schematic;
    }

    public static FaweSchematic get(String alias) {
        return schematics.get(alias);
    }

    public static void remove(String alias) {
        schematics.remove(alias);
    }

    /**
     * Saves the schematic
     *
     * @param   file
     *          The file to where it'll be saved
     *
     * @param   format
     *          The format used
     */
    public void save(File file, BuiltInClipboardFormat format) {
        try {
            Schematic schematic = FaweUtil.toSchematic(file);

            if (schematic == null) {
                Skript.error("Invalid file while trying to save schematic!");
                return;
            }

            schematic.save(file, format);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, FaweSchematic> getSchematics() {
        return schematics;
    }

    public static FaweSchematic getLastLoaded() {
        return lastLoaded;
    }

    /**
     * Gets the name
     *
     * @return the name
     */
    public String getName() {
        return file.toString();
    }

    /**
     * Gets the file
     *
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * Gets the clipboard
     *
     * @return the clipboard
     */
    public Clipboard getClipboard() {
        return clipboard;
    }
}
