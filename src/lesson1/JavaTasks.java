package lesson1;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.StrictMath.abs;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     * <p>
     * Пример:
     * <p>
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    //time = O(n*log(n))
    //memory: O(n)
    static public void sortTimes(String inputName, String outputName) throws IOException {
        List<Integer> toSort = new ArrayList<>();
        Map<Integer, String> map = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputName));
             Writer writer = new FileWriter(outputName)) {
            String string = reader.readLine();
            while (string != null) {
                if (!string.matches("(0\\d|1[0-2]):[0-5]\\d:[0-5]\\d\\s(AM|PM)")) {
                    throw new IllegalArgumentException();
                }
                String[] strings = string.split(":");
                int firstNumber = Integer.parseInt(strings[0]) == 12 ? 0 : Integer.parseInt(strings[0]);
                int value = firstNumber * 3600 + Integer.parseInt(strings[1]) * 60 +
                        Integer.parseInt(strings[2].substring(0, 2));
                if (strings[2].contains("PM"))
                    value += 12 * 3600;
                toSort.add(value);
                map.put(value, string);
                string = reader.readLine();
            }
            Collections.sort(toSort);
            for (int value : toSort) {
                writer.write(map.get(value) + System.lineSeparator());
            }
        }
    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    //time: O(n*log(n))
    //memory: O(n)
    static public void sortAddresses(String inputName, String outputName) throws IOException {
        Map<String, List<String>> map = new TreeMap<>((o1, o2) -> {
            String street1 = o1.split(" ")[0];
            String street2 = o2.split(" ")[0];
            Integer number1 = Integer.parseInt(o1.split(" ")[1]);
            Integer number2 = Integer.parseInt(o2.split(" ")[1]);
            if (!street1.equals(street2))
                return street1.compareTo(street2);
            else
                return number1.compareTo(number2);
        });
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(inputName), StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr);
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputName, StandardCharsets.UTF_8))) {
            String string = reader.readLine();
            while (string != null) {
                if (!string.matches("^([\\wА-Яа-яЁё]+ ){2}- ([\\wА-Яа-яЁё\\-]+ \\d+)$"))
                    throw new IllegalArgumentException();

                String name = string.split(" - ")[0];
                String address = string.split(" - ")[1];
                map.putIfAbsent(address, new ArrayList<>());
                map.get(address).add(name);
                string = reader.readLine();
            }

            for (String address : map.keySet()) {
                List<String> names = map.get(address);
                Collections.sort(names);
                writer.write(address + " - " + names.get(0));
                for (int i = 1; i < names.size(); i++) {
                    writer.write(", " + names.get(i));
                }
                writer.write(System.lineSeparator());
            }
        }
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    //time: O(n)
    //memory: O(n)
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        int min = 2730;
        int limit = 2730 + 5000 + 1;
        int[] temperatures = new int[limit];
        try (BufferedReader reader = new BufferedReader(new FileReader(inputName));
             Writer writer = new FileWriter(outputName)) {
            String line = reader.readLine();
            while (line != null) {
                temperatures[(int) (Double.parseDouble(line) * 10 + min)]++;
                line = reader.readLine();
            }
            for (int i = 0; i < limit; i++) {
                if (temperatures[i] == 0)
                    continue;
                int t = i - min;
                for (int j = 0; j < temperatures[i]; j++) {
                    if (t < 0)
                        writer.write("-");
                    writer.write(abs(t / 10) + "." + abs(t % 10) + System.lineSeparator());
                }
            }
        }
    }

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    //time: O(n)
    //memory: O(n)
    static public void sortSequence(String inputName, String outputName) throws IOException {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputName));
             Writer writer = new FileWriter(outputName)) {
            String line = reader.readLine();
            while (line != null) {
                int number = Integer.parseInt(line);
                if (map.containsKey(number))
                    map.put(number, map.get(number) + 1);
                else
                    map.put(number, 1);
                numbers.add(number);
                line = reader.readLine();
            }
            int maxCounter = Collections.max(map.values());
            int min = 0;
            for (int number : numbers) {
                if (map.get(number) == maxCounter && (min == 0 || min > number))
                    min = number;
            }
            for (int number : numbers)
                if (number != min)
                    writer.write(number + System.lineSeparator());
            for (int i = 0; i < maxCounter; i++)
                writer.write(min + System.lineSeparator());
        }
    }

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    //time: O(second.length)
    //memory: O(1)
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        int fi = 0;
        int si = first.length;
        for (int i = 0; i < second.length; i++) {
            if (fi < first.length && (si == second.length || first[fi].compareTo(second[si]) < 0)) {
                second[i] = first[fi++];
            } else {
                second[i] = second[si++];
            }
        }
    }
}
