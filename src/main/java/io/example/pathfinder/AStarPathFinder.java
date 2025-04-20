package io.example.pathfinder;

import io.example.entity.Entity;
import io.example.map.Position;
import io.example.map.WorldMap;
import io.example.util.MapUtils;

import java.util.*;
import java.util.function.Predicate;

public class AStarPathFinder {

    public List<Position> findPath(WorldMap map, Position start, Predicate<Entity> isGoalEntity) {
        Set<Position> closedSet = new HashSet<>();
        Map<Position, Integer> gScore = new HashMap<>();
        Map<Position, Position> cameFrom = new HashMap<>();
        PriorityQueue<Node> openQueue = new PriorityQueue<>();

        gScore.put(start, 0);
        openQueue.add(new Node(start, heuristic(start, isGoalEntity, map)));

        while (!openQueue.isEmpty()) {
            Node currentNode = openQueue.poll();
            Position current = currentNode.pos;

            if (goalMatches(current, map, isGoalEntity)) {
                return reconstructPath(cameFrom, current);
            }

            if (!closedSet.add(current)) continue;

            for (Position neighbor : getNeighbours(current, map)) {
                if (map.isOccupied(neighbor) && !goalMatches(neighbor, map, isGoalEntity)) continue;

                int tentativeG = gScore.get(current) + 1;
                if (tentativeG < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeG);
                    int f = tentativeG + heuristic(neighbor, isGoalEntity, map);
                    openQueue.add(new Node(neighbor, f));
                }
            }
        }

        return List.of(); // no path found
    }

    private boolean goalMatches(Position pos, WorldMap map, Predicate<Entity> isGoal) {
        return map.getEntity(pos).map(isGoal::test).orElse(false);
    }

    private int heuristic(Position from, Predicate<Entity> isGoal, WorldMap map) {
        int minDist = Integer.MAX_VALUE;
        for (Map.Entry<Position, Entity> entry : map.getEntitiesSnapshot().entrySet()) {
            if (isGoal.test(entry.getValue())) {
                int dist = MapUtils.manhattanDistance(from, entry.getKey());
                if (dist < minDist) {
                    minDist = dist;
                }
            }
        }
        return minDist == Integer.MAX_VALUE ? 0 : minDist;
    }

    private List<Position> reconstructPath(Map<Position, Position> cameFrom, Position current) {
        LinkedList<Position> path = new LinkedList<>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.addFirst(current);
        }
        return path;
    }

    private List<Position> getNeighbours(Position pos, WorldMap worldMap) {
        List<Position> neighbours = new ArrayList<>();
        addIfValid(pos.offset(-1, 0), worldMap, neighbours);
        addIfValid(pos.offset(1, 0), worldMap, neighbours);
        addIfValid(pos.offset(0, -1), worldMap, neighbours);
        addIfValid(pos.offset(0, 1), worldMap, neighbours);
        return neighbours;
    }

    private void addIfValid(Position pos, WorldMap map, List<Position> path) {
        if (map.isWithinBounds(pos)) {
            path.add(pos);
        }
    }

    private static class Node implements Comparable<Node> {
        Position pos;
        int fscore;

        Node(Position pos, int fscore) {
            this.pos = pos;
            this.fscore = fscore;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.fscore, other.fscore);
        }
    }
}
