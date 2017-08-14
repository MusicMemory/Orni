package domain;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BirdRepository {

    private static BirdRepository instance;

    private String csvFile;
    private List<Bird> birds = new ArrayList<Bird>();

    private BirdRepository(String csvFile){
        this.csvFile = csvFile;
        init(csvFile);
    }

    public int noBirds(){
        return birds.size();
    }

    public Bird getBirdByID(int id){
        return birds.get(id);
    }


    private void init(String csvFile) {
        String line;
        String csvSplitBy = ";";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
           while ((line = br.readLine()) != null){
               String[] data = line.split(csvSplitBy);
               if (data[0].endsWith(".JPG")) {
                   String filename = data[0];
                   String name = data[1];
                   String order = data[2];
                   int difficulty = Integer.parseInt(data[3]);

                   Bird bird = new Bird(filename,name,order,difficulty);
                   this.birds.add(bird);
               }
           }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static BirdRepository getInstance(){
        if (BirdRepository.instance == null) {
            BirdRepository.instance = new BirdRepository("src//birds.csv");
        }
        return BirdRepository.instance;
    }
}
