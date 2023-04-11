package com.sebastianstaniak.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Matchmaking {

    public List<Group> assignGroups(int maxGroupCount, List<Clan> clans) {
        List<Group> groups = new ArrayList<Group>();

        clans.parallelStream()
                .sorted()
                .collect(Collectors.toCollection(LinkedList::new))
                .forEach(clan -> {
                    boolean assigned = false;
                    for (Group group : groups) {
                        if (group.canFit(clan)) {
                            group.add(clan);
                            assigned = true;
                            break;
                        }
                    }
                    if (!assigned) {
                        Group newGroup = new Group(maxGroupCount);
                        groups.add(newGroup);
                        newGroup.add(clan);
                    }
                });

        return groups;
    }
}
