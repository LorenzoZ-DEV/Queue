/*
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⢔⣶⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡜⠀⠀⡼⠗⡿⣾⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢄⣀⠀⠀⡇⢀⡼⠓⡞⢩⣯⡀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣀⠀⠀⠀⠀⠉⠳⢜⠰⡹⠁⢰⠃⣩⣿⡇⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⢷⣿⠿⣉⣩⠛⠲⢶⡠⢄⢙⣣⠃⣰⠗⠋⢀⣯⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⣯⣠⠬⠦⢤⣀⠈⠓⢽⣿⢔⣡⡴⠞⠻⠙⢳⡄           C O D E D  B Y  
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣵⣳⠖⠉⠉⢉⣩⣵⣿⣿⣒⢤⣴⠤⠽⣬⡇
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⢻⣟⠟⠋⢡⡎⢿⢿⠳⡕⢤⡉⡷⡽⠁        V A N I X Y   A K A   L O R E N Z Z
⣧⢮⢭⠛⢲⣦⣀⠀⠀⠀⠀⡀⠀⠀⠀⡾⣥⣏⣖⡟⠸⢺⠀⠀⠈⠙⠋⠁⠀⠀
⠈⠻⣶⡛⠲⣄⠀⠙⠢⣀⠀⢇⠀⠀⠀⠘⠿⣯⣮⢦⠶⠃⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⢻⣿⣥⡬⠽⠶⠤⣌⣣⣼⡔⠊⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⢠⣿⣧⣤⡴⢤⡴⣶⣿⣟⢯⡙⠒⠤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠘⣗⣞⣢⡟⢋⢜⣿⠛⡿⡄⢻⡮⣄⠈⠳⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠈⠻⠮⠴⠵⢋⣇⡇⣷⢳⡀⢱⡈⢋⠛⣄⣹⣲⡀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⣱⡇⣦⢾⣾⠿⠟⠿⠷⠷⣻⠧⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠻⠽⠞⠊⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
*/

package dev.lorenzz.queue.cmds.user;

import dev.lorenzz.queue.managment.ManagerService;
import dev.lorenzz.queue.queue.QueueManager;
import dev.lorenzz.queue.util.C;
import dev.lorenzz.queue.bootstrap.QueueCore;
import me.empire.commandapi.annotations.Command;
import me.empire.commandapi.annotations.Permission;
import me.empire.commandapi.annotations.Sender;
import org.bukkit.entity.Player;

public class QueueCmd
{
    @Command(name = "queue", aliases = {"q"}, usage = "&7Usa &d/joinqueue <server> &7o &d/leavequeue")
    @Permission("queue.cmd")
    public void root(@Sender Player sender) {
        sender.sendMessage(C.translate("""
                &7This server is using &aQueue &7by &fLorenzzzzz
                &r
                &7Use &a/joinqueue <server> &7to join a queue.
                &7Use &a/leavequeue &7to leave your current queue.
                """));
    }

    @Command(name="joinqueue", aliases = {"jq"}, usage = "&7Usage: &a/joinqueue <server>")
    public void joinqueue(@Sender Player player, String server) {
        QueueManager qm = ManagerService.get().getQueueManager();
        if (qm.isPaused(server)) {
            String msg = QueueCore.get().getConfigFile().getString("QUEUE.QUEUE_PAUSED", "&cThe queue for &e{server} &cis currently paused. Please try again later.");
            msg = msg.replace("{server}", server);
            player.sendMessage(C.translate(msg));
            return;
        }
        if (qm.isQueued(player.getUniqueId())) {
            String msg = QueueCore.get().getConfigFile().getString("QUEUE.ALREADY_IN_QUEUE", "&cYou are already in the queue for &e{server}&c.");
            player.sendMessage(C.translate(msg.replace("{server}", server)));
            return;
        }
        boolean added = qm.enqueue(server, player.getUniqueId());
        if (added) {
            int pos = qm.position(server, player.getUniqueId());
            String msg = QueueCore.get().getConfigFile().getString("QUEUE.JOINED_QUEUE", "&aYou have joined the queue for &e{server} &7(Position: &e{position}&7).");
            msg = msg.replace("{server}", server).replace("{position}", String.valueOf(pos));
            player.sendMessage(C.translate(msg));
        } else {
            player.sendMessage(C.translate(QueueCore.get().getConfigFile().getString("QUEUE.ERROR_OCCURRED", "&cAn error occurred while processing your request. Please try again later.")));
        }
    }

    @Command(name="leavequeue", aliases = {"lq"}, usage = "&7Usage: &a/leavequeue")
    public void leavequeue(@Sender Player player) {
        QueueManager qm = ManagerService.get().getQueueManager();
        var queued = qm.queueOf(player.getUniqueId());
        if (queued.isEmpty()) {
            String msg = QueueCore.get().getConfigFile().getString("QUEUE.NOT_IN_QUEUE", "&cYou are not in the queue for &e{server}&c.");
            player.sendMessage(C.translate(msg));
            return;
        }
        String server = queued.get();
        boolean removed = qm.dequeue(server, player.getUniqueId());
        if (removed) {
            String msg = QueueCore.get().getConfigFile().getString("QUEUE.LEFT_QUEUE", "&cYou are already in the queue for &e{server}&c.");
            player.sendMessage(C.translate(msg.replace("{server}", server)));
        } else {
            player.sendMessage(C.translate(QueueCore.get().getConfigFile().getString("QUEUE.ERROR_OCCURRED", "&cAn error occurred while processing your request. Please try again later.")));
        }
    }
}
