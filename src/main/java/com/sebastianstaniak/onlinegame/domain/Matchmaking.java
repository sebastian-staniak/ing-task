package com.sebastianstaniak.onlinegame.domain;

import java.util.ArrayList;
import java.util.List;

public class Matchmaking {

    public List<Group> assignGroups(int maxGroupCount, List<Clan> clans) {
        List<Group> groups = new ArrayList<>();
        List<Group> nonFullGroups = new ArrayList<>();

        clans.parallelStream()
                .sorted()
                .toList()
                .forEach(clan -> {
                    boolean assigned = false;
                    for (Group group : nonFullGroups) {
                        if (group.canFit(clan)) {
                            group.add(clan);
                            assigned = true;

                            if (group.isFull()) {
                                nonFullGroups.remove(group);
                            }
                            break;
                        }
                    }
                    if (!assigned) {
                        Group newGroup = new Group(maxGroupCount);
                        groups.add(newGroup);
                        nonFullGroups.add(newGroup);
                        newGroup.add(clan);
                    }
                });

        return groups;
    }
}
