package com.sebastianstaniak

import com.sebastianstaniak.domain.Bank
import com.sebastianstaniak.domain.Transaction
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

@MicronautTest
class BankSpec extends Specification {

    void 'test it works'() {
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

        and:
            def list = results.values()
            list[0].getIBAN() == "06105023389842834748547303"
            list[1].getIBAN() == "31074318698137062235845814"
            list[2].getIBAN() == "32309111922661937852684864"
            list[3].getIBAN() == "66105036543749403346524547"
    }

}
