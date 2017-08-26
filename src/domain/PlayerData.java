package domain;

public class PlayerData {
    private int difficulty = 1;
    private int points = 0;

    public void addPoints(int points) {
        this.points += points;
        System.out.println(this.points);
    }

    public void assignDifficulty(int difficulty){
        this.difficulty = difficulty;
        System.out.println(this.difficulty);
    }

    public int getPoints() {
        return points;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
