package com.celeste.celestelobby.listener;

import com.celeste.celestelobby.CelesteLobby;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldListener implements Listener {

    public WorldListener(final CelesteLobby plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        Bukkit.getWorlds().forEach(world -> world.setGameRuleValue("doDaylightCycle", "false"));
    }

    public void onWeatherChange(final WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    public void onEntitySpawn(final EntitySpawnEvent event) {
        event.setCancelled(true);
    }

    public void onExplode(final EntityExplodeEvent event) {
        event.setCancelled(true);
    }
}
