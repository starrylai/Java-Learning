import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class toMapConflict {
    static class keyValue{
        String key;
        int value;

        public keyValue(String key,int value){
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }
        public void setKey(String key) {
            this.key = key;
        }
        public int getValue() {
            return value;
        }
        public void setValue(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args){
        List<keyValue> kYs = Arrays.asList(
                new keyValue("key1",10),
                new keyValue("key2",20),
                new keyValue("key3",30),
                new keyValue("key1",20),
                new keyValue("key2",40)
        );

        Map<String, Integer> newMap = kYs.stream()
                .collect(Collectors.toMap(
                        keyValue -> keyValue.getKey(),
                        keyValue -> keyValue.getValue(),
                        Math::max
                ));

        System.out.println(newMap);
    }
}
