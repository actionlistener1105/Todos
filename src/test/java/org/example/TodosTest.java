package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

    // Возвращает несколько таск
    @Test
    void shouldReturnMultipleTasksWhenQueryMatchesSeveral() {
        Todos todos = new Todos();

        // Добавляем задачи в строго определённом порядке, иначе Assertions.assertArrayEquals не сработает
        SimpleTask task1 = new SimpleTask(1, "Купить молоко");
        todos.add(task1);

        String[] subtasks = {"Купить хлеб", "Купить хлеб белый", "Хлеб черный"};
        Epic epic2 = new Epic(2, subtasks);
        todos.add(epic2);

        Meeting meeting3 = new Meeting(3, "Созвон с шефом", "Проект для ITProger", "В 12:00");
        todos.add(meeting3);

        String query = "Купить";
        Task[] result = todos.search(query);
        Task[] expected = new Task[]{task1, epic2};

        Assertions.assertArrayEquals(expected, result);
    }

    // Должен вернуть одну задачу
    @Test
    void shouldReturnSingleTaskWhenQueryMatchesOne() {
        Todos todos = new Todos();

        SimpleTask task101 = new SimpleTask(101, "Оплатить интернет");
        todos.add(task101);

        String[] tasks = {"Заказать пиццу", "Пепперони"};
        Epic epic102 = new Epic(102, tasks);
        todos.add(epic102);

        Meeting meeting103 = new Meeting(103, "Свидание", "В Шоколаднице", "в 18:00 в субботу");
        todos.add(meeting103);

        String query = "пиццу";

        Task[] result = todos.search(query);
        Task[] expected = new Task[]{epic102};

        Assertions.assertArrayEquals(expected, result);
    }

    // Должен вернуть пустой массив, если совпадений нет
    @Test
    void shouldReturnEmptyArrayWhenNoTasksMatch() {
        Todos todos = new Todos();

        todos.add(new SimpleTask(501, "Сдать отчёт"));
        String[] tasks = {"Помыть машину", "Только в сухую погоду"};
        todos.add(new Epic(502, tasks));

        String query = "починить велосипед"; // такого нет

        Task[] result = todos.search(query);
        Task[] expected = new Task[]{};
        Assertions.assertArrayEquals(expected, result);
    }

}
