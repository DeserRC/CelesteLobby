package com.celeste.celestelobby.command;

import com.celeste.celestelobby.CelesteLobby;
import com.celeste.celestelobby.manager.ConfigManager;
import com.celeste.celestelobby.util.LocationUtil;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import me.saiintbrisson.minecraft.command.target.CommandTarget;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class SetSpawnCommand {

    private final CelesteLobby plugin;

    @Command(
      name = "setspawn",
      permission = "lobby.admin",
      target = CommandTarget.PLAYER,
      usage = "setspawn"
    )
    public void handleSetSpawnCommand(final Context<Player> context) {
        final ConfigManager config = plugin.getConfigManager();

        final Player player = context.getSender();
        final Location location = player.getLocation();

        final String serializeLocation = LocationUtil.serialize(location, true);
        config.put("spawn", serializeLocation);

        player.sendMessage("§c§lCELESTE §7Localização do spawn alterada com sucesso.");
    }

}
