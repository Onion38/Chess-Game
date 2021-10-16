package pieces;


public class rook extends base{
   

    public rook(String color, int[] position, String piece){
        super(color, position, piece);
    }

    public boolean validMove(base[][] board, int[] newPos)
    {
        if (this.toRight(board, newPos) || this.toLeft(board, newPos) || this.toTop(board, newPos) || this.toBottom(board, newPos)) return true;
        return false;
    }

    private boolean toRight(base[][] board, int[] newPos)
    {
        int y = this.position[0], x = this.position[1];

        while (x < 9){
            int[] rightPossible = {y, x};


            if (rightPossible == newPos) return true;
            else if (board[y][x] != null) return false;
            x++;
        }
        return false;
    }

    private boolean toLeft(base[][] board, int[] newPos)
    {
        int y = this.position[0], x = this.position[1];

        while (x >= 0){
            int[] leftPossible = {y, x};


            if (leftPossible == newPos) return true;
            else if (board[y][x] != null) return false;
            x--;
        }
        return false;
    }

    private boolean toTop(base[][] board, int[] newPos)
    {
        int x = this.position[1], y = this.position[0];

        while (y < 9)
        {
            int[] topPossible = {y, x};

            if (topPossible == newPos) return true;
            else if (board[y][x] != null) return false;
            y++;
        }
        return false;
    }
    private boolean toBottom(base[][] board, int[] newPos)
    {
        int x = this.position[1], y = this.position[0];

        while (y >= 0)
        {
            int[] bottomPossible = {y, x};

            if (bottomPossible == newPos) return true;
            else if (board[y][x] != null) return false;
            y--;
        }
        return false;        
    }



}