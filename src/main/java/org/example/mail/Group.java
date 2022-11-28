package org.example.mail;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private List<String> group = new ArrayList<>();

    public void addPerson(String person) {
        group.add(person);
    }

    public List<String> getGroup() {
        return group;
    }
}
