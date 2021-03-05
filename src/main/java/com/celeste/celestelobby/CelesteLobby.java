package com.celeste.celestelobby;

import com.celeste.celestelobby.command.SetLocationCommand;
import com.celeste.celestelobby.manager.ConfigManager;
import lombok.Getter;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import me.saiintbrisson.minecraft.command.message.MessageHolder;
import me.saiintbrisson.minecraft.command.message.MessageType;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class CelesteLobby extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onLoad() {
        this.configManager = new ConfigManager(this);
    }

    @Override
    public void onEnable() {
        final BukkitFrame frame = new BukkitFrame(this);
        final MessageHolder holder = frame.getMessageHolder();

        holder.setMessage(MessageType.ERROR, "§c§lCELESTE §7Um erro ocorreu");
        holder.setMessage(MessageType.INCORRECT_TARGET, "§c§lCELESTE §7Apenas jogadores podem executar esse comando.");
        holder.setMessage(MessageType.INCORRECT_USAGE, "§c§lCELESTE §7Erro! Utilize: /{usage}");
        holder.setMessage(MessageType.NO_PERMISSION, "§c§lCELESTE §7Você não tem permissão para isso.");

        frame.registerCommands(new SetLocationCommand(this));
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();
    }
}
