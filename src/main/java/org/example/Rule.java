package org.example;

import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class Rule {

    private final Set<Integer> shouldSurvive;
    private final Set<Integer> shouldArise;

    public Rule(String rule) {
        var parsed = rule.split("/");
        shouldSurvive = splitString(parsed[0]);
        shouldArise = splitString(parsed[1]);
    }

    private static Set<Integer> splitString(String input) {
        return input.chars().mapToObj(i -> (char) i).map(String::valueOf).map(Integer::parseInt).collect(Collectors.toSet());

    }
}
