package day9;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class SensorBoost {
    private IncodeDecoderBigInteger incodeReader;

    public SensorBoost(String file){
        this.incodeReader = new IncodeDecoderBigInteger(this.readDataInList(file), BigInteger.valueOf(2));
    }

    public static void main(String[] args) {
        SensorBoost sensorBoost = new SensorBoost("data.txt");
        System.out.println(sensorBoost.incodeReader.toString());
        sensorBoost.incodeReader.SolvePuzzle();
        System.out.println(sensorBoost.incodeReader.toString());
    }

    public ArrayList<BigInteger> readDataInList(String file) {
        ArrayList<BigInteger> list = new ArrayList<>();
        InputStream inputStream = SensorBoost.class.getResourceAsStream(file);

        Scanner scan = new Scanner(inputStream).useDelimiter(",");

        while (scan.hasNextBigInteger()) {
            list.add(scan.nextBigInteger());
        }
        return list;
    }
}
