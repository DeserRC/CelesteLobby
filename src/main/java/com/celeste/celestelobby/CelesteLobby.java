package com.celeste.celestelobby;

import com.celeste.celestelobby.command.SetSpawnCommand;
import com.celeste.celestelobby.command.SetNPCCommand;
import com.celeste.celestelobby.listener.NPCListener;
import com.celeste.celestelobby.listener.PlayerListener;
import com.celeste.celestelobby.listener.WorldListener;
import com.celeste.celestelobby.manager.BungeeCordManager;
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
    private BungeeCordManager bungeeCordManager;

    @Override
    public void onLoad() {
        this.configManager = new ConfigManager(this);
        this.bungeeCordManager = new BungeeCordManager(this);
    }

    @Override
    public void onEnable() {
        registerListener();
        registerCommands();
    }

    @Override
    public void onDisable() {
        bungeeCordManager.close();
        HandlerList.unregisterAll();
    }

    private void registerCommands() {
        final BukkitFrame frame = new BukkitFrame(this);
        final MessageHolder holder = frame.getMessageHolder();

        holder.setMessage(MessageType.ERROR, "§c§lCELESTE §7Um erro ocorreu");
        holder.setMessage(MessageType.INCORRECT_TARGET, "§c§lCELESTE §7Apenas jogadores podem executar esse comando.");
        holder.setMessage(MessageType.INCORRECT_USAGE, "§c§lCELESTE §7Erro! Utilize: /{usage}");
        holder.setMessage(MessageType.NO_PERMISSION, "§c§lCELESTE §7Você não tem permissão para isso.");

        frame.registerCommands(
          new SetSpawnCommand(this),
          new SetNPCCommand(this)
        );
    }

    private void registerListener() {
        new PlayerListener(this);
        new NPCListener(this);
        new WorldListener(this);
    }

}
