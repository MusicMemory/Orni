import domain.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    private static final int NO_QUESTIONS = 10;
    private static final int NO_ANSWERS = 4;

    private PlayerData playerData = new PlayerData();
    private final Game game = new Game(BirdRepository.getInstance().noBirds(), NO_QUESTIONS, NO_ANSWERS, playerData.getDifficulty());

    private final RootPane rootPane = new RootPane();
    private final Scene scene = new Scene(rootPane);

    private int currentQuestion = 0;

    /*-----------------------------------------------------------------------*\
     * RootPane                                                              *
    \*-----------------------------------------------------------------------*/

    public class RootPane extends BorderPane {

        private AnswerPane answerPane = new AnswerPane(NO_ANSWERS); //bottom
        private ImageView imageView = new ImageView(); //center
        private Label headerLabel = new Label();
        private Label messageLabel = new Label();

        public RootPane() {
            getStyleClass().add("root-pane");
            headerLabel.getStyleClass().add("header-label");
            messageLabel.getStyleClass().add("message-label");

            setBottom();
            setImage();
            setText(playerData.getPoints() + " Punkte.");
        }

        private void setImage() {
            final int birdId = game.getQuestion(currentQuestion);
            final Bird bird = BirdRepository.getInstance().getBirdByID(birdId);
            final Image image = ImageRepository.getInstance().loadImage(bird.getFilename());
            imageView.setImage(image);
            this.setCenter(imageView);
        }

        private void setBottom() {
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            setAnswers();
            vBox.getChildren().addAll(messageLabel,answerPane);
            this.setBottom(vBox);
        }

        private void setMessageLabel(Boolean correct) {
            if (correct) {
                messageLabel.setText("Richtig");
                messageLabel.setTextFill(Color.GREEN);
            }
            else {
                messageLabel.setText("Total versagt!");
                messageLabel.setTextFill(Color.RED);
            }
            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(750);
                    return null;
                }
            };
            sleeper.setOnSucceeded(e -> messageLabel.setText(""));
            new Thread(sleeper).start();
        }

        private void setAnswers() {
            List<Integer> answerIds = game.getAnswers(currentQuestion);
            List<String> answers = new ArrayList<>();
            for (int a = 0; a < answerIds.size(); a++){
                answers.add(BirdRepository.getInstance().getBirdByID(answerIds.get(a)).getName());
            }
            answerPane.setAnswers(answers);
        }

        private void setText(String text){
            headerLabel.setText(text);
            this.setTop(headerLabel);
        }
    }

    /*-----------------------------------------------------------------------*\
     * AnswerPane                                                            *
    \*-----------------------------------------------------------------------*/

    private class AnswerPane extends VBox {

        private List<Button> buttons = new ArrayList<>();

        public AnswerPane(int noAnswers){
            super();

            HBox hBox = new HBox();

            for (int i = 0; i < noAnswers; i++){
                Button button = new Button();
                //button.setPrefWidth(120);
                button.setOnAction(e -> {
                    if (currentQuestion < NO_QUESTIONS - 1) {
                        if (isAnswerCorrect(button)) {
                            rootPane.setMessageLabel(true);
                            playerData.addPoints(5);
                        }
                        else rootPane.setMessageLabel(false);
                        nextQuestion();
                    }
                    else if (currentQuestion < NO_QUESTIONS) {
                        if (isAnswerCorrect(button)) {
                            rootPane.setMessageLabel(true);
                            playerData.addPoints(5);
                        }
                        else rootPane.setMessageLabel(false);
                        currentQuestion++;
                        rootPane.setText("Du hast " + playerData.getPoints() + " Punkte erreicht.");
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
            rootPane.setBottom();
            rootPane.setImage();
            rootPane.setText(playerData.getPoints() + " Punkte.");
        }
    }

    /*-----------------------------------------------------------------------*\
     * main                                                                  *
    \*-----------------------------------------------------------------------*/

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Ornithology");

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
