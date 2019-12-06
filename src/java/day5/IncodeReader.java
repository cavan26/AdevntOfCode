package day5;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IncodeReader {
    public static void main(String[] args) {
        IncodeReader incodeReader = new IncodeReader();
        ArrayList<Integer> list = incodeReader.readDataInList();
        incodeReader.SolvePuzzle(list, 5);
    }

    public ArrayList<Integer> readDataInList() {
        ArrayList<Integer> list = new ArrayList<>();
        InputStream inputStream = IncodeReader.class.getResourceAsStream("data.txt");

        Scanner scan = new Scanner(inputStream).useDelimiter(",");

        while (scan.hasNextInt()) {
            list.add(scan.nextInt());
        }
        return list;
    }

    public ArrayList<Integer> SolvePuzzle(List<Integer> original_list, int input){
        ArrayList<Integer> list = new ArrayList<>(original_list);
        int i = 0;
        while (i<list.size()) {
            List<Integer> instructions = decomposeInstruction(list.get(i));
            System.out.println(instructions);
            switch (instructions.get(0)) {
                case 1:
                    int param1 = (instructions.get(1) == 0)?list.get(list.get(i + 1)):list.get(i+1);
                    int param2 = (instructions.get(2) == 0)?list.get(list.get(i + 2)):list.get(i+2);
                    list.set(list.get(i+3), param1 +param2);
                    i = i + 4;
                    break;
                case 2:
                    int param3 = (instructions.get(1) == 0)?list.get(list.get(i + 1)):list.get(i+1);
                    int param4 = (instructions.get(2) == 0)?list.get(list.get(i + 2)):list.get(i+2);
                    list.set(list.get(i+3), param3 * param4);
                    i = i + 4;
                    break;
                case 3:
                    list.set(list.get(i+1), input);
                    i = i +2;
                    break;
                case 4:
                    System.out.println("Diagnostic code: " + list.get(list.get(i+1)));
                    i = i +2;
                    break;
                case 5:
                    int param5 = (instructions.get(1) == 0)?list.get(list.get(i + 1)):list.get(i+1);
                    int param6 = (instructions.get(2) == 0)?list.get(list.get(i + 2)):list.get(i+2);
                    if (param5 != 0){
                        i = param6;
                    } else {
                        i = i + 3;
                    }
                    break;
                case 6:
                    int param7 = (instructions.get(1) == 0)?list.get(list.get(i + 1)):list.get(i+1);
                    int param8 = (instructions.get(2) == 0)?list.get(list.get(i + 2)):list.get(i+2);
                    if (param7 == 0){
                        i = param8;
                    } else {
                        i = i + 3;
                    }
                    break;
                case 7:
                    int param9 = (instructions.get(1) == 0)?list.get(list.get(i + 1)):list.get(i+1);
                    int param10 = (instructions.get(2) == 0)?list.get(list.get(i + 2)):list.get(i+2);
                    list.set(list.get(i+3), (param9 < param10) ? 1 : 0);
                    i = i+4;
                    break;
                case 8:
                    int param11 = (instructions.get(1) == 0)?list.get(list.get(i + 1)):list.get(i+1);
                    int param12 = (instructions.get(2) == 0)?list.get(list.get(i + 2)):list.get(i+2);
                    list.set(list.get(i+3), param11 == param12 ? 1 : 0);
                    i = i+4;
                    break;
                case 99:
                    System.out.println("Found a 99");
                    return list;
                default:
                    System.out.println("Code is " + instructions.get(0));
                    throw new RuntimeException("Puzzle does not work");
            }
        }
        return list;
    }


    public List<Integer> decomposeInstruction(int code){
        ArrayList<Integer> instructions = new ArrayList<Integer>();
        instructions.add(code%100);
        instructions.add((code-instructions.get(0))%1000/100);
        instructions.add((code-instructions.get(0)-instructions.get(1)*100)%10000/1000);
        instructions.add((code-instructions.get(0)-instructions.get(1)*100 - instructions.get(2)*1000)/10000);

        return instructions;
    }
}
