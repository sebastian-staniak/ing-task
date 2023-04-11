package com.sebastianstaniak.infrastructure;

import com.sebastianstaniak.domain.ATM;
import com.sebastianstaniak.domain.AtmServicePlanner;
import com.sebastianstaniak.domain.Task;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import java.util.Collection;
import java.util.List;

@Controller("/onlinegame")
public class REST {
    AtmServicePlanner planner = new AtmServicePlanner();

    @Post(uri = "/calculate", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Collection<ATM>> generateReport(
            @Body List<Task> tasks
    ) {
        return HttpResponse.ok(planner.plan(tasks));
    }
}
