package dev.aston.search;

import dev.aston.entities.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinarySearcherTest {
    private Searcher<Person> searcher;
    private List<Person> people;

    @BeforeEach
    void setUp() {
        searcher = new BinarySearcher<>();

        people = new ArrayList<>(List.of(
                new Person.Builder().name("Alice").surname("Brown").age(25).build(),
                new Person.Builder().name("Bob").surname("Smith").age(30).build(),
                new Person.Builder().name("Charlie").surname("Adams").age(22).build(),
                new Person.Builder().name("David").surname("Jones").age(27).build(),
                new Person.Builder().name("Eve").surname("White").age(29).build()
        ));

        Collections.sort(people);
    }

    @Test
    void testSearch_FoundMiddle() {
        Person target = new Person.Builder().name("Charlie").surname("Adams").age(22).build();
        int index = searcher.search(people, target);
        assertTrue(index >= 0, "Элемент должен быть найден");
        assertEquals(target.getName(), people.get(index).getName());
    }

    @Test
    void testSearch_FoundFirst() {
        Person target = people.getFirst();
        int index = searcher.search(people, target);
        assertEquals(0, index, "Первый элемент должен быть найден на позиции 0");
    }

    @Test
    void testSearch_FoundLast() {
        Person target = people.getLast();
        int index = searcher.search(people, target);
        assertEquals(people.size() - 1, index, "Последний элемент должен быть найден корректно");
    }

    @Test
    void testSearch_NotFound_ReturnsMinusOne() {
        Person target = new Person.Builder().name("Zoe").surname("Gray").age(40).build();
        int index = searcher.search(people, target);
        assertEquals(-1, index, "Если элемента нет в коллекции, должен вернуться -1");
    }

    @Test
    void testSearch_SingleElement_Found() {
        List<Person> single = List.of(
                new Person.Builder().name("Solo").surname("Man").age(99).build()
        );
        Person target = new Person.Builder().name("Solo").surname("Man").age(99).build();

        int index = searcher.search(single, target);
        assertEquals(0, index, "Один элемент - найден на позиции 0");
    }

    @Test
    void testSearch_SingleElement_NotFound() {
        List<Person> single = List.of(
                new Person.Builder().name("Solo").surname("Man").age(99).build()
        );
        Person target = new Person.Builder().name("Different").surname("Guy").age(50).build();

        int index = searcher.search(single, target);
        assertEquals(-1, index, "Если элемент не совпадает, должен вернуться -1");
    }

    @Test
    void testSearch_EmptyList() {
        List<Person> empty = List.of();
        Person target = new Person.Builder().name("Nobody").surname("Empty").age(0).build();

        int index = searcher.search(empty, target);
        assertEquals(-1, index, "Пустая коллекция всегда возвращает -1");
    }

    @Test
    void testSearch_SameNameDifferentAge() {
        people = new ArrayList<>(List.of(
                new Person.Builder().name("Alex").surname("Brown").age(20).build(),
                new Person.Builder().name("Alex").surname("Brown").age(21).build(),
                new Person.Builder().name("Alex").surname("Brown").age(22).build()
        ));
        Collections.sort(people);

        Person target = new Person.Builder().name("Alex").surname("Brown").age(21).build();
        int index = searcher.search(people, target);
        assertEquals(1, index, "Элемент с одинаковыми name/surname, но другим age должен быть найден корректно");
    }
}
