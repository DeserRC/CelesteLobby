package com.celeste.celestelobby.command;

import com.celeste.celestelobby.CelesteLobby;
import com.celeste.celestelobby.manager.ConfigManager;
import com.celeste.celestelobby.util.LocationUtil;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class SetNPCCommand {

    private final CelesteLobby plugin;

    @Command(
      name = "setnpc",
      permission = "lobby.admin",
      target = CommandTarget.PLAYER,
      usage = "setnpc"
    )
    public void handleSetNPCCommand(final Context<Player> context, final String serverName) {
        final ConfigManager config = plugin.getConfigManager();

        final Player player = context.getSender();
        final Location location = player.getLocation();
        final String serializeLocation = LocationUtil.serialize(location, false);

        final NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, "§c§lSERVIDOR " + serverName);
        npc.data().setPersistent(NPC.PLAYER_SKIN_UUID_METADATA, "Bob");
        npc.data().setPersistent(NPC.PLAYER_SKIN_USE_LATEST, false);
        npc.spawn(location);

        final String path = "npc." + npc.getId();
        config.put(path + ".server", serverName);
        config.put(path + ".location", serializeLocation);

        player.sendMessage("§c§lCELESTE §7NPC setado com sucesso.");
    }

}
