package net.nextinfinity.core.team;

import net.nextinfinity.core.arena.Arena;
import net.nextinfinity.core.player.GamePlayer;

import java.util.List;

/**
 * The TeamHandler manages Team objects and handles their creation and removal for individual Arenas as well as
 * assignment of GamePlayers to Teams once an Arena starts.
 *
 * @see Team
 */
public interface TeamHandler {

    /**
     * Loads teams from the teams.yml file.
     */
    void loadTeams();

    /**
     * Creates Team objects for the Arena.
     *
     * @param arena the Arena to create Teams for
     */
    void createTeams(Arena arena);

    /**
     * Gets the Teams for an Arena.
     *
     * @param arena the Arena to get Teams for
     * @return the Teams associated with the Arena
     */
    List<Team> getTeams(Arena arena);

    /**
     * Removes all Teams for an Arena.
     *
     * @param arena the Arena to remove Teams for
     */
    void removeArena(Arena arena);

    /**
     * Assigns players for an Arena. This will balance Team sizes and fill smaller Teams first, if possible.
     *
     * @param arena the Arena to assign players for
     */
    void assignPlayers(Arena arena);

    /**
     * Gets the winning Team for an Arena. This is the Team with the highest score. If multiple Team objects have
     * the same highest score, a random one will be returned.
     *
     * @param arena the Arena to get the winner of
     * @return the winning Team in the Arena
     */
    Team getWinner(Arena arena);
}
