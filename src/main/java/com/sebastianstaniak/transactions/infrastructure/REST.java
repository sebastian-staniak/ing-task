package com.sebastianstaniak.transactions.infrastructure;

import com.sebastianstaniak.transactions.domain.Account;
import com.sebastianstaniak.transactions.domain.Bank;
import com.sebastianstaniak.transactions.domain.Transaction;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import java.util.Collection;
import java.util.List;

@Controller("/transactions")
public class REST {

    @Post(uri = "/report", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Collection<Account>> generateReport(
            @Body List<Transaction> transactions
    ) {
        Bank bank = new Bank();
        bank.makeTransfers(transactions);

        return HttpResponse.ok(bank.getSortedAccounts().values());
    }
}
