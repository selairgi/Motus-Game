package controllers;

public class Matrix {

    private String[][] entries;
    private char[][] ent;
    private int rows;
    private int cols;
    private int counter;

    public Matrix(int rows, int cols) {
        this.ent = new char[rows][cols];
        this.entries = new String[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.counter = 0;
    }
    public void setEntry(int i, int j, char value){
        ent[i][j] = value;
    }
    public void set(String value) {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                ent[j][i] = value.charAt(i);

            }
        }
        // System.out.println("______________");
        // this.showEntries();
    }

    public void add(String value) {
        int row = counter / cols;
        int col = counter % cols;

        if (counter < rows * cols) {
            entries[row][col] = value;
            counter++;
        } else {
            counter ++;
        }
        // System.out.println("______________");
        // this.showEntries();
    }

    public void showEntries() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (entries[i][j] != null) {
                    System.out.println(entries[i][j]);
                } else {
                    break; // Exit the inner loop when encountering a null value
                }
            }
        }
    }

    public static void main(String args[]) {
        Matrix mat = new Matrix(2, 5);
        mat.set("salah");
        mat.showEntries();
    }
}
