import java.util.Comparator;

public class HomeWorkComparator implements Comparator<HomeWork> {

    @Override
    public int compare(HomeWork o1, HomeWork o2) {
        return o1.deadLine().compareTo(o2.deadLine());
    }

}
