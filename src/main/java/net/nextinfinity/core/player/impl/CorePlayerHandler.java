package net.nextinfinity.core.player.impl;

import net.nextinfinity.core.Game;
import net.nextinfinity.core.player.GamePlayer;
import net.nextinfinity.core.player.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class CorePlayerHandler implements PlayerHandler {

    private final Map<UUID, GamePlayer> players = new HashMap<>();
    private final Game game;
    private Class<? extends GamePlayer> playerClass = CoreGamePlayer.class;

    public CorePlayerHandler(Game game) {
        this.game = game;
    }

    @Override
    public GamePlayer loadPlayer(Player bukkitPlayer) {
        GamePlayer player = null;
        try {
            player = this.playerClass.getConstructor(Player.class, Game.class).newInstance(bukkitPlayer, game);
        } catch (Exception exception) {
            Bukkit.getLogger().log(Level.SEVERE, "Player class should use same constructor as CoreGamePlayer!");
        }
        players.put(bukkitPlayer.getUniqueId(), player);
        return player;
    }

    @Override
    public void unloadPlayer(Player bukkitPlayer) {
        GamePlayer player = getPlayer(bukkitPlayer);
        if (player.isInGame()) {
            if (player.isPlaying()) {
                player.getArena().removePlayer(player);
            } else {
                player.getArena().removeSpectator(player);
            }
        }
        players.remove(bukkitPlayer.getUniqueId());
    }

    @Override
    public GamePlayer getPlayer(Player bukkitPlayer) {
        return players.get(bukkitPlayer.getUniqueId());
    }

    @Override
    public void setPlayerClass(Class<? extends GamePlayer> playerClass) {
        this.playerClass = playerClass;
    }

}
