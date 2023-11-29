public record Student(String name) {

    public void submitHomework(HomeworkSystem system, Homework homework) {
        system.receiveHomework(homework);
    }
}
