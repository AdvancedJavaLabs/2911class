public class Homework implements Comparable<Homework> {
    private final int id;
    private final String name;
    private final long deadline;

    public Homework(int id, String name, long deadline) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getDeadline() {
        return deadline;
    }

    @Override
    public int compareTo(Homework other) {
        return Long.compare(this.deadline, other.deadline);
    }
}
