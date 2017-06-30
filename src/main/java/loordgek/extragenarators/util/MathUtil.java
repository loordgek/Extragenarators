package loordgek.extragenarators.util;

public class MathUtil {
    public static long getpercentage(long Current, long max){
        return scale(Current, max, 100);
    }

    public static long getpercentagereverse(long Current, long max){
        return scalereverse(Current, max, 100);
    }

    public static long scale(long currentint, long maxlong, long size) {
        if (maxlong == 0) return 0;
        return currentint * size / maxlong;
    }

    public static long scalereverse(long currentint, long maxint, long size) {
        if (maxint == 0) return 0;
        return (reverseNumber(currentint, 0, maxint) * size / maxint);
    }

    public static long reverseNumber(long num, long min, long max) {
        return (max + min) - num;
    }
}
