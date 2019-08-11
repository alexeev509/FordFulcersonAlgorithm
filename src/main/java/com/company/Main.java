package com.company;

import java.util.*;

public class Main {
    public static int countOf_V;
    public static int countOf_E;
    public static Graph graph;

    public static void main(String[] args) {
        createNewGraph(new Scanner(System.in));
        System.out.println(dfs());
    }

    public static void createNewGraph(Scanner scanner) {
        countOf_V = scanner.nextInt();
        countOf_E = scanner.nextInt();
        graph = new Graph(countOf_V);
        for (int i = 0; i < countOf_E; i++) {
            graph.addEdgeToGraph(new Edge(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }
    }

    public static Integer dfs() {
        return graph.replacing();
    }
}

class Edge {
    private int from;
    private int to;
    private int capacity;
    private int flow;


    public Edge(int from, int to, int capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        flow = 0;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFlow() {
        return flow;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (from != edge.from) return false;
        return to == edge.to;
    }

    @Override
    public int hashCode() {
        int result = from;
        result = 31 * result + to;
        return result;
    }
}

class Graph {
    private List<Edge>[] mass;
    private static int maxFlow = 0;
    private static List<Integer> parents;
    private static int V;
    private static boolean[] visited;
    private static Integer lastVisitedVertex;

    public Graph(int V) {
        this.V = V;
        mass = new ArrayList[V];
        for (int i = 0; i < mass.length; i++) {
            mass[i] = new ArrayList<Edge>();
        }
    }

    public void addEdgeToGraph(Edge edge) {
        mass[edge.getFrom()].add(edge);
    }

    private Integer findIndexOfEdge(int indexOfCurrentVertex, int indexOfParent) {
        List<Edge> listOfChildsOfCurrentVertex = mass[indexOfCurrentVertex];
        for (int i = 0; i < listOfChildsOfCurrentVertex.size(); i++) {
            if (listOfChildsOfCurrentVertex.get(i).getTo() == indexOfParent) {
                return i;
            }
        }
        return -1;//if dont find
    }

    public boolean dfs() {
        parents = new LinkedList<Integer>();
        visited = new boolean[V];
        Arrays.fill(visited, false);

        Queue<Integer> queue = new ArrayDeque<Integer>();
        queue.add(0);
        parents.add(-1);
        while (!queue.isEmpty()) {
            List<Edge> children = mass[queue.peek()];
            for (int i = 0; i < children.size(); i++) {
                if (children.get(i).getFlow() < children.get(i).getCapacity()) {
                    parents.add(queue.peek());
                    queue.add(children.get(i).getTo());
                    break;
                }
            }
            visited[queue.peek()] = true;
            lastVisitedVertex=queue.peek();
            queue.poll();
        }

        return visited[V - 1];
    }

    public boolean hasNeighboursWithCapacityBiggerZero(int i) {
        List<Edge> children = mass[i];
        boolean has = false;
        for (int i1 = 0; i1 < children.size(); i1++) {
            if (mass[i].get(i1).getCapacity() !=0) {
                has = true;
            }
        }
        return has;
    }

    public Integer replacing() {
        Integer pathFlow = 0;

        while (hasNeighboursWithCapacityBiggerZero(0)) {
            if (dfs()) {
                Integer indexOfEdge = findIndexOfEdge(parents.get(parents.size() - 1), V - 1);
                Edge lastEdge = mass[parents.get(parents.size() - 1)].get(indexOfEdge);
                Integer minValueCapacity = lastEdge.getCapacity();
                for (int i = parents.size() - 1; i > 1; i--) {
                    indexOfEdge = findIndexOfEdge(parents.get(i - 1), parents.get(i));
                    lastEdge = mass[parents.get(i - 1)].get(indexOfEdge);//parents[i]???
                    minValueCapacity = Math.min(minValueCapacity, lastEdge.getCapacity());
                }
                pathFlow += minValueCapacity;

                indexOfEdge = findIndexOfEdge(parents.get(parents.size() - 1), V - 1);
                lastEdge = mass[parents.get(parents.size() - 1)].get(indexOfEdge);
                lastEdge.setCapacity(lastEdge.getCapacity() - minValueCapacity);
                for (int i = parents.size() - 1; i > 1; i--) {
                    indexOfEdge = findIndexOfEdge(parents.get(i - 1), parents.get(i));
                    lastEdge = mass[parents.get(i - 1)].get(indexOfEdge);
                    lastEdge.setCapacity(lastEdge.getCapacity() - minValueCapacity);
                }

            } else {
                Integer indexOfEdge = findIndexOfEdge(0, lastVisitedVertex);
                Edge lastEdge = mass[parents.get(parents.size() - 1)].get(indexOfEdge);
                lastEdge.setCapacity(lastEdge.getCapacity() - 1);
                for (int i = parents.size() - 1; i > 1; i--) {
                    indexOfEdge = findIndexOfEdge(parents.get(i - 1), parents.get(i));
                    lastEdge = mass[parents.get(i - 1)].get(indexOfEdge);
                    lastEdge.setCapacity(lastEdge.getCapacity() - 1);
                }
            }
        }

        return pathFlow;
    }

}
