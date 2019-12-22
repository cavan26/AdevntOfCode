package day10;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
        System.out.println(asteroidDetector.findVaporisedAsteroid(chosenAsteroid, 200));
    }

    public List<Asteroid> getMap() {
        InputStream inputStream = AsteroidDetector.class.getResourceAsStream("data.txt");
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
                    angle = 2*Math.PI + angle;
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

    public Asteroid findVaporisedAsteroid(Asteroid asteroid, int number){
        int count = 0;
        int i = 0;
        Asteroid lastasteroid = new Asteroid(0,0);
        List<Double> keys = new ArrayList<>(asteroid.angleMap.keySet());
        while (count < number){
            List<Double> listAsteroid = asteroid.angleMap.get(keys.get(i))
                    .stream().map(x -> Math.pow(x.x-asteroid.x,2) + Math.pow(x.y-asteroid.y,2))
                    .collect(Collectors.toList());
            lastasteroid = asteroid.angleMap.get(keys.get(i))
                    .remove(listAsteroid.indexOf(Collections.min(listAsteroid)));
            if (asteroid.angleMap.get(keys.get(i)).isEmpty()){
                keys.remove(i);
                if (i==keys.size()){
                    i=0;
                }
            } else {
                if (i==keys.size()){
                    i = 0;
                } else {
                    i+=1;
                }
            }
            count += 1;
        }
        return lastasteroid;
    }

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
