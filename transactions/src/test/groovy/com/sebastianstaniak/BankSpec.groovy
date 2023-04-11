package com.sebastianstaniak

import com.sebastianstaniak.domain.Bank
import com.sebastianstaniak.domain.Transaction
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

@MicronautTest
class BankSpec extends Specification {

    void 'test it can calculate results'() {
        given:
            def bank = new Bank()
            def transactions = [
                    new Transaction("31074318698137062235845814", "66105036543749403346524547", 200.90),
                    new Transaction("66105036543749403346524547", "32309111922661937852684864", 50.10),
                    new Transaction("32309111922661937852684864", "06105023389842834748547303", 10.90),
            ]

        when:
            bank.makeTransfers(transactions)

        then:
            def results = bank.getSortedAccounts();
            results.get("06105023389842834748547303").getBalance() == 10.90
            results.get("31074318698137062235845814").getBalance() == -200.90
            results.get("32309111922661937852684864").getBalance() == 39.20
            results.get("66105036543749403346524547").getBalance() == 150.80

        and:
            results.get("06105023389842834748547303").getDebitCount() == 0
            results.get("31074318698137062235845814").getDebitCount() == 1
            results.get("32309111922661937852684864").getDebitCount() == 1
            results.get("66105036543749403346524547").getDebitCount() == 1

        and:
            results.get("06105023389842834748547303").getCreditCount() == 1
            results.get("31074318698137062235845814").getCreditCount() == 0
            results.get("32309111922661937852684864").getCreditCount() == 1
            results.get("66105036543749403346524547").getCreditCount() == 1
    }

    void 'test it stores account sorted'() {
        given:
            def bank = new Bank()
            def transactions = [
                    new Transaction("5", "4", 200.90),
                    new Transaction("1", "2", 50.10),
                    new Transaction("3", "2", 10.90),
            ]

        when:
            bank.makeTransfers(transactions)

        then:
            def list = bank.getSortedAccounts().values()
            list[0].getIban() == "1"
            list[1].getIban() == "2"
            list[2].getIban() == "3"
            list[3].getIban() == "4"
            list[4].getIban() == "5"
    }

    void 'test it can calculate big numbers'() {
        given:
            def bank = new Bank()
            def transactions = [
                    new Transaction("1", "2", 2147483690.0),
                    new Transaction("3", "2", 2147483690.0),
                    new Transaction("4", "2", 2147483690.0),
            ]

        when:
            bank.makeTransfers(transactions)
            def results = bank.getSortedAccounts();

        then:
            results.get("1").getBalance() == -2147483690.00
            results.get("2").getBalance() == 6442451070.00
            results.get("3").getBalance() == -2147483690.00
            results.get("4").getBalance() == -2147483690.00
    }

    void 'test it works well with different rounding'() {
        given:
            def bank = new Bank()
            def transactions = [
                    new Transaction("1", "2", 1.00000),
                    new Transaction("3", "4", 1.00001),
            ]

        when:
            bank.makeTransfers(transactions)
            def results = bank.getSortedAccounts();

        then:
            results.get("2").getBalance() == 1.00
            results.get("4").getBalance() == 1.00
            results.get("1").getBalance() == -1.00
            results.get("3").getBalance() == -1.00
    }

}
