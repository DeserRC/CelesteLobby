package com.celeste.celestelobby.listener;

import com.celeste.celestelobby.CelesteLobby;
import com.celeste.celestelobby.manager.ConfigManager;
import lombok.SneakyThrows;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class NPCListener implements Listener {

    private final CelesteLobby plugin;

    public NPCListener(final CelesteLobby plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onClickNPC(final NPCRightClickEvent event) {
        final ConfigManager config = plugin.getConfigManager();

        final Player player = event.getClicker();
        final int id = event.getNPC().getId();

        final String server = config.get("npc." + id + ".server");
        connect(server, player);
    }

    @SneakyThrows
    private void connect(final String server, final Player player) {
        try (
          final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
          final DataOutputStream out = new DataOutputStream(byteOut)
        ) {
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(plugin, "BungeeCord", byteOut.toByteArray());
        }
    }

}
