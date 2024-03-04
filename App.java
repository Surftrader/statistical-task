import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            BigDecimal min = null;
            BigDecimal max = null;
            BigDecimal sum = BigDecimal.ZERO;
            List<BigDecimal> numbers = new LinkedList<>();

            // Incr
            LinkedList<BigDecimal> longestIncrSeq = new LinkedList<>();
            LinkedList<BigDecimal> currentIncrSeq = new LinkedList<>();

            // Decr
            LinkedList<BigDecimal> longestDecrSeq = new LinkedList<>();
            LinkedList<BigDecimal> currentDecrSeq = new LinkedList<>();

            if (args.length > 0) {
                String fileName = args[0];
                File file = new File(fileName);
                if (file.exists() && file.isFile()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            try {
                                BigDecimal number = BigDecimal.valueOf(Double.parseDouble(line.trim()));
                                // MIN
                                if (min == null || number.compareTo(min) <= 0) min = number;
                                // MAX
                                if (max == null || number.compareTo(max) > 0) max = number;
                                // SUM
                                sum = sum.add(number);

                                // Incr
                                if (currentIncrSeq.isEmpty() || number.compareTo(currentIncrSeq.getLast()) > 0) {
                                    currentIncrSeq.add(number);
                                } else {
                                    if (currentIncrSeq.size() > longestIncrSeq.size()) {
                                        longestIncrSeq = new LinkedList<>(currentIncrSeq);
                                    }
                                    currentIncrSeq.clear();
                                    currentIncrSeq.add(number);
                                }

                                if (currentIncrSeq.size() > longestIncrSeq.size()) {
                                    longestIncrSeq = new LinkedList<>(currentIncrSeq);
                                }

                                // Decr
                                if (currentDecrSeq.isEmpty() || number.compareTo(currentDecrSeq.getLast()) < 0) {
                                    currentDecrSeq.add(number);
                                } else {
                                    if (currentDecrSeq.size() > longestDecrSeq.size()) {
                                        longestDecrSeq = new LinkedList<>(currentDecrSeq);
                                    }
                                    currentDecrSeq.clear();
                                    currentDecrSeq.add(number);
                                }

                                if (currentDecrSeq.size() > longestDecrSeq.size()) {
                                    longestDecrSeq = new LinkedList<>(currentDecrSeq);
                                }

                                numbers.add(number);
                            } catch (NumberFormatException e) {
                                System.err.println("NumberFormatException! :" + e.getMessage());
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Something went wrong!");
                    }

                    int size = numbers.size();
                    // MAX
                    System.out.println("max: " + max);
                    // MIN
                    System.out.println("min: " + min);
                    // MEDIAN
                    printMedian(numbers);
                    // AVERAGE
                    System.out.println("average: " + sum.divide(BigDecimal.valueOf(size), 2, RoundingMode.UP));
                    // INCR
                    printList("the largest increasing sequence", longestIncrSeq);
                    // DECR
                    printList("the largest decreasing sequence", longestDecrSeq);

                    long end = System.currentTimeMillis();
                    System.out.println("Program running time: " + (end - start) / 1000 + " sec");
                } else {
                    System.err.println("Wrong filename");
                }
            } else {
                System.err.println("File not found");
            }
        }
        catch (Exception e) {
            System.err.println("Error! " + e.getMessage());
        }
    }

    private static void printMedian(List<BigDecimal> numbers) {
        List<BigDecimal> sortedList = new ArrayList<>(numbers);
        sortedList.sort(Comparator.naturalOrder());
        int sortedSize = sortedList.size();
        int half = sortedSize / 2;
        if (sortedSize != 0) {
            if (sortedSize % 2 == 0) {
                BigDecimal result = (sortedList.get(half).add(sortedList.get(half - 1)))
                        .divide(BigDecimal.valueOf(2), 2, RoundingMode.UP);
                System.out.println("median: " + result);
            } else {
                System.out.println("median: " + sortedList.get(half));
            }
        }
    }

    private static void printList(String name, List<BigDecimal> list) {
        System.out.println(name + "(" + list.size() + "):");
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                System.out.println(list.get(i));
            } else {
                System.out.print(list.get(i) + ", ");
            }
        }
    }
}
