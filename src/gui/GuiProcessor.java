package gui;

import domain.ImageRepository;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GuiProcessor extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Image i = ImageRepository.getInstance().loadImage("alpenstrandlaeufer_01.JPG");
        ImageView iv = new ImageView(i);

        Scene s = new Scene(new Group(iv));
        //stage.setResizable(false);

        //iv.fitHeightProperty().bind(s.heightProperty());
        //iv.fitWidthProperty().bind(s.widthProperty());

        stage.setScene(s);
        stage.show();
    }

}
