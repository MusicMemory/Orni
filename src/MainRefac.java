import domain.Bird;
import domain.BirdRepository;
import domain.Game;
import domain.ImageRepository;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class MainRefac extends Application {

    private static final int NO_QUESTIONS = 10;
    private static final int NO_ANSWERS = 4;
    private static final int DIFFICULTY = 1;

    private Game game = new Game(BirdRepository.getInstance().noBirds(), NO_QUESTIONS, NO_ANSWERS, DIFFICULTY);
    private int currentQuestion = 0;
    private RootPane rootPane = new RootPane();


    /*-----------------------------------------------------------------------*\
     * RootPane                                                              *
    \*-----------------------------------------------------------------------*/

    private class RootPane extends BorderPane {

        private AnswerPane answerPane = new AnswerPane(NO_ANSWERS);

        public RootPane() {
            setPrefSize(600, 500);
            setImage();
            setAnswers();
        }

        public void setAnswers() {
            answerPane = new AnswerPane(NO_ANSWERS);
            List<Integer> answerIds = game.getAnswers(currentQuestion);
            List<String> answers = new ArrayList<>();
            for (int a = 0; a < answerIds.size(); a++) {
                answers.add(BirdRepository.getInstance().getBirdByID(answerIds.get(a)).getName());
            }
            answerPane.setAnswers(answers);
            this.setBottom(answerPane);
        }

        public void setImage() {
            final int birdId = game.getQuestion(currentQuestion);
            final Bird bird = BirdRepository.getInstance().getBirdByID(birdId);
            final Image image = ImageRepository.getInstance().loadImage(bird.getFilename());
            ImageView iv = new ImageView(image);
            this.setCenter(iv);
        }
    }

    /*-----------------------------------------------------------------------*\
     * AnswerPane                                                              *
    \*-----------------------------------------------------------------------*/

    private class AnswerPane extends VBox {

        private List<Button> buttons = new ArrayList<>();

        AnswerPane(int noAnswers) {
            super(20);
            HBox hBox = new HBox();
            for (int i = 0; i < noAnswers; i++) {
                Button button = new Button();
                button.setPrefWidth(120);
                button.setOnAction(e -> {
                    System.out.println(isAnswerCorrect(button) ? "correct" : "incorrect");
                    nextQuestion();
                });
                buttons.add(button);
                hBox.getChildren().add(button);
            }
            getChildren().add(hBox);
        }

        public void setAnswers(List<String> answers) {
            for (int a = 0; a < answers.size(); a++) {
                buttons.get(a).setText(answers.get(a));
            }
        }

        private boolean isAnswerCorrect(Button button) {
            final int birdId = game.getQuestion(currentQuestion);
            final Bird bird = BirdRepository.getInstance().getBirdByID(birdId);
            return button.getText().equals(bird.getName());
        }

        private void nextQuestion() {
            currentQuestion += 1;
            rootPane.setAnswers();
            rootPane.setImage();
        }
    }


    /*-----------------------------------------------------------------------*\
     * main                                                                  *
    \*-----------------------------------------------------------------------*/

    @Override
    public void start(Stage stage) {
        rootPane = new RootPane();
        stage.setScene(new Scene(rootPane));
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }

}
