import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sequence {
    public static void generateSequence(Queue<Integer> q) {
        // Generate a list of numbers from 0 to 6
        List<Integer> sequence = IntStream.rangeClosed(1, 7)
            .boxed()
            .collect(Collectors.toList());

        // Shuffle the list
        Collections.shuffle(sequence);

        // Print the shuffled list
        System.out.println(sequence);
        // Add the shuffled list elements to the queue
        for (int i = 0; i < 7; i++) q.add(sequence.get(i));
    }
}