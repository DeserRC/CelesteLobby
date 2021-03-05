package com.celeste.celestelobby.listener;

import com.celeste.celestelobby.CelesteLobby;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldListener implements Listener {

    private final CelesteLobby plugin;

    public WorldListener(final CelesteLobby plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        Bukkit.getWorlds().forEach(world -> world.setGameRuleValue("doDaylightCycle", "false"));
    }

    public void onWeatherChange(final WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
