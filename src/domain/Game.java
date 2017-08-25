package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private int points;
    private int noBirds;
    private int noQuestions;
    private int noAnswers;
    private int difficuty;
    private List<Integer> questions = new ArrayList<>();
    private List<List<Integer>> answers = new ArrayList<>();

    public Game(int noBirds, int noQuestions, int noAnswers, int difficulty){
        this.points = 0;
        this.noBirds = noBirds;
        this.noQuestions = noQuestions;
        this.noAnswers = noAnswers;
        this.difficuty = difficulty;
        init(this.noBirds,this.noQuestions,this.noAnswers,this.difficuty);
    }

    public int getQuestion(int q){
        return questions.get(q);
    }

    public List<Integer> getAnswers(int q){
        return answers.get(q);
    }

    public boolean isCorrect(int q,int a){
        if (questions.get(q).equals(answers.get(q).get(a))) return true;
        else return false;
    }

    public void addPoints(int p){
        points += p;
    }

    public int getPoints(){
        return points;
    }

    private void init(int noBirds, int noQuestions, int noAnswers, int difficulty) {

        BirdRepository birdRepository = BirdRepository.getInstance();

        int birdCandidateId;
        Bird birdCandidate;

        Random r = new Random();

        for (int q = 0; q < noQuestions; q++){
            //QUESTIONS
            while (true) {
                birdCandidateId = r.nextInt(noBirds);
                birdCandidate = birdRepository.getBirdByID(birdCandidateId);
                if ((! questions.contains(birdCandidateId)) && (birdCandidate.getDifficulty() == difficulty)){
                    questions.add(birdCandidateId);
                    break;
                }
            }
            //ANSWERS
            Bird birdQuestion = birdRepository.getBirdByID(questions.get(q));
            List<Integer> answerList = new ArrayList<Integer>();

            while (answerList.size() < noAnswers){
                birdCandidateId = r.nextInt(noBirds);
                birdCandidate = birdRepository.getBirdByID(birdCandidateId);
                if (birdCandidate.getName().equals(birdQuestion.getName())) {
                    continue;
                }
                boolean isDifferent = true;
                for(int a = 0; a < answerList.size(); a++){
                    if (birdCandidate.getName().equals(birdRepository.getBirdByID(answerList.get(a)).getName())) {
                        isDifferent = false;
                        continue;
                    }
                }
                if (isDifferent){
                    answerList.add(birdCandidateId);
                }
            }
            int posRightAnswer = r.nextInt(noAnswers);
            answerList.set(posRightAnswer,questions.get(q));
            answers.add(answerList);
        }
        System.out.println(questions);
        System.out.println(answers);
    }
}
