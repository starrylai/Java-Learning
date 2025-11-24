import java.io.IOException;
import java.util.Comparator;
import java.util.regex.Pattern;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class WordFrequency {
    public static void main(String[] args) {
        String currentDir = System.getProperty("user.dir");
        System.out.println("当前工作目录: " + currentDir);
        String filePath = "Beauty.txt";
        try{
            Pattern pattern = Pattern.compile("[^a-zA-Z]");
            Map<String,Integer>wordCountMap = Files.lines(Paths.get(filePath)).
                    flatMap(pattern::splitAsStream).filter(word -> !word.isEmpty()).
                    map(String::toLowerCase).collect(Collectors.toMap(
                            word -> word,
                            word -> 1,
                            Integer::sum
                    ));

            wordCountMap.entrySet().stream().
                    sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                    limit(10)
                    .forEach(entry ->
                            System.out.println(entry.getKey() + " : " + entry.getValue()));
        }catch(IOException e){
            System.err.println("读取文件时出错："+e.getMessage());
            e.printStackTrace();
        }
    }
}
