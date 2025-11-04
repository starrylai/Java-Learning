package com.github.starrylai;
//活期账户
class CurrentAccount extends Account {
    private static final double INTEREST_RATE = 0.003;//年利率

    public CurrentAccount(String accountNumber, double initialBalance, String accountHolder) {
        super(accountNumber, initialBalance, accountHolder);
    }

    @Override
    public boolean withdraw(double amount) {
        if(amount > 0 && amount <= balance){
            balance -= amount;
            System.out.println(accountHolder+"取款"+amount+"，当前余额："+balance);
            return true;
        }else{
            System.out.println("取款失败：余额不足或金额无效");
            return false;
        }
    }

    @Override
    public double calcInterest() {
        return balance * INTEREST_RATE;//计算当前余额的年利率
    }

    @Override
    public String getAccountType() {
        return "活期账户";
    }
}
