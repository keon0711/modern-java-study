package org.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class ConstructorReferenceTest {
    @Test
    void getTest() {
        Supplier<Apple> c1 = Apple::new;
        Apple a1 = c1.get();

        assertThat(a1).isInstanceOf(Apple.class);
    }

    @Test
    void functionTest() {
        Function<Integer, Apple> c2 = Apple::new;
        Apple a2 = c2.apply(100);
        assertThat(a2).isEqualTo(new Apple(100));
    }

    @Test
    void constructApplesByWeight() {
        List<Integer> weights = List.of(7, 3, 4, 10);
        List<Apple> apples = map(weights, Apple::new);

        assertThat(apples).containsExactly(
                new Apple(7),
                new Apple(3),
                new Apple(4),
                new Apple(10)
        );
    }

    @Test
    void constructApplesByWeight2() {
        List<Integer> weights = List.of(7, 3, 4, 10);
        List<Apple> apples = weights.stream().map(Apple::new).collect(Collectors.toList());

        assertThat(apples).containsExactly(
                new Apple(7),
                new Apple(3),
                new Apple(4),
                new Apple(10)
        );
    }

    public List<Apple> map(List<Integer> ws, Function<Integer, Apple> f) {
        List<Apple> result = new ArrayList<>();
        for (Integer w : ws) {
            result.add(f.apply(w));
        }
        return result;
    }
}
