package com.example.chessgui.Player;

import com.example.chessgui.board.Board;
import com.example.chessgui.board.Move;

public class MoveTransition {
    final Board transitionBoard;
    final Move move;
    final MoveStatus moveStatus;

    public MoveTransition(final Board transitionBoard,
                          final Move move,
                          final MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    public Board getBoardAfterTransition() {
        return this.transitionBoard;
    }
}
