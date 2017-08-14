package domain;

public class Bird {

    private String filename;
    private String name;
    private String order;
    private int difficulty;

    public Bird(String filename, String name, String order, int difficulty){
         this.filename = filename;
         this.name = name;
         this.order = order;
         this.difficulty = difficulty;
    }

    public String getFilename() {
        return filename;
    }

    public String getName() {
        return name;
    }

    public String getOrder() {
        return order;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
