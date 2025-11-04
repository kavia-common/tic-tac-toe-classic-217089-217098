package org.example.app.logic

import org.example.app.model.Board
import org.example.app.model.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class WinCheckerTest {

    @Test
    fun `no winner on empty board`() {
        val board = Board()
        assertNull(WinChecker.checkWinner(board))
        assertTrue(!WinChecker.isDraw(board))
    }

    @Test
    fun `row win detected`() {
        val board = Board()
        board.place(0, 0, Player.X)
        board.place(0, 1, Player.X)
        board.place(0, 2, Player.X)
        assertEquals(Player.X, WinChecker.checkWinner(board))
    }

    @Test
    fun `column win detected`() {
        val board = Board()
        board.place(0, 1, Player.O)
        board.place(1, 1, Player.O)
        board.place(2, 1, Player.O)
        assertEquals(Player.O, WinChecker.checkWinner(board))
    }

    @Test
    fun `diagonal win detected TL-BR`() {
        val board = Board()
        board.place(0, 0, Player.X)
        board.place(1, 1, Player.X)
        board.place(2, 2, Player.X)
        assertEquals(Player.X, WinChecker.checkWinner(board))
    }

    @Test
    fun `diagonal win detected TR-BL`() {
        val board = Board()
        board.place(0, 2, Player.O)
        board.place(1, 1, Player.O)
        board.place(2, 0, Player.O)
        assertEquals(Player.O, WinChecker.checkWinner(board))
    }

    @Test
    fun `draw is detected when full and no winner`() {
        val board = Board()
        // Fill with a known draw pattern:
        // X O X
        // X O O
        // O X X
        board.place(0, 0, Player.X)
        board.place(0, 1, Player.O)
        board.place(0, 2, Player.X)

        board.place(1, 0, Player.X)
        board.place(1, 1, Player.O)
        board.place(1, 2, Player.O)

        board.place(2, 0, Player.O)
        board.place(2, 1, Player.X)
        board.place(2, 2, Player.X)

        assertNull(WinChecker.checkWinner(board))
        assertTrue(WinChecker.isDraw(board))
    }
}
