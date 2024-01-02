package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;
import static org.example.Color.GREEN;
import static org.example.Color.RED;

public class ComparatorTest {
    List<Apple> inventory;

    @BeforeEach
    void init() {
         inventory = new ArrayList<>(List.of(
                new Apple(10, RED),
                new Apple(15, RED),
                new Apple(15, GREEN),
                new Apple(15, RED),
                new Apple(1, GREEN),
                new Apple(1, RED),
                new Apple(10, GREEN),
                new Apple(10, GREEN)
        ));
    }

    @Test
    void thenCompare() {
        inventory.sort(comparing(Apple::getWeight).thenComparing(Apple::getColor));
        System.out.println("weight and color = " + inventory);
    }

    @Test
    void justCompare() {
        inventory.sort(comparing(Apple::getWeight));
        System.out.println("just weight = " + inventory);
    }
}
