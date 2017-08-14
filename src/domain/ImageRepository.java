package domain;

import javafx.scene.image.Image;
import java.io.FileInputStream;

public class ImageRepository {

    private static ImageRepository instance;
    private static String folder;

    private ImageRepository(String folder){
        this.folder = folder;
    }

    public Image loadImage(String filename) throws Exception {
        Image i = new Image(new FileInputStream(folder + filename));
        return i;
    }

    public static ImageRepository getInstance(){
        if (ImageRepository.instance == null){
            ImageRepository.instance = new ImageRepository("src/images/");
        }
        return ImageRepository.instance;
    }
}
