package chess.engine.board;

import java.util.HashMap;
import java.util.Map;

public class BoardData
{
    public static final boolean[] FIRST_COL =initCol(0) ;
    public static final boolean[] SECOND_COL = initCol(1);
    public static final boolean[] SEVENTH_COL =initCol(6);
    public static final boolean[] EIGHTH_COL =initCol(7);

    public static final boolean[] EIGHTH_RANK =initRow(0);
    public static final boolean[] SEVENTH_RANK =initRow(8);
    public static final boolean[] SIXTH_RANK =initRow(16);
    public static final boolean[] FIFTH_RANK =initRow(24);
    public static final boolean[] FOURTH_RANK =initRow(32);
    public static final boolean[] THIRD_RANK =initRow(40);
    public static final boolean[] SECOND_RANK =initRow(48);
    public static final boolean[] FIRST_RANK =initRow(56);

    public static final String[] ALGEBRAIC_NOTATION =initAlgebraicNotation();
    public static final Map<String, Integer> POSITION_TO_COORD = initPositionToCoord();

    private BoardData()
    {
        throw new RuntimeException("Not for creating");
    }
    public static  boolean isValidSquareCoord(final int pieceCoord)
    {
        return pieceCoord>=0 && pieceCoord<64;
    }
    private static String[] initAlgebraicNotation() {
        return new String[]
                      { "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                        "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                        "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                        "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                        "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                        "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                        "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                        "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1" };

    }
    private static Map<String, Integer> initPositionToCoord() {
        final Map<String, Integer> positionToCoord = new HashMap<>();
        for (int i = 0; i < 64; i++) {
            positionToCoord.put(ALGEBRAIC_NOTATION[i], i);
        }
        return Map.copyOf(positionToCoord);
    }
    public static int getCoordAtPosition(final String position) {
        return POSITION_TO_COORD.get(position);
    }
    public static String getPositionAtCoord(final int coord) {
        return ALGEBRAIC_NOTATION[coord];
    }
    private static boolean[] initCol(int colNum)
    {
        final boolean[] col=new boolean[64];
        do
        {
            col[colNum]=true;
            colNum+=8;
        }while(colNum<64);
        return col;
    }
    private static boolean[] initRow(int rowNum)
    {
        final boolean[] row=new boolean[64];
        do
        {
            row[rowNum]=true;
            ++rowNum;

        }while(rowNum%8!=0);
        return row;
    }
}
