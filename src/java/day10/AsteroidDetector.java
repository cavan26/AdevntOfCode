package day10;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class AsteroidDetector {
    private List<Asteroid> asteroidMap;

    public AsteroidDetector(){
        this.asteroidMap = new ArrayList<>();
    }

    public List<Asteroid> getAsteroidMap(){
        return new ArrayList<>(asteroidMap);
    }

    public static void main(String[] args) {
        AsteroidDetector asteroidDetector = new AsteroidDetector();
        asteroidDetector.getMap();
        int max = 0;
        Asteroid chosenAsteroid = asteroidDetector.asteroidMap.get(0);
        for (Asteroid asteroid: asteroidDetector.asteroidMap){
            int numberVisibleAsteroid = asteroidDetector.findNumberVisibleAsteroid(asteroid);
            if (numberVisibleAsteroid>max){
                max = numberVisibleAsteroid;
                chosenAsteroid = asteroid;
            }
        }
        System.out.println(chosenAsteroid);
        System.out.println(chosenAsteroid.angleMap);
        System.out.println(max);
    }

    public List<Asteroid> getMap() {
        InputStream inputStream = AsteroidDetector.class.getResourceAsStream("data_ex.txt");
        Scanner scan = new Scanner(inputStream).useDelimiter("\n");
        int j = 0;
        while (scan.hasNext()) {
            int i = 0;
            char[] chars = scan.next().toCharArray();
            for (char c : chars) {
                if (c == '#') {
                    asteroidMap.add(new Asteroid(i, j));
                }
                i += 1;
            }
            j += 1;
        }
        return asteroidMap;
    }

    public int findNumberVisibleAsteroid(Asteroid asteroid){
        TreeMap<Double, List<Asteroid>> setAngle = new TreeMap<>();
        for (Asteroid asteroid1: asteroidMap){
            double x = (double) asteroid1.x-asteroid.x;
            double y = (double) asteroid1.y-asteroid.y;
            if (!(x==0&&y==0)) {
                double angle = Math.atan2(y, x) + Math.PI/2;
                if (angle<0){
                    angle = 2*Math.PI - angle;
                }
                if (setAngle.containsKey(angle)){
                    List<Asteroid> list = setAngle.get(angle);
                    list.add(asteroid1);
                    setAngle.put(angle, list);
                } else {
                    setAngle.put(angle, new ArrayList<>(Arrays.asList(asteroid1)));
                }
                asteroid.setAngleMap(setAngle);
            }
        }
        return setAngle.keySet().size();

    }

//    public int findVaporisedAsteroid(Asteroid asteroid, int number){
//        int count = 0;
//        int i = 0;
//        List<Double> keys = new ArrayList<>(asteroid.angleMap.keySet());
//        System.out.println(keys);
//        while (count <= number){
//            int value = asteroid.angleMap.get(keys.get(i));
//            asteroid.angleMap.put(keys.get(i), value - 1);
//            if (value == 1){
//                keys.remove(i);
//            }
//            if (i==keys.size()-1){
//                i = 0;
//            } else {
//                i+=1;
//            }
//        }
//        return 0;
//    }

    private class Asteroid{
        private int x;
        private int y;
        private TreeMap<Double, List<Asteroid>> angleMap;

        public Asteroid(int x, int y){
            this.x = x;
            this.y = y;
            this.angleMap = new TreeMap<>();
        }

        @Override
        public String toString() {
            return "Asteroid: (" + x + ", " + y + ")";
        }

        public void setAngleMap(TreeMap<Double, List<Asteroid>> angleMap){
            this.angleMap = angleMap;
        }
    }
}
