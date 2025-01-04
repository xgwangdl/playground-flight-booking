package ai.spring.demo.ai.playground.data;

public class InterView {
    private String number;
    private String name;
    private int  score;
    private InterViewStatus interViewStatus;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public InterViewStatus getInterViewStatus() {
        return interViewStatus;
    }

    public void setInterViewStatus(InterViewStatus interViewStatus) {
        this.interViewStatus = interViewStatus;
    }
}
