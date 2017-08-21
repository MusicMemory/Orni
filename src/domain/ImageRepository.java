package domain;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ImageRepository {

    private static ImageRepository instance;
    private static String folder;

    private ImageRepository(String folder){
        this.folder = folder;
    }

    public Image loadImage(String filename)  {
        try {
            return new Image(new FileInputStream(folder + filename),600,400,false,false);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ImageRepository getInstance(){
        if (ImageRepository.instance == null){
            ImageRepository.instance = new ImageRepository("src/images/");
        }
        return ImageRepository.instance;
    }
}
