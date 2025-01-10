package ai.spring.demo.ai.playground.data;

public class InterView {
    private String number;
    private String name;
    private int  score;
    private InterViewStatus interViewStatus;
    private String evaluate;
    private String email;
    private String mp3Path;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMp3Path() {
        return mp3Path;
    }

    public void setMp3Path(String mp3Path) {
        this.mp3Path = mp3Path;
    }

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

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }
}
