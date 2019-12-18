package day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IncodeReader {
    private ArrayList<Integer> list;
    private int index;
    private List<Integer> input;
    private String state;
    private List<Integer> output;

    public IncodeReader(ArrayList<Integer> intcode, int startInput) {
        this.list = intcode;
        this.index = 0;
        this.input = new ArrayList<>(Arrays.asList(startInput));
        this.state = "Waiting for input";
        this.output = new ArrayList<>();
    }

    public void addInput(List<Integer> input){
        this.input.addAll(input);
        this.state = "Running";
        this.SolvePuzzle();
    }

    public List<Integer> getOutput(){
        return this.output;
    }

    public void deleteOutput(){
        this.output = new ArrayList<>();
    }

    public String getState(){
        return this.state;
    }

    public String SolvePuzzle(){
        int param1 = 0;
        int param2 = 0;
        while (index<list.size()) {
            List<Integer> instructions = decomposeInstruction(list.get(index));
            if (instructions.get(0) != 3 && instructions.get(0) != 4 && instructions.get(0) != 99){
                param1 = (instructions.get(1) == 0) ? list.get(list.get(index + 1)) : list.get(index + 1);
                param2 = (instructions.get(2) == 0) ? list.get(list.get(index + 2)) : list.get(index + 2);
            }
            switch (instructions.get(0)) {
                case 1:
                    list.set(list.get(index+3), param1 + param2);
                    index = index + 4;
                    break;
                case 2:
                    list.set(list.get(index+3), param1 * param2);
                    index = index + 4;
                    break;
                case 3:
                    if (input.isEmpty()){
                        this.state = "Waiting for input";
                        return this.state;
                    } else {
                        list.set(list.get(index+1), input.get(0));
                        input.remove(0);
                        index = index +2;
                    }
                    break;
                case 4:
                    int code = list.get(list.get(index+1));
                    index += 2;
                    this.output.add(code);
                    break;
                case 5:
                    if (param1 != 0){
                        index = param2;
                    } else {
                        index = index + 3;
                    }
                    break;
                case 6:
                    if (param1 == 0){
                        index = param2;
                    } else {
                        index = index + 3;
                    }
                    break;
                case 7:
                    list.set(list.get(index+3), (param1 < param2) ? 1 : 0);
                    index = index+4;
                    break;
                case 8:
                    list.set(list.get(index+3), param1 == param2 ? 1 : 0);
                    index = index+4;
                    break;
                case 99:
                    this.state = "END";
                    return this.state;
                default:
                    System.out.println("Code is " + instructions.get(0));
                    System.out.println(list);
                    System.out.println(index);
                    throw new RuntimeException("Puzzle does not work");
            }
        }
        return "Error: got to the end of the list without getting a 99";
    }


    public List<Integer> decomposeInstruction(int code){
        ArrayList<Integer> instructions = new ArrayList<Integer>();
        instructions.add(code%100);
        instructions.add((code-instructions.get(0))%1000/100);
        instructions.add((code-instructions.get(0)-instructions.get(1)*100)%10000/1000);
        instructions.add((code-instructions.get(0)-instructions.get(1)*100 - instructions.get(2)*1000)/10000);

        return instructions;
    }

    @Override
    public String toString() {

        return "Inputs: " + this.input + " - Ouputs: " + this.output + " \n State: " + this.state;
    }
}
