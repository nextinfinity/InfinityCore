package net.nextinfinity.core.arena;

import net.nextinfinity.core.player.GamePlayer;

/**
 * The ScoreboardManager manges the Scoreboard and BossBar for an individual Arena.
 * This is the functional in-game UI, which displays player scores and time remaining.
 */
public interface ScoreboardManager {

    /**
     * Refresh the time on the scoreboard and bossbar to the set time.
     *
     * @param time the time remaining
     * @param initialTime the initial time, used for the bossbar countdown
     */
    void refreshTime(int time, int initialTime);

    /**
     * Sets the sidebar scoreboard for the player to the scoreboard managed by the ScoreboardManager.
     *
     * @param player the player to set the scoreboard for
     */
    void setBoard(GamePlayer player);

    /**
     * Resets the sidebar scoreboard for the player to a blank scoreboard.
     *
     * @param player the player to reset the scoreboard for
     */
    void clearBoard(GamePlayer player);

    /**
     * Refresh the scores in the scoreboard managed by this ScoreboardManager.
     */
    void refreshScores();
}
