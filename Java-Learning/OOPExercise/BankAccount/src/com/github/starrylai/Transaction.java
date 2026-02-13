package com.github.starrylai;

class Transaction {
    private String type;
    private String accountNumber;
    private double amount;
    private String timestamp;

    public Transaction(String type, String accountNumber, double amount) {
        this.type = type;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    @Override
    public String toString() {
        return String.format("账户：%s，交易类型：%s，金额：%.2f，时间：%s",
                accountNumber,type,amount,timestamp);
    }
}
