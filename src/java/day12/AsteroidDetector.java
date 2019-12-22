package day12;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AsteroidDetector {
    private List<List<Integer>> positions;
    private List<List<Integer>> velocities;
    private List<Integer> potentialEnergy;
    private List<Integer> kineticEnergy;
    private List<Integer> totalEnergy;
    private int totalEnergySystem;

    public AsteroidDetector(){
        positions = new ArrayList<>();
        velocities = new ArrayList<>();
        this.readPosition();
        this.initialVelocities();
        this.potentialEnergy = new ArrayList<>(Arrays.asList(0,0,0,0));
        this.kineticEnergy = new ArrayList<>(Arrays.asList(0,0,0,0));
        this.totalEnergy = new ArrayList<>(Arrays.asList(0,0,0,0));
        this.totalEnergySystem = 0;
        this.calculateEnergies();
    }

    public static void main(String[] args) {
        AsteroidDetector asteroidDetector = new AsteroidDetector();
        System.out.println("Energy after 1 step: ");
        System.out.println(asteroidDetector.toString());
        for (int i=0; i<277300; i++){
            asteroidDetector.updateGravity();
            asteroidDetector.updatePosition();
            asteroidDetector.calculateEnergies();
            //System.out.println("Energy after " + i +" steps: ");
            //System.out.println(asteroidDetector.toString());
        }
        System.out.println("Energy afterq steps: ");
        System.out.println(asteroidDetector.toString());
    }

    public void readPosition() {
        InputStream inputStream = AsteroidDetector.class.getResourceAsStream("data.txt");
        Scanner scan = new Scanner(inputStream).useDelimiter("\n");
        int moon = 0;
        while (scan.hasNext()){
            String sequence = scan.next();
            positions.add(new ArrayList<>());
            int start = sequence.indexOf("x") + 2;
            positions.get(moon).add(Integer.parseInt(sequence.subSequence(
                            start, sequence.indexOf(",", start)).toString()));
            start = sequence.indexOf("y") + 2;
            positions.get(moon).add(Integer.parseInt(sequence.subSequence(
                    start, sequence.indexOf(",", start)).toString()));
            start = sequence.indexOf("z") + 2;
            positions.get(moon).add(Integer.parseInt(sequence.subSequence(
                    start, sequence.indexOf(">", start)).toString()));
            moon +=1;
        }
    }

    public void initialVelocities(){
        for (int i=0; i<4; i++){
            velocities.add(new ArrayList<>(Arrays.asList(0,0,0)));
        }
    }

    public void calculateEnergies(){
        for (int i=0; i<positions.size(); i++){
            potentialEnergy.set(i, positions.get(i).stream().map(x -> Math.abs(x)).reduce(0, Integer::sum));
            kineticEnergy.set(i, velocities.get(i).stream().map(x -> Math.abs(x)).reduce(0, Integer::sum));
            totalEnergy.set(i, potentialEnergy.get(i) * kineticEnergy.get(i));
        }
        totalEnergySystem = totalEnergy.stream().reduce(0, Integer::sum);
    }

    @Override
    public String toString(){
        String string = "";
        for (int i=0; i<positions.size(); i++){
            string += "pot: ";
            for (int j=0; j<positions.get(i).size(); j++) {
                string += positions.get(i).get(j);
                if (j == positions.get(i).size() - 1) {
                    string += " = " + potentialEnergy.get(i) + ";    ";
                } else {
                    string += " + ";
                }
            }
            string += "kin: ";
            for (int j=0; j<velocities.get(i).size(); j++) {
                string += velocities.get(i).get(j);
                if (j == velocities.get(i).size() - 1) {
                    string += " = " + kineticEnergy.get(i) + ";    ";
                } else {
                    string += " + ";
                }
            }
            string += "total: " + potentialEnergy.get(i) + " * " + kineticEnergy.get(i) + " = " + totalEnergy.get(i) + "\n";
        }
        string += "Sum of total energy: ";
        for (int i=0; i<totalEnergy.size(); i++){
            string += totalEnergy.get(i);
            if (i == totalEnergy.size() - 1){
                string += " = " + totalEnergySystem;
            } else {
                string += " + ";
            }
        }
        return string;
    }

    public void updateGravity(){
        for (int i=0; i<positions.size(); i++){
            for (int j=i+1; j<positions.size(); j++){
                for (int k=0; k<3; k++) {
                    if (positions.get(i).get(k) < positions.get(j).get(k)) {
                        changeValue(velocities, i, k, 1);
                        changeValue(velocities, j, k, -1);
                    } else if (positions.get(i).get(k) > positions.get(j).get(k)){
                        changeValue(velocities, i, k, -1);
                        changeValue(velocities, j, k, 1);
                    }
                }
            }
        }
    }

    public void updatePosition(){
        for (int i=0; i<positions.size(); i++){
            for (int j=0; j<3; j++){
                changeValue(positions, i, j, velocities.get(i).get(j));
            }
        }
    }

    public void changeValue(List<List<Integer>> listPosition, int i, int j, int value){
        List<Integer> coordinates = listPosition.get(i);
        coordinates.set(j, coordinates.get(j) + value);
        listPosition.set(i, coordinates);
    }
}
