package com.sebastianstaniak.onlinegame.infrastructure;

import com.sebastianstaniak.onlinegame.domain.Clan;
import com.sebastianstaniak.onlinegame.domain.Group;
import com.sebastianstaniak.onlinegame.domain.Matchmaking;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import java.util.List;

@Controller("/onlinegame")
public class REST {
    private Matchmaking matchmaking = new Matchmaking();

    @Post(uri = "/calculate", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<List<Clan>>> assignPlayers(
            @Body QueueRequest request
    ) {
        return HttpResponse.ok(
                matchmaking
                        .assignGroups(request.getGroupCount(), request.getClans())
                        .stream().map(Group::getClans).toList()
        );
    }
}
