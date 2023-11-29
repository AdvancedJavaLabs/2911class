package org.example;

public class Task {
    private boolean isReviewed;
    private int mark;
    public final int id;

    public Task(int id) {
        this.id = id;
        mark = -1;
        isReviewed = false;
    }


    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
