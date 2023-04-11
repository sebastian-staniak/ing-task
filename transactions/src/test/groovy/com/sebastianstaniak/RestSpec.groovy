package com.sebastianstaniak

import com.sebastianstaniak.domain.Account
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class RestSpec extends Specification {

    @Inject
    @Client('/')
    HttpClient client

    void 'test it servers data via REST'() {
        given:
            def request = [
                    [
                            debitAccount : 1,
                            creditAccount: 2,
                            amount       : 1
                    ]
            ]

        when:
            def result = client.toBlocking().retrieve(
                    HttpRequest.POST('/transactions/report', request), List<Account>
            )

        then:
            result[0].iban == "1"
            result[0].debitCount == 1
            result[0].creditCount == 0
            result[0].balance == -1.00
    }
}
