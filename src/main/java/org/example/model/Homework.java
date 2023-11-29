package org.example.model;

public class Homework implements Comparable<Homework> {
    private final Student student;
    private final String content;
    private final long deadline;
    private String result;

    public Homework(Student student, String content, long deadline) {
        this.student = student;
        this.content = content;
        this.deadline = deadline;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Student getStudent() {
        return student;
    }

    public String getContent() {
        return content;
    }

    public long getDeadline() {
        return deadline;
    }

    public String getResult() {
        return result;
    }

    @Override
    public int compareTo(Homework o) {
        return Long.compare(this.deadline, o.deadline);
    }
}
