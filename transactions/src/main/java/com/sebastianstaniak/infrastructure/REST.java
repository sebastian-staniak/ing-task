package com.sebastianstaniak.infrastructure;

import com.sebastianstaniak.domain.Account;
import com.sebastianstaniak.domain.Bank;
import com.sebastianstaniak.domain.Transaction;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import java.util.Collection;
import java.util.List;

@Controller("/transactions")
public class REST {
    private final Bank bank = new Bank();

    @Post(uri = "/report", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Collection<Account>> generateReport(
            @Body List<Transaction> transactions
    ) {
        bank.makeTransfers(transactions);

        return HttpResponse.ok(bank.getSortedAccounts().values());
    }
}
