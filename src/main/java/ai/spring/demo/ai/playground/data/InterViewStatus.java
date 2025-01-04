package ai.spring.demo.ai.playground.data;

public enum InterViewStatus {
    WAIT("等待"),PERFECT("完美"),EXCELLENT("优秀"),QUALIFIED("合格"),ELIMINATE("淘汰");
    private String value;

    private InterViewStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
