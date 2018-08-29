package ru.JavaHomeTask.FifthLesson;

public final class CoefficientResolver implements Runnable {
    private final float[] array;
    private final int index;

    public CoefficientResolver(float[] array, int index, int count) {
        this.array = new float[count];
        System.arraycopy(array, index, this.array, 0, count);
        this.index = index;
    }

    @Override
    public void run() {
        for (int i = 0, step = index; i < array.length; i++, step++) {
            float coef = 0.2f + step / 5f;
            array[i] = (float) (array[i] * Math.sin(coef) * Math.cos(coef) * Math.cos(0.4f + step / 2f));
        }
    }

    public float[] getArray() {
        return array;
    }
}
