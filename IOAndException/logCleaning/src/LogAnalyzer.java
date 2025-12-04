import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogAnalyzer {
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[]  args){
        String logFilePath = "application.log";
        try{
            Map<LocalDate, Integer> errorCountByDay = analyzeLogFile(logFilePath);
            System.out.println("===日志每日ERROR统计===");
            for(Map.Entry<LocalDate, Integer> entry : errorCountByDay.entrySet()){
                System.out.printf("日期：%s，ERROR数量：%d%n",entry.getKey(), entry.getValue());
            }

        }catch(IOException e){
            System.err.println("读取日志失败"+e.getMessage());
        }
    }

    public static Map<LocalDate, Integer> analyzeLogFile(String logFilePath) throws IOException{
        Map<LocalDate, Integer> errorCountByDay = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(logFilePath));
        for(String line : lines){
            try{
                processLogLine(line, errorCountByDay);
            }catch(Exception e){
                System.err.println("无法解析日志行："+ line);
            }
        }
        return errorCountByDay;
    }

    private static void processLogLine(String line, Map<LocalDate, Integer> errorCountByDay) throws IOException{
        if(line == null || line.trim().isEmpty()){
            return;
        }
        //提取时间
        String timeString = line.substring(0,19);
        LocalDateTime dateTime = LocalDateTime.parse(timeString, DTF);
        //提取日志级别
        String level;
        if(line.length()>20){
            String afterTime = line.substring(20);
            String[] parts = afterTime.split("\\s+",2);//级别+信息
            level = parts[0].trim();
        }else{
            return;
        }

        if(level.equalsIgnoreCase("ERROR")){
            LocalDate date = dateTime.toLocalDate();
            errorCountByDay.put(date, errorCountByDay.getOrDefault(date, 0) + 1);
        }
    }

}
