package day9;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IncodeDecoderBigInteger {
    private ListIntcode list;
    private BigInteger index;
    private List<BigInteger> input;
    private String state;
    private List<BigInteger> output;
    private BigInteger offset;

    public IncodeDecoderBigInteger(ArrayList<BigInteger> intcode, BigInteger startInput) {
        this.list = new ListIntcode(intcode);
        this.index = BigInteger.ZERO;
        this.offset = BigInteger.ZERO;
        this.input = new ArrayList<>(Arrays.asList(startInput));
        this.state = "Waiting for input";
        this.output = new ArrayList<>();
    }


    public void addInput(List<BigInteger> input) {
        this.input.addAll(input);
        this.state = "Running";
        this.SolvePuzzle();
    }

    public List<BigInteger> getOutput() {
        return this.output;
    }

    public void deleteOutput() {
        this.output = new ArrayList<>();
    }

    public String getState() {
        return this.state;
    }

    public String SolvePuzzle() {
        BigInteger param1 = BigInteger.ZERO;
        BigInteger param2 = BigInteger.ZERO;
        BigInteger param3 = BigInteger.ZERO;
        while (index.compareTo(list.size()) == -1 ){
            List<Integer> instructions = decomposeInstruction(list.get(index).intValue());
            System.out.println();
            System.out.println(instructions);
            if (instructions.get(0) != 99){
                param1 = getParam(instructions.get(1), BigInteger.ONE);
                System.out.println("Param 1: " + param1 + ", in mode: " + getMode(instructions.get(1)) +
                        ", for index " + index.add(BigInteger.ONE));
            }
            if (instructions.get(0) != 3 && instructions.get(0) != 4 && instructions.get(0) != 99 && instructions.get(0) != 9) {
                param2 = getParam(instructions.get(2), BigInteger.TWO);
                System.out.println("Param 2: " + param2 + ", in mode: " + getMode(instructions.get(2))
                        + ", for index " + index.add(BigInteger.TWO));
            }
            BigInteger offset1 = (instructions.get(1) == 2) ? offset : BigInteger.ZERO;
            BigInteger offset3 = (instructions.get(3) == 2) ? offset : BigInteger.ZERO;



            switch (instructions.get(0)) {
                case 1:
                    list.set(list.get(index.add(BigInteger.valueOf(3))).add(offset3), param1.add(param2));
                    index = index.add(BigInteger.valueOf(4));
                    break;
                case 2:
                    list.set(list.get(index.add(BigInteger.valueOf(3))).add(offset3), param1.multiply(param2));
                    index = index.add(BigInteger.valueOf(4));
                    break;
                case 3:
                    if (input.isEmpty()) {
                        this.state = "Waiting for input";
                        return this.state;
                    } else {
                        System.out.println("The input " + input.get(0) + " is stored in position " +
                                list.get(list.get(index.add(BigInteger.ONE))));
                        list.set(list.get(index.add(BigInteger.ONE)).add(offset1), input.get(0));
                        input.remove(0);
                        index = index.add(BigInteger.valueOf(2));
                    }
                    break;
                case 4:
                    BigInteger code = list.get(list.get(index.add(BigInteger.ONE)).add(offset1));
                    System.out.println("Add value to output: " + code);
                    index = index.add(BigInteger.valueOf(2));
                    this.output.add(code);
                    break;
                case 5:
                    if (param1.compareTo(BigInteger.ZERO) != 0) {
                        index = param2;
                    } else {
                        index = index.add(BigInteger.valueOf(3));
                    }
                    break;
                case 6:
                    if (param1.compareTo(BigInteger.ZERO) == 0) {
                        index = param2;
                    } else {
                        index = index.add(BigInteger.valueOf(3));
                    }
                    break;
                case 7:
                    list.set(list.get(index.add(BigInteger.valueOf(3))).add(offset3), (param1.compareTo(param2) == -1) ?
                            BigInteger.ONE : BigInteger.ZERO);
                    index = index.add(BigInteger.valueOf(4));
                    break;
                case 8:
                    list.set(list.get(index.add(BigInteger.valueOf(3))).add(offset3), (param1.compareTo(param2) == 0) ?
                            BigInteger.ONE : BigInteger.ZERO);
                    index = index.add(BigInteger.valueOf(4));
                    break;
                case 9:
                    this.offset = this.offset.add(param1);
                    System.out.println("Add value to the offset: " + param1 +  ". Now offset is equal to " + offset);
                    index = index.add(BigInteger.valueOf(2));
                    break;
                case 99:
                    this.state = "END";
                    return this.state;
                default:
                    throw new RuntimeException("Puzzle does not work");
            }
        }
        return "Error: got to the end of the list without getting a 99";
    }

    public List<Integer> decomposeInstruction(int code) {
        ArrayList<Integer> instructions = new ArrayList<>();
        instructions.add(code%100);
        instructions.add((code-instructions.get(0))%1000/100);
        instructions.add((code-instructions.get(0)-instructions.get(1)*100)%10000/1000);
        instructions.add((code-instructions.get(0)-instructions.get(1)*100 - instructions.get(2)*1000)/10000);
        return instructions;
    }

    public BigInteger getParam(int instruction, BigInteger position){
        if (instruction == 0){
            return list.get(list.get(index.add(position)));
        } else if (instruction == 1){
            return list.get(index.add(position));
        } else if (instruction == 2){
            return list.get(list.get(index.add(position)).add(this.offset));
        } else {
            throw new RuntimeException("The mode is not 0,1 or 2 ");
        }
    }

    public String getMode(int mode){
        if (mode == 0){
            return "Position";
        } else if (mode == 1){
            return "Immediate";
        } else {
            return "Relative";
        }
    }

    @Override
    public String toString() {
        return "Inputs: " + this.input + " - Ouputs: " + this.output + " \n State: " + this.state;
    }

    private class ListIntcode {
        private HashMap<BigInteger, BigInteger> intcode;

        public ListIntcode(List<BigInteger> intcode) {
            this.intcode = new HashMap<>();
            for (int i = 0; i < intcode.size(); i++) {
                this.intcode.put(BigInteger.valueOf(i), intcode.get(i));
            }
        }

        public void set(BigInteger position, BigInteger value) {
            this.intcode.put(position, value);
        }

        public BigInteger get(BigInteger position) {
            if (position.compareTo(BigInteger.ZERO) == -1){
                throw new RuntimeException("Try to access negative memory");
            }
            if (this.intcode.containsKey(position)) {
                return this.intcode.get(position);
            }
            this.set(position, BigInteger.valueOf(0));
            return BigInteger.valueOf(0);
        }

        public BigInteger size() {
            BigInteger max = BigInteger.valueOf(0);
            for (BigInteger intcode : this.intcode.keySet()) {
                if (max.compareTo(intcode) == -1) {
                    max = intcode;
                }
            }
            return max;
        }
    }
}
