package com.sebastianstaniak.transactions.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Account implements Comparable<Account> {
    private final String iban;
    // AtomicLong are used there in order to have guaranteed multi thread support.
    // Conversion from double is very naive.
    // but task did not set any rules on that.
    private final AtomicLong debitCount = new AtomicLong(0);
    private final AtomicLong creditCount = new AtomicLong(0);
    private final AtomicLong balance = new AtomicLong(0);

    public Account(String iban) {
        this.iban = iban;
    }

    public void credit(BigDecimal amount) {
        this.balance.addAndGet(bigDecimalToInternalMoneyRepresentation(amount));
        this.creditCount.incrementAndGet();
    }

    public void debit(BigDecimal amount) {
        this.balance.addAndGet(bigDecimalToInternalMoneyRepresentation(amount) * -1);
        this.debitCount.incrementAndGet();

    }

    public BigDecimal getBalance() {
        return new BigDecimal(this.balance.longValue())
                .setScale(2, RoundingMode.HALF_EVEN)
                .divide(new BigDecimal(100), RoundingMode.HALF_EVEN);
    }

    public int getDebitCount() {
        return debitCount.intValue();
    }

    public int getCreditCount() {
        return creditCount.intValue();
    }

    public String getIban() {
        return iban;
    }

    @Override
    public int compareTo(Account another) {
        return this.iban.compareTo(another.getIban());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(iban, account.iban) && Objects.equals(debitCount, account.debitCount) && Objects.equals(creditCount, account.creditCount) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban, debitCount, creditCount, balance);
    }

    private long bigDecimalToInternalMoneyRepresentation(BigDecimal amount) {
        return amount
                .setScale(2, RoundingMode.HALF_EVEN)
                .multiply(new BigDecimal(100))
                .longValue();
    }
}
