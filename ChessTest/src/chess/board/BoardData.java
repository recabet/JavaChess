package chess.board;

public class BoardData
{
    public static final boolean[] FIRST_COL =initCol(0) ;
    public static final boolean[] SECOND_COL = initCol(1);
    public static final boolean[] SEVENTH_COL =initCol(6) ;
    public static final boolean[] EIGHTH_COL =initCol(7) ;
    public static final boolean[] SECOND_ROW=initRow(8);
    public static final boolean[] SEVENTH_ROW=initRow(48);



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
