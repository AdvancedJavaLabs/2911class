public class Main {
    public static void main(String[] args) {
        HomeworkCheckingSystem system = new HomeworkCheckingSystem(3);

        String[] homeworkNames = {"Математика", "Физика", "Литература", "История", "Биология"};

        while (true) {
            int studentId = (int) (Math.random() * 100);
            int homeworkId = (int) (Math.random() * 100);
            String homeworkName = homeworkNames[(int) (Math.random() * homeworkNames.length)];
            long deadline = System.currentTimeMillis() + (long) (Math.random() * 10);

            Student student = new Student(studentId);
            student.submitHomework(system, new Homework(homeworkId, homeworkName, deadline));

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
