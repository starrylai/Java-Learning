import java.util.Set;
import java.util.EnumSet;

public enum OrderStatus {
    PENDING("待支付",1){
        @Override
        public boolean canTransferTo(OrderStatus tarSt){
            return tarSt == PAID || tarSt == CANCELLED;
        }
    },
    PAID("已支付",2){
        @Override
        public boolean canTransferTo(OrderStatus tarSt){
            return tarSt == SHIPPED || tarSt == REFUNDING;
        }
    },
    SHIPPED("已发货",3){
        @Override
        public boolean canTransferTo(OrderStatus tarSt){
            return tarSt == REFUNDING || tarSt == DELIVERED;
        }
    },
    DELIVERED("已送达",4){
        @Override
        public boolean canTransferTo(OrderStatus tarSt){
            return tarSt == REFUNDING || tarSt == COMPLETED;
        }
    },
    COMPLETED("已完成",5){
        @Override
        public boolean canTransferTo(OrderStatus tarSt){
            return tarSt == REFUNDING;
        }
    },
    CANCELLED("已取消",6){
        @Override
        public boolean canTransferTo(OrderStatus tarSt){
            return false;
        }
    },
    REFUNDING("退款中",7){
        @Override
        public boolean canTransferTo(OrderStatus tarSt){
            return tarSt == REFUNDED;
        }
    },
    REFUNDED("已退款",8){
        @Override
        public boolean canTransferTo(OrderStatus tarSt){
            return false;
        }
    };

    private final String description;
    private final int code;

    OrderStatus(String description, int code){
        this.description = description;
        this.code = code;
    }

    public String getDescription(){
        return description;
    }

    public int getCode(){
        return code;
    }

    public abstract boolean canTransferTo(OrderStatus targetStatus);

    public TransferResult validateTransfer(OrderStatus targetStatus){
        if(this == targetStatus){
            System.out.println("已在当前状态");
            return new TransferResult(false,"不能转移到当前状态");
        }
        if(canTransferTo(targetStatus)){
            return new TransferResult(true,String.format("状态转移成功：%s -> %s",
                    this.description,targetStatus.description));
        }else{
            return new TransferResult(false,String.format("非法状态转移：%s -> %s",
                    this.description,targetStatus.description));
        }
    }

    public TransferResult transferProcess(OrderStatus targetStatus){
        TransferResult result = validateTransfer(targetStatus);
        if(result.isSuccess()){
            System.out.println("执行状态转移:"+this.description+" -> "+targetStatus.description);
        }
        return result;
    }

    public static class TransferResult{
        private final boolean success;
        private final String message;

        private TransferResult(boolean success, String message){
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess(){
            return success;
        }

        public String getMessage(){
            return message;
        }

        @Override
        public String toString(){
            return String.format("TransferResult{success=%s, message='%s'}", success, message);
        }

    }

    public Set<OrderStatus> getTransferableSet(){
        Set<OrderStatus> Transferable = EnumSet.noneOf(OrderStatus.class);
        for(OrderStatus St : OrderStatus.values()){
            if(this != St && canTransferTo(St)){
                Transferable.add(St);
            }
        }
        return Transferable;
    }

}
