package dev.timatifey.homesystem;


public class Inspector extends Thread {

    int id;
    private final VerifySystem verifySystem;

    public Inspector(int id, VerifySystem verifySystem) {
        this.id = id;
        this.verifySystem = verifySystem;
    }

    @Override
    public void run() {
        while (true) {
            try {
                verifySystem.verifyTask(id);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

