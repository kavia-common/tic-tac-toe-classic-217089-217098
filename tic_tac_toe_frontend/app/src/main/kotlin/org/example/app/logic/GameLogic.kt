package org.example.app.logic

import org.example.app.model.Board
import org.example.app.model.GameState
import org.example.app.model.Player

/**
 * Pure Kotlin facade exposing simple game operations to the UI layer.
 *
 * This file intentionally contains no Android dependencies and can be unit tested.
 */
object GameLogic {

    // PUBLIC_INTERFACE
    /**
     * Attempts to make a move for [player] at [row], [col] on the given [state.board].
     *
     * - If the cell is occupied or the game is already finished (winner/draw), the original [state] is returned.
     * - Otherwise, the mark is placed and a new [GameState] is returned with:
     *   - [winner] computed
     *   - [isDraw] computed
     *   - [currentPlayer] toggled if the game continues
     */
    fun makeMove(state: GameState, row: Int, col: Int, player: Player): GameState {
        // If game already finished, ignore further moves.
        if (state.winner != null || state.isDraw) return state

        val placed = state.board.place(row, col, player)
        if (!placed) return state

        val winner = WinChecker.checkWinner(state.board)
        val draw = winner == null && state.board.isFull()

        return if (winner != null) {
            state.copy(winner = winner, isDraw = false)
        } else if (draw) {
            state.copy(winner = null, isDraw = true)
        } else {
            state.copy(currentPlayer = state.currentPlayer.next())
        }
    }

    // PUBLIC_INTERFACE
    /**
     * Resets the underlying board and returns a fresh [GameState] with X to start.
     */
    fun reset(state: GameState): GameState {
        state.board.clear()
        return GameState(board = state.board, currentPlayer = Player.X, winner = null, isDraw = false)
    }

    // PUBLIC_INTERFACE
    /**
     * Creates a brand-new [GameState] with a fresh board.
     */
    fun newGame(): GameState = GameState(board = Board(), currentPlayer = Player.X, winner = null, isDraw = false)
}
