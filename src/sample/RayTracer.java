package sample;

import java.util.ArrayList;

public class RayTracer {

    private int width, height, depth;
    private ArrayList<ArrayList <Ray>> rays = new ArrayList<>();
    private int[][][] worldSpace = new int[height][width][depth];   //мир представлен трехмерным массивом (0 - пустота, 1 - твердый обьект)

    public RayTracer(int width, int height, int depth, int[][][] worldSpace) {

        this.width = width;
        this.height = height;
        this.depth = depth;
        this.worldSpace = worldSpace;

        defineArray();  //определяем массив лучей

    }

    private void defineArray() {

        rays.clear();

        for (int row = 0; row < height; row++) {
            rays.add(new ArrayList<Ray>());
            for (int col = 0; col < width; col++) {
                rays.get(row).add(new Ray(depth, worldSpace[row][col]));
            }
        }

    }

    public int[][] trace(int[][][] worldSpace) {

        //обновим данные
        this.worldSpace = worldSpace;
        defineArray();

        int[][] distancesArray = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                distancesArray[row][col] = rays.get(row).get(col).shot();
            }
        }

        return distancesArray;
    }

}
