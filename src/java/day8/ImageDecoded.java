package day8;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ImageDecoded {

    public static void main(String[] args) {
        ImageDecoded imageDecoded = new ImageDecoded();
        System.out.println(imageDecoded.layerWithFewestDigits(imageDecoded.getLayers(imageDecoded.readDataInList(), 25, 6), 0));
    }

    public ArrayList<Integer> readDataInList() {
        ArrayList<Integer> list = new ArrayList<>();
        InputStream inputStream = ImageDecoded.class.getResourceAsStream("data.txt");

        Scanner scan = new Scanner(inputStream).useDelimiter("\n");
        String sequence = scan.next();
        char[] chars = sequence.toCharArray();
        for (char c: chars){
            list.add(Integer.parseInt(String.valueOf(c)));
        }
        return list;
    }

    public List<List<Integer>> getLayers(List<Integer> image, int wide, int tall){
        int sizeLayer = wide * tall;
        List<List<Integer>> layers = new ArrayList<>();
        for (int i=0; i+sizeLayer<=image.size(); i+=sizeLayer){
            layers.add(image.subList(i, i+sizeLayer));
        }
        return layers;
    }

    public int layerWithFewestDigits(List<List<Integer>> layers, int digit){
        List<Long> numberOfZeros =  layers.stream().map(l -> countNumberDigit(l, 0)).collect(Collectors.toList());
        List<Integer> layer = layers.get(numberOfZeros.indexOf(Collections.min(numberOfZeros)));

        return (int) (countNumberDigit(layer, 1)* countNumberDigit(layer, 2));
    }

    public long countNumberDigit(List<Integer> list, int digit){
        return list
                .stream()
                .filter(c -> c == digit)
                .count();
    }

    public List<Integer> decodedImage(List<List<Integer>> layers, int sizeImage){
        return layers.get(0);
    }

    public int getPixelColor(List<Integer> pixels){
        for (int pixel:pixels){
            if (pixel == 0){
                return 0;
            } else if (pixel == 1){
                return 1;
            }
        }
        return 2;
    }


}
