package org.example.app.model

/**
 * A simple 3x3 Tic Tac Toe board.
 *
 * Pure Kotlin, no Android dependencies.
 * Internally stores nullable Player values for each cell.
 */
class Board {

    /**
     * Internal 3x3 grid. Each cell can be null (empty) or hold a Player.
     */
    private val cells: Array<Array<Player?>> = Array(3) { arrayOfNulls<Player>(3) }

    // PUBLIC_INTERFACE
    /**
     * Places the given player's mark at the specified [row], [col] (0-based).
     *
     * - Returns true when the cell was empty and the mark was placed.
     * - Returns false if the provided coordinates are out of bounds or the cell is already occupied.
     */
    fun place(row: Int, col: Int, player: Player): Boolean {
        if (!isInBounds(row, col)) return false
        if (cells[row][col] != null) return false
        cells[row][col] = player
        return true
    }

    // PUBLIC_INTERFACE
    /**
     * Gets the cell value at [row], [col] (0-based).
     *
     * - Returns the Player at the cell, or null if empty or out of bounds.
     */
    fun get(row: Int, col: Int): Player? {
        if (!isInBounds(row, col)) return null
        return cells[row][col]
    }

    // PUBLIC_INTERFACE
    /**
     * Clears all cells to empty (null).
     */
    fun clear() {
        for (r in 0..2) {
            for (c in 0..2) {
                cells[r][c] = null
            }
        }
    }

    // PUBLIC_INTERFACE
    /**
     * Returns true if every cell is filled with a Player (no nulls).
     */
    fun isFull(): Boolean {
        for (r in 0..2) {
            for (c in 0..2) {
                if (cells[r][c] == null) return false
            }
        }
        return true
    }

    /**
     * Utility to assert indices are within the 3x3 bounds.
     */
    private fun isInBounds(row: Int, col: Int): Boolean =
        row in 0..2 && col in 0..2
}
