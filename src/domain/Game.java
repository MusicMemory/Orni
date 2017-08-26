package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {

    private int noBirds;
    private int noQuestions;
    private int noAnswers;
    private int difficuty;

    private List<Integer> questions = new ArrayList<>();
    private List<List<Integer>> answers = new ArrayList<>();
    private final int MAX_ITERATIONS = 100;

    public Game(int noBirds, int noQuestions, int noAnswers, int difficulty){
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

    private void init(int noBirds, int noQuestions, int noAnswers, int difficulty) {

        BirdRepository birdRepository = BirdRepository.getInstance();

        int birdCandidateId;
        Bird birdCandidate;

        Random r = new Random();
        //LOOP
        for (int q = 0; q < noQuestions; q++){
            //QUESTIONS
            int counter = 0;
            while (counter < MAX_ITERATIONS) {
                birdCandidateId = r.nextInt(noBirds);
                birdCandidate = birdRepository.getBirdByID(birdCandidateId);
                if ((! questions.contains(birdCandidateId)) && (birdCandidate.getDifficulty() == difficulty)){
                    questions.add(birdCandidateId);
                    break;
                }
                counter++;
            }
            if (counter >= MAX_ITERATIONS) System.out.println("no birds");
            //ANSWERS
            List<Integer> answerList = new ArrayList<Integer>();
            Bird birdQuestion = birdRepository.getBirdByID(questions.get(q));

            List<Integer> birdIdsSameOrder = birdRepository.getBirdIdsByOrder(birdQuestion.getOrder());
            Collections.shuffle(birdIdsSameOrder);
            System.out.println(q + ". Same Order (" + birdQuestion.getOrder() + "): " + birdIdsSameOrder);

            outerloop:
            for (int i = 0; i < birdIdsSameOrder.size(); i++){
                if (answerList.size() >= noAnswers) break;
                birdCandidateId = birdIdsSameOrder.get(i);
                birdCandidate = birdRepository.getBirdByID(birdCandidateId);

                if (birdCandidate.getName().equals(birdQuestion.getName())) continue outerloop;
                for (int a = 0; a < answerList.size(); a++) {
                    final String birdNameAnswer = birdRepository.getBirdByID(answerList.get(a)).getName();
                    if (birdCandidate.getName().equals(birdNameAnswer)) {
                        continue outerloop;
                    }
                }
                answerList.add(birdCandidateId);
            }
            int newPos = -1;
            outerloop:
            while (answerList.size() < noAnswers){
                birdCandidateId = r.nextInt(noBirds);
                birdCandidate = birdRepository.getBirdByID(birdCandidateId);
                if (birdCandidate.getName().equals(birdQuestion.getName())) {
                    continue outerloop;
                }
                for(int a = 0; a < answerList.size(); a++) {
                    final String birdNameAnswer = birdRepository.getBirdByID(answerList.get(a)).getName();
                    if (birdCandidate.getName().equals(birdNameAnswer)) {
                        continue outerloop;
                    }
                }
                answerList.add(birdCandidateId);
                newPos = answerList.size() - 1 ;
                counter++;
            }
            //RIGHT ANSWER
            if (newPos != -1) {
                answerList.set(newPos, questions.get(q));
            }
            else {
                int posRightAnswer = r.nextInt(noAnswers);
                answerList.set(posRightAnswer, questions.get(q));
            }
            //ADD
            answers.add(answerList);
        }
        System.out.println("Fragen: " + questions);
        System.out.println("Antworten: " + answers);
    }
}
