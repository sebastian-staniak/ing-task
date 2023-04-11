package com.sebastianstaniak.domain;

import java.math.BigDecimal;

public class Transaction {
        public String debitAccount;
        public String creditAccount;
        public BigDecimal amount;

        public Transaction() {
        }

        public Transaction(String debitAccount, String creditAccount, BigDecimal amount) {
                this.debitAccount = debitAccount;
                this.creditAccount = creditAccount;
                this.amount = amount;
        }
}
