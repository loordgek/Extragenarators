package loordgek.extragenarators.enums;

public enum EnumUpgrade {
    SPEED1(0, "speedboost1"),
    SPEED2(1, "speedboost2"),
    SPEED3(2, "speedboost3"),
    SPEED4(3, "speedboost4"),

    POWERSTORE1(4, "powerstore1"),
    POWERSTORE2(5, "powerstore2"),
    POWERSTORE3(6, "powerstore3"),
    POWERSTORE4(7, "powerstore4"),

    MULTIPLIER1(8,  "multiplier1"),
    MULTIPLIER2(9,  "multiplier2"),
    MULTIPLIER3(10, "multiplier3"),
    MULTIPLIER4(11, "multiplier4");


    private final int meta;
    private final String name;

    EnumUpgrade(int meta, String name) {
        this.meta = meta;
        this.name = name;
    }

    public int getMeta() {
        return meta;
    }

    public String getName() {
        return name;
    }
}
