package com.vitekkor.kotoed;

public class Worker extends Thread {
    private final Kotoed kotoed;

    private final int id;

    public Worker(Kotoed kotoed, int id) {
        this.kotoed = kotoed;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            kotoed.checkHomeWork(id);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
