package chess.engine.pieces;

import chess.Color;
import java.util.HashMap;
import java.util.Map;

public enum PieceData {

    INSTANCE;

    private final Map<Color, Map<Integer, Queen>> ALL_POSSIBLE_QUEENS;
    private final Map<Color, Map<Integer, Pawn>> ALL_POSSIBLE_PAWNS;

    PieceData() {
        ALL_POSSIBLE_PAWNS = createAllPossibleMovedPawns();
        ALL_POSSIBLE_QUEENS = createAllPossibleMovedQueens();
    }

    Pawn getMovedPawn(final Color color, final int destinationCoordinate) {
        return ALL_POSSIBLE_PAWNS.get(color).get(destinationCoordinate);
    }

    Queen getMovedQueen(final Color color, final int destinationCoordinate) {
        return ALL_POSSIBLE_QUEENS.get(color).get(destinationCoordinate);
    }

    private Map<Color, Map<Integer, Pawn>> createAllPossibleMovedPawns() {
        final Map<Color, Map<Integer, Pawn>> pieces = new HashMap<>();
        for (final Color color : Color.values()) {
            Map<Integer, Pawn> innerMap = new HashMap<>();
            for (int i = 0; i < 64; i++) {
                innerMap.put(i, new Pawn(i, color, false));
            }
            pieces.put(color, innerMap);
        }
        return pieces;
    }

    private Map<Color, Map<Integer, Queen>> createAllPossibleMovedQueens() {
        final Map<Color, Map<Integer, Queen>> pieces = new HashMap<>();
        for (final Color color : Color.values()) {
            Map<Integer, Queen> innerMap = new HashMap<>();
            for (int i = 0; i < 64; i++) {
                innerMap.put(i, new Queen(i, color, false));
            }
            pieces.put(color, innerMap);
        }
        return pieces;
    }
}
