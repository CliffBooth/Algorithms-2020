package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    //time: O(n)
    //memory: O(n)
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) throws IOException {
        List<Integer> delta = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputName))) {
            String line = reader.readLine();
            String line2 = reader.readLine();
            while (line2 != null) {
                delta.add(Integer.parseInt(line2) - Integer.parseInt(line));
                line = line2;
                line2 = reader.readLine();
            }
        }
        int maxSum = 0;
        int currentSum = 0;
        int start = -1;
        int end = -1;
        Pair<Integer, Integer> result = new Pair<>(0, 0);
        for (int i = 0; i < delta.size(); i++) {
            currentSum += delta.get(i);
            if (start == -1)
                start = i;
            if (currentSum > maxSum) {
                maxSum = currentSum;
                end = i;
            }
            if (currentSum < 0) {
                start = -1;
                end = -1;
                currentSum = 0;
            }
            if (end > -1)
                result = new Pair<>(start, end);
        }
        result = new Pair<>(result.getFirst() + 1, result.getSecond() + 2);
        return result;
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     * <p>
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    //time: O(first.length() * second.length())
    //memory: O(first.length() * second.length())
    static public String longestCommonSubstring(String first, String second) {
        int max = 0;
        int index = 0;
        int[][] matrix = new int[first.length() + 1][second.length() + 1];
        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1))
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                if (max < matrix[i][j]) {
                    max = matrix[i][j];
                    index = i - 1;
                }
            }
        }
        if (max == 0)
            return "";
        return first.substring(index - max + 1, index + 1);
    }

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    //time: n*log(log(n))
    //memory: O(n)
    static public int calcPrimesNumber(int limit) {
        if (limit <= 1) {
            return 0;
        }
        int result = 0;
        boolean[] primes = new boolean[limit + 1];
        for (int i = 2; i * i <= limit; i++)
            if (!primes[i]) {
                for (int j = i * i; j <= limit; j += i)
                    primes[j] = true;
            }
        for (int i = 2; i <= limit; i++)
            if (!primes[i])
                result++;
        return result;
    }
}
