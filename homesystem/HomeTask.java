package dev.timatifey.homesystem;


public class HomeTask {

    int id;
    int studentId;
    long deadlineTimestamp;

    Status status;
    int score;

    int inspectorId;

    HomeTask(int id, int studentId, long deadlineTimestamp) {
        this.id = id;
        this.studentId = studentId;
        this.status = Status.NEED_VERIFY;
        this.deadlineTimestamp = deadlineTimestamp;
    }


    public enum Status {
        NEED_VERIFY,
        VERIFIED
    }
}
