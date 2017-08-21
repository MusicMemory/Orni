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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main extends Application {

    private static final int noQuestions = 10;
    private static final int noAnswers = 4;
    private static final int difficulty = 1;
    private Game game = new Game(BirdRepository.getInstance().noBirds(),noQuestions,noAnswers,difficulty);

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
                    if (button.getText().equals("test1")){
                        System.out.println("correct");
                        nextQuestion();
                    }
                    else {
                        System.out.println("incorrect");
                        nextQuestion();
                    }
                });
                buttons.add(button);
                hBox.getChildren().add(button);
            }
            getChildren().add(hBox);
        }
        public void setAnswers(String... answers){
            Collections.shuffle(buttons);
            for (int i = 0; i < noAnswers; i++){
                buttons.get(i).setText(answers[i]);
            }
        }
    }

    private Parent createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(600,500);
        answerPane.setAnswers("test1","test2","test3","test4");
        root.setBottom(answerPane);
        return root;
    }

    private void nextQuestion(){
        answerPane.setAnswers("d","c","b","a");
    }

    @Override
    public void start(Stage stage) throws Exception {


        List<Integer> answers = game.getAnswers(question);
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static void main(String[] args) throws Exception{
        launch(args);
    }

}
