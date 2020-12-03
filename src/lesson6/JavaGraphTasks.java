package lesson6;

import kotlin.NotImplementedError;
import lesson6.impl.GraphBuilder;

import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     * <p>
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     * <p>
     * Пример:
     * <p>
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     * <p>
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */
    //time: O(E+V)
    //memory: O(E+V)
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        List<Graph.Edge> result = new ArrayList<>();

        if (graph.getVertices().isEmpty())
            return result;
        for (Graph.Vertex v : graph.getVertices())
            if (graph.getNeighbors(v).size() % 2 == 1)
                return new ArrayList<>();

        Set<Graph.Edge> visitedEdges = new HashSet<>();
        Deque<Graph.Vertex> stack = new ArrayDeque<>();
        stack.push(graph.getVertices().iterator().next());
        while (!stack.isEmpty()) {
            Graph.Vertex current = stack.peek();
            boolean foundEdge = false;
            for (Graph.Vertex v : graph.getNeighbors(current)) {
                if (visitedEdges.contains(graph.getConnection(current, v)))
                    continue;
                stack.push(v);
                visitedEdges.add(graph.getConnection(current, v));
                foundEdge = true;
                break;
            }
            if (!foundEdge) {
                stack.pop();
                if (stack.peek() != null)
                    result.add(graph.getConnection(current, stack.peek()));
            }
        }
        if (result.size() != graph.getEdges().size())
            return new ArrayList<>();
        return result;
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     * <p>
     * Дан связный граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     * <p>
     * Пример:
     * <p>
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ:
     * <p>
     *      G    H
     *      |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    //time: O(V+E)
    //memory: O(V)
    public static Graph minimumSpanningTree(Graph graph) {
        GraphBuilder result = new GraphBuilder();
        Set<Graph.Vertex> visitedVertices = new HashSet<>();
        for (Graph.Vertex current : graph.getVertices()) {
            result.addVertex(current.getName());
            for (Graph.Vertex neighbour : graph.getNeighbors(current)) {
                if (visitedVertices.contains(neighbour) && visitedVertices.contains(current))
                    continue;
                visitedVertices.add(current);
                visitedVertices.add(neighbour);
                result.addConnection(current, neighbour, 1);
            }
        }
        return result.build();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     * <p>
     * Дан граф без циклов (получатель), например
     * <p>
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     * <p>
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     * <p>
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     * <p>
     * В данном случае ответ (A, E, F, D, G, J)
     * <p>
     * Если на входе граф с циклами, бросить IllegalArgumentException
     * <p>
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     * <p>
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     * <p>
     * Пример:
     * <p>
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    //time: O(V+E)
    //memory: O(E)
    public static Path longestSimplePath(Graph graph) {
        Path longest = new Path();
        Deque<Path> paths = new ArrayDeque<>();
        for (Graph.Vertex vertex : graph.getVertices()) {
            paths.push(new Path(vertex));
            while (!paths.isEmpty()) {
                Path current = paths.pop();
                if (longest.getLength() < current.getLength())
                    longest = current;
                Graph.Vertex last = current.getVertices().get(current.getVertices().size() - 1);
                for (Graph.Vertex v : graph.getNeighbors(last))
                    if (!current.getVertices().contains(v))
                        paths.push(new Path(current, graph, v));
            }
        }
        return longest;
    }


    /**
     * Балда
     * Сложная
     * <p>
     * Задача хоть и не использует граф напрямую, но решение базируется на тех же алгоритмах -
     * поэтому задача присутствует в этом разделе
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
