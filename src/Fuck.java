import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


public class Fuck {

    public static void main(String[] args) {
        int threshold = 10;
        List<Double> numbers = range(128);
        System.out.println("Numbers: " + numbers);

        new Threads.ApplicatorTask<>(
                Math::sin, threshold,
                numbers, 0, numbers.size())
                .invoke();

        System.out.println("Sinuses: " + numbers);
    }

    private static List<Double> range(int to) {
        return IntStream.range(1, to)
                .asDoubleStream().boxed()
                .collect(toList());
    }
}