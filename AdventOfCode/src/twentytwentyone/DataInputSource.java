package twentytwentyone;
public enum DataInputSource {
    SAMPLE(1),
    TEST(2);
    private final int value;
    private DataInputSource(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

}
