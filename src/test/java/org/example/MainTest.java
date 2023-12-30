package org.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.*;
import static org.example.Color.GREEN;
import static org.example.Color.RED;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    List<Apple> inventory = new ArrayList<>(List.of(
            new Apple(10, RED),
            new Apple(15, RED),
            new Apple(7, GREEN),
            new Apple(2, RED),
            new Apple(1, GREEN),
            new Apple(23, RED)
    ));

    @Test
    void filterGreenApple() {
        List<Apple> result = filterApples(inventory, (a) -> GREEN.equals(a.getColor()));

        System.out.println(result.toString());
        assertThat(result).contains(inventory.get(2), inventory.get(4));
    }

    @Test
    void sortApplesByWeight() {
        List<Apple> result = new ArrayList<>(inventory);
        result.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));

        System.out.println(result);
        assertThat(result).usingElementComparatorOnFields("weight");
    }

    @Test
    void enumTest() {

    }

    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }
}