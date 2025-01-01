package ai.spring.demo.ai.playground.data;

public enum BookingClass {
    ECONOMY("经济舱"), PREMIUM_ECONOMY("头等舱"), BUSINESS("商务舱");
    private String value;

    private BookingClass(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
