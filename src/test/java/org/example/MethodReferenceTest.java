package org.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.example.Color.GREEN;
import static org.example.Color.RED;

public class MethodReferenceTest {
    @Test
    void sortString1() {
        List<String> str = Arrays.asList("A", "b", "C", "a", "B");
        str.sort((s1, s2) -> s1.compareToIgnoreCase(s2));

        // stable sort
        assertThat(str).containsExactly("A", "a", "b", "B", "C");
    }

    @DisplayName("메소드 참조를 활용한 문자열 정렬")
    void sortString2() {
        List<String> str = Arrays.asList("A", "b", "C", "a", "B");
        str.sort(String::compareToIgnoreCase);

        // stable sort
        assertThat(str).containsExactly("A", "a", "b", "B", "C");
    }

    @Test
    void sortString3() {
        List<String> str = Arrays.asList("A", "b", "C", "a", "B");
        str.sort(String::compareTo);

        System.out.println(str);
        assertThat(str).containsExactly("A", "B", "C", "a", "b");
    }

    @Test
    void toIntFunction() {
        List<String> str = List.of("123", "2", "3");
        Function<String, Integer> stringToInteger = Integer::parseInt;

        List<Integer> integers = str.stream().map(stringToInteger).collect(Collectors.toList());
        assertThat(integers).containsExactly(123, 2, 3);
    }

    @Test
    void sortByMethodReference() {
        List<Apple> inventory = new ArrayList<>(List.of(
                new Apple(10, RED),
                new Apple(15, RED),
                new Apple(7, GREEN),
                new Apple(2, RED),
                new Apple(1, GREEN),
                new Apple(23, RED)
        ));

        inventory.sort(Comparator.comparing(Apple::getWeight));
        System.out.println(inventory);
        assertThat(inventory).usingElementComparatorOnFields("weight");
    }
}
