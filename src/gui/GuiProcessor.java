package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.image.Image;

public class GuiProcessor {

    static int answer;

    public static int display(String title, String message, Image image){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button button = new Button("Close this window");
        button.setOnAction(e -> {
            answer = 1;
            stage.close();

        });
        ImageView imageView = new ImageView(image);

        VBox vBoxTop = new VBox(10);
        vBoxTop.getChildren().addAll(label,button);
        vBoxTop.setAlignment(Pos.CENTER);

//        VBox vBoxBottom = new VBox(10);
//        Label label11 = new Label("11");
//        Label label12 = new Label("12");
//        Label label21 = new Label("21");
//        Label label22 = new Label("22");
//        vBoxBottom.getChildren().addAll(new HBox(label11,label21),new HBox(label12,label22));

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(imageView);
        borderPane.setTop(vBoxTop);
//        borderPane.setBottom(vBoxBottom);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.showAndWait();

        return answer;
    }

}
