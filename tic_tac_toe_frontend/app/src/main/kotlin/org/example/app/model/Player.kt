package org.example.app.model

// PUBLIC_INTERFACE
enum class Player {
    /** Player X marker */
    X,

    /** Player O marker */
    O;

    // PUBLIC_INTERFACE
    /**
     * Returns the next player in turn order.
     *
     * This is a small helper to alternate players in a game loop.
     */
    fun next(): Player = if (this == X) O else X
}
