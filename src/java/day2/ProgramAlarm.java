package day2;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProgramAlarm {
    public static void main(String[] args) {
        System.out.println("Program Alarm is running");
        ProgramAlarm programAlarm = new ProgramAlarm();
        ArrayList<Integer> list = programAlarm.readDataInList();
        System.out.println(programAlarm.ReversePuzzle(list, 19690720));
    }

    public ArrayList<Integer> readDataInList() {
        ArrayList<Integer> list = new ArrayList<>();
        InputStream inputStream = ProgramAlarm.class.getResourceAsStream("data.txt");

        Scanner scan = new Scanner(inputStream).useDelimiter(",");

        while (scan.hasNextInt()) {
            list.add(scan.nextInt());
        }
        return list;
    }

    public ArrayList<Integer> SolvePuzzle(List<Integer> original_list){
        ArrayList<Integer> list = new ArrayList<>(original_list);
        for (int i=0; i<=list.size()/4; i++) {
            switch (list.get(i*4)) {
                case 1:
                    list.set(list.get(i*4 + 3), list.get(list.get(i*4 + 1)) + list.get(list.get(i*4 + 2)));
                    break;
                case 2:
                    list.set(list.get(i*4 + 3), list.get(list.get(i*4 + 1)) * list.get(list.get(i*4 + 2)));
                    break;
                case 99:
                    return list;
                default:
                    throw new RuntimeException("Puzzle does not work");
            }
        }
        return list;
    }

    public int ReversePuzzle(List<Integer> list, int output) {
        ProgramAlarm programAlarm = new ProgramAlarm();
        ArrayList<Integer> new_list = new ArrayList<>();
        for(int i=0; i<list.size(); i++){
            for (int j=0; j<list.size(); j++){
                list.set(1, i);
                list.set(2, j);
                try {
                    new_list = programAlarm.SolvePuzzle(list);
                    if (new_list.get(0) == output){
                        return new_list.get(1)*100 + new_list.get(2);
                    }
                } catch(RuntimeException e) {
                    System.out.println("Do nothing");
                }
            }
        }
        return -1;
    }
}
