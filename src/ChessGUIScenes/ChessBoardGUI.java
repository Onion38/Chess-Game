package ChessGUIScenes;

import game.Board;
import game.Base;
import game.King;
import game.Queen;
import game.Rook;
import game.Bishop;
import game.Knight;
import game.Pawn;

import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.IOException;


import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.ImagePattern;
import javafx.scene.layout.VBox;



public class ChessBoardGUI extends GridPane{
    public Board boardObject; 
    
    public Label stateOfDaMove;

    public boolean isGalooeh;

    public int boardView = 1;
    
    
    private Stage promotionAlertWhite;
    private Stage promotionAlertBlack;
    
    private int newXPromotion;
    private int newYPromotion;

    public Button moveBackward;
    public Button moveForward;
    public VBox buttonContainer;


    public ChessBoardGUI(Label stateOfMove, boolean isGalooehKnows){
        //Input state label
        stateOfDaMove = stateOfMove;

        //Should pics represent actual piece or galooeh
        isGalooeh = isGalooehKnows;


        //Inits the boardObject
        this.boardObject =  createGame();
        //creates pieceGUIs and background
        createBoardGUI();
        
        
        //Inits Promotion stages
        this.promotionAlertWhite = new Stage();
        createPromotionStage("white", this.promotionAlertWhite);

        this.promotionAlertBlack = new Stage();
        createPromotionStage("black", this.promotionAlertBlack);
    }



    private void createPromotionStage(String color, Stage daStage){
        HBox holder = new HBox();


        String[] pieces  = {"queen", "rook", "bishop", "knight"};
        for (String piece : pieces){
            try {
                FileInputStream pathway;
                if (isGalooeh) { //Sets img to galooeh
                    pathway = new FileInputStream(FilePaths.daPath + "galooeh.png");
                }
                else { //sets img to actual piece
                    pathway = new FileInputStream(FilePaths.daPath + color + piece + ".png");
                }
                
                Image image = new Image(pathway);
                            
                ImageView imageNode = new ImageView(image);  
            
            
                Button buttonPiece = new Button();
                buttonPiece.setGraphic(imageNode);

                buttonPiece.setOnMouseClicked(e ->{
                    this.updateGUIForPromotion(piece, color);
                    daStage.close();
                });

                holder.getChildren().add(buttonPiece);
            
            }
            catch(IOException ye ) {System.out.println("Ayoh");}                       

        }  
        Scene promotionScene = new Scene(holder);
        daStage.setScene(promotionScene);
    }

    private void updateGUIForPromotion(String piece, String color){


        this.destroyPiece(this.boardObject.matrix[this.newYPromotion][this.newXPromotion].chessPieceGUI);



        if (piece.equals("rook")){
            this.boardObject.matrix[this.newYPromotion][this.newXPromotion] = new Rook(color, this.newYPromotion, this.newXPromotion, piece);
        }
        else if (piece.equals("bishop")){
            this.boardObject.matrix[this.newYPromotion][this.newXPromotion] = new Bishop(color, this.newYPromotion, this.newXPromotion, piece);
        }
        else if (piece.equals("knight")){
            this.boardObject.matrix[this.newYPromotion][this.newXPromotion] = new Knight(color, this.newYPromotion, this.newXPromotion, piece);
        }
        else if (piece.equals("queen")){
            this.boardObject.matrix[this.newYPromotion][this.newXPromotion] = new Queen(color, this.newYPromotion, this.newXPromotion, piece);
        }



        try{
            FileInputStream pathway;
            if (isGalooeh) { //Sets img to galooeh
                pathway = new FileInputStream(FilePaths.daPath + "galooeh.png");
            }
            else { //sets img to actual piece
                pathway = new FileInputStream(FilePaths.daPath + color + piece + ".png");
            }

            ChessPieceGUI promotionPiece = new ChessPieceGUI(pathway, this);
            this.boardObject.matrix[this.newYPromotion][this.newXPromotion].chessPieceGUI = promotionPiece;

            GridPane.setRowIndex(promotionPiece, this.newYPromotion);
            GridPane.setColumnIndex(promotionPiece, this.newXPromotion);


            this.getChildren().addAll(promotionPiece);
        }
        catch (IOException e){
            System.out.println("Promotion image aint workin");
        }


    }

    public void destroyPiece(ChessPieceGUI pieceGUI){
        this.getChildren().remove(pieceGUI);
    }



    public void updateBoardGUI(int y, int x, int newY, int newX, ChessPieceGUI pieceGUI){

        GridPane.setColumnIndex(pieceGUI, newX);
        GridPane.setRowIndex(pieceGUI, newY);
        pieceGUI.setTranslateX(0);
        pieceGUI.setTranslateY(0);
        GridPane.setHalignment(pieceGUI, HPos.CENTER);
        GridPane.setValignment(pieceGUI, VPos.CENTER);

        if (this.boardObject.matrix[newY][newX].castle){

            if (newX > x){
                //Updates node
                GridPane.setColumnIndex(this.boardObject.matrix[newY][newX - 1].chessPieceGUI, newX - 1);
                GridPane.setRowIndex(this.boardObject.matrix[newY][newX - 1].chessPieceGUI, newY);

                //sets node to translate of 0
                this.boardObject.matrix[newY][newX - 1].chessPieceGUI.setTranslateX(0);
                this.boardObject.matrix[newY][newX - 1].chessPieceGUI.setTranslateY(0);
                GridPane.setHalignment(this.boardObject.matrix[newY][newX - 1].chessPieceGUI, HPos.CENTER);
                GridPane.setValignment(this.boardObject.matrix[newY][newX - 1].chessPieceGUI, VPos.CENTER);
            }
            else{
                GridPane.setColumnIndex(this.boardObject.matrix[newY][newX + 1].chessPieceGUI, newX + 1);
                GridPane.setRowIndex(this.boardObject.matrix[newY][newX + 1].chessPieceGUI, newY);
                this.boardObject.matrix[newY][newX + 1].chessPieceGUI.setTranslateX(0);
                this.boardObject.matrix[newY][newX + 1].chessPieceGUI.setTranslateY(0);
                GridPane.setHalignment(this.boardObject.matrix[newY][newX + 1].chessPieceGUI, HPos.CENTER);
                GridPane.setValignment(this.boardObject.matrix[newY][newX + 1].chessPieceGUI, VPos.CENTER);   
            }
        }

        if (this.boardObject.matrix[newY][newX].promotion){
            this.newXPromotion = newX;
            this.newYPromotion = newY;

            if (this.boardObject.matrix[newY][newX].color.equals("white")) this.promotionAlertWhite.show();

            else this.promotionAlertBlack.show();
        }

        
    }


    private void createBoardGUI(){
        this.getChildren().clear();

        HelperGUI.createBackground(this);

        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                


                if (boardObject.matrix[y][x] != null){
                    try{
                        FileInputStream pathway;
                        if (isGalooeh) { //Sets img to galooeh
                            pathway = new FileInputStream(FilePaths.daPath + "galooeh.png");
                        }
                        else { //sets img to actual piece
                            pathway = new FileInputStream(FilePaths.daPath + this.boardObject.matrix[y][x].color + this.boardObject.matrix[y][x].piece + ".png");
                        }                                    
                        
                        ChessPieceGUI imageNode = new ChessPieceGUI(pathway, this);                        
                        this.boardObject.matrix[y][x].chessPieceGUI = imageNode;
                        GridPane.setRowIndex(imageNode, y);
                        GridPane.setColumnIndex(imageNode, x);

                        this.getChildren().addAll(imageNode);
                        
                        GridPane.setHalignment(imageNode, HPos.CENTER);
                        GridPane.setValignment(imageNode, VPos.CENTER);

                    }
                    catch (IOException e) {
                        System.out.println("FILE ERROR PICTURES");
                    }
                }
            }
        }
        
        return;
    }

    public void moveBackOrForward(int decrement){
        if (this.boardView + decrement > 0 && this.boardView + decrement <= this.boardObject.prevBoards.size()){
            this.boardView += decrement;
            this.updateBoardViewing(this.boardObject.prevBoards.get(this.boardObject.prevBoards.size() - this.boardView));
        } 

    }

    private void updateBoardViewing(Base[][] displayBoard){
        this.getChildren().clear();
        HelperGUI.createBackground(this);

        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                

                if (displayBoard[y][x] == null) continue;

                //if galooeh game, change images back to regular images
                if (isGalooeh){
                    String color = displayBoard[y][x].color;
                    String piece = displayBoard[y][x].piece;
                    
                    try{
                        FileInputStream pathway = new FileInputStream(FilePaths.daPath + color + piece + ".png");
                        
                        Image image = new Image(pathway);
                        displayBoard[y][x].chessPieceGUI.setFill(new ImagePattern(image));    
                    }
                    catch (IOException e) {
                        System.out.println("FILE ERROR PICTURES");
                    } 
                }
                
                this.add(displayBoard[y][x].chessPieceGUI, x, y);  


                       
            }
        }
    }

    //Galooeh game stuff - show pieces and back and forth buttons after checkmate

    public void showRegPieces(){
        for (int y = 0; y < 8; y++){
            for (int x = 0; x < 8; x++){
                if (boardObject.matrix[y][x] == null) continue;
                String color = this.boardObject.matrix[y][x].color;
                String piece = this.boardObject.matrix[y][x].piece;
                
                try{
                    FileInputStream pathway = new FileInputStream(FilePaths.daPath + color + piece + ".png");
                    
                    Image image = new Image(pathway);
                    this.boardObject.matrix[y][x].chessPieceGUI.setFill(new ImagePattern(image));    
                }
                catch (IOException e) {
                    System.out.println("FILE ERROR PICTURES");
                } 
            }    
        }
    }

    public void showButtons(){
        buttonContainer.getChildren().addAll(moveBackward, moveForward);

    }







    //For now we will just create a new instance. DOwn the road make pieces attributes, and just reinput pieces to save memory.
    public void resetBoard(){
        if (isGalooeh){
            this.buttonContainer.getChildren().remove(this.moveBackward);
            this.buttonContainer.getChildren().remove(this.moveForward);
        }
        this.boardView = 1;
        this.boardObject =  createGame();
        createBoardGUI();

    }


    private Board createGame(){
        Base blackKing = new King("black", 0, 4, "king");
        Base blackQueen = new Queen("black", 0, 3, "queen");
        Base blackBishop1 = new Bishop("black", 0, 2, "bishop");
        Base blackBishop2 = new Bishop("black", 0, 5, "bishop");
        Base blackKnight2 = new Knight("black", 0, 6, "knight");
        Base blackKnight1 = new Knight("black", 0, 1, "knight");
        Base blackRook1 = new Rook("black", 0 , 0, "rook");
        Base blackRook2 = new Rook("black", 0, 7, "rook");
        Base blackPawn0 = new Pawn("black", 1, 0, "pawn");
        Base blackPawn1 = new Pawn("black", 1, 1, "pawn");
        Base blackPawn2 = new Pawn("black", 1, 2, "pawn");
        Base blackPawn3 = new Pawn("black", 1, 3, "pawn");
        Base blackPawn4 = new Pawn("black", 1, 4, "pawn");
        Base blackPawn5 = new Pawn("black", 1, 5, "pawn");
        Base blackPawn6 = new Pawn("black", 1, 6, "pawn");
        Base blackPawn7 = new Pawn("black", 1, 7, "pawn");
        
        Base whiteKing = new King("white", 7, 4, "king");
        Base whiteQueen = new Queen("white", 7, 3, "queen");
        Base whiteBishop1 = new Bishop("white", 7, 2, "bishop");
        Base whiteBishop2 = new Bishop("white", 7, 5, "bishop");
        Base whiteKnight2 = new Knight("white", 7, 6, "knight");
        Base whiteKnight1 = new Knight("white", 7, 1, "knight");
        Base whiteRook1 = new Rook("white", 7 , 0, "rook");
        Base whiteRook2 = new Rook("white", 7, 7, "rook");
        Base whitePawn0 = new Pawn("white", 6, 0, "pawn");
        Base whitePawn1 = new Pawn("white", 6, 1, "pawn");
        Base whitePawn2 = new Pawn("white", 6, 2, "pawn");
        Base whitePawn3 = new Pawn("white", 6, 3, "pawn");
        Base whitePawn4 = new Pawn("white", 6, 4, "pawn");
        Base whitePawn5 = new Pawn("white", 6, 5, "pawn");
        Base whitePawn6 = new Pawn("white", 6, 6, "pawn");
        Base whitePawn7 = new Pawn("white", 6, 7, "pawn");

        Base[][] matrix = {
                          {blackRook1, blackKnight1, blackBishop1, blackQueen, blackKing, blackBishop2, blackKnight2, blackRook2}, 
                          {blackPawn0, blackPawn1, blackPawn2, blackPawn3, blackPawn4, blackPawn5, blackPawn6, blackPawn7}, 
                          {null, null, null, null, null, null, null, null}, 
                          {null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null},
                          {whitePawn0, whitePawn1, whitePawn2, whitePawn3, whitePawn4, whitePawn5, whitePawn6, whitePawn7},
                          {whiteRook1, whiteKnight1, whiteBishop1, whiteQueen, whiteKing, whiteBishop2, whiteKnight2, whiteRook2},
                          }; 

        ArrayList<Base[][]> prevBoards = new ArrayList<Base[][]>();

        //create board object
        Board boardObject = new Board(matrix, prevBoards);

        //add starting matrix to prevBoards
        Base[][] tempBoardMatrix = new Base[8][8];
        for (int i = 0; i < boardObject.matrix.length; i++){
            for (int j = 0; j < boardObject.matrix[i].length; j++){
                tempBoardMatrix[i][j] = boardObject.matrix[i][j]; 
            }
        }
        boardObject.prevBoards.add(tempBoardMatrix); 
        return boardObject;
    }

}