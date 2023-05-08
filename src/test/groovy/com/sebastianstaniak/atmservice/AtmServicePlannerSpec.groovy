package com.sebastianstaniak.atmservice

import com.sebastianstaniak.atmservice.domain.AtmServicePlanner
import com.sebastianstaniak.atmservice.domain.Task
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

@MicronautTest
class AtmServicePlannerSpec extends Specification {

    void 'it can order by regions'() {
        given:
            def planner = new AtmServicePlanner()
        and:
            def tasks = [
                    new Task(10, Task.TaskType.STANDARD, 2),
                    new Task(11, Task.TaskType.STANDARD, 3),
                    new Task(1, Task.TaskType.STANDARD, 1),
            ]

        when:
            def result = planner.plan(tasks)

        then:
            result[0].region == 1
            result[1].region == 10
            result[2].region == 11
    }

    void 'it can order when multiple atms in one region'() {
        given:
            def planner = new AtmServicePlanner()
        and:
            def tasks = [
                    new Task(1, Task.TaskType.STANDARD, 1),
                    new Task(1, Task.TaskType.STANDARD, 2),
                    new Task(1, Task.TaskType.STANDARD, 3)
            ]

        when:
            def result = planner.plan(tasks)

        then:
            result[0].region == 1
            result[1].region == 1
            result[2].region == 1
    }

    void 'it can order by task priority in single region'() {
        given:
            def planner = new AtmServicePlanner()
        and:
            def tasks = [
                    new Task(1, Task.TaskType.STANDARD, 1),
                    new Task(1, Task.TaskType.PRIORITY, 2),
                    new Task(1, Task.TaskType.FAILURE_RESTART, 3),
                    new Task(1, Task.TaskType.SIGNAL_LOW, 4),
            ]

        when:
            def result = planner.plan(tasks)

        then:
            result[0].atmId == 3
            result[1].atmId == 2
            result[2].atmId == 4
            result[3].atmId == 1
    }

    void 'it puts SIGNAL LOW before STANDARD'() {
        given:
            def planner = new AtmServicePlanner()
        and:
            def tasks = [
                    new Task(3, Task.TaskType.STANDARD, 4),
                    new Task(3, Task.TaskType.SIGNAL_LOW, 2),
                    new Task(3, Task.TaskType.FAILURE_RESTART, 1),
            ]

        when:
            def result = planner.plan(tasks)

        then:
            result[0].atmId == 1
            result[1].atmId == 2
            result[2].atmId == 4
    }

    void 'it puts SIGNAL LOW after PRIORITY'() {
        given:
            def planner = new AtmServicePlanner()
        and:
            def tasks = [
                    new Task(3, Task.TaskType.PRIORITY, 4),
                    new Task(3, Task.TaskType.SIGNAL_LOW, 2),
                    new Task(3, Task.TaskType.FAILURE_RESTART, 1),
            ]

        when:
            def result = planner.plan(tasks)

        then:
            result[0].atmId == 1
            result[1].atmId == 4
            result[2].atmId == 2
    }

    void 'it should keep just one task for atm'() {
        given:
            def planner = new AtmServicePlanner()
        and:
            def tasks = [
                    new Task(1, Task.TaskType.FAILURE_RESTART, 1),
                    new Task(1, Task.TaskType.STANDARD, 1),
            ]

        when:
            def result = planner.plan(tasks)

        then:
            result.size() == 1
    }

    void 'it covers example scenario'() {
        given:
            def planner = new AtmServicePlanner()
        and:
            def tasks = [
                    new Task(4, Task.TaskType.STANDARD, 1),
                    new Task(1, Task.TaskType.STANDARD, 1),
                    new Task(2, Task.TaskType.STANDARD, 1),
                    new Task(3, Task.TaskType.PRIORITY, 2),
                    new Task(3, Task.TaskType.STANDARD, 1),
                    new Task(2, Task.TaskType.SIGNAL_LOW, 1),
                    new Task(5, Task.TaskType.STANDARD, 2),
                    new Task(5, Task.TaskType.FAILURE_RESTART, 1),
            ]

        when:
            def result = planner.plan(tasks)

        then:
            result[0].region == 1
            result[0].atmId == 1

            result[1].region == 2
            result[1].atmId == 1

            result[2].region == 3
            result[2].atmId == 2

            result[3].region == 3
            result[3].atmId == 1

            result[4].region == 4
            result[4].atmId == 1

            result[5].region == 5
            result[5].atmId == 1

            result[6].region == 5
            result[6].atmId == 2

            result.size() == 7
    }

    void 'it covers second example scenario'() {
        given:
            def planner = new AtmServicePlanner()
        and:
            def tasks = [
                    new Task(1, Task.TaskType.STANDARD, 2),
                    new Task(1, Task.TaskType.STANDARD, 1),
                    new Task(2, Task.TaskType.PRIORITY, 3),
                    new Task(3, Task.TaskType.STANDARD, 4),
                    new Task(4, Task.TaskType.STANDARD, 5),
                    new Task(5, Task.TaskType.PRIORITY, 2),
                    new Task(5, Task.TaskType.STANDARD, 1),
                    new Task(3, Task.TaskType.SIGNAL_LOW, 2),
                    new Task(2, Task.TaskType.SIGNAL_LOW, 1),
                    new Task(3, Task.TaskType.FAILURE_RESTART, 1),
            ]

        when:
            def result = planner.plan(tasks)

        then:
            result[0].region == 1
            result[0].atmId == 2

            result[1].region == 1
            result[1].atmId == 1

            result[2].region == 2
            result[2].atmId == 3

            result[3].region == 2
            result[3].atmId == 1

            result[4].region == 3
            result[4].atmId == 1

            result[5].region == 3
            result[5].atmId == 2

            result[6].region == 3
            result[6].atmId == 4

            result[7].region == 4
            result[7].atmId == 5

            result[8].region == 5
            result[8].atmId == 2

            result[9].region == 5
            result[9].atmId == 1

            result.size() == 10
    }
}
