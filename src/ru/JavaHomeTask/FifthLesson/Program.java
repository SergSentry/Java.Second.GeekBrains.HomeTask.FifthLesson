package ru.JavaHomeTask.FifthLesson;

import java.util.Arrays;

/**
 * Класс программы домашнего задания пятого урока
 */
public class Program {

    private static final int MAX_COEF_SIZE = 10000000;
    private static final int HALF_COEF_SIZE = MAX_COEF_SIZE >> 1;

    /**
     * Точка входа в приложение
     *
     * @param args параметры командной строки
     */
    public static void main(String[] args) throws InterruptedException {
        float[] resultFirst = simpleMethod();
        float[] resultSecond = multiThreadMethod();

        if (Arrays.compare(resultFirst, resultSecond) == 0)
            System.out.println("Коэффициенты вычисленные разными способами равны.");
    }

    private static float[] initializeCoefficient() {
        float[] retArray = new float[MAX_COEF_SIZE];
        Arrays.fill(retArray, 1f);
        return retArray;
    }

    private static float[] simpleMethod() {
        float[] array = initializeCoefficient();
        long beginTimeMillis = System.currentTimeMillis();

        for (int i = 0; i < array.length; i++) {
            float coef = 0.2f + i / 5f;
            array[i] = (float) (array[i] * Math.sin(coef) * Math.cos(coef) * Math.cos(0.4f + i / 2f));
        }

        long resultCalcTime = System.currentTimeMillis() - beginTimeMillis;
        System.out.printf("Вычисления коэффициентов в одном потоке заняло: %d (милисек)\n", resultCalcTime);
        return array;
    }

    private static float[] multiThreadMethod() throws InterruptedException {
        float[] array = initializeCoefficient();
        long beginTimeMillis = System.currentTimeMillis();

        CoefficientResolver resolverFirst = new CoefficientResolver(array, 0, HALF_COEF_SIZE);
        Thread threadFirst = new Thread(resolverFirst, "Поток 1");
        CoefficientResolver resolverSecond = new CoefficientResolver(array, HALF_COEF_SIZE, HALF_COEF_SIZE);
        Thread threadSecond = new Thread(resolverSecond, "Поток 2");
        threadFirst.start();
        threadSecond.start();
        threadFirst.join();
        threadSecond.join();
        System.arraycopy(resolverFirst.getArray(), 0, array, 0, HALF_COEF_SIZE);
        System.arraycopy(resolverSecond.getArray(), 0, array, HALF_COEF_SIZE, HALF_COEF_SIZE);

        long resultCalcTime = System.currentTimeMillis() - beginTimeMillis;
        System.out.printf("Вычисления коэффициентов в двух потоках заняло: %d (милисек)\n", resultCalcTime);
        return array;
    }
}
