package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TodosTest {

    @Test
    public void shouldAddThreeTasksOfDifferentType() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");

        String[] subTask = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subTask);

        Meeting meeting = new Meeting(
                555,
                "Собеседование",
                "Автоматическое тестирование",
                "5.11.2026 в 12:00"
        );

        Todos todos = new Todos();

        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        Task[] expected = {simpleTask, epic, meeting};
        Task[] actual = todos.findAll();
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldReturnTrueUponQuerySimpleTaskTest() {
        SimpleTask simpleTask = new SimpleTask(3, "Купить коту корм Greenfield");
        String query = "Greenfield";
        assertTrue(simpleTask.matches(query));
    }

    @Test
    public void shouldReturnFalseUponQuerySimpleTaskTest() {
        SimpleTask simpleTask = new SimpleTask(3, "Купить коту корм Greenfield");
        String query = "Felix";
        Assertions.assertFalse(simpleTask.matches(query));
    }

    @Test
    public void shouldReturnTrueUponQueryEpicTest() {
        String[] subtasks = {"Сдать домашку", "Проверить почту", "Пройти новый модуль"};
        Epic epic = new Epic(11, subtasks);
        String query = "модуль";
        assertTrue(epic.matches(query));
    }

    @Test
    public void shouldReturnFalseUponQueryEpicTest() {
        String[] subtasks = {"Сдать домашку", "Проверить почту", "Пройти новый модуль"};
        Epic epic = new Epic(11, subtasks);
        String query = "кот";
        Assertions.assertFalse(epic.matches(query));
    }

    @Test
    public void shouldReturnTrueUponQueryMeetingTest() {
        String topic = "Встреча с лидом";
        String pro = "Игра на vue.js";
        String time = "Завтра в 12:00";
        Meeting meeting = new Meeting(22, topic, pro, time);
        String query = "Завтра";
        assertTrue(meeting.matches(query));
    }

    @Test
    public void shouldReturnFalseUponQueryMeetingTest() {
        String topic = "Встреча с лидом";
        String pro = "Игра на vue.js";
        String time = "Завтра в 12:00";
        Meeting meeting = new Meeting(22, topic, pro, time);
        String query = "17";
        Assertions.assertFalse(meeting.matches(query));
    }

    @Test
    public void shouldFindTaskUponQueryInTodosTest() {
        SimpleTask simpleTask = new SimpleTask(5, "Позвонить родителям");

        String[] subTask = {"Молоко", "Яйца", "Хлеб"};
        Epic epic = new Epic(55, subTask);

        Meeting meeting = new Meeting(
                555,
                "Собеседование",
                "Автоматическое тестирование",
                "5.11.2026 в 12:00"
        );

        Todos todos = new Todos();

        todos.add(simpleTask);
        todos.add(epic);
        todos.add(meeting);

        String query = "Собеседование";
        Task[] expected = new Task[]{meeting};
        Task[] actual = todos.search(query);
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnMultipleTasksWhenQueryMatchesSeveral() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(1, "Купить молоко"));
        String[] tasks = {"Купить хлеб", "Купить хлеб белый", "Хлеб черный"};
        todos.add(new Epic(2, tasks));
        todos.add(new Meeting(3, "Созвон с шефом", "Проект для ITProger", "В 12:00"));

        String query = "Купить";
        Task[] result = todos.search(query);

        assertEquals(2, result.length, "Должно найтись ровно 2 задачи с 'купить'");
        Set<Integer> foundIds = Arrays.stream(result)
                .map(Task::getId)
                .collect(Collectors.toSet());
        assertTrue(foundIds.contains(1));
        assertTrue(foundIds.contains(2));
    }

    @Test
    void shouldReturnSingleTaskWhenQueryMatchesOne() {

        Todos todos = new Todos();
        todos.add(new SimpleTask(101, "Оплатить интернет"));
        String[] tasks = {"Заказать пиццу", "Пепперони"};
        todos.add(new Epic(102, tasks));
        todos.add(new Meeting(103, "Свидание", "В Шоколаднице", "в 18:00 в субботу"));

        String query = "пиццу";

        Task[] result = todos.search(query);

        assertEquals(1, result.length, "Должна найтись ровно одна задача с 'пицца'");
        assertEquals(102, result[0].getId());
    }

    @Test
    void shouldReturnEmptyArrayWhenNoTasksMatch() {
        Todos todos = new Todos();
        todos.add(new SimpleTask(501, "Сдать отчёт"));
        String[] tasks = {"Помыть машину", "Только в сухую погоду"};
        todos.add(new Epic(502, tasks));

        String query = "починить велосипед"; // такого нет

        Task[] result = todos.search(query);

        assertNotNull(result, "Результат поиска не должен быть null");
        assertEquals(0, result.length, "При отсутствии совпадений должен возвращаться пустой массив");
    }

}
