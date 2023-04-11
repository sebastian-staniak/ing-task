package com.sebastianstaniak

import com.sebastianstaniak.domain.ATM
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
                            region : 4,
                            requestType: "STANDARD",
                            atmId       : 1
                    ],
                    [
                            region : 4,
                            requestType: "STANDARD",
                            atmId       : 2
                    ]
            ]

        when:
            def result = client.toBlocking().retrieve(
                    HttpRequest.POST('/atms/calculateOrder', request), List<ATM>
            )

        then:
            result[0].region == 4
            result[0].atmId == 1
        and:
            result[1].region == 4
            result[1].atmId == 2
    }
}
