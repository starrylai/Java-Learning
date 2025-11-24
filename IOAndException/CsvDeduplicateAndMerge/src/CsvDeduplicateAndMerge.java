import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class CsvDeduplicateAndMerge {
    public static void main(String[] args) {
        String file1 = "file1.csv";
        String file2 = "file2.csv";
        String outputFile = "merge.csv";
        try{
            creatCsvFile(file1, file2);
            System.out.println("示例文件创建成功");
        }catch(IOException e){
            System.err.println("创建文件时发生错误："+e.getMessage());
            e.printStackTrace();
        }

        try{
            dedupAndMerg(file1,file2,outputFile);
            System.out.println("去重合并成功："+outputFile);
        }catch(IOException e){
            System.err.println("处理CSV文件时发生错误："+e.getMessage());
            e.printStackTrace();
        }
    }

    private static void dedupAndMerg(String Path1, String Path2, String outPath) throws IOException {
        Map<String, String> mergedData = new LinkedHashMap<>();
        readCsvFile(Path1,mergedData);
        int firRec = mergedData.size();
        System.out.println("第一个文件读取完成，记录数："+firRec);

        readCsvFile(Path2,mergedData);
        int newRec = mergedData.size();
        System.out.println("第二个文件读取完成，新增记录数："+(newRec-firRec));

        writeCsvFile(outPath,mergedData);
        System.out.println("合并完成，总记录数："+newRec);


    }

    private static void readCsvFile(String filePath, Map<String, String>mergedData) throws IOException{
        try(BufferedReader br = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)){
            String line;

            while((line = br.readLine())!=null){
                line = line.trim();
                if(line.isEmpty()){
                    continue;
                }
                String priKey = extractPriKey(line);
                if(priKey != null && !priKey.isEmpty()){
                    mergedData.put(priKey,line);
                }
            }
        }
    }

    private static String extractPriKey(String csvLine){
        //以ID为主键
        String[] columns = csvLine.split(",");
        if(columns.length > 0){
            return columns[0].trim();
        }
        return null;
    }

    private static void writeCsvFile(String outPath, Map<String, String> mergedData) throws IOException {
        try(BufferedWriter bw = Files.newBufferedWriter(Paths.get(outPath), StandardCharsets.UTF_8)){
            for(String row:mergedData.values()){
                bw.write(row);
                bw.newLine();
            }
        }
    }

    public static void creatCsvFile(String Path1, String Path2) throws IOException{
        //创建第一个文件
        try(BufferedWriter bw = Files.newBufferedWriter(Paths.get(Path1), StandardCharsets.UTF_8)){
            bw.write("ID, Name, Age, E-mail");
            bw.newLine();
            bw.write("1,张三,18,zhangsan@example.com");
            bw.newLine();
            bw.write("2,李四,24,lisi@example.com");
            bw.newLine();
            bw.write("3,王五,22,wangwu@example.com");
        }

        //创建第二个文件
        try(BufferedWriter bw = Files.newBufferedWriter(Paths.get(Path2), StandardCharsets.UTF_8)){
            bw.write("ID, Name, Age, E-mail");
            bw.newLine();
            bw.write("3,王五,19,wangwu_update@example.com");
            bw.newLine();
            bw.write("4,赵六,33,zhaoliu@example.com");
            bw.newLine();
            bw.write("5,孙七,27,sunqi@example.com");
        }
    }
}
