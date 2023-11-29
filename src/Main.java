public class Main {
    public static void main(String[] args) {
        HomeWorkCheckSystem system = new HomeWorkCheckSystem();
        system.start();
        for (var i = 0; i < 15; i++) {
            Student st = new Student(system, i);
            st.start();
        }
    }
}