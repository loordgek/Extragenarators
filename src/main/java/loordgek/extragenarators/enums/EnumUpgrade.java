package loordgek.extragenarators.enums;

public enum EnumUpgrade {

    SPEED1("speedboost1"),
    SPEED2("speedboost2"),
    SPEED3("speedboost3"),
    SPEED4("speedboost4"),

    POWERSTORE1( "powerstore1"),
    POWERSTORE2("powerstore2"),
    POWERSTORE3("powerstore3"),
    POWERSTORE4("powerstore4"),

    MULTIPLIER1("multiplier1"),
    MULTIPLIER2("multiplier2"),
    MULTIPLIER3("multiplier3"),
    MULTIPLIER4("multiplier4"),;



    private final String name;

    EnumUpgrade(String name) {

        this.name = name;
    }

    public int getMeta() {
        return this.ordinal();
    }

    public String getName() {
        return name;
    }
}
