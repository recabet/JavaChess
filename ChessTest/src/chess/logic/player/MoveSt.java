package chess.logic.player;

/**
 * Enum representing the state of a move.
 */
public enum MoveSt {
    /**
     * The move has been executed successfully.
     */
    DONE {
        /**
         * Checks if the move is successfully executed.
         *
         * @return True if the move is executed successfully, false otherwise.
         */
        @Override
        public boolean isDone() {
            return true;
        }
    },

    /**
     * The move is illegal.
     */
    ILLEGAL_MOVE {
        /**
         * Checks if the move is illegal.
         *
         * @return False since an illegal move cannot be considered done.
         */
        @Override
        public boolean isDone() {
            return false;
        }
    },

    /**
     * The move puts the opponent in check.
     */
    IN_CHECK {
        /**
         * Checks if the opponent is in check after the move.
         *
         * @return False since being in check does not indicate the move is done.
         */
        @Override
        public boolean isDone() {
            return false;
        }
    };

    /**
     * Abstract method to determine if the move is done.
     *
     * @return True if the move is considered done, false otherwise.
     */
    public abstract boolean isDone();
}
