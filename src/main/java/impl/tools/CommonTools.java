package impl.tools;

public class CommonTools {
    public static boolean isEqual(Object first, Object second) {
        return first == null && second == null || first != null && first.equals(second);
    }
}
