package org.example.app.model

/**
 * Immutable snapshot of the current game state.
 *
 * - [board]: the game board instance (mutable on its own; callers control when to clear/place).
 * - [currentPlayer]: whose turn it is; defaults to X.
 * - [winner]: the winner if the game has been won; otherwise null.
 * - [isDraw]: true when the game ended without a winner and the board is full.
 *
 * This class intentionally contains no Android dependencies and is easy to unit test.
 */
data class GameState(
    // PUBLIC_INTERFACE
    /** The underlying 3x3 board for the game. */
    val board: Board,
    // PUBLIC_INTERFACE
    /** The player whose turn it is; defaults to X. */
    val currentPlayer: Player = Player.X,
    // PUBLIC_INTERFACE
    /** The winner of the game, or null if there is no winner yet. */
    val winner: Player? = null,
    // PUBLIC_INTERFACE
    /** Whether the game ended in a draw (no winner and board is full). */
    val isDraw: Boolean = false
)
