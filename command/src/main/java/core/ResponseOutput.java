package core;

public class ResponseOutput {
    private static StringBuilder stringBuilder = new StringBuilder();

    public static void appendln(Object toOut) {
        stringBuilder.append(toOut + "\n");
    }

    public static String getString() {
        return stringBuilder.toString();
    }

    public static String getAndClear() {
        String toReturn = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return toReturn;
    }

}
