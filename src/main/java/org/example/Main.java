package org.example;

public class Main {
    public static void main(String[] args) {
        CheckingSystem system = new CheckingSystem();

        Student student1 = new Student("Student1");
        Student student2 = new Student("Student2");
        Student student3 = new Student("Student3");
        Student student4 = new Student("Student4");
        Student student5 = new Student("Student5");

        for (int i = 1; i <= 10; i++) {
            student1.submitHomework(new Homework(i*100,"Task" + i, i*100, student1.getName()), system);
            student2.submitHomework(new Homework(i*200,"Task" + i, i*200, student2.getName()), system);
            student3.submitHomework(new Homework(i*150,"Task" + i, i*150, student3.getName()), system);
            student4.submitHomework(new Homework(i*120,"Task" + i, i*120, student4.getName()), system);
            student5.submitHomework(new Homework(i*180,"Task" + i, i*180, student5.getName()), system);
        }

        system.startChecking();

        // Подождем когда все домашки будут проверены
        while (system.getAllResults().size() != 50 ) {
        }

        // Получим результаты проверки лаб для каждого студента
        for (int i = 1; i <= 10; i++) {
            var hwStudent1 = new Homework(i*100,"Task" + i, i*100, student1.getName());
            var hwStudent2 = new Homework(i*200,"Task" + i, i*200, student2.getName());
            var hwStudent3 = new Homework(i*150,"Task" + i, i*150, student3.getName());
            var hwStudent4 = new Homework(i*120,"Task" + i, i*120, student4.getName());
            var hwStudent5 = new Homework(i*180,"Task" + i, i*180, student5.getName());

            System.out.println("Task " + i + ": " + system.getResult(hwStudent1));
            System.out.println("Task " + i + ": " + system.getResult(hwStudent2));
            System.out.println("Task " + i + ": " + system.getResult(hwStudent3));
            System.out.println("Task " + i + ": " + system.getResult(hwStudent4));
            System.out.println("Task " + i + ": " + system.getResult(hwStudent5));
        }
    }
}