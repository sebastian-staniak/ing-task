package com.sebastianstaniak.infrastructure;

import com.sebastianstaniak.domain.Clan;
import com.sebastianstaniak.domain.Group;
import com.sebastianstaniak.domain.Matchmaking;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import java.util.List;
import java.util.stream.Collectors;

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
                        .stream().map(Group::getClans).collect(Collectors.toList())
        );
    }
}
