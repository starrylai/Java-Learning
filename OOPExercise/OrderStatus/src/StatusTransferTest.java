import java.util.Set;

public class StatusTransferTest {
    public static void main(String[] args) {
        System.out.println("===合法转移测试===");
        testTransfer(OrderStatus.PENDING,OrderStatus.PAID);
        testTransfer(OrderStatus.PAID,OrderStatus.SHIPPED);
        testTransfer(OrderStatus.SHIPPED,OrderStatus.DELIVERED);
        testTransfer(OrderStatus.DELIVERED,OrderStatus.COMPLETED);

        System.out.println("\n===非法状态转移测试===");
        testTransfer(OrderStatus.PENDING, OrderStatus.SHIPPED);
        testTransfer(OrderStatus.COMPLETED, OrderStatus.PAID);
        testTransfer(OrderStatus.CANCELLED, OrderStatus.PAID);

        System.out.println("\n===退款流程测试===");
        testTransfer(OrderStatus.PAID, OrderStatus.REFUNDING);
        testTransfer(OrderStatus.REFUNDING, OrderStatus.REFUNDED);

        System.out.println("\n===显示所有状态的可转移状态===");
        for(OrderStatus OrSt : OrderStatus.values()){
            Set<OrderStatus> Transferable = OrSt.getTransferableSet();
            System.out.printf("状态%s（%s,code=%d）可转移到：%s\n",
                    OrSt.getDescription(),OrSt.name(),OrSt.getCode(),
                    Transferable.isEmpty()? "无" : Transferable.stream()
                            .map(s -> s.getDescription()+"("+s.name()+")")
                            .reduce((a,b)->a+","+b).orElse(""));
        }
    }

    private static void testTransfer(OrderStatus from, OrderStatus to){
        OrderStatus.TransferResult res = from.validateTransfer(to);
        System.out.println(res.toString());
    }
}
