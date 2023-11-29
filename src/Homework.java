import java.time.LocalDateTime;

public record Homework(
        String content,
        String authorName,
        LocalDateTime sendDateTime
) implements Comparable<Homework> {
    @Override
    public int compareTo(Homework o) {
        if (this.sendDateTime.isAfter(o.sendDateTime)) {
            return 1;
        } else if (this.sendDateTime.isBefore(o.sendDateTime)) {
            return -1;
        }

        return 0;
    }
}
