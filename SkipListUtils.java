public class SkipListUtils {

    public static double calculateExpectedHeight(double p) {
        return 1 - (1 / p);
    }

    public static boolean[] changedMethodsArray() {
        boolean[] result = { true, true, true, false, false, false, false, false, false };
        return result;
    }
}
