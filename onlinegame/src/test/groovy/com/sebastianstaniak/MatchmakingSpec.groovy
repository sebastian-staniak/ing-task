package com.sebastianstaniak

import com.sebastianstaniak.domain.Clan
import com.sebastianstaniak.domain.Matchmaking
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

@MicronautTest
class MatchmakingSpec extends Specification {

    void 'it can create single group with one clan'() {
        given:
            def matchmaking = new Matchmaking()
        and:
            def clans = [
                    new Clan(1, 10),
            ]

        when:
            def groups = matchmaking.assignGroups(1, clans)

        then:
           groups.size() == 1
    }

    void 'it can create many groups'() {
        given:
            def matchmaking = new Matchmaking()
        and:
            def clans = [
                    new Clan(1, 10),
                    new Clan(1, 10),
                    new Clan(1, 10),
            ]

        when:
            def groups = matchmaking.assignGroups(1, clans)

        then:
           groups.size() == 3
    }

    void 'it can create group with many clans'() {
        given:
            def matchmaking = new Matchmaking()
        and:
            def clans = [
                    new Clan(1, 10),
                    new Clan(1, 10),
            ]

        when:
            def groups = matchmaking.assignGroups(2, clans)

        then:
            groups.size() == 1
    }

    void 'it can create group with many clans with many members'() {
        given:
            def matchmaking = new Matchmaking()
        and:
            def clans = [
                    new Clan(2, 10),
                    new Clan(2, 11),
                    new Clan(2, 13),
                    new Clan(2, 14),
            ]

        when:
            def groups = matchmaking.assignGroups(4, clans)

        then:
            groups.size() == 2
            groups[0].size() == 4
            groups[1].size() == 4
    }

    void 'it can create group with many clans with many members with different amounts'() {
        given:
            def matchmaking = new Matchmaking()
        and:
            def clans = [
                    new Clan(2, 10),
                    new Clan(1, 11),
                    new Clan(2, 13),
                    new Clan(1, 14),
            ]

        when:
            def groups = matchmaking.assignGroups(4, clans)

        then:
            groups.size() == 2
            groups[0].size() == 4
            groups[1].size() == 2
    }

    void 'it can order clans by points'() {
        given:
            def matchmaking = new Matchmaking()
        and:
            def clans = [
                    new Clan(1, 10),
                    new Clan(1, 11),
            ]

        when:
            def groups = matchmaking.assignGroups(1, clans)

        then:
            groups[0].clans[0].points == 11
            groups[1].clans[0].points == 10
    }

    void 'it can order clans by number of players if points are equal'() {
        given:
            def matchmaking = new Matchmaking()
        and:
            def clans = [
                    new Clan(7, 100),
                    new Clan(5, 10),
                    new Clan(6, 10),
            ]

        when:
            def groups = matchmaking.assignGroups(15, clans)

        then:
            groups[0].clans[0].points == 100
            groups[0].clans[1].points == 10
            groups[0].clans[1].numberOfPlayers == 5

            groups[1].clans[0].points == 10
            groups[1].clans[0].numberOfPlayers == 6
    }

    void 'it will pick next clan if the one does not fit'() {
        given:
            def matchmaking = new Matchmaking()
        and:
            def clans = [
                    new Clan(7, 100),
                    new Clan(5, 10),
                    new Clan(1, 1),
            ]

        when:
            def groups = matchmaking.assignGroups(8, clans)

        then:
            groups.size() == 2
            groups[0].clans[0].numberOfPlayers == 7
            groups[0].clans[1].numberOfPlayers == 1

            groups[1].clans[0].numberOfPlayers == 5
    }

    void 'it will cover provided use case'() {
        given:
            def matchmaking = new Matchmaking()
        and:
            def clans = [
                    new Clan(4, 50),
                    new Clan(2, 70),
                    new Clan(6, 60),
                    new Clan(1, 15),
                    new Clan(5, 40),
                    new Clan(3, 45),
                    new Clan(1, 12),
                    new Clan(4, 40),
            ]

        when:
            def groups = matchmaking.assignGroups(6, clans)

        then:
            groups[0].clans[0].numberOfPlayers == 2
            groups[0].clans[0].points == 70
            groups[0].clans[1].numberOfPlayers == 4
            groups[0].clans[1].points == 50

            groups[1].clans[0].numberOfPlayers == 6
            groups[1].clans[0].points == 60

            groups[2].clans[0].numberOfPlayers == 3
            groups[2].clans[0].points == 45
            groups[2].clans[1].numberOfPlayers == 1
            groups[2].clans[1].points == 15
            groups[2].clans[2].numberOfPlayers == 1
            groups[2].clans[2].points == 12

            groups[3].clans[0].numberOfPlayers == 4
            groups[3].clans[0].points == 40

            groups[4].clans[0].numberOfPlayers == 5
            groups[4].clans[0].points == 40

    }

    void 'it keeps group order'() {
        given:
            def matchmaking = new Matchmaking()
        and:
            def clans = [
                    new Clan(4, 9),
                    new Clan(3, 8),
                    new Clan(4, 7),
                    new Clan(1, 2),

            ]

        when:
            def groups = matchmaking.assignGroups(4, clans)

      then:
        groups[0].clans[0].points == 9

        groups[1].clans[0].points == 8
        groups[1].clans[1].points == 2

        groups[2].clans[0].points == 7
    }
}
