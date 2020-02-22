package com.efnilite.skematic.object;

import com.boydti.fawe.object.clipboard.ReadOnlyClipboard;
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
import java.util.Set;

/**
 * A wrapper class for schematics
 */
public class FaweSchematic {

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
        this.clipboard = FaweUtil.toSchematic(file).getClipboard();
    }

    /**
     * Creates a new instance
     *
     * @param   cuboid
     *          The cuboid
     */
    public FaweSchematic(CuboidRegion cuboid) {
        this.file = null;
        this.clipboard = new BlockArrayClipboard(cuboid, ReadOnlyClipboard.of(cuboid.getWorld(), cuboid));
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
        EditSession session = FaweUtil.getEditSession(Bukkit.getWorld(world.getName()));
        Operation operation = new ClipboardHolder(clipboard)
                .createPaste(session.getRegionExtent())
                .to(vector)
                .ignoreAirBlocks(!options.contains(FaweOptions.AIR))
                .ignoreEntities(!options.contains(FaweOptions.ENTITIES))
                .ignoreBiomes(!options.contains(FaweOptions.BIOMES))
                .build();
        Operations.completeLegacy(operation);

        session.flushQueue();
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
            FaweUtil.toSchematic(file).save(file, format);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
