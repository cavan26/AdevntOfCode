package day7;

import day5.IncodeReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AmplificationCircuit {
    private List<IncodeReader> incodeReaders;
    private ArrayList<Integer> list;

    public AmplificationCircuit (int numberCircuits, List<Integer> startIntcode){
        this.incodeReaders = new ArrayList<>();
        this.list = readDataInList();
        for (int i=0; i<numberCircuits; i++){
            incodeReaders.add(new IncodeReader(new ArrayList<>(list), startIntcode.get(i)));
        }
    }

    public ArrayList<Integer> readDataInList() {
        ArrayList<Integer> list = new ArrayList<>();
        InputStream inputStream = AmplificationCircuit.class.getResourceAsStream("data.txt");

        Scanner scan = new Scanner(inputStream).useDelimiter(",");

        while (scan.hasNextInt()) {
            list.add(scan.nextInt());
        }
        return list;
    }

    public static void main(String[] args) {
        int max = 0;
        for (List<Integer> sequence: permute(new int[]{5,6,7,8,9}, 0, new ArrayList<>())){
            AmplificationCircuit amplificationCircuit = new AmplificationCircuit(5, sequence);
            int result =  amplificationCircuit.getOutputFeedbackLoop();
            max = Math.max(result, max);
            System.out.println("Sequence " + sequence + ": " + result);
        }
        System.out.println("The max is " + max);
    }

    public int getOutputFeedbackLoop(){
        incodeReaders.get(0).addInput(new ArrayList<>(Arrays.asList(0)));
        int i = 0;
        while(!endLoop()){
            List<Integer> output = incodeReaders.get(i).getOutput();
            incodeReaders.get(i).deleteOutput();
            if (i==4){
                i=0;
            } else {
                i+=1;
            }
            incodeReaders.get(i).addInput(output);
        }
        List<Integer> outputLastMachine = incodeReaders.get(4).getOutput();
        return outputLastMachine.get(outputLastMachine.size()-1);
    }

    public boolean endLoop(){
        boolean isEnded = true;
        for (IncodeReader incodeReader: incodeReaders){

            isEnded = incodeReader.getState() == "END" ? isEnded : false;
        }
        return isEnded;
    }

    static List<List<Integer>> permute(int[] a, int k, List<List<Integer>> sequences)
    {
        if (k == a.length) {
            ArrayList<Integer> sequence = new ArrayList<>();
            for (int i = 0; i < a.length; i++)
            {
                sequence.add(a[i]);
            }
            sequences.add(sequence);
            return sequences;
        }
        else {
            for (int i = k; i < a.length; i++) {
                int temp = a[k];
                a[k] = a[i];
                a[i] = temp;
                sequences = permute(a, k + 1, sequences);
                temp = a[k];
                a[k] = a[i];
                a[i] = temp;
            }
        }
        return sequences;
    }

    @Override
    public String toString() {
        String string = "";
        int i = 0;
        for (IncodeReader incodeReader: incodeReaders){
            string += "Machine " + i + ": " + incodeReader.toString() + '\n';
            i =+1;
        }
        return string;
    }
}
