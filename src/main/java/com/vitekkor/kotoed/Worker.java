package com.vitekkor.kotoed;

public class Worker extends Thread {
    private final Kotoed kotoed;

    public int getWorkerId() {
        return id;
    }

    private final int id;

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    private boolean isWorking = false;

    public Worker(Kotoed kotoed, int id) {
        this.kotoed = kotoed;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            kotoed.checkHomeWork(this);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
