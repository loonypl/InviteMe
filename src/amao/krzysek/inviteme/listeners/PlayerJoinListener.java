package amao.krzysek.inviteme.listeners;

import amao.krzysek.inviteme.InviteMe;
import amao.krzysek.inviteme.managers.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final User user = new User(e.getPlayer());
        try {
            if (!(user.exists())) user.create();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        final boolean bool = InviteMe.getYaml().getBoolean("messages.on-join.enable");
        if (bool) {
            final String message = InviteMe.getYaml().getString("messages.on-join.message");
            user.sendMessage(message);
        }
    }

}
