package items;

public class Score {
    private static Score instance = null;
    private int score = 0;

    protected Score(){
    }

    public static Score getInstance() {
        if(instance == null) {
            instance = new Score();
        }
        return instance;
    }

    public int getScore(){
        return score;
    }

    public void addToScore(int amount){
        score += amount;
    }

}
