package com.celeste.celestelobby.listener;

import com.celeste.celestelobby.CelesteLobby;
import com.celeste.celestelobby.util.LocationUtil;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.lang.reflect.Constructor;

import static com.celeste.celestelobby.util.ReflectionUtil.getCon;
import static com.celeste.celestelobby.util.ReflectionUtil.getNMS;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.VOID;

public class PlayerListener implements Listener {

    private final CelesteLobby plugin;

    private final Object packet;

    @SneakyThrows
    public PlayerListener(final CelesteLobby plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        final Class<?> packetPlayInClient = getNMS("PacketPlayInClientCommand");
        final Class enumClient = getNMS("PacketPlayInClientCommand$EnumClientCommand");

        final Constructor<?> ppiccCon = getCon(packetPlayInClient, enumClient);

        final Enum<?> respawn = Enum.valueOf(enumClient, "PERFORM_RESPAWN");

        this.packet = ppiccCon.newInstance(respawn);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        event.setJoinMessage(null);

        final String spawnLocation = getSpawnLocation();
        if (spawnLocation == null) return;

        final Location location = LocationUtil.deserialize(spawnLocation);
        event.getPlayer().teleport(location);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onKick(final PlayerKickEvent event) {
        if (event.getReason().equals("disconnect.spam")) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!event.getCause().equals(VOID)) return;

        final String spawnLocation = getSpawnLocation();
        if (spawnLocation == null) return;
        event.setCancelled(true);

        final Location location = LocationUtil.deserialize(spawnLocation);
        event.getEntity().teleport(location);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(final PlayerDeathEvent event) {
        event.setDeathMessage(null);

        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            try {
                final Player player = event.getEntity();
                final Object handle = player.getClass().getMethod("getHandle").invoke(player);
                final Object connection = handle.getClass().getField("playerConnection").get(handle);
                connection.getClass().getMethod("a", packet.getClass()).invoke(connection, packet);
            } catch (ReflectiveOperationException | RuntimeException exception) {
                exception.printStackTrace();
            }
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRespawn(final PlayerRespawnEvent event) {
        final String spawnLocation = getSpawnLocation();
        if (spawnLocation == null) return;

        final Location location = LocationUtil.deserialize(spawnLocation);
        event.setRespawnLocation(location);
    }

    private String getSpawnLocation() {
        final String location = plugin.getConfigManager().get("spawn");
        return location.equals("") ? null : location;
    }

}
