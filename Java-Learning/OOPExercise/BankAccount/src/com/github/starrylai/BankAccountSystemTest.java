package com.github.starrylai;

public class BankAccountSystemTest {
    public static void main(String[] args) {
        BankSystem bankSystem = new BankSystem();

        Account currentAccount = new CurrentAccount("A001",5000,"张三");
        bankSystem.addAccount(currentAccount);
        Account fixedAccount = new FixedAccount("B001",10000,"李四",12);
        bankSystem.addAccount(fixedAccount);

        String[][] transactions = {
                {"A001","deposit","2000"},
                {"A001","withdraw","3000"},
                {"A001","withdraw","5000"},//测试透支
                {"B001","deposit","3000"},
                {"B001","withdraw","2000"},//测试未到期能否取款
                {"A001","deposit","1000"},
        };

        bankSystem.addTransaction(transactions);
        ((FixedAccount)fixedAccount).mature();

        String[][] maturedTransactions = {{"B001","withdraw","2000"}};
        bankSystem.addTransaction(maturedTransactions);

        bankSystem.calcAndDispFinalState();
        bankSystem.dispTransactionsHistory();
    }
}
