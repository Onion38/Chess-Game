package game;

public class Queen extends Base{

    public Queen(String color, int y, int x, String piece)
    {
        super(color, y, x, piece);
    }

    @Override
    public boolean validMove(Board board, int newY, int newX){

        if (this.x == newX && this.y == newY) return false; //Cant move to same spot
        else if (this.x == newX || this.y == newY) return this.validRowColumn(board.matrix, newY, newX);
        else return this.validDiagnols(board.matrix, newY, newX);
        
    }

    private boolean validRowColumn(Base[][] board, int newY, int newX){ //This function checks the rows and columns for queen moves

        if (board[newY][newX] != null && board[newY][newX].color == this.color) return false; // Checks to make sure new position isnt occupied by piece of same color
        
        int x = this.x, y = this.y;
        int xOffset = newX - x, yOffset = newY - y;

        for (int i = 1; i < Math.max(Math.abs(xOffset), Math.abs(yOffset)); i++)
        {   
            if (xOffset != 0) x += xOffset/Math.abs(xOffset);       //xOffset or yOffset will be 0 so must use if statment for the incrementing
            else y += yOffset/Math.abs(yOffset);                    

            if (board[y][x] != null) return false;                  //Returns false since piece blocking path
        }
        return true;
    }

    private boolean validDiagnols(Base[][] board, int newY, int newX){

        int x = this.x, y = this.y;                                     
        int xOffset = newX - x, yOffset = newY - y;

        if (board[newY][newX] != null && board[newY][newX].color == this.color) return false; // Checks to make sure new position isnt occupied by piece of same color
        if (Math.abs(yOffset) != Math.abs(xOffset)) return false; //Checks to make sure piece is on SAME DIAGNOL

        for (int i = 1; i < Math.abs(xOffset); i++)
        {
            x += xOffset / Math.abs(xOffset);                    //This tells us what to increment/decrement the x and y by
            y += yOffset / Math.abs(yOffset);

            if (board[y][x] != null) return false;               //If there is something blocking the path return false
        }
        return true;                                             //Return True because there is nothing blocking the path
    }

}
