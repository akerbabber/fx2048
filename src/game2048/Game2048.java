package game2048;

import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import giocatoreAutomatico.*;
import giocatoreAutomatico.player.*;

/**
 * @author bruno.borges@oracle.com
 */
public class Game2048 extends Application {

    private GameManager gameManager;
    private Bounds gameBounds;

    @Override
    public void start(Stage primaryStage) {
        gameManager = new GameManager();
        gameBounds = gameManager.getLayoutBounds();
        int token=0;

        StackPane root = new StackPane(gameManager);
        root.setPrefSize(gameBounds.getWidth(), gameBounds.getHeight());
        ChangeListener<Number> resize = (ov, v, v1) -> {
            gameManager.setLayoutX((root.getWidth() - gameBounds.getWidth()) / 2d);
            gameManager.setLayoutY((root.getHeight() - gameBounds.getHeight()) / 2d);
        };
        root.widthProperty().addListener(resize);
        root.heightProperty().addListener(resize);

        Scene scene = new Scene(root, 600, 720);
        scene.getStylesheets().add("game2048/game.css");
        addKeyHandler(scene);
        addSwipeHandlers(scene);
        
        
        gameManager.getAutomatonButton().setOnAction((event)->{

            try{
                /* Lock the keys */
                scene.setOnKeyPressed(ke->{});

                GiocatoreAutomatico player1=GiocatoreAutomatico.getGiocatoreAutomatico();

                if(player1==null)
                    System.out.println("player1 == null");

                /* !! FIND A WAY TO SYNCHRONIZE THE TWO THREADS OTHER THAN THE TIMEOUT! */
                while(!(gameManager.isGameOver())){

                    switch(player1.prossimaMossa(new GrigliaObject(gameManager.getGameGrid()));){

                        case 0:
                            gameManager.move(Direction.UP);
                            System.out.println("Moved up");
                        break;

                        case 1:
                            gameManager.move(Direction.RIGHT);
                            System.out.println("Moved right");
                        break;

                        case 2:
                            gameManager.move(Direction.DOWN);
                            System.out.println("Moved down");
                        break;

                        case 3:
                            gameManager.move(Direction.LEFT);
                            System.out.println("Moved left");
                        break;

                    }

                    System.out.println("Waiting 5 seconds...");

                    synchronized(this){

                        this.wait(5000);

                    }
                    
                    System.out.println("5 seconds elapsed!");

                }

            }catch(Exception e1){}
            
            /* Reset user input */
            addKeyHandler(scene);

        });


        if (isARMDevice()) {
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitHint("");
        }

        if (Platform.isSupported(ConditionalFeature.INPUT_TOUCH)) {
            scene.setCursor(Cursor.NONE);
        }

        primaryStage.setTitle("2048FX");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(gameBounds.getWidth());
        primaryStage.setMinHeight(gameBounds.getHeight());
        primaryStage.show();
    }

    private boolean isARMDevice() {
        return System.getProperty("os.arch").toUpperCase().contains("ARM");
    }

    private void addKeyHandler(Scene scene) {
        scene.setOnKeyPressed(ke -> {
            KeyCode keyCode = ke.getCode();
            if (keyCode.equals(KeyCode.S)) {
                gameManager.saveSession();
                return;
            }
            if (keyCode.equals(KeyCode.R)) {
                gameManager.restoreSession();
                return;
            }
            if (keyCode.isArrowKey() == false) {
                return;
            }
            Direction direction = Direction.valueFor(keyCode);
            gameManager.move(direction);
        });
    }

    private void addSwipeHandlers(Scene scene) {
        scene.setOnSwipeUp(e -> gameManager.move(Direction.UP));
        scene.setOnSwipeRight(e -> gameManager.move(Direction.RIGHT));
        scene.setOnSwipeLeft(e -> gameManager.move(Direction.LEFT));
        scene.setOnSwipeDown(e -> gameManager.move(Direction.DOWN));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
