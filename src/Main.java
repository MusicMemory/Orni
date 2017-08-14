import domain.BirdRepository;
import domain.Game;
import domain.ImageRepository;
import gui.GuiProcessor;
import javafx.application.Application;


public class Main {

    public static void main(String[] args) throws Exception{
        BirdRepository birdRepository = BirdRepository.getInstance();
        Game game = new Game(birdRepository.noBirds(),10,4,1);
        GuiProcessor gui = new GuiProcessor(300,300);
        Application.launch(GuiProcessor.class,args);

    }

}
