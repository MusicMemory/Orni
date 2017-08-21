import domain.BirdRepository;
import domain.Game;
import domain.ImageRepository;
import gui.GuiProcessor;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    private static final int noQuestions = 10;
    private static final int noAnswers = 4;
    private static final int difficulty = 1;
    private Game game = new Game(BirdRepository.getInstance().noBirds(),noQuestions,noAnswers,difficulty);
    private int currentQuestion = 0;

    private AnswerPane answerPane = new AnswerPane(noAnswers);

    private class AnswerPane extends VBox {

        private List<Button> buttons = new ArrayList<>();

        public AnswerPane(int noAnswers){
            super(20);
            HBox hBox = new HBox();
            for (int i = 0; i < noAnswers; i++){
                Button button = new Button();
                button.setPrefWidth(120);
                button.setOnAction(e -> {
                    if (button.getText().equals(BirdRepository.getInstance().getBirdByID(game.getQuestion(currentQuestion)).getName())){
                        System.out.println("correct");
                        currentQuestion+=1;
                        nextQuestion();
                    }
                    else {
                        System.out.println("incorrect");
                        currentQuestion+=1;
                        nextQuestion();
                    }
                });
                buttons.add(button);
                hBox.getChildren().add(button);
            }
            getChildren().add(hBox);
        }
        public void setAnswers(List<String> answers){
            for (int a = 0; a < answers.size(); a++){
                buttons.get(a).setText(answers.get(a));
            }
        }
    }

    private Parent createContent(List<Integer> answerIds) throws Exception {
        BorderPane root = new BorderPane();
        root.setPrefSize(600,500);

        ImageView iv = new ImageView(ImageRepository.getInstance().loadImage(BirdRepository.getInstance().getBirdByID(game.getQuestion(currentQuestion)).getFilename()));

        List<String> answers = new ArrayList<>();
        for (int a = 0; a < answerIds.size(); a++){
            answers.add(BirdRepository.getInstance().getBirdByID(answerIds.get(a)).getName());
        }
        answerPane.setAnswers(answers);

        root.setBottom(answerPane);
        root.setCenter(iv);

        return root;
    }

    private void nextQuestion(){
        List<Integer> answerIds = game.getAnswers(currentQuestion);
        List<String> answers = new ArrayList<>();
        for (int a = 0; a < answerIds.size(); a++){
            answers.add(BirdRepository.getInstance().getBirdByID(answerIds.get(a)).getName());
        }
        answerPane.setAnswers(answers);
    }

    @Override
    public void start(Stage stage) throws Exception {
        List<Integer> answerIds = game.getAnswers(currentQuestion);
        stage.setScene(new Scene(createContent(answerIds)));
        stage.show();
    }

    public static void main(String[] args) throws Exception{
        launch(args);
    }

}
