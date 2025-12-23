package dev.lorenzz.queue.queue;

import dev.lorenzz.queue.bootstrap.QueueCore;
import dev.lorenzz.queue.util.C;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class QueueDispatcher {
    private static final int INTERVAL_TICKS = 40; // 2 secondi

    private final Plugin plugin;
    private final QueueManager queueManager;
    private BukkitTask task;

    public QueueDispatcher(Plugin plugin, QueueManager queueManager) {
        this.plugin = plugin;
        this.queueManager = queueManager;
    }

    public void start() {
        stop();
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::tick, INTERVAL_TICKS, INTERVAL_TICKS);
    }

    public void stop() {
        if (this.task != null) {
            this.task.cancel();
            this.task = null;
        }
    }

    private void tick() {
        queueManager.getQueues().forEach((server, deque) -> {
            if (queueManager.isPaused(server)) return;

            Optional<UUID> nextOpt = queueManager.peek(server);
            if (nextOpt.isEmpty()) return;

            UUID uuid = nextOpt.get();
            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !player.isOnline()) {
                queueManager.dequeue(server, uuid);
                return;
            }

            if (sendToServer(player, server)) {
                queueManager.dequeue(server, uuid);
                player.sendMessage(C.translate(QueueCore.get().getConfigFile().getString("QUEUE.YOU_WILL_BE_MOVED_SOON","&7You will be moved to &e{server} &7shortly. Please wait...").replace("{server}", server)));
            }
        });
    }

    private boolean sendToServer(Player player, String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
            return true;
        } catch (IOException e) {
            C.line();
            C.error("Errore nell'invio del player alla server " + server + ": " + e.getMessage());
            C.line();
            return false;
        }
    }
}
