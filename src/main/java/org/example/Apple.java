package org.example;

import java.util.Objects;

public class Apple {
    private Integer weight;
    private Color color;

    public Apple(Integer weight, Color color) {
        this.weight = weight;
        this.color = color;
    }

    public Apple() {

    }

    public Apple(Integer integer) {
        this.weight = integer;
    }

    public Integer getWeight() {
        return weight;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "weight=" + weight +
                ", color=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apple apple = (Apple) o;
        return Objects.equals(weight, apple.weight) && color == apple.color;
    }
}
