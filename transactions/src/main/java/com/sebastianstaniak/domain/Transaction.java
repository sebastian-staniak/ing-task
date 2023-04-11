package com.sebastianstaniak.domain;

import java.math.BigDecimal;

public class Transaction {
        private String debitAccount;
        private String creditAccount;
        private BigDecimal amount;

        public Transaction() {
        }

        public Transaction(String debitAccount, String creditAccount, BigDecimal amount) {
                this.debitAccount = debitAccount;
                this.creditAccount = creditAccount;
                this.amount = amount;
        }

        public String getDebitAccount() {
                return debitAccount;
        }

        public String getCreditAccount() {
                return creditAccount;
        }

        public BigDecimal getAmount() {
                return amount;
        }
}
