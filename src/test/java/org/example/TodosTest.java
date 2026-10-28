package org.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TodosTest {

    @Test
    public void shouldAddThreeTasksOfDifferentType(){
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
    public void shouldReturnTrueUponQuerySimpleTaskTest(){
        SimpleTask simpleTask = new SimpleTask(3, "Купить коту корм Greenfield");
        String query = "Greenfield";
        Assertions.assertTrue(simpleTask.matches(query));
    }

    @Test
    public void shouldReturnFalseUponQuerySimpleTaskTest(){
        SimpleTask simpleTask = new SimpleTask(3, "Купить коту корм Greenfield");
        String query = "Felix";
        Assertions.assertFalse(simpleTask.matches(query));
    }

    @Test
    public void shouldReturnTrueUponQueryEpicTest(){
        String[] subtasks = {"Сдать домашку", "Проверить почту", "Пройти новый модуль"};
        Epic epic = new Epic(11, subtasks);
        String query = "модуль";
        Assertions.assertTrue(epic.matches(query));
    }

    @Test
    public void shouldReturnFalseUponQueryEpicTest(){
        String[] subtasks = {"Сдать домашку", "Проверить почту", "Пройти новый модуль"};
        Epic epic = new Epic(11, subtasks);
        String query = "кот";
        Assertions.assertFalse(epic.matches(query));
    }

    @Test
    public void shouldReturnTrueUponQueryMeetingTest(){
        String topic = "Встреча с лидом";
        String pro = "Игра на vue.js";
        String time = "Завтра в 12:00";
        Meeting meeting = new Meeting(22, topic, pro, time);
        String query = "Завтра";
        Assertions.assertTrue(meeting.matches(query));
    }

    

    @Test
    public void shouldFindTaskUponQueryInTodosTest(){
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
}
