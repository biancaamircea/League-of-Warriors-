import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private static final int MAX_SIZE = 10;
    public int playerX;
    public int playerY;
    public boolean[][] visitedCells;
    private final Random random = new Random();
    public boolean isOnPortal = false;
    public boolean isOnSanctuary = false;
    public boolean isOnEnemy = false;

    public Grid(int rows, int cols) {
        if (rows > MAX_SIZE || cols > MAX_SIZE) {
            throw new IllegalArgumentException("Grid size cannot exceed " + MAX_SIZE + "x" + MAX_SIZE);
        }

        visitedCells = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                row.add(new Cell(CellEntityType.EMPTY));
            }
            this.add(row);
        }

        placePlayer();
        placeEntities(2, CellEntityType.ENEMY);
        placeEntities(3, CellEntityType.SANCTUARY);
        placeEntities(1, CellEntityType.PORTAL);
    }

    private void placePlayer() {
        int rows = this.size();
        int cols = this.get(0).size();
        int x, y;

        do {
            x = random.nextInt(rows);
            y = random.nextInt(cols);
        } while (this.get(x).get(y).getType() != CellEntityType.EMPTY);

        this.get(x).set(y, new Cell(CellEntityType.EMPTY));
        playerX = x;
        playerY = y;
        visitedCells[x][y] = true;
    }

    private void placeEntities(int count, CellEntityType type) {
        int rows = this.size();
        int cols = this.get(0).size();
        int placed = 0;

        while (placed < count) {
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);

            if (this.get(x).get(y).getType() == CellEntityType.EMPTY) {
                this.get(x).set(y, new Cell(type));
                placed++;
            }
        }
    }

    public void printGrid() {
        System.out.println("Current Grid:");
        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < this.get(i).size(); j++) {
                if (i == playerX && j == playerY) {
                    System.out.print("P ");
                } else if (visitedCells[i][j]) {
                    switch (this.get(i).get(j).getType()) {
                        case ENEMY -> System.out.print("E ");
                        case SANCTUARY -> System.out.print("S ");
                        case PORTAL -> System.out.print("T ");
                        default -> System.out.print("V ");
                    }
                } else {
                    System.out.print("N ");
                }
            }
            System.out.println();
        }
    }

    public void movePlayer(int dx, int dy, Character player) throws Exception {
        int newX = playerX + dx;
        int newY = playerY + dy;

        if (newX < 0 || newX >= this.size() || newY < 0 || newY >= this.get(0).size()) {
            throw new Exception("Invalid move! The player cannot leave the grid.");
        }

        Cell nextCell = this.get(newX).get(newY);
        visitedCells[newX][newY] = true;

        switch (nextCell.getType()) {
            case ENEMY -> {
                isOnEnemy = true;
                System.out.println("You encountered an enemy!");
            }
            case SANCTUARY -> {
                isOnSanctuary = true;
                System.out.println("You found a sanctuary!");
            }
            case PORTAL -> {
                isOnPortal = true;
                System.out.println("You found a portal!");
            }
            case EMPTY -> System.out.println("You moved to an empty cell.");
        }

        this.get(playerX).set(playerY, new Cell(CellEntityType.VISITED));
        playerX = newX;
        playerY = newY;
        this.get(playerX).set(playerY, new Cell(CellEntityType.EMPTY));
    }

    public void goNorth(Character player) throws Exception {
        movePlayer(-1, 0, player);
    }

    public void goSouth(Character player) throws Exception {
        movePlayer(1, 0, player);
    }

    public void goWest(Character player) throws Exception {
        movePlayer(0, -1, player);
    }

    public void goEast(Character player) throws Exception {
        movePlayer(0, 1, player);
    }

    public void resetGrid() {
    }
}
