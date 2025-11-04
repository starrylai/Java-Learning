package com.github.starrylai;

abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String accountHolder;

    public Account(String accountNumber, double initialBalance, String accountHolder) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
        this.accountHolder = accountHolder;
    }

    //存款
    public void deposit(double amount){
        if (amount > 0){
        balance += amount;
        System.out.println(accountHolder+"存入"+amount+"，当前余额为："+balance);
        }else{
            System.out.println("存款金额必须大于0");
        }
    }

    //取款
    public abstract boolean withdraw(double amount);

    //计算利息
    public abstract double calcInterest();

    //获取账户信息
    public void displayAccountInfo(){
        System.out.println("账户号码："+accountNumber);
        System.out.println("账户持有人："+accountHolder);
        System.out.println("账户类型："+getAccountType());
        System.out.println("当前余额："+balance);
    }

    //获取账户类型
    public abstract String getAccountType();

    public String getAccountNumber() {return accountNumber;}
    public void setAccountNumber(String accountNumber) {this.accountNumber = accountNumber;}
    public double getBalance() {return balance;}
    public void setBalance(double balance) {this.balance = balance;}
    public String getAccountHolder() {return accountHolder;}
    public void setAccountHolder(String accountHolder) {this.accountHolder = accountHolder;}

}
