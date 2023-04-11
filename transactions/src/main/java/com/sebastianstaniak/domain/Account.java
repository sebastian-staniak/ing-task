package com.sebastianstaniak.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicInteger;

public class Account implements Comparable<Account> {
    private final String IBAN;
    private final AtomicInteger debitCount = new AtomicInteger(0);
    private final AtomicInteger creditCount = new AtomicInteger(0);
    private final AtomicInteger balance = new AtomicInteger(0);

    public Account(String iban) {
        IBAN = iban;
    }

    public void credit(BigDecimal amount) {
        this.balance.addAndGet(amount.multiply(new BigDecimal(100)).intValue());
        this.creditCount.incrementAndGet();
    }

    public void debit(BigDecimal amount) {
        this.balance.addAndGet(amount.multiply(new BigDecimal(-100)).intValue());
        this.debitCount.incrementAndGet();

    }

    public BigDecimal getBalance() {
        return new BigDecimal(this.balance.intValue())
                .setScale(2, RoundingMode.HALF_EVEN)
                .divide(new BigDecimal(100), RoundingMode.HALF_EVEN);
    }

    public int getDebitCount() {
        return debitCount.intValue();
    }

    public int getCreditCount() {
        return creditCount.intValue();
    }

    public String getIBAN() {
        return IBAN;
    }

    @Override
    public int compareTo(Account another) {
        return this.IBAN.compareTo(another.getIBAN());
    }
}
