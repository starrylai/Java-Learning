import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class keyWordsFilterAndSort {
    public static List<String> filterAndSort(List<String> inputStrings, String keyWords){
        if (inputStrings == null){
            return new ArrayList<>();
        }
        if (keyWords == null){
            return inputStrings;
        }

        final String LCKW = keyWords.toLowerCase();

        return inputStrings.stream()
                .filter(s -> s.toLowerCase().contains(LCKW))
                .sorted(Comparator.comparing(String::length)
                        .thenComparing(String::compareTo))
                .collect(Collectors.toList());
    }

    public static void main(String[] args){
        List<String>words = Arrays.asList(
                "Java Programming",
                "Python Programming",
                "Cpp Programming",
                "JavaScript",
                "Java research",
                "Matlab Exercise"
        );

        String keyWords = "Java";
        System.out.println("原始列表：");
        words.forEach(System.out::println);

        System.out.println("\n过滤包含"+keyWords+"的字符串：");
        List<String> filteredWords = filterAndSort(words, keyWords);
        filteredWords.forEach(System.out::println);
    }
}
