import domain.BirdRepository;
import domain.Game;
import domain.ImageRepository;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.event.Event;

import java.io.FileInputStream;
import java.util.List;


public class Test extends Application{

    public static void main(String[] args) throws Exception{
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle( "Timeline Example" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        stage.setScene( theScene );

        Canvas canvas = new Canvas( 700, 700 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image earth = new Image(new FileInputStream("earth.png"));
        Image space = new Image(new FileInputStream("space.jpg"));


        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                double x = 232 + 128 * Math.cos(t);
                double y = 232 + 128 * Math.sin(t);
                gc.drawImage(space,0,0);
                gc.drawImage( earth, x, y );
            }
        }.start();

        //gc.setFill( Color.RED );
        //gc.setStroke( Color.BLACK );
        //gc.setLineWidth(2);
        //Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        //gc.setFont( theFont );
        //gc.fillText( "Hello, World!", 60, 50 );
        //gc.strokeText( "Hello, World!", 60, 50 );

        stage.show();

    }
}