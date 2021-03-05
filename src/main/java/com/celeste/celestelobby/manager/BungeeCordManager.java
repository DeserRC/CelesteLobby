package com.celeste.celestelobby.manager;

import com.celeste.celestelobby.CelesteLobby;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class BungeeCordManager implements AutoCloseable {

    private final CelesteLobby plugin;
    private final Messenger messenger;

    public BungeeCordManager(final CelesteLobby plugin) {
        this.plugin = plugin;
        this.messenger = plugin.getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(plugin, "BungeeCord");
    }

    @Override
    public void close() {
        messenger.unregisterOutgoingPluginChannel(plugin, "BungeeCord");
    }

    @SneakyThrows
    public void connect(final String server, final Player player) {
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
