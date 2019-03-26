package net.nextinfinity.core.team;

import net.nextinfinity.core.player.GamePlayer;
import org.bukkit.ChatColor;

import java.util.List;

/**
 * A Team stores players and is displayed on the scoreboard in an Arena.
 */
public interface Team {

    /**
     * Gets the name of the team.
     *
     * @return the name of the team
     */
    String getName();

    /**
     * Gets the prefix of the team. This is usually used to set the team's color.
     *
     * @return the prefix of the team
     */
    String getPrefix();

    /**
     * Sets the prefix of the team.
     *
     * @param prefix the new prefix of the team
     */
    void setPrefix(String prefix);

    /**
     * Gets the suffix of the team.
     *
     * @return the suffix of the team
     */
    String getSuffix();

    /**
     * Sets the suffix of the team.
     *
     * @param suffix the new suffix of the team
     */
    void setSuffix(String suffix);

    /**
     * Gets the color of the team.
     *
     * @return the color of the team
     */
    ChatColor getColor();

    /**
     * Sets the color of the team.
     *
     * @param color the new color of the team
     */
    void setColor(ChatColor color);

    /**
     * Gets the player limit for the team
     *
     * @return the amount the team can hold
     */
    int getAmount();

    /**
     * Sets the player limit for the team
     *
     * @param amount the new amount the team can hold
     */
    void setAmount(int amount);

    /**
     * Adds a player to the team.
     *
     * @param player the GamePlayer to add
     */
    void addPlayer(GamePlayer player);

    /**
     * Removes a player from the team.
     *
     * @param player the GamePlayer to remove
     */
    void removePlayer(GamePlayer player);

    /**
     * Gets the list of players associated with the team.
     *
     * @return the list of all players currently belonging to the team
     */
    List<GamePlayer> getPlayers();

    /**
     * Gets the score of the team.
     *
     * @return the score of the team
     */
    int getScore();

    /**
     * Sets the score of the team to a specific value.
     *
     * @param score the new score of the team
     */
    void setScore(int score);

    /**
     * Increases the score of the team by a certain amount.
     *
     * @param increment the amount to increase by
     */
    void incrementScore(int increment);

    /**
     * Decreases the score of the team by a certain amount.
     *
     * @param decrement the amount to decrease by
     */
    void decrementScore(int decrement);

}
