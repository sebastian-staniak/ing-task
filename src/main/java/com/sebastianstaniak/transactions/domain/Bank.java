package com.sebastianstaniak.transactions.domain;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bank {
    private final TreeMap<String, Account> accounts = new TreeMap<>();

    public void makeTransfers(List<Transaction> transactions) {
        openAccounts(transactions);

        transactions
                .parallelStream()
                .forEach(transaction ->
                {
                    this.accounts.get(transaction.getDebitAccount()).debit(transaction.getAmount());
                    this.accounts.get(transaction.getCreditAccount()).credit(transaction.getAmount());
                });
    }

    private void openAccounts(List<Transaction> transactions) {
        transactions
                .parallelStream()
                .flatMap(transaction ->
                        Stream.of(transaction.getCreditAccount(), transaction.getDebitAccount())
                )
                .collect(Collectors.toSet())
                .forEach(id -> this.accounts.put(id, new Account(id)));
    }

    public SortedMap<String, Account> getSortedAccounts() {
        return this.accounts;
    }
}
