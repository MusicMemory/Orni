import domain.BirdRepository;
import domain.Game;
import domain.ImageRepository;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.Event;
import java.util.List;


public class Main extends Application{

    private Game game;
    private int noQuestions = 10;
    private int noAnswers = 4;
    private int difficulty = 1;
    private Image image;
    private ImageView imageView;

    public static void main(String[] args) throws Exception{
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        game = new Game(BirdRepository.getInstance().noBirds(),noQuestions,noAnswers,difficulty);
        imageView = new ImageView();
        int question;
        List<Integer> answers;
        Scene scene;
        final boolean[] b = {true};
        for(int q = 0; q < noQuestions;q++){
            question = game.getQuestion(q);
            answers = game.getAnswers(q);
            System.out.println(question);
            System.out.println(answers);
            image = ImageRepository.getInstance().loadImage(BirdRepository.getInstance().getBirdByID(question).getFilename());
            imageView.setImage(image);
            scene = new Scene(new Group(imageView));
            stage.setScene(scene);
            stage.show();
            System.out.println(BirdRepository.getInstance().getBirdByID(question).getName());
        }
        //stage.setResizable(false);

        //iv.fitHeightProperty().bind(s.heightProperty());
        //iv.fitWidthProperty().bind(s.widthProperty());
    }
}
