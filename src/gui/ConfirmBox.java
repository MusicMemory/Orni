package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.image.Image;

public class ConfirmBox {

    public static void display(String title, String message, Image image){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button button = new Button("Close this window");
        button.setOnAction(e -> stage.close());
        ImageView imageView = new ImageView(image);
        //imageView.setFitWidth(600);
        //imageView.setFitHeight(400);


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,button,imageView);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }

}
