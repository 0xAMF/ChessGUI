package com.example.engine.Player;

import com.example.engine.board.Board;
import com.example.engine.board.Move;

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
