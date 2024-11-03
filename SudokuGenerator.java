import java.util.Random;
import java.util.Scanner;

public class SudokuGenerator {

    private static final int GRID_SIZE = 9;
    private static final int EMPTY_CELL = 0;
    private static int[][] fullSudokuGrid = new int[GRID_SIZE][GRID_SIZE];
    private static int[][] sudokuPuzzle = new int[GRID_SIZE][GRID_SIZE];
    private static Scanner scanner = new Scanner(System.in);

    // Function to check if a number can be placed in a cell without breaking Sudoku rules
    private static boolean isSafe(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (grid[row][i] == num || grid[i][col] == num ||
                    grid[row - row % 3 + i / 3][col - col % 3 + i % 3] == num) {
                return false;
            }
        }
        return true;
    }

    // Recursive function to fill the grid using backtracking
    private static boolean fillGrid(int[][] grid) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (grid[row][col] == EMPTY_CELL) {
                    for (int num = 1; num <= GRID_SIZE; num++) {
                        if (isSafe(grid, row, col, num)) {
                            grid[row][col] = num;
                            if (fillGrid(grid)) {
                                return true;
                            }
                            grid[row][col] = EMPTY_CELL;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Function to create a full Sudoku grid using the backtracking solution
    private static void createFullSudokuGrid() {
        fillGrid(fullSudokuGrid);
    }

    // Function to create a Sudoku puzzle by removing random numbers
    private static void createSudokuPuzzle() {
        for (int i = 0; i < GRID_SIZE; i++) {
            System.arraycopy(fullSudokuGrid[i], 0, sudokuPuzzle[i], 0, GRID_SIZE);
        }

        int cellsToRemove = GRID_SIZE * GRID_SIZE / 2;  // Remove half of the cells
        Random rand = new Random();

        while (cellsToRemove > 0) {
            int row = rand.nextInt(GRID_SIZE);
            int col = rand.nextInt(GRID_SIZE);
            if (sudokuPuzzle[row][col] != EMPTY_CELL) {
                sudokuPuzzle[row][col] = EMPTY_CELL;
                cellsToRemove--;
            }
        }
    }

    // Function to print the Sudoku grid
    private static void printSudoku(int[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("-----------");
            }
            for (int j = 0; j < GRID_SIZE; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("|");
                }
                System.out.print(grid[i][j] == EMPTY_CELL ? " ." : " " + grid[i][j]);
            }
            System.out.println();
        }
    }

    // Main function to manage the menu and user interaction
    public static void main(String[] args) {
        System.out.println("Welcome to the Sudoku Generator!");

        while (true) {
            // Generate a full valid Sudoku grid
            fullSudokuGrid = new int[GRID_SIZE][GRID_SIZE];
            createFullSudokuGrid();

            // Create a puzzle by removing numbers
            createSudokuPuzzle();

            System.out.println("\nHere's your generated Sudoku puzzle:");
            printSudoku(sudokuPuzzle);

            // Menu options for user
            System.out.println("\nOptions:");
            System.out.println("a) Show solution");
            System.out.println("b) Generate new puzzle");
            System.out.println("c) Quit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "a":
                    System.out.println("\nHere is the solution:");
                    printSudoku(fullSudokuGrid);
                    break;
                case "b":
                    System.out.println("\nGenerating a new puzzle...");
                    break;
                case "c":
                    System.out.println("Thank you for using the Sudoku Generator. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
