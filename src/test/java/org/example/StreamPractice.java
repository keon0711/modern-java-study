package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class StreamPractice {
    Trader raoul;
    Trader mario;
    Trader alan;
    Trader brian;

    List<Transaction> transactions;

    @BeforeEach
    void init() {
        raoul = new Trader("Raoul", "Cambridge");
        mario = new Trader("Mario", "Milan");
        alan = new Trader("Alan", "Cambridge");
        brian = new Trader("Brian", "Cambridge");

        transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    @Test
    void sortByValueIn2011() {
        List<Transaction> collect = transactions.stream()
                .filter(x -> x.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());

        System.out.println("collect = " + collect);
    }

    @Test
    void uniqueCities() {
        List<String> cities = transactions.stream()
                .map(x -> x.getTrader().getCity())
                .distinct()
                .collect(toList());

        System.out.println("cities = " + cities);
    }

    @Test
    void sortTraderInCambridgeByName() {
        List<Trader> traders = transactions.stream()
                .map(Transaction::getTrader)
                .distinct()
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .sorted(comparing(Trader::getName))
                .collect(toList());

        System.out.println("traders = " + traders);
    }

    @Test
    void sortTraderByName() {
        List<String> names = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(toList());

        System.out.println("names = " + names);
    }

    @Test
    void isTraderInMilan() {
        boolean isTraderInMilan = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));

        System.out.println("isTraderInMilan = " + isTraderInMilan);
    }

    @Test
    void allTransactionValueInCambridge() {
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .forEach(transaction -> System.out.println("transaction.getValue() = " + transaction.getValue()));
    }

    @Test
    void maxTransactionValue() {
        OptionalInt max = transactions.stream()
                .mapToInt(Transaction::getValue)
                .max();
        System.out.println("max.getAsInt() = " + max.getAsInt());
    }

    @Test
    void minTransactionValue() {
        OptionalInt min = transactions.stream()
                .mapToInt(Transaction::getValue)
                .min();
        System.out.println("min.getAsInt() = " + min.getAsInt());
    }


}
