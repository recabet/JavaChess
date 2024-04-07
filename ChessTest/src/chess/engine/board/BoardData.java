package chess.engine.board;

import java.awt.*;

public class BoardData
{

    public static final boolean[] FIRST_COL =initCol(0) ;
    public static final boolean[] SECOND_COL = initCol(1);
    public static final boolean[] SEVENTH_COL =initCol(6);
    public static final boolean[] EIGHTH_COL =initCol(7);

    public static final boolean[] FIRST_ROW=initRow(0);
    public static final boolean[] SECOND_ROW=initRow(8);
    public static final boolean[] THIRD_ROW=initRow(16);
    public static final boolean[] FOURTH_ROW=initRow(24);
    public static final boolean[] FIFTH_ROW=initRow(32);
    public static final boolean[] SIXTH_ROW=initRow(40);
    public static final boolean[] SEVENTH_ROW=initRow(48);
    public static final boolean[] EIGHTH_ROW=initRow(56);

    private BoardData()
    {
        throw new RuntimeException("Not for creating");
    }
    public static  boolean isValidSquareCoord(final int pieceCoord)
    {
        return pieceCoord>=0 && pieceCoord<64;
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
