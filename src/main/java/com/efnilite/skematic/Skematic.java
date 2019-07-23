package com.efnilite.skematic;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.efnilite.skematic.elements.fawe.FaweTypes;
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
    private static boolean redaktor;

    @Override
    public void onEnable() {
        fawe = this.getServer().getPluginManager().getPlugin("FastAsyncWorldEdit") != null;
        redaktor = this.getServer().getPluginManager().getPlugin("Redaktor") != null;

        if (!fawe && !redaktor) {
            this.getLogger().severe("You need FastAsyncWorldEdit or Redaktor for Skematic to work! Disabling..");
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
        if (redaktor) {
            try {
                addon.loadClasses("com.efnilite.skematic.redaktor", "elements");
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
        if (redaktor) {
            metrics.addCustomChart(new Metrics.SimplePie("redaktor_version", () -> Bukkit.getPluginManager().getPlugin("Redaktor").getDescription().getVersion()));
        }
    }

    public static boolean hasFawe() {
        return fawe;
    }

    public static boolean hasRedaktor() {
        return redaktor;
    }

    public static Plugin getInstance() {
        return instance;
    }
}