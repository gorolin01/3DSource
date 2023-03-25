package sample;

import javax.swing.*;

public class source {

    private static int width = 100;
    private static int height = 100;
    private static int depth = 100;
    private static int[][][] worldSpace = new int[height][width][depth];   //мир представлен трехмерным массивом (0 - пустота, 1 - твердый обьект)
    private static RayTracer rayTracer = new RayTracer(width, height, depth, worldSpace);

    public static void main(String[] args) throws InterruptedException {

        //отладка
        //setWorldSpace();
        //drawCube(10, 10, 10, Math.PI / 4, Math.PI / 6, Math.PI / 8);

        JFrame frame = new JFrame("Image Panel");
        MyPanel panel = new MyPanel(rayTracer.trace(worldSpace), depth);
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //ОТРИСОВКА
        int angle = 0;
        for (int n = 0; n < 60*10; n++) {
            resetWorldSpace();  //перезагрузка мира
            drawCube(40, 40, 40, angle, angle, angle);
            //printMass(rayTracer.trace());
            panel.setImageData(rayTracer.trace(worldSpace));
            angle += 1;
            if(angle > 360){
                angle = 0;
            }
            Thread.sleep(1000 / 60);
        }

    }

    public int getElementWorldSpace(int x, int y, int z) {
        return worldSpace[x][y][z];
    }

    public void setElementWorldSpace(int x, int y, int z, int value) {
        worldSpace[x][y][z] = value;
    }

    //очистка всего мира
    private static void resetWorldSpace(){
        for (int y = 0; y < worldSpace.length; y++) {
            for (int x = 0; x < worldSpace[y].length; x++) {
                for (int z = 0; z < worldSpace[y][x].length; z++) {
                    worldSpace[y][x][z] = 0;
                }
            }
        }
    }

    public static void drawCube(int height, int width, int depth, double angleXG, double angleYG, double angleZG) {
        // Определяем центр куба в пространстве
        int centerX = worldSpace.length / 2;
        int centerY = worldSpace[0].length / 2;
        int centerZ = worldSpace[0][0].length / 2;

        //конвертируем углы в радианы
        double angleX = Math.toRadians(angleXG);
        double angleY = Math.toRadians(angleYG);
        double angleZ = Math.toRadians(angleZG);

        // Создаем матрицы поворота для каждой оси
        double[][] rotationX = {{1, 0, 0}, {0, Math.cos(angleX), -Math.sin(angleX)}, {0, Math.sin(angleX), Math.cos(angleX)}};
        double[][] rotationY = {{Math.cos(angleY), 0, Math.sin(angleY)}, {0, 1, 0}, {-Math.sin(angleY), 0, Math.cos(angleY)}};
        double[][] rotationZ = {{Math.cos(angleZ), -Math.sin(angleZ), 0}, {Math.sin(angleZ), Math.cos(angleZ), 0}, {0, 0, 1}};

        // Создаем матрицу поворота для всего куба, перемножив матрицы поворота по каждой оси
        double[][] rotation = multiplyMatrices(multiplyMatrices(rotationX, rotationY), rotationZ);

        // Проходимся по всем пикселям пространства, и для каждого пикселя вычисляем расстояние от центра куба в трех измерениях
        for (int i = 0; i < worldSpace.length; i++) {
            for (int j = 0; j < worldSpace[0].length; j++) {
                for (int k = 0; k < worldSpace[0][0].length; k++) {
                    int distanceX = Math.abs(i - centerX);
                    int distanceY = Math.abs(j - centerY);
                    int distanceZ = Math.abs(k - centerZ);

                    // Если пиксель находится внутри куба, то присваиваем ему значение 1
                    if (distanceX <= width / 2 && distanceY <= height / 2 && distanceZ <= depth / 2) {
                        // Применяем матрицу поворота к координатам пикселя
                        double[] rotated = multiplyMatrixVector(rotation, new double[]{i - centerX, j - centerY, k - centerZ});

                        // Присваиваем значение 1 в соответствующей точке в пространстве
                        worldSpace[(int) rotated[0] + centerX][(int) rotated[1] + centerY][(int) rotated[2] + centerZ] = 1;
                    }
                }
            }
        }
    }

    // Функция для умножения двух матриц
    public static double[][] multiplyMatrices(double[][] a, double[][] b) {
        int n = a.length;
        int m = b[0].length;
        double[][] result = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < b.length; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    // Функция для умножения матрицы на вектор
    public static double[] multiplyMatrixVector(double[][] a, double[] b) {
        int n = a.length;
        int m = b.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i] += a[i][j] * b[j];
            }
        }
        return result;
    }

    //заполнение мира
    private static void setWorldSpace() {

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int z = 0; z < depth; z++) {
                    worldSpace[y][x][z] = Randomizer.generate();
                }
            }
        }

    }

    //для отладки
    private static void printMass(int mass[][]) {
        for (int i = 0; i < mass.length; i++) {
            for (int j = 0; j < mass[i].length; j++) {
                System.out.print(" " + mass[i][j]);
            }
            System.out.println();
        }
    }

}