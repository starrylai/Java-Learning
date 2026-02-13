import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class GenericUtils {
    public static class Pair<L, R>{
        public final L left;
        public final R right;

        public Pair(L left, R right){
            this.left = left;
            this.right = right;
        }

        public L getLeft(){
            return left;
        }

        public R getRight(){
            return right;
        }

        @Override
        public String toString(){
            return "Pair{"+left+","+right+"}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?>pair = (Pair<?, ?>) o;
            return Objects.equals(left, pair.left) && Objects.equals(right, pair.right);
        }

        @Override
        public int hashCode(){
            return Objects.hash(left,right);
        }

        public static <L, R> Pair<L, R> of(L left, R right){
            return new Pair<>(left, right);
        }
    }

    public static <T, R> List<R> map(List<? extends T>list, Function<? super T, ? extends R> function){
        List<R> result = new ArrayList<>();
        for(T element : list){
            result.add(function.apply(element));
        }
        return result;
    }

    public static void main(String[] args){
        Pair<String, Integer> pair1 = Pair.of("age", 25);
        Pair<Integer, String> pair2 = Pair.of(1, "one");
        System.out.println("Pair1:"+pair1);
        System.out.println("Pair2:"+pair2);

        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        List<String> strings = map(numbers, Objects::toString);
        System.out.println("Numbers map to Strings:" + strings);

        List<Integer> squares = map(numbers, x -> x * x);
        System.out.println("Squares:" + squares);

        List<String> words = Arrays.asList("hello","world","java");
        List<Integer> lengths = map(words, String::length);
        System.out.println("Lengths:" + lengths);
    }
}
