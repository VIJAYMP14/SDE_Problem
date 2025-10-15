import java.util.*;

public class TileBoard {
    private int size;
    private int[][] board;
    private int score;
    private boolean gameOver;

    public TileBoard(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.score = 0;
        this.gameOver = false;
        initBoard();
    }

    private void initBoard() {
        for (int[] row : board) Arrays.fill(row, 0);
        addRandomTile();
        addRandomTile();
        score = 0;
        gameOver = false;
    }

    private void addRandomTile() {
        List<int[]> empty = new ArrayList<>();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (board[i][j] == 0)
                    empty.add(new int[]{i, j});
        if (!empty.isEmpty()) {
            int[] cell = empty.get(new Random().nextInt(empty.size()));
            board[cell[0]][cell[1]] = new Random().nextDouble() < 0.9 ? 2 : 4;
        }
    }

    public boolean move(Direction direction) {
        boolean moved = false;
        switch (direction) {
            case LEFT: moved = moveLeft(); break;
            case RIGHT: moved = moveRight(); break;
            case UP: moved = moveUp(); break;
            case DOWN: moved = moveDown(); break;
        }
        if (moved) addRandomTile();
        gameOver = isGameOver();
        return moved;
    }

    private boolean moveLeft() {
        boolean moved = false;
        for (int i = 0; i < size; i++) {
            int[] original = Arrays.copyOf(board[i], size);
            board[i] = compressAndMerge(board[i]);
            if (!Arrays.equals(original, board[i])) moved = true;
        }
        return moved;
    }

    private boolean moveRight() {
        boolean moved = false;
        for (int i = 0; i < size; i++) {
            int[] reversed = reverse(board[i]);
            int[] merged = compressAndMerge(reversed);
            int[] newRow = reverse(merged);
            if (!Arrays.equals(board[i], newRow)) moved = true;
            board[i] = newRow;
        }
        return moved;
    }

    private boolean moveUp() {
        boolean moved = false;
        for (int j = 0; j < size; j++) {
            int[] col = new int[size];
            for (int i = 0; i < size; i++) col[i] = board[i][j];
            int[] merged = compressAndMerge(col);
            for (int i = 0; i < size; i++) {
                if (board[i][j] != merged[i]) moved = true;
                board[i][j] = merged[i];
            }
        }
        return moved;
    }

    private boolean moveDown() {
        boolean moved = false;
        for (int j = 0; j < size; j++) {
            int[] col = new int[size];
            for (int i = 0; i < size; i++) col[i] = board[i][j];
            int[] reversed = reverse(col);
            int[] merged = compressAndMerge(reversed);
            int[] newCol = reverse(merged);
            for (int i = 0; i < size; i++) {
                if (board[i][j] != newCol[i]) moved = true;
                board[i][j] = newCol[i];
            }
        }
        return moved;
    }

    private int[] compressAndMerge(int[] line) {
        int[] compressed = Arrays.stream(line).filter(v -> v != 0).toArray();
        List<Integer> merged = new ArrayList<>();
        int skip = -1;

        for (int i = 0; i < compressed.length; i++) {
            if (i == skip) continue;
            if (i + 1 < compressed.length && compressed[i] == compressed[i + 1]) {
                merged.add(compressed[i] * 2);
                score += compressed[i] * 2;
                skip = i + 1;
            } else {
                merged.add(compressed[i]);
            }
        }
        while (merged.size() < size) merged.add(0);
        return merged.stream().mapToInt(i -> i).toArray();
    }

    private int[] reverse(int[] arr) {
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++)
            res[i] = arr[arr.length - 1 - i];
        return res;
    }

    private boolean isGameOver() {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) return false;
                if (j < size - 1 && board[i][j] == board[i][j + 1]) return false;
                if (i < size - 1 && board[i][j] == board[i + 1][j]) return false;
            }
        return true;
    }

    // Getters and setters
    public int getSize() { return size; }
    public int[][] getBoard() { return board; }
    public int getScore() { return score; }
    public boolean isGameOverFlag() { return gameOver; }
    public void restart() { initBoard(); }
}
