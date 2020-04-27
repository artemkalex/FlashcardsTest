import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;

import flashcards.Main;
import org.hyperskill.hstest.testcase.TestCase;
import org.hyperskill.hstest.testing.TestedProgram;
import org.hyperskill.hstest.testing.expect.ExpectationSearcher;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;

public class FlashcardsTest extends StageTest<String> {

    private static int count = 0;
    private String outCount = "";
    private String out = "";

    public void checkCount(String out) {
        if (out.isEmpty()) {
            return;
        }
        String[] outCount = out.split("\n");
        count += outCount.length;

    }
    @DynamicTestingMethod
    CheckResult test1() {

        // Вывод должен быть
        String[] lines = {
                "Input the number of cards:",
                "The card #1:",
                "The definition of the card #1:",
                "The card #2:",
                "The card \"2\" already exists. Try again:",
                "The definition of the card #2:",
                "The definition \"2\" already exists. Try again:",
                "Print the definition of \"2\":",
                "Correct answer.",
                "Print the definition of \"3\":",
                "Correct answer."
        };
        // Second test to check count lines
        // Two cards - "1" and "2". Two definitions - "1" and "2" respectively.
        TestedProgram main = new TestedProgram(Main.class);

        outCount = main.start();
        checkCount(outCount);

        for (int i = 0; i < 9; i++) {
            if (i == 4 || i == 6 || i == 8) {
                outCount = main.execute("3");
                checkCount(outCount);
                continue;
            }
            outCount = main.execute("2");
            checkCount(outCount);

        }
        if (count != 11) {
            throw new WrongAnswer("The number of lines in your output is " + count
                    + ", but it should be 11. " +
                    "Check, that you output your lines with println, not print. And there are no extra outputs.\n"
                    +"Lines:\n"
                    + Arrays.asList(lines));
        }

        count = 0;
        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test2() {

        // Test program -
        // where two Card = "1","2" and two Definition = "1","2" respectively
        // Check output lines

        TestedProgram main2 = new TestedProgram(Main.class);
        out = main2.start();

        // Задаем количество карточек - 3
        // Карточки будут 1 - black, 2 - white, 3 - red
        // Определения    1 - white, 2 - black, 3 - blue
        main2.execute("3");
        main2.execute("black");
        main2.execute("white");

        // первая проверка на совпадения карточек
        out = main2.execute("black");
        expect(out).toContain().regex("(\"black\")");
        main2.execute("white");

        // следующая проверка на совпадение определений
        out = main2.execute("white");
        expect(out).toContain().regex("(\"white\")");

        main2.execute("black");
        main2.execute("red");
        main2.execute("blue");
        // даем верный ответ на первый вопрос.
        main2.execute("white");

        // здесь ошибаемся. Но отвечаем определением, которое относится к карточке "red"
        out = main2.execute("blue");
        expect(out.toLowerCase()).toContain().regex("(the correct one is \"black\")");
        expect(out.toLowerCase()).toContain().regex("(the definition of \"red\")");

        // здесь отвечаем неверно и проверяем вывод. Такого ответа в карточках нет
        out = main2.execute("??sdsa@");
        expect(out.toLowerCase()).toContain().regex("(\"blue\")");

        return CheckResult.correct();
    }

    @DynamicTestingMethod
    CheckResult test3() {
        // Five cards
        // terms from 1 to 5 and definition from 1 to 5 respectively
        TestedProgram main3 = new TestedProgram(Main.class);
        outCount = main3.start();
        checkCount(outCount);
        //Сколько карточек
        outCount = main3.execute("5");
        checkCount(outCount);

        // цикл для записи терминов и определений от 1 до 5
        // и проверка на правильность записей.
        for (int i = 1; i <= 5; i++) {

            // проверка вывода номера карты
            expect(outCount).toContain().regex(("#"+i));

            outCount = main3.execute(String.valueOf(i));
            checkCount(outCount);

            // проверка вывода номер определения
            expect(outCount).toContain().regex(("#"+i));

            outCount = main3.execute(String.valueOf(i));
            checkCount(outCount);

        }
        // ответы на вопросы
        for (int i = 1; i <= 5; i++) {
            // проверка на вывод названия термина
            expect(outCount).toContain().regex(("\""+i+"\""));

            outCount = main3.execute(String.valueOf(i));
            checkCount(outCount);


        }
        // проверка на количество выводимых строк
        if (count != 21) {
            throw new WrongAnswer("The number of lines in your output is " + count
                    + ", but it should be 11. " +
                    "Check, that you output your lines with println, not print. And there are no extra outputs.\n");
        }

        count = 0;
        return CheckResult.correct();
    }
}
