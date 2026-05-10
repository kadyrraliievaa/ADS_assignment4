import java.util.*;

public class Main {

    static class Node {
        String city;
        int distance;

        Node(String city, int distance) {
            this.city = city;
            this.distance = distance;
        }
    }

    static Map<String, List<Node>> graph = new HashMap<>();

    public static void main(String[] args) {

        addEdge("Glasgow", "Stirling", 50);
        addEdge("Glasgow", "Edinburgh", 70);
        addEdge("Stirling", "Perth", 40);
        addEdge("Stirling", "Edinburgh", 50);
        addEdge("Perth", "Edinburgh", 100);
        addEdge("Perth", "Dundee", 60);

        dijkstra("Edinburgh", "Dundee");
    }

    public static void addEdge(String source, String destination, int weight) {

        graph.putIfAbsent(source, new ArrayList<>());
        graph.putIfAbsent(destination, new ArrayList<>());

        graph.get(source).add(new Node(destination, weight));
        graph.get(destination).add(new Node(source, weight));
    }

    public static void dijkstra(String start, String end) {

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));

        for (String city : graph.keySet()) {
            distances.put(city, Integer.MAX_VALUE);
        }

        distances.put(start, 0);
        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {

            Node current = pq.poll();

            for (Node neighbor : graph.get(current.city)) {

                int newDistance = distances.get(current.city) + neighbor.distance;

                if (newDistance < distances.get(neighbor.city)) {

                    distances.put(neighbor.city, newDistance);
                    previous.put(neighbor.city, current.city);

                    pq.add(new Node(neighbor.city, newDistance));
                }
            }
        }

        List<String> path = new ArrayList<>();
        String current = end;

        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }

        Collections.reverse(path);

        System.out.println("Shortest Path:");
        System.out.println(String.join(" -> ", path));

        System.out.println("Total Distance:");
        System.out.println(distances.get(end));
    }
}
