package io.github.Leonardo0013YT.UltraSkyWars;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.schematic.Schematic;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.world.World;
import io.github.Leonardo0013YT.UltraSkyWars.calls.CallBackAPI;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.WorldEdit;
import io.github.Leonardo0013YT.UltraSkyWars.objects.GlassBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class WorldEditUtils_Old implements WorldEdit {

    private final HashMap<String, Schematic> cache = new HashMap<>();
    private final String path;
    private final UltraSkyWars plugin;

    public WorldEditUtils_Old(UltraSkyWars plugin) {
        this.plugin = plugin;
        this.path = Bukkit.getWorldContainer() + "/plugins/WorldEdit/schematics";
    }

    private Schematic loadSchematic(String schematicName) {
        if (!cache.containsKey(schematicName)) {
            File file = new File(path, schematicName + ".schematic");
            ClipboardFormat cf = ClipboardFormat.findByFile(file);
            if (cf != null) {
                try {
                    Schematic schematic = cf.load(file);
                    cache.put(schematicName, schematic);
                    return schematic;
                } catch (IOException e) {
                    plugin.getLogger().warning("Failed to load schematic: " + schematicName + " - " + e.getMessage());
                }
            }
        }
        return cache.get(schematicName);
    }

    @Override
    public void paste(Location loc, String schematic, boolean air, CallBackAPI<Boolean> done) {
        String schematicName = schematic.replaceAll(".schematic", "");
        Vector bukkitVector = new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        com.sk89q.worldedit.world.World world = FaweAPI.getWorld(loc.getWorld().getName());

        Schematic sh = loadSchematic(schematicName);
        if (sh != null) {
            com.sk89q.worldedit.Vector weVector = new com.sk89q.worldedit.Vector(bukkitVector.getX(), bukkitVector.getY(), bukkitVector.getZ());
            EditSession editSession = sh.paste(world, weVector);
            editSession.flushQueue();
            done.done(true);
        } else {
            done.done(false);
        }
    }

    @Override
    public HashMap<Vector, GlassBlock> getBlocks(String schematic) {
        HashMap<Vector, GlassBlock> blocks = new HashMap<>();
        String schematicName = schematic.replaceAll(".schematic", "");

        Schematic sh = loadSchematic(schematicName);
        if (sh != null) {
            Clipboard clipboard = sh.getClipboard();
            for (int x2 = clipboard.getMinimumPoint().getBlockX(); x2 <= clipboard.getMaximumPoint().getBlockX(); x2++) {
                for (int y2 = clipboard.getMinimumPoint().getBlockY(); y2 <= clipboard.getMaximumPoint().getBlockY(); y2++) {
                    for (int z2 = clipboard.getMinimumPoint().getBlockZ(); z2 <= clipboard.getMaximumPoint().getBlockZ(); z2++) {
                        BaseBlock block = clipboard.getBlock(new com.sk89q.worldedit.Vector(x2, y2, z2));
                        if (!block.isAir()) {
                            int x = x2 - clipboard.getMinimumPoint().getBlockX();
                            int y = y2 - clipboard.getMinimumPoint().getBlockY();
                            int z = z2 - clipboard.getMinimumPoint().getBlockZ();
                            blocks.put(new Vector(x, y, z), new GlassBlock());
                        }
                    }
                }
            }
        }
        return blocks;
    }
}