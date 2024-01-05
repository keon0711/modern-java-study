package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class StreamTest {
    List<Dish> menu;


    @BeforeEach
    void init() {
        menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", true, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );
    }

    @Test
    void findHighCalorieDish() {
        List<String> threeHighCaloricDishNames =
                menu.stream()
                        .filter(dish -> dish.getCalories() > 300)
                        .map(Dish::getName)
                        .limit(3)
                        .collect(toList());

        assertThat(threeHighCaloricDishNames).containsExactly("pork", "beef", "chicken");
    }

    @Test
    void filteringByTakeWhile() {
        List<Dish> sortedDish = new ArrayList<>(menu);
        sortedDish.sort(comparing(Dish::getCalories));
        System.out.println("sortedDish = " + sortedDish);

        List<Dish> lowCalorieDishes = sortedDish.stream()
                .takeWhile(dish -> dish.getCalories() < 350)
                .collect(toList());
        System.out.println("lowCalorieDishes = " + lowCalorieDishes);
    }

    @Test
    void filteringByDropWhile() {
        List<Dish> sortedDish = new ArrayList<>(menu);
        sortedDish.sort(comparing(Dish::getCalories));
        System.out.println("sortedDish = " + sortedDish);

        List<Dish> highCalorieDishes = sortedDish.stream()
                .dropWhile(dish -> dish.getCalories() < 350)
                .collect(toList());
        System.out.println("highCalorieDishes = " + highCalorieDishes);

    }

    @Test
    void notFlatten() {
        String[] strings = new String[]{"hello", "world"};
        List<String> result = Arrays.stream(strings)
                .map(word -> word.split(""))
                .map(Arrays::toString)
                .collect(toList());

        System.out.println("result = " + result);
    }

    @Test
    void flatten() {
        String[] strings = new String[]{"hello", "world"};
        Arrays.stream(strings)
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .forEach(x -> System.out.println(x));
    }

    @Test
    void doubleMap() {
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> result = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .map(j -> new int[]{i, j}))
                .collect(toList());
    }

    @Test
    void filteringMod3() {
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> expected = Arrays.asList(new int[][]{
                new int[]{2, 4},
                new int[]{3, 3}});
        List<int[]> actual = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .map(j -> new int[]{i, j}))
                .filter(x -> (x[0] + x[1]) % 3 == 0)
                .collect(toList());

        System.out.println(actual.get(0));
        System.out.println(expected.get(0));
        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }

    @Test
    void anyMatch() {
        if (menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("채식요리가 있습니다.");
        }
    }

    @Test
    void allMatch() {
        if (menu.stream().allMatch(x -> x.getCalories() < 1000)) {
            System.out.println("1000칼로리보다 칼로리가 높은 음식은 없습니다.");
        }
    }

    @Test
    void noneMatch() {
        if (menu.stream().noneMatch(x -> x.getCalories() > 1000)) {
            System.out.println("1000칼로리보다 칼로리가 높은 음식은 없습니다.");
        }
    }

    @Test
    void findAny() {
        Optional<Dish> any = menu.stream()
                .filter(Dish::isVegetarian)
                .findAny();
        System.out.println("any = " + any);
    }

    @Test
    void shortCircuit() {
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        boolean actual = integers.stream()
                .allMatch(x -> x < 6);

        assertThat(actual).isFalse();
    }

    @Test
    public void reducing() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
    }

    @Test
    public void reduce1() {
        Integer count = menu.stream()
                .map(dish -> 1)
                .reduce(0, (a, b) -> a + b);
        assertThat(count).isEqualTo(menu.size());
    }

    @Test
    public void max() {
        Optional<Dish> maxDish = menu.stream()
                .max(comparing(Dish::getCalories));
        assertThat(maxDish.get())
                .usingRecursiveAssertion()
                .isEqualTo(new Dish("pork", false, 800, Dish.Type.MEAT));
    }

    @Test
    public void collectionAssertion() {
        List<Dish> actual = menu.stream()
                .filter(x -> x.getCalories() < 600)
                .collect(toList());

        List<Dish> expected = List.of(
                new Dish("chicken", true, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );

        assertThat(actual)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expected);
    }


    @Test
    void mapToIntSum() {
        List<Dish> emptyList = new ArrayList<>();
        int sum = emptyList.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        System.out.println("sum = " + sum);
    }

    @Test
    void intStreamMax() {
        OptionalInt max = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();

        System.out.println(max.orElse(-1));
    }

    @Test
    void emptryStreamMax() {
        List<Dish> emptyList = new ArrayList<>();
        OptionalInt max = emptyList.stream()
                .mapToInt(Dish::getCalories)
                .max();

        System.out.println("max.orElse(-1) = " + max.orElse(-1));
    }

    @Test
    void generateNumbersInRange() {
        IntStream numbers = IntStream.range(5, 20);

    }
}
