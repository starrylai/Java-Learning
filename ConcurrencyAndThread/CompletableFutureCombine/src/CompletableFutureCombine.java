import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureCombine {
    public static CompletableFuture<Integer> getServiceA(){
        return CompletableFuture.supplyAsync(() -> {
            try{
                TimeUnit.MILLISECONDS.sleep(100+new Random().nextInt(100));
                System.out.println("Service A执行完成，返回10");
                return 10;
            }catch(InterruptedException e){
                throw new RuntimeException("Service A中断异常", e);
            }
        }).exceptionally(throwable -> {
            System.err.println("Service A异常" + throwable.getMessage());
            return 0;
        });
    }

    public static CompletableFuture<Integer> getServiceB(){
        return CompletableFuture.supplyAsync(() -> {
            try{
                TimeUnit.MILLISECONDS.sleep(150+new Random().nextInt(100));
                if (new Random().nextInt(100) < 30){
                    throw new RuntimeException("Service B 运行异常");
                }
                System.out.println("Service B执行完成，返回20");
                return 20;
            }catch(InterruptedException e){
                throw new RuntimeException("Service B中断异常", e);
            }
        }).exceptionally(throwable -> {
            System.err.println("Service B异常" + throwable.getMessage());
            return 0;
        });
    }

    public static void combineServices(){
        CompletableFuture<Integer> futureA = getServiceA();
        CompletableFuture<Integer> futureB = getServiceB();

        CompletableFuture<Integer> futureCombine = futureA.thenCombine(futureB, (rA,rB) ->
        {
            System.out.println("组合结果："+rA+"+"+rB+"="+(rA+rB));
            return rA+rB;
        });

        try{
            Integer FR =  futureCombine.get();
            System.out.println("最终结果："+FR);

            if(FR == 30){
                System.out.println("两个服务正常执行，得到预期结果：30");
            }else{
                System.out.println("某个服务发生异常，组合结果为：" + FR);
            }
        }catch(InterruptedException |ExecutionException e){
            System.err.println("组合过程异常："+e.getMessage());
        }
    }

    public static void main(String[] args) {
        combineServices();
    }
}
