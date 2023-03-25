package sample;

public class Ray {

    private int depth;
    private int [] map;

    public Ray(int depth, int[] map) {

        this.depth = depth;
        this.map = map; //перердаю сюда только часть мира (прямую на 1*1*n условных единиц)

    }

    public int shot() {

        for (int dist = 0; dist < depth; dist++) {
            //0 - прозрачно, 1 - 100% поглощение света
            if (map[dist] != 0) {
                return dist;    //расстояние от камеры до препядствия
            }
        }

        return 0;   //препядствий нет
    }

}
