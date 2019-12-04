package day1;

import day2.ProgramAlarm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FuelCalculator {
    public static void main(String[] args) {
        BufferedReader modules = new BufferedReader(new InputStreamReader(FuelCalculator.class.getResourceAsStream("data.txt")));

        FuelCalculator fuelCalculator = new FuelCalculator();

        int result = modules.lines()
                .mapToInt(Integer::parseInt)
                .map(x-> x/3 -2)
                .map(x-> fuelCalculator.additionalFuel(x))
                .sum();

        System.out.println(result);

    }

    public static int additionalFuel(int fuel){
        int sum = fuel;
        while(fuel/3-2 >0){
            fuel = fuel/3-2;
            sum += fuel;
        }
        return sum;
    }
}
