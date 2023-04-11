package com.sebastianstaniak.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Matchmaking {

    public List<Group> assignGroups(int maxGroupCount, List<Clan> clans) {
        List<Group> groups = new ArrayList<Group>();
        List<Group> fullGroups = new ArrayList<Group>();

        clans.parallelStream()
                .sorted()
                .collect(Collectors.toCollection(LinkedList::new)) // TODO make it parallelStream
                .forEach(clan -> {
                    boolean assigned = false;
                    for (Group group : groups) {
                        if (group.canFit(clan)) {
                            group.add(clan);
                            assigned = true;
                            if (group.isFull()) {
                                groups.remove(group);
                                fullGroups.add(group);
                            }
                            break;
                        }
                    }
                    if (!assigned) {
                        Group newGroup = new Group(maxGroupCount);
                        groups.add(newGroup);
                        newGroup.add(clan);
                    }
                });
        fullGroups.addAll(groups); // TODO make sure order of this merge is same as without full group optimization
        return fullGroups;
    }
}
