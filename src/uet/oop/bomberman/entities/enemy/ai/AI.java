package uet.oop.bomberman.entities.enemy.ai;

import uet.oop.bomberman.entities.player.Player;
import uet.oop.bomberman.Direction;
import uet.oop.bomberman.level.Map;

import java.util.ArrayList;
import java.util.List;

public class AI {
    private IQ iq;
    private boolean wallPass;
    private boolean brickPass;
    private Player player;

    public static enum IQ {
        LOW, MEDIUM;
    }

    class Node {
        private int x, y;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public AI(IQ iq, boolean brickPass, boolean wallPass) {
        this.iq = iq;
        this.wallPass = wallPass;
        this.brickPass = brickPass;
        player = Player.getPlayer();
    }

    public Direction changeDirection(char[][] stillEntities, int xTile, int yTile) {
        Direction direction = switch (iq) {
            case LOW -> Direction.dir[(int) (Math.random() * 4)];
            case MEDIUM -> pathFinding(stillEntities, xTile, yTile, player.getXTile(), player.getYTile(), IQ.MEDIUM);
            default -> null;
        };
        return direction;
    }

    private Direction randomMoving(char[][] stillEntities, int xTile, int yTile) {
        return Direction.dir[(int) (Math.random() * 4)];
    }

    private boolean movableNode(char[][] stillEntities, int x, int y) {
        return 0 <= x && x < Map.mapWidth && 0 <= y && y < Map.mapHeight
                && (' ' == stillEntities[y][x] || (brickPass && '*' == stillEntities[y][x]) || (wallPass && '#' == stillEntities[y][x]));
    }

    private Direction pathFinding(char[][] stillEntities, int xTile, int yTile, int playerXTile, int playerYTile, IQ iq) {
        Direction direction = null;

        char[][] checkingMap = new char[Map.mapHeight][Map.mapWidth];
        for (int i = 0; i < Map.mapHeight; i++) {
            for (int j = 0; j < Map.mapWidth; j++) {
                if (movableNode(stillEntities, j, i)) {
                    checkingMap[i][j] = '1';
                } else {
                    checkingMap[i][j] = '0';
                }
            }
        }

        int tracingRange = 0;

        if (iq == IQ.MEDIUM) {
            tracingRange = 25;
        }

        boolean pathExits = false;

        if (Math.abs((xTile - playerXTile) * (yTile - playerYTile)) < tracingRange) {
            List<Node> queue = new ArrayList<>();
            queue.add(new Node(playerXTile, playerYTile));

            while (!queue.isEmpty()) {
                Node lastNode = queue.remove(0);
                if (lastNode.x - 1 == xTile && lastNode.y == yTile) {
                    direction = Direction.RIGHT;
                    pathExits = true;
                    break;
                }
                if (lastNode.x + 1 == xTile && lastNode.y == yTile) {
                    direction = Direction.LEFT;
                    pathExits = true;
                    break;
                }
                if (lastNode.x == xTile && lastNode.y - 1 == yTile) {
                    direction = Direction.DOWN;
                    pathExits = true;
                    break;
                }
                if (lastNode.x == xTile && lastNode.y + 1 == yTile) {
                    direction = Direction.UP;
                    pathExits = true;
                    break;
                }
                try {
                    checkingMap[lastNode.y][lastNode.x] = '0';
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
                List<Node> neighbors = getNeighborNodes(checkingMap, lastNode);
                queue.addAll(neighbors);
            }
        }

        if (!pathExits) {
            direction = randomMoving(stillEntities, xTile, yTile);
        }
        return direction;
    }

    private List<Node> getNeighborNodes(char[][] stillEntities, Node node) {
        List<Node> neighbors = new ArrayList<>();
        if (validNode(stillEntities, node.x - 1, node.y)) {
            neighbors.add(new Node(node.x - 1, node.y));
        }
        if (validNode(stillEntities, node.x + 1, node.y)) {
            neighbors.add(new Node(node.x + 1, node.y));
        }
        if (validNode(stillEntities, node.x, node.y - 1)) {
            neighbors.add(new Node(node.x, node.y - 1));
        }
        if (validNode(stillEntities, node.x, node.y + 1)) {
            neighbors.add(new Node(node.x, node.y + 1));
        }
        return neighbors;
    }

    private boolean validNode(char[][] stillEntities, int x, int y) {
        return 0 <= x && x < Map.mapWidth && 0 <= y && y < Map.mapHeight && '0' != stillEntities[y][x];
    }
}
