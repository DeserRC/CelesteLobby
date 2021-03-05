package com.celeste.celestelobby;

import com.celeste.celestelobby.manager.ConfigManager;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class CelesteLobby extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onLoad() {
        this.configManager = new ConfigManager(this);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }
}
