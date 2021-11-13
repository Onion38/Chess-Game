package ChessGUIScenes;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.ImagePattern;


public class PieceGUI extends Rectangle {
    private double startX;
    private double startY;

    private double translationX;
    private double translationY;

    private BoardGUI boardGUI;
    private Label stateOfDaLabel;

    
    public PieceGUI(Image image, BoardGUI boardGUIinitial, Label stateOfMove){

        this.setHeight(90);
        this.setWidth(90);
        this.setFill(new ImagePattern(image, 0.0, 0.0, 90.0, 90.0, false));


        
        boardGUI = boardGUIinitial;
        stateOfDaLabel = stateOfMove;

        onClick();
    }   
    
    

    public void onClick(){
        this.setOnMousePressed(e ->{
            startX = e.getSceneX();
            startY = e.getSceneY();
            //this.setCenter()
            int pointX = ((int)((e.getSceneX() - 30) / 90)) * 90 + 45;
            int pointY = ((int)((e.getSceneY() - 50) / 90)) * 90 - 45;

            System.out.println(e.getSceneX() + " " + e.getSceneY());

            //this.setTranslateX(e.getSceneX() - pointX);
            //this.setTranslateY(e.getSceneY() - pointY);

            System.out.println(pointX + " " + pointY);
            translationX = e.getSceneX() - pointX;
            translationY = e.getSceneY() - pointY;

            this.setCursor(Cursor.CLOSED_HAND);
            this.toFront();
        });

        this.setOnMouseDragged(e ->{
            
            this.setTranslateX((e.getSceneX() - startX));
            this.setTranslateY((e.getSceneY() - startY));
            this.setCursor(Cursor.CLOSED_HAND);
        });
        
        this.setOnMouseReleased(e ->{
            
            this.setCursor(Cursor.DEFAULT);

            int newX = (int) ((e.getSceneX() - 30) / 90.0);
            int newY = (int) ((e.getSceneY() - 50) / 90.0);
             
            int x = (int) ((startX - 30)/ 90.0) ;
            int y = (int) ((startY - 50)/ 90.0) ;

            if (boardGUI.boardObject.matrix[y][x] == null || !boardGUI.boardObject.turn.equals(boardGUI.boardObject.matrix[y][x].color) || newY > 7 || newY < 0 || newX > 7 || newX < 0) {
                this.setTranslateX(0);
                this.setTranslateY(0);
                stateOfDaLabel.setText("That aint right, choose right piece");
            }

            else if (boardGUI.boardObject.matrix[y][x].validMove(boardGUI.boardObject, newY, newX) && !boardGUI.boardObject.matrix[y][x].isCheckAfterMove(boardGUI.boardObject, newY, newX)){
                
                boardGUI.boardObject.updateBoardObjectMatrix(y, x, newY, newX);
                boardGUI.updateBoardGUI(y, x, newY, newX);




                boardGUI.boardObject.updateAttributesMoveWork(newY, newX);


                if (boardGUI.boardObject.isGameOver()) stateOfDaLabel.setText("U truly da best. Simply Put");
                else stateOfDaLabel.setText("Gotta give it to you, valid Move");
            }

            else {
                this.setTranslateX(0);
                this.setTranslateY(0);

                boardGUI.boardObject.matrix[y][x].castle = false;
                boardGUI.boardObject.matrix[y][x].enPessant = false;
                boardGUI.boardObject.matrix[y][x].promotion = false;
                stateOfDaLabel.setText("Cmon now, Invalid");
            }

        
        });

    } 

}
