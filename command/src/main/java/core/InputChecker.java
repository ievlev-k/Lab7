package core;


/**
 * Этот класс используется для проверки правильности ввода пользователя
 */


public class InputChecker {
    public boolean floatChecker(String s, float min, float max, boolean canEmpty) {
        try {
            float x = Float.parseFloat(s);
            if (x > min && x <= max) return true;
            System.out.println("Введите числа в нужном диапазоне ");
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Введите число типа float ");
            return false;
        }
    }

    public boolean longChecker(String s, long min, long max, boolean canEmpty) {
        try {
            long x = Long.parseLong(s);
            if (x > min && x <= max) return true;
            System.out.println("Введите числа в нужном диапазоне ");
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Введите число типа long ");
            return false;
        }
    }

    

    public boolean intChecker(String s, int min, int max, boolean canEmpty) {
        try {
            int x = Integer.parseInt(s);
            if (x > min && x <= max) return true;
            System.out.println("Введите числа в нужном диапазоне ");
            return false;
        } catch (NumberFormatException e) {
            System.out.println("Введите число типа integer ");
            return false;
        }
    }
}
