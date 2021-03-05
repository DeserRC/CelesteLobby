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
        npc.data().setPersistent(NPC.PLAYER_SKIN_TEXTURE_PROPERTIES_METADATA, "ewogICJ0aW1lc3RhbXAiIDogMTYxNDkxOTUwNzAyMSwKICAicHJvZmlsZUlkIiA6ICI4MmM2MDZjNWM2NTI0Yjc5OGI5MWExMmQzYTYxNjk3NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJOb3ROb3RvcmlvdXNOZW1vIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzlkODJhZDFjZWUzY2MzYzVlN2I5MTExNmQxY2I2MWE5NmYwMGQxZDBhZTk1NmYyYzBhOWRlMDQ4MmU0OTVhZDMiCiAgICB9CiAgfQp9");
        npc.data().setPersistent(NPC.PLAYER_SKIN_TEXTURE_PROPERTIES_SIGN_METADATA, "t+6eam22ku0fuNeZ5Nw6Cu36Fg3xL3dCiD7X8oyOERTI5bGAN8qwzR8ti9Ij6okVQNDsKVYBj70cYJQf0SHIYAJmOXeAn4u31mdiqxVBXuzC2Kr8VyNxOu4kXKX0iAZNiZfMj6tNm6gb0KNaQvGDR4WonaPmE8MGEGMKm4y1W+mgPYt516X3nonaXMyg1sJDJKyW1c/XyZIMbCvOGr4OU8DuBW6FUGJp/l2hUoQriQK0DNAxErkovHDArFiM8QtzwihPxqsefcA3eDWNa62QSXGfEnuyEnaVi9Sihm6nQu5TUWKoh1PomL+mS9hXWRxvknf+OQR55yrIlL3qG3cRLYWjHZU3t7Q7EU56QR+WcEC6CnkNw7KPjY97YhqpHFzpZFHeZS5V2nxBj55YZ+zbtqO1FP7BEkPTUghX1K2w1szOs4dzoX7DiJmcOAAo8+58JKr3q+pdI4R2VPOpbeHYtwOk33Dezq+uVjVRQSSHpynUHfA9W5GvjykxTJ/kMzJhelCe/JdLJQkCHZxRNwsao5vA8mswTH0S0ppvF5JIzM7ES+noNCU69Cu7d0vwib9lXWQhbTY/FrBn0aArW9U3kAjgnyvOtnY610HBUnNj4ExnBkJ3r2pQko72j1oyepEmoqFoJanM2Xbk9a2x/XGEZKYSbPhs0WKVvnnLwwokqqQ=");
        npc.spawn(location);

        final String path = "npc." + npc.getId();
        config.put(path + ".server", serverName);
        config.put(path + ".location", serializeLocation);

        player.sendMessage("§c§lCELESTE §7NPC setado com sucesso.");
    }

}
