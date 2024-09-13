package chess.logic.pieces;

import chess.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum singleton class responsible for managing all possible moved pawns and queens.
 */
public enum PieceData {

    INSTANCE; // Singleton instance

    private final Map<Color, Map<Integer, Queen>> ALL_POSSIBLE_QUEENS; // Map of all possible moved queens
    private final Map<Color, Map<Integer, Pawn>> ALL_POSSIBLE_PAWNS; // Map of all possible moved pawns

    /**
     * Constructor for PieceData enum. Initializes the maps of all possible moved pawns and queens.
     */
    PieceData()
    {
        ALL_POSSIBLE_PAWNS = createAllPossibleMovedPawns();
        ALL_POSSIBLE_QUEENS = createAllPossibleMovedQueens();
    }

    /**
     * Retrieves the moved pawn of the specified color and destination coordinate.
     *
     * @param color                 The color of the pawn.
     * @param destinationCoordinate The destination coordinate of the pawn.
     * @return The moved pawn.
     */
    Pawn getMovedPawn(final Color color, final int destinationCoordinate)
    {
        return ALL_POSSIBLE_PAWNS.get(color).get((Object) destinationCoordinate);
    }

    /**
     * Retrieves the moved queen of the specified color and destination coordinate.
     *
     * @param color                 The color of the queen.
     * @param destinationCoordinate The destination coordinate of the queen.
     * @return The moved queen.
     */
    Queen getMovedQueen(final Color color, final int destinationCoordinate)
    {
        return ALL_POSSIBLE_QUEENS.get(color).get((Object) (destinationCoordinate));
    }

    /**
     * Creates a map containing all possible moved pawns for each color.
     *
     * @return Map of all possible moved pawns.
     */
    private Map<Color, Map<Integer, Pawn>> createAllPossibleMovedPawns()
    {
        final Map<Color, Map<Integer, Pawn>> pieces = new HashMap<>();
        for(final Color color : Color.values())
        {
            Map<Integer, Pawn> innerMap = new HashMap<>();
            for(int i = 0; i < 64; i++)
            {
                innerMap.put((Integer) i, new Pawn(i, color, false));
            }
            pieces.put(color, innerMap);
        }
        return pieces;
    }

    /**
     * Creates a map containing all possible moved queens for each color.
     *
     * @return Map of all possible moved queens.
     */
    private Map<Color, Map<Integer, Queen>> createAllPossibleMovedQueens()
    {
        final Map<Color, Map<Integer, Queen>> pieces = new HashMap<>();
        for(final Color color : Color.values())
        {
            Map<Integer, Queen> innerMap = new HashMap<>();
            for(int i = 0; i < 64; i++)
            {
                innerMap.put((Integer) i, new Queen(i, color, false));
            }
            pieces.put(color, innerMap);
        }
        return pieces;
    }
}
