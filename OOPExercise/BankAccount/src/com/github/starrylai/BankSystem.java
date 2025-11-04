package com.github.starrylai;

import java.util.ArrayList;
import java.util.List;

class BankSystem {
    private List<Account> accounts;
    private List<Transaction> transactions;

    public BankSystem() {
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }

    //添加账户
    public void addAccount(Account account) {
        accounts.add(account);
        System.out.println("账号创建成功："+account.getAccountNumber());
    }

    //执行交易序列
    public void addTransaction(String[][] transactionSequence) {
        for(String[] transaction : transactionSequence){
            if(transaction.length < 3)continue;
            String accountNumber = transaction[0];
            String type = transaction[1];
            double amount = Double.parseDouble(transaction[2]);
            Account account = findAccount(accountNumber);
            if(account == null){
                System.out.println("账户不存在："+accountNumber);
                continue;
            }
            transactions.add(new Transaction(type, accountNumber, amount));

            switch(type.toLowerCase()){
                case "deposit":
                    account.deposit(amount);
                    break;
                case "withdraw":
                    account.withdraw(amount);
                    break;
                default:
                    System.out.println("未知交易类型："+type);
            }
        }
    }
    private Account findAccount(String accountNumber){
        return accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst().orElse(null);
    }

    public void calcAndDispFinalState(){
        System.out.println("\n===最终账户状态===");
        for(Account account: accounts){
            double interest = account.calcInterest();
            System.out.println("账户："+account.getAccountNumber());
            System.out.println("持有人："+account.getAccountHolder());
            System.out.println("类型: " + account.getAccountType());
            System.out.println("最终余额: " + account.getBalance());
            System.out.println("应计利息: " + interest);
            System.out.println("预计总金额: " + (account.getBalance() + interest));
            System.out.println("------------------------");
        }
    }

    public void dispTransactionsHistory(){
        System.out.println("\n===交易历史===");
        for(Transaction transaction: transactions){
            System.out.println(transaction);
        }
    }
}
