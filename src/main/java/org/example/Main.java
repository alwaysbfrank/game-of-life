package org.example;

public class Main {
    public static void main(String[] args) {
        ArgumentParser arguments;
        try {
            arguments = new ArgumentParser(args);
        } catch (Exception ex) {
            System.out.println("Correct argument syntax (die/live rule, grid size, seeds' coordinates): 32/23 50 3,3 4,4 3,4 3,5");
            throw ex;
        }

        new Game(arguments).play();
    }
}