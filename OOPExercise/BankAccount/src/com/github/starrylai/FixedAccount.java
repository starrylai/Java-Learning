package com.github.starrylai;
//定期账户
class FixedAccount extends Account {
    private static final double INTEREST_RATE = 0.035;//年利率
    private int termMonths;//存款期限（月）
    private boolean isMatured;//是否到期

    public FixedAccount(String accountNumber, double initialBalance, String accountHolder, int termMonths) {
        super(accountNumber, initialBalance, accountHolder);
        this.termMonths = termMonths;
        this.isMatured = false;
    }

    @Override
    public boolean withdraw(double amount) {
        if(! isMatured){
            System.out.println("取款失败，定期存款尚未到期");
            return false;
        }

        if(amount > 0 && amount <= balance){
            balance -= amount;
            System.out.println(accountHolder+"取款"+amount+"，当前余额："+balance);
            return true;
        }else{
            System.out.println("取款失败：余额不足或金额无效");
            return false;
        }
    }

    public void mature(){
        this.isMatured = true;
        System.out.println("定期账户"+accountHolder+"已到期，可以取款");
    }

    @Override
    public double calcInterest() {
        return balance * INTEREST_RATE*(termMonths/12.0);//定期利息
    }

    @Override
    public String getAccountType() {
        return "定期账户："+termMonths;
    }

    public int getTermMonths() {return termMonths;}
    public void setTermMonths(int termMonths) {this.termMonths = termMonths;}
    public boolean isMatured() {return isMatured;}
    public void setMatured(boolean isMatured) {this.isMatured = isMatured;}
}
