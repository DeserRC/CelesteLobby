package com.celeste.celestelobby.listener;

import com.celeste.celestelobby.CelesteLobby;
import com.celeste.celestelobby.manager.BungeeCordManager;
import com.celeste.celestelobby.manager.ConfigManager;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCListener implements Listener {

    private final CelesteLobby plugin;

    public NPCListener(final CelesteLobby plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onClickNPC(final NPCRightClickEvent event) {
        final ConfigManager config = plugin.getConfigManager();
        final BungeeCordManager bungee = plugin.getBungeeCordManager();

        final Player player = event.getClicker();
        final int id = event.getNPC().getId();

        final String server = config.get("npc." + id + ".server");
        bungee.connect(server, player);
    }

}
