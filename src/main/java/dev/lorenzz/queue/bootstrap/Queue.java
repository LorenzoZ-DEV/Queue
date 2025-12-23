package dev.lorenzz.queue.bootstrap;

import org.bukkit.plugin.java.JavaPlugin;

public final class Queue extends JavaPlugin {

    private QueueCore provider;
    @Override
    public void onEnable() {
        this.provider = new QueueCore(this);
        this.provider.start();
    }

    @Override
    public void onDisable() {
        if (this.provider != null) {
            this.provider.stop();
        }
    }
}
