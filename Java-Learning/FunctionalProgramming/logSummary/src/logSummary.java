import java.util.*;
import java.util.stream.Collectors;

public class logSummary {
    static class logRecord{
        String username;
        double cost;

        logRecord(String username,double cost){
            this.username = username;
            this.cost = cost;
        }

    }

    public static void main(String[] args){
        List<logRecord> LR = new ArrayList<> (
                Arrays.asList(new logRecord("u1",10),
                        new logRecord("u2",20),
                        new logRecord("u1",30)
                )
        );

        Map<String, DoubleSummaryStatistics> summary = LR.stream().
                collect(Collectors.groupingBy(lr -> lr.username,
                        Collectors.summarizingDouble(lr -> lr.cost)
                ));

        StringBuilder sb = new StringBuilder("{");
        summary.forEach((user, su) -> {
            sb.append(String.format(
                    "%s:sum = %.1f, avg = %.1f, count = %d;\n",
                    user, su.getSum(), su.getAverage(), su.getCount()
            ));
        });
        if(sb.length()>1){
            sb.setLength(sb.length()-2);
        }
        sb.append("}");
        System.out.println(sb.toString());
    }

}
