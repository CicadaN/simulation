package io.example.pathfinder;

import io.example.map.Position;
import io.example.map.WorldMap;

import java.util.*;
import java.util.function.Predicate;

public class AStarPathFinder {

    public List<Position> findPath(WorldMap map, Position start, Predicate<Position> isGoal) {
        Set<Position> closedSet = new HashSet<>();
        Map<Position, Integer> gScore = new HashMap<>();
        Map<Position, Position> cameFrom = new HashMap<>();
        PriorityQueue<Node> openQueue = new PriorityQueue<>();

        gScore.put(start, 0);
        openQueue.add(new Node(start, heuristic(start, isGoal, map )));

        while (!openQueue.isEmpty()) {
            Node currentNode = openQueue.poll();
            Position current = currentNode.pos;

            if (isGoal.test(current)) {
                return reconstructPath(cameFrom, current);
            }

            if (closedSet.contains(current)) continue;
            closedSet.add(current);

            for (Position neighbor : getNeighbours(current, map)) {
                if (map.isOccupied(neighbor) && !isGoal.test(neighbor)) continue;

                int tentativeG = gScore.get(current) + 1;

                if (tentativeG < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeG);
                    int f = tentativeG + heuristic(neighbor, isGoal, map);
                    openQueue.add(new Node(neighbor, f));
                }
            }
        }

        return List.of();
    }

    private List<Position> reconstructPath(Map<Position, Position> cameFrom, Position current) {
        List<Position> path = new ArrayList<>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(0, current);
        }
        return path;
    }

    private int heuristic(Position from, Predicate<Position> isGoal, WorldMap map) {
        int minDist = Integer.MAX_VALUE;
        for (Position pos : map.allPositions()) {
            if (isGoal.test(pos)) {
                int dist = from.getDistance(pos);
                if (dist < minDist) minDist = dist;
            }
        }
        return minDist == Integer.MAX_VALUE ? 0 : minDist;
    }

    private List<Position> getNeighbours(Position pos, WorldMap map) {
        List<Position> neighbours = new ArrayList<>();
        addIfValid(pos.move(-1,0), map, neighbours);
        addIfValid(pos.move(1,0), map, neighbours);
        addIfValid(pos.move(0,-1), map, neighbours);
        addIfValid(pos.move(0,1), map, neighbours);
        return neighbours;
    }


    private void addIfValid(Position pos, WorldMap map, List<Position> path) {
        if(map.isWithinBounds(pos)) {
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
