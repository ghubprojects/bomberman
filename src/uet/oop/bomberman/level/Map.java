package uet.oop.bomberman.level;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.enemy.*;
import uet.oop.bomberman.entities.stillEntities.Brick;
import uet.oop.bomberman.entities.stillEntities.Grass;
import uet.oop.bomberman.entities.stillEntities.Portal;
import uet.oop.bomberman.entities.stillEntities.Wall;
import uet.oop.bomberman.entities.stillEntities.powerup.*;
import uet.oop.bomberman.entities.player.Player;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.EventHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Map {
    private static Scene scene;
    private static Canvas canvas;
    private static GraphicsContext gc;

    public static int CANVAS_WIDTH;
    public static int CANVAS_HEIGHT;

    public static int mapWidth;
    public static int mapHeight;
    public static int mapLevel;

    public static char[][] map;
    public static char[][] stillEntities;
    private static Player player;

    private static final List<Entity> topLayer = new ArrayList<>();
    private static final List<Entity> midLayer = new ArrayList<>();
    private static final List<Entity> boardLayer = new ArrayList<>();
    private static final List<Enemy> enemyLayer = new ArrayList<>();

    private static int level = 1;
    private static int MAX_LEVEL = 5;
    private static int gameScore = 0;

    private static Label levelsLabel;
    private static Label scoreLabel;
    private static Label livesLabel;
    private static Label enemiesLabel;
    private static Label bombsLabel;
    private static Label powerupLabel;
    private static int LABEL_WIDTH;
    private static final int LABEL_HEIGHT = 48;

    public static void initScene() {
        canvas = new Canvas();
        gc = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);

        root.setLayoutX(0);
        root.setLayoutY(LABEL_HEIGHT);

        createMap(level);
        initLabel();

        AnchorPane pane = new AnchorPane();
        pane.getChildren().addAll(root, levelsLabel, scoreLabel, livesLabel, enemiesLabel, bombsLabel, powerupLabel);
        scene = new Scene(pane);
        scene.getStylesheets().add("/css/style.css");

        EventHandler.handle(scene);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                updateGame();
                renderGame(gc);
            }
        };
        timer.start();
    }

    private static void initLabel() {
        levelsLabel = new Label();
        levelsLabel.setLayoutX(0);
        levelsLabel.setPrefWidth(LABEL_WIDTH);
        levelsLabel.setPrefHeight(LABEL_HEIGHT);
        levelsLabel.setAlignment(Pos.CENTER);

        scoreLabel = new Label();
        scoreLabel.setLayoutX(LABEL_WIDTH);
        scoreLabel.setPrefWidth(LABEL_WIDTH);
        scoreLabel.setPrefHeight(LABEL_HEIGHT);
        scoreLabel.setAlignment(Pos.CENTER);

        livesLabel = new Label();
        livesLabel.setLayoutX(2 * LABEL_WIDTH);
        livesLabel.setPrefWidth(LABEL_WIDTH);
        livesLabel.setPrefHeight(LABEL_HEIGHT);
        livesLabel.setAlignment(Pos.CENTER);

        enemiesLabel = new Label();
        enemiesLabel.setLayoutX(3 * LABEL_WIDTH);
        enemiesLabel.setPrefWidth(LABEL_WIDTH);
        enemiesLabel.setPrefHeight(LABEL_HEIGHT);
        enemiesLabel.setAlignment(Pos.CENTER);

        bombsLabel = new Label();
        bombsLabel.setLayoutX(4 * LABEL_WIDTH);
        bombsLabel.setPrefWidth(LABEL_WIDTH);
        bombsLabel.setPrefHeight(LABEL_HEIGHT);
        bombsLabel.setAlignment(Pos.CENTER);

        powerupLabel = new Label();
        powerupLabel.setLayoutX(5 * LABEL_WIDTH);
        powerupLabel.setPrefWidth(LABEL_WIDTH);
        powerupLabel.setPrefHeight(LABEL_HEIGHT);
        powerupLabel.setAlignment(Pos.CENTER);
    }

    public static void createMap(int level) {
        clearMap();
        loadMapFile("/levels/Level" + level + ".txt");
        canvas.setHeight(CANVAS_HEIGHT);
        canvas.setWidth(CANVAS_WIDTH);
        LABEL_WIDTH = CANVAS_WIDTH / 6;
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                addEntity(map[i][j], j * Sprite.SCALED_SIZE, i * Sprite.SCALED_SIZE);
            }
        }
    }

    public static void loadMapFile(String filePath) {
        try {
            URL urlMap = Map.class.getResource(filePath);
            assert urlMap != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlMap.openStream()));

            String data = reader.readLine();
            StringTokenizer tokens = new StringTokenizer(data);

            mapLevel = Integer.parseInt(tokens.nextToken());
            mapHeight = Integer.parseInt(tokens.nextToken());
            mapWidth = Integer.parseInt(tokens.nextToken());

            CANVAS_HEIGHT = mapHeight * Sprite.TILE_SIZE;
            CANVAS_WIDTH = mapWidth * Sprite.TILE_SIZE;

            map = new char[mapHeight][mapWidth];
            stillEntities = new char[mapHeight][mapWidth];

            for (int i = 0; i < mapHeight; i++) {
                char[] lineTiles = reader.readLine().toCharArray();
                for (int j = 0; j < mapWidth; j++) {
                    map[i][j] = lineTiles[j];
                    if ('#' == lineTiles[j] || '*' == lineTiles[j] || 'x' == lineTiles[j]
                            || 'b' == lineTiles[j] || 'f' == lineTiles[j] || 's' == lineTiles[j]
                            || 'w' == lineTiles[j] || 'm' == lineTiles[j]) {
                        stillEntities[i][j] = lineTiles[j];
                    } else {
                        stillEntities[i][j] = ' ';
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addEntity(char c, int x, int y) {
        switch (c) {
            //Still Entities
            case '#' -> boardLayer.add(new Wall(x, y));
            case '*' -> {
                boardLayer.add(new Grass(x, y));
                topLayer.add(new Brick(x, y));
            }
            case 'x' -> {
                boardLayer.add(new Grass(x, y));
                midLayer.add(new Portal(x, y));
                topLayer.add(new Brick(x, y));
            }
            case ' ' -> boardLayer.add(new Grass(x, y));

            //Powerups
            case 'b' -> {
                boardLayer.add(new Grass(x, y));
                midLayer.add(new PowerupBombs(x, y));
                topLayer.add(new Brick(x, y));
            }
            case 's' -> {
                boardLayer.add(new Grass(x, y));
                midLayer.add(new PowerupSpeed(x, y));
                topLayer.add(new Brick(x, y));
            }
            case 'f' -> {
                boardLayer.add(new Grass(x, y));
                midLayer.add(new PowerupFlames(x, y));
                topLayer.add(new Brick(x, y));
            }
            case 'w' -> {
                boardLayer.add(new Grass(x, y));
                midLayer.add(new PowerupWallPass(x, y));
                topLayer.add(new Brick(x, y));
            }
            case 'm' -> {
                boardLayer.add(new Grass(x, y));
                midLayer.add(new PowerupFlamePass(x, y));
                topLayer.add(new Brick(x, y));
            }
            //Player
            case 'p' -> {
                boardLayer.add(new Grass(x, y));
                player = Player.setPlayer(x, y);
            }
            //Enemies
            case '1' -> {
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Ballom(x, y));
            }
            case '2' -> {
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Oneal(x, y));
            }
            case '3' -> {
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Doll(x, y));
            }
            case '4' -> {
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Minvo(x, y));
            }
            case '5' -> {
                boardLayer.add(new Grass(x, y));
                enemyLayer.add(new Kondoria(x, y));
            }
        }
    }

    public static Entity getStillEntity(int x, int y) {
        for (Entity entity : boardLayer) {
            if (entity instanceof Wall && entity.getX() == x && entity.getY() == y) {
                return entity;
            }
        }
        for (Entity entity : topLayer) {
            if (entity instanceof Brick && entity.getX() == x && entity.getY() == y) {
                return entity;
            }
        }
        return null;
    }

    public static void removeEntity() {
        midLayer.removeIf(Entity::isRemoved);
        topLayer.removeIf(Entity::isRemoved);
        for (int i = 0; i < enemyLayer.size(); i++) {
            if (enemyLayer.get(i).isRemoved()) {
                gameScore += enemyLayer.get(i).getScore();
                enemyLayer.remove(i);
                --i;
            }
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Next Levels
    |--------------------------------------------------------------------------
    */
    public static void nextLevel() {
        clearMap();
        increaseLevel();
        createMap(level);
        player.resetState();
        player.setBombCount();
    }

    public static void clearMap() {
        gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        enemyLayer.clear();
        topLayer.clear();
        midLayer.clear();
        boardLayer.clear();
    }

    public static void increaseLevel() {
        if (level < MAX_LEVEL) {
            level++;
        } else {
            level = 1;
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Render and Update Game
    |--------------------------------------------------------------------------
    */
    private static void updateGame() {
        updateLabels();
        updateLayers();
        Player.getPlayer().update();
        removeEntity();
    }

    private static void updateLabels() {
        livesLabel.setText("Lives: " + player.getLives());
        bombsLabel.setText("Bombs: " + player.getRemainBomb());
        scoreLabel.setText("Score: " + gameScore);
        if (player.getPowerup() == null) {
            powerupLabel.setText("Powerup: " + 0);
        } else powerupLabel.setText("Powerup: " + player.getPowerup());
        enemiesLabel.setText("Enemies: " + enemyLayer.size());
        levelsLabel.setText("Level: " + level);
    }

    private static void updateLayers() {
        for (int i = 0; i < midLayer.size(); i++) {
            Map.getMidLayer().get(i).update();
        }
        for (int i = 0; i < topLayer.size(); i++) {
            Map.getTopLayer().get(i).update();
        }
        for (int i = 0; i < enemyLayer.size(); i++) {
            Map.getEnemyLayer().get(i).update();
        }
    }

    private static void renderGame(GraphicsContext gc) {
        gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        for (Entity entity : boardLayer) {
            entity.render(gc);
        }

        for (Entity entity : midLayer) {
            entity.render(gc);
        }
        for (Entity entity : topLayer) {
            entity.render(gc);
        }
        for (Entity entity : enemyLayer) {
            entity.render(gc);
        }
        Player.getPlayer().render(gc);
    }

    /*
    |--------------------------------------------------------------------------
    | Getters and Setters
    |--------------------------------------------------------------------------
    */
    public static Scene getScene() {
        return scene;
    }

    public static List<Entity> getBoardLayer() {
        return boardLayer;
    }

    public static List<Entity> getMidLayer() {
        return midLayer;
    }

    public static List<Entity> getTopLayer() {
        return topLayer;
    }

    public static List<Enemy> getEnemyLayer() {
        return enemyLayer;
    }
}
