package org.example;

import io.vavr.Tuple2;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
class ArgumentParser {

    private static final String DEFAULT_RULE = "23/3";
    private static final String DEFAULT_GRID_SIZE = "15";

    private static final String[] DEFAULT_SEED = {"3,3", "4,4", "3,4", "3,5"};

    private final Rule rule;
    private final Integer gridSize;

    private final Set<Tuple2<Integer, Integer>> seed;

    public ArgumentParser(String[] argz) {
       var myRule = DEFAULT_RULE;
       var myGrid = DEFAULT_GRID_SIZE;
       var mySeed = DEFAULT_SEED;

       if (argz.length > 0) {
           myRule = argz[0];
       }

       if (argz.length > 1) {
           myGrid = argz[1];
       }

       if (argz.length > 2) {
           mySeed = Arrays.copyOfRange(argz, 2, argz.length - 1);
       }

        this.rule = new Rule(myRule);
        this.gridSize = Integer.parseInt(myGrid);
        this.seed = parseSeed(mySeed);
    }

    private static Set<Tuple2<Integer, Integer>> parseSeed(String[] input) {
        return Arrays.stream(input).map(ArgumentParser::parseSingleSeed).collect(Collectors.toSet());
    }

    private static Tuple2<Integer, Integer> parseSingleSeed(String input) {
        var split = input.split(",");
        return new Tuple2<>(Integer.parseInt(split[0]) - 1, Integer.parseInt(split[1]) - 1);
    }

}