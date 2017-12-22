package minesweeperjamannow;

public class MinesweeperModel {
    
    private static final int BOMB_COUNT = 50;
    public static final int BOARD_WIDTH = 30;
    public static final int BOARD_HEIGHT = 16;
    
    /*
     *  -1   : bomb
     *  0..n : number of bombs surrounding a cell
     */
    private int[][] mapStatus = new int[BOARD_WIDTH][BOARD_HEIGHT];
    private boolean[][] mapVisible = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
    private int openBlockCount = 0;
    
    public MinesweeperModel() {}
    
    public boolean checkWin() {
        return BOARD_WIDTH*BOARD_HEIGHT == BOMB_COUNT+openBlockCount;
    }
    
    public void openBlockAt(int x, int y) {
        if (openBlockCount == 0) {
            generateBombsWithoutTouching(x, y);
            fillNonBombCell();
        }
        floodfillZeroCell(x, y);
    }
    
    public void printMapToSTDIN() {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                System.out.print(mapStatus[x][y] == -1 ? "X" : mapStatus[x][y]);
//                System.out.print(mapVisible[x][y]? "O" : "X");
            }
            System.out.println("");
        }
    }
    
    private void generateBombsWithoutTouching(int x, int y) {
        int curBombCount = 0;
        while (curBombCount < BOMB_COUNT) {
            int bombX = Tool.getRandomIntegerInRange(0, BOARD_WIDTH-1);
            int bombY = Tool.getRandomIntegerInRange(0, BOARD_HEIGHT-1);
            if ( mapStatus[bombX][bombY] != -1 &&
                    (Math.abs(bombX-x) >= 2 || Math.abs(bombY-y) >= 2) ) {
                mapStatus[bombX][bombY] = -1;
                curBombCount += 1;
            }
        }
    }
    
    private void fillNonBombCell() {
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                if (mapStatus[x][y] != -1) {
                    mapStatus[x][y] += hasBomb(x-1, y-1);
                    mapStatus[x][y] += hasBomb(x, y-1);
                    mapStatus[x][y] += hasBomb(x+1, y-1);
                    
                    mapStatus[x][y] += hasBomb(x-1, y);
                    mapStatus[x][y] += hasBomb(x+1, y);
                    
                    mapStatus[x][y] += hasBomb(x-1, y+1);
                    mapStatus[x][y] += hasBomb(x, y+1);
                    mapStatus[x][y] += hasBomb(x+1, y+1);
                }
            }
        }
    }
    
    private int hasBomb(int x, int y) {
        if (0 <= x && x < BOARD_WIDTH &&
            0 <= y && y < BOARD_HEIGHT &&
            mapStatus[x][y] == -1)
                return 1;
        else 
            return 0;
    }
    
    private void floodfillZeroCell(int x, int y) {
        if (!(0 <= x && x < BOARD_WIDTH && 0 <= y && y < BOARD_HEIGHT)) return;
        if (mapVisible[x][y]) return;
        mapVisible[x][y] = true;
        openBlockCount += 1;
        if (mapStatus[x][y] != 0) return;
        int[][] dirs = { {1,0}, {-1,0}, {0,1}, {0,-1} };
        for (int[] dir : dirs) {
            floodfillZeroCell(x+dir[0], y+dir[1]);
        }
    }
    
    public int getMapStatusAt(int x, int y) {
        return mapStatus[x][y];
    }
    
    public boolean getMapVisibleAt(int x, int y) {
        return mapVisible[x][y];
    }
    
    public boolean checkLose(int x, int y) {
        return mapStatus[x][y] == -1;
    }
}
