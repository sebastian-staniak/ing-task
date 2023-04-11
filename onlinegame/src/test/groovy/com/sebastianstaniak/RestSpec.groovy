package com.sebastianstaniak

import com.sebastianstaniak.domain.Clan
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
                    "groupCount": 6,
                    "clans"     : [
                            [
                                    "numberOfPlayers": 6,
                                    "points"         : 500
                            ]
                    ]
            ]

        when:
            def result = client.toBlocking().retrieve(
                    HttpRequest.POST('/onlinegame/calculate', request), List<List<Clan>>
            )

        then:
            result[0][0].numberOfPlayers == 6
            result[0][0].points == 500
    }
}
