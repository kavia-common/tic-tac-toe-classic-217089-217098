package org.example.app

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

import org.example.app.logic.GameLogic
import org.example.app.model.GameState
import org.example.app.model.Player

class MainActivity : Activity() {

    private lateinit var tvTurn: TextView
    private lateinit var tvResult: TextView
    private lateinit var buttons: Array<Array<Button>>
    private lateinit var btnReset: Button

    private var state: GameState = GameLogic.newGame()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTurn = findViewById(R.id.tvTurn)
        tvResult = findViewById(R.id.tvResult)
        btnReset = findViewById(R.id.btnReset)

        buttons = arrayOf(
            arrayOf(findViewById(R.id.btnCell_0_0), findViewById(R.id.btnCell_0_1), findViewById(R.id.btnCell_0_2)),
            arrayOf(findViewById(R.id.btnCell_1_0), findViewById(R.id.btnCell_1_1), findViewById(R.id.btnCell_1_2)),
            arrayOf(findViewById(R.id.btnCell_2_0), findViewById(R.id.btnCell_2_1), findViewById(R.id.btnCell_2_2))
        )

        wireCellClicks()
        btnReset.setOnClickListener { resetGame() }

        render()
    }

    private fun wireCellClicks() {
        for (r in 0..2) {
            for (c in 0..2) {
                buttons[r][c].setOnClickListener {
                    onCellClicked(r, c)
                }
            }
        }
    }

    private fun onCellClicked(row: Int, col: Int) {
        if (state.winner != null || state.isDraw) return

        val player = state.currentPlayer
        val newState = GameLogic.makeMove(state, row, col, player)

        // If no change (illegal move), do nothing
        if (newState === state) return

        state = newState
        render()
    }

    private fun resetGame() {
        state = GameLogic.reset(state)
        // Clear button labels
        for (r in 0..2) {
            for (c in 0..2) {
                buttons[r][c].text = ""
                buttons[r][c].isEnabled = true
            }
        }
        render()
    }

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

        // Update turn/result texts
        if (state.winner != null) {
            tvResult.visibility = View.VISIBLE
            tvResult.text = if (state.winner == Player.X) getString(R.string.x_wins) else getString(R.string.o_wins)
            tvTurn.text = "" // Clear turn text on game end
        } else if (state.isDraw) {
            tvResult.visibility = View.VISIBLE
            tvResult.text = getString(R.string.draw)
            tvTurn.text = ""
        } else {
            tvResult.visibility = View.GONE
            tvResult.text = ""
            tvTurn.text = if (state.currentPlayer == Player.X) getString(R.string.turn_x) else getString(R.string.turn_o)
        }
    }
}
