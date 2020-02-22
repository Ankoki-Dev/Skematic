package com.efnilite.skematic;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.efnilite.skematic.util.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Skematic extends JavaPlugin {

    private static Plugin instance;
    private static Metrics metrics;
    private static SkriptAddon addon;

    private static boolean fawe;

    @Override
    public void onEnable() {
        fawe = this.getServer().getPluginManager().getPlugin("FastAsyncWorldEdit") != null;

        if (!fawe) {
            this.getLogger().severe("You need FastAsyncWorldEdit for Skematic to work! Disabling..");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        instance = this;
        addon = Skript.registerAddon(this).setLanguageFileDirectory("lang");
        if (fawe) {
            new FaweTypes();
            try {
                addon.loadClasses("com.efnilite.skematic.elements.fawe", "elements");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.getLogger().info("Enabling Metrics..");
        metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("skript_version", () -> Bukkit.getPluginManager().getPlugin("Skript").getDescription().getVersion()));
        if (fawe) {
            metrics.addCustomChart(new Metrics.SimplePie("fawe_version", () -> Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit").getDescription().getVersion()));
        }
    }

    public static void error(String error) {
        instance.getLogger().severe(error);
    }

    public static boolean hasFawe() {
        return fawe;
    }

    public static Plugin getInstance() {
        return instance;
    }
}