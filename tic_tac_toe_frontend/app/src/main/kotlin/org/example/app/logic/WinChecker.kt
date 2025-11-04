package org.example.app.logic

import org.example.app.model.Board
import org.example.app.model.Player

/**
 * Pure Kotlin utility for evaluating Tic Tac Toe game outcomes.
 *
 * This utility has no Android dependencies and operates solely on the domain models.
 */
object WinChecker {

    // PUBLIC_INTERFACE
    /**
     * Determines the winner on the provided [board], if any.
     *
     * Checks all rows, columns, and the two diagonals for three-in-a-row.
     *
     * @param board The game board to evaluate.
     * @return The [Player] who has won, or null if there is currently no winner.
     */
    fun checkWinner(board: Board): Player? {
        // Check rows
        for (r in 0..2) {
            val p0 = board.get(r, 0)
            val p1 = board.get(r, 1)
            val p2 = board.get(r, 2)
            val winner = threeEqual(p0, p1, p2)
            if (winner != null) return winner
        }

        // Check columns
        for (c in 0..2) {
            val p0 = board.get(0, c)
            val p1 = board.get(1, c)
            val p2 = board.get(2, c)
            val winner = threeEqual(p0, p1, p2)
            if (winner != null) return winner
        }

        // Check diagonals
        val center = board.get(1, 1)
        // Top-left to bottom-right
        threeEqual(board.get(0, 0), center, board.get(2, 2))?.let { return it }
        // Top-right to bottom-left
        threeEqual(board.get(0, 2), center, board.get(2, 0))?.let { return it }

        return null
    }

    // PUBLIC_INTERFACE
    /**
     * Determines whether the game is a draw.
     *
     * A draw occurs when the [board] is full and there is no winner.
     *
     * @param board The game board to evaluate.
     * @return true if all cells are filled and there is no winner; false otherwise.
     */
    fun isDraw(board: Board): Boolean {
        return checkWinner(board) == null && board.isFull()
    }

    /**
     * Helper to check if three cells contain the same non-null [Player].
     *
     * @return that player if all equal and non-null, otherwise null.
     */
    private fun threeEqual(a: Player?, b: Player?, c: Player?): Player? {
        if (a == null || b == null || c == null) return null
        return if (a == b && b == c) a else null
    }
}
