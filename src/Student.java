public class Student {
    private final int id;

    public Student(int id) {
        this.id = id;
    }

    public void submitHomework(HomeworkCheckingSystem system, Homework homework) {
        system.receiveHomework(homework);
    }
}
