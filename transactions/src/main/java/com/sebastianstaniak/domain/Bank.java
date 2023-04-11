package com.sebastianstaniak.domain;

import java.util.List;
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
                    Account to = this.accounts.get(transaction.creditAccount);
                    Account from = this.accounts.get(transaction.debitAccount);

                    from.debit(transaction.amount);
                    to.credit(transaction.amount);
                });
    }

    private void openAccounts(List<Transaction> transactions) {
        transactions
                .parallelStream()
                .flatMap(transaction ->
                        Stream.of(transaction.creditAccount, transaction.debitAccount)
                )
                .collect(Collectors.toSet())
                .forEach(id -> this.accounts.put(id, new Account(id)));
    }

    public TreeMap<String, Account> getSortedAccounts() {
        return this.accounts;
    }
}
