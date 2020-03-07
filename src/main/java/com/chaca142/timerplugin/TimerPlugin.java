package com.chaca142.timerplugin;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class TimerPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("TimerPluginを読み込みました");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("TimerPluginを無効にしました");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("stop")) {
                getServer().getScheduler().cancelTasks(this);
                sender.sendMessage("§aタイマーを止めました");
                return true;
            }
            if(!StringUtils.isNumeric(args[0])){
                sender.sendMessage("<time>には数字を入れてください");
                return true;
            }
            final int time = Integer.valueOf(args[0]);
            if(time <= 0){
                sender.sendMessage("<time>には1以上の数字を入れてください");
                return true;
            }
            sender.sendMessage("§a" + time + "秒数えます");
            BukkitRunnable task = new BukkitRunnable() {
                int i = time;
                @Override
                public void run() {
                    if(i == 0){
                        sender.sendMessage("§aタイマーが終了しました");
                        cancel();
                        return;
                    }
                    sender.sendMessage("残り時間: " + i);
                    i--;
                }
            };
            task.runTaskTimer(this, 0L, 20L);
            return true;
        }
        return false;
    }
}
