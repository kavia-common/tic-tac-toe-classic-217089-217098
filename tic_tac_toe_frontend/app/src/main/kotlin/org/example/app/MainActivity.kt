package org.example.app

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.example.app.logic.GameLogic
import org.example.app.model.GameState
import org.example.app.model.Player

/**
 * MainActivity wires the UI to the core Tic Tac Toe game logic.
 *
 * Responsibilities:
 * - Bind views and set up click listeners for the 3x3 grid and Reset button.
 * - Delegate moves to GameLogic ensuring only valid moves while game is ongoing.
 * - Render current player, board, and terminal states (win/draw).
 * - Persist and restore full game state across configuration changes (e.g., rotation).
 */
class MainActivity : Activity() {

    private lateinit var tvTurn: TextView
    private lateinit var tvResult: TextView
    private lateinit var buttons: Array<Array<Button>>
    private lateinit var btnReset: Button

    // Backing game state
    private var state: GameState = GameLogic.newGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind views
        tvTurn = findViewById(R.id.tvTurn)
        tvResult = findViewById(R.id.tvResult)
        btnReset = findViewById(R.id.btnReset)

        // Bind board buttons (IDs must match activity_main.xml)
        buttons = arrayOf(
            arrayOf(findViewById(R.id.btnCell_0_0), findViewById(R.id.btnCell_0_1), findViewById(R.id.btnCell_0_2)),
            arrayOf(findViewById(R.id.btnCell_1_0), findViewById(R.id.btnCell_1_1), findViewById(R.id.btnCell_1_2)),
            arrayOf(findViewById(R.id.btnCell_2_0), findViewById(R.id.btnCell_2_1), findViewById(R.id.btnCell_2_2))
        )

        // Wire interactions
        wireCellClicks()
        btnReset.setOnClickListener { resetGame() }

        // Restore state if available, then render
        restoreFromBundle(savedInstanceState)
        render()
    }

    /**
     * Restores game state from the savedInstanceState bundle (if provided).
     * We serialize a 3x3 board into a flat IntArray of length 9:
     * - 0 = empty, 1 = X, 2 = O
     * Also store current player, winner and draw flags.
     */
    private fun restoreFromBundle(bundle: Bundle?) {
        if (bundle == null) return

        val boardArray = bundle.getIntArray(KEY_BOARD) ?: return
        if (boardArray.size != 9) return

        // Reset board and fill from array
        state = GameLogic.reset(state)
        var idx = 0
        for (r in 0..2) {
            for (c in 0..2) {
                when (boardArray[idx++]) {
                    1 -> state.board.place(r, c, Player.X)
                    2 -> state.board.place(r, c, Player.O)
                }
            }
        }

        val currentPlayerFlag = bundle.getInt(KEY_CURRENT_PLAYER, 0)
        val current = if (currentPlayerFlag == 1) Player.O else Player.X

        val winnerVal = bundle.getInt(KEY_WINNER, 0)
        val winner: Player? = when (winnerVal) {
            1 -> Player.X
            2 -> Player.O
            else -> null
        }

        val isDraw = bundle.getBoolean(KEY_IS_DRAW, false)

        // Apply reconstructed state
        state = GameState(
            board = state.board,
            currentPlayer = current,
            winner = winner,
            isDraw = isDraw
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Serialize the 3x3 board and state metadata
        val boardArray = IntArray(9)
        var idx = 0
        for (r in 0..2) {
            for (c in 0..2) {
                boardArray[idx++] = when (state.board.get(r, c)) {
                    Player.X -> 1
                    Player.O -> 2
                    null -> 0
                }
            }
        }

        outState.putIntArray(KEY_BOARD, boardArray)
        // 0 -> X, 1 -> O
        outState.putInt(KEY_CURRENT_PLAYER, if (state.currentPlayer == Player.O) 1 else 0)
        val winnerVal = when (state.winner) {
            Player.X -> 1
            Player.O -> 2
            null -> 0
        }
        outState.putInt(KEY_WINNER, winnerVal)
        outState.putBoolean(KEY_IS_DRAW, state.isDraw)
    }

    private fun wireCellClicks() {
        for (r in 0..2) {
            for (c in 0..2) {
                buttons[r][c].setOnClickListener { onCellClicked(r, c) }
            }
        }
    }

    private fun onCellClicked(row: Int, col: Int) {
        // Only allow moves while game is ongoing and target cell is empty
        if (state.winner != null || state.isDraw) return
        if (state.board.get(row, col) != null) return

        val player = state.currentPlayer
        val newState = GameLogic.makeMove(state, row, col, player)

        // If no change (illegal move), do nothing
        if (newState === state) return

        state = newState
        render()
    }

    private fun resetGame() {
        state = GameLogic.reset(state)
        // Clear button labels and enable all
        for (r in 0..2) {
            for (c in 0..2) {
                buttons[r][c].text = ""
                buttons[r][c].isEnabled = true
            }
        }
        tvResult.visibility = View.GONE
        tvResult.text = ""
        render()
    }

    /**
     * Updates the UI to reflect the current state:
     * - Sets texts for each cell (X/O/empty)
     * - Enables/disables cells based on occupancy and terminal states
     * - Shows current turn or result message with colors
     */
    private fun render() {
        // Update board visuals
        for (r in 0..2) {
            for (c in 0..2) {
                val cell = state.board.get(r, c)
                val btn = buttons[r][c]
                btn.text = when (cell) {
                    Player.X -> "X"
                    Player.O -> "O"
                    null -> ""
                }
                // Disable button if occupied or game ended
                btn.isEnabled = (cell == null) && (state.winner == null && !state.isDraw)
            }
        }

        // Update turn/result texts and colors
        if (state.winner != null) {
            tvResult.visibility = View.VISIBLE
            tvResult.text = if (state.winner == Player.X) getString(R.string.x_wins) else getString(R.string.o_wins)
            tvResult.setTextColor(ContextCompat.getColor(this, R.color.colorSuccess))
            tvTurn.text = "" // Clear turn text on game end
            disableBoard()
        } else if (state.isDraw) {
            tvResult.visibility = View.VISIBLE
            tvResult.text = getString(R.string.draw)
            tvResult.setTextColor(ContextCompat.getColor(this, R.color.colorSecondary))
            tvTurn.text = ""
            disableBoard()
        } else {
            tvResult.visibility = View.GONE
            tvResult.text = ""
            tvTurn.text = if (state.currentPlayer == Player.X) getString(R.string.turn_x) else getString(R.string.turn_o)
            tvTurn.setTextColor(ContextCompat.getColor(this, R.color.colorOnSurface))
        }
    }

    private fun disableBoard() {
        for (r in 0..2) {
            for (c in 0..2) {
                buttons[r][c].isEnabled = false
            }
        }
    }

    companion object {
        private const val KEY_BOARD = "board_3x3"
        private const val KEY_CURRENT_PLAYER = "current_player"
        private const val KEY_WINNER = "winner"
        private const val KEY_IS_DRAW = "is_draw"
    }
}
