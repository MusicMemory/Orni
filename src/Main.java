import domain.Bird;
import domain.BirdRepository;
import domain.Game;
import domain.ImageRepository;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    private static final int NO_QUESTIONS = 10;
    private static final int NO_ANSWERS = 4;
    private static final int DIFFICULTY = 1;

    private final Game game = new Game(BirdRepository.getInstance().noBirds(), NO_QUESTIONS, NO_ANSWERS, DIFFICULTY);
    private final RootPane rootPane = new RootPane();
    private final Scene scene = new Scene(rootPane);

    private int currentQuestion = 0;

    /*-----------------------------------------------------------------------*\
     * RootPane                                                              *
    \*-----------------------------------------------------------------------*/

    public class RootPane extends BorderPane {

        private AnswerPane answerPane = new AnswerPane(NO_ANSWERS);

        public RootPane() {
            setPrefSize(600, 500);
            getStyleClass().add("root-pane");

            setAnswers();
            setImage();
        }

        private void setImage() {
            final int birdId = game.getQuestion(currentQuestion);
            final Bird bird = BirdRepository.getInstance().getBirdByID(birdId);
            final Image image = ImageRepository.getInstance().loadImage(bird.getFilename());
            ImageView iv = new ImageView(image);
            this.setCenter(iv);
        }

        private void setAnswers() {
            List<Integer> answerIds = game.getAnswers(currentQuestion);
            List<String> answers = new ArrayList<>();
            for (int a = 0; a < answerIds.size(); a++){
                answers.add(BirdRepository.getInstance().getBirdByID(answerIds.get(a)).getName());
            }
            answerPane.setAnswers(answers);
            this.setBottom(answerPane);
        }
    }

    /*-----------------------------------------------------------------------*\
     * AnswerPane                                                            *
    \*-----------------------------------------------------------------------*/

    private class AnswerPane extends VBox {

        private List<Button> buttons = new ArrayList<>();

        public AnswerPane(int noAnswers){
            super();

            HBox hBox = new HBox(10);

            for (int i = 0; i < noAnswers; i++){
                Button button = new Button();
                //button.setPrefWidth(120);
                button.setOnAction(e -> {
                    if (currentQuestion < NO_QUESTIONS-1) {
                        if (isAnswerCorrect(button)) {
                            game.addPoints(5);
                        }
                        System.out.println(game.getPoints());
                        nextQuestion();
                    }
                    else {
                        System.out.println("Du hast " + game.getPoints() + " Punkte erreicht.");
                    }
                });
                buttons.add(button);
                hBox.getChildren().add(button);
            }
            getChildren().add(hBox);
        }

        private boolean isAnswerCorrect(Button button) {
            final int birdId = game.getQuestion(currentQuestion);
            final Bird bird = BirdRepository.getInstance().getBirdByID(birdId);
            return  button.getText().equals(bird.getName());
        }

        public void setAnswers(List<String> answers){
            for (int a = 0; a < answers.size(); a++){
                buttons.get(a).setText(answers.get(a));
            }
        }

        public void nextQuestion() {
            currentQuestion += 1;
            rootPane.setAnswers();
            rootPane.setImage();
        }
    }

    /*-----------------------------------------------------------------------*\
     * main                                                                  *
    \*-----------------------------------------------------------------------*/

    @Override
    public void start(Stage stage) throws Exception {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new KeyEventHandler());

        scene.getStylesheets().add("/css/game.css");

        stage.setScene(scene);
        stage.show();
    }

    private void pressButton(int i) {
        if (i < NO_ANSWERS){
            rootPane.answerPane.buttons.get(i).fire();
        }
    }

    private class KeyEventHandler implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            final KeyCode code = event.getCode();
            if (code == KeyCode.ESCAPE) {
                Platform.exit();
                return;
            }
            else if (code == KeyCode.DIGIT1) {
                pressButton(0);
                return;
            }
            else if (code == KeyCode.DIGIT2) {
                pressButton(1);
                return;
            }
            else if (code == KeyCode.DIGIT3) {
                pressButton(2);
                return;
            }
            else if (code == KeyCode.DIGIT4) {
                pressButton(3);
                return;
            }
            else if (code == KeyCode.DIGIT5) {
                pressButton(4);
                return;
            }
            else if (code == KeyCode.DIGIT6) {
                pressButton(5);
                return;
            }
            else if (code == KeyCode.DIGIT7) {
                pressButton(6);
                return;
            }
            else if (code == KeyCode.DIGIT8) {
                pressButton(7);
                return;
            }
            else if (code == KeyCode.DIGIT9) {
                pressButton(8);
                return;
            }
        }
    }

    public static void main(String[] args) throws Exception{
        launch(args);
    }

}
