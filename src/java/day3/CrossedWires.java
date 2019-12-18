package day3;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class CrossedWires {

    public static void main(String[] args) {
        CrossedWires crossedWires = new CrossedWires();
        List<String> lines = crossedWires.getData();

        List<List<Position>> intersection = crossedWires.getIntersections(crossedWires.getListPosition(lines.get(0)),
                crossedWires.getListPosition(lines.get(1)));
        System.out.println("Smallest position " + crossedWires.smallestDistance(intersection));
        System.out.println("Smaller number of steps " + crossedWires.smallestNumberSteps(intersection));
        System.out.println("Done!");
    }

    public List<String> getData(){
        ArrayList<String> lines = new ArrayList<>();

        try{
            InputStream inputStream = CrossedWires.class.getResourceAsStream("data.txt");
            Scanner scan = new Scanner(inputStream).useDelimiter("\n");
            while (scan.hasNext()) {
                lines.add(scan.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    public List<Position> getListPosition(String line){
        ArrayList<Position> positions = new ArrayList<>();
        Position currentPosition = new Position();
        for (String order: line.split(",")){
            char direction = order.charAt(0);
            int number = Integer.parseInt(order.substring(1));
            for (int i=0; i<number; i++){
                currentPosition = currentPosition.move(direction);
                positions.add(currentPosition);
            }
        }
        return positions;
    }

    public List<List<Position>> getIntersections(List<Position> positions1, List<Position> positions2) {
        List<List<Position>> inter = new ArrayList<>();
        for (Position pos1: positions1) {
            for (Position pos2: positions2) {
                if (pos1.equal(pos2)) {
                    inter.add(new ArrayList<>(Arrays.asList(pos1, pos2)));
                }
            }
        }
        return inter;
    }

    public int smallestDistance(List<List<Position>> positions){
        int min = Integer.MAX_VALUE;
        for (List<Position> pos: positions){
            System.out.println(pos.get(0));
            if (Math.abs(pos.get(0).x) + Math.abs(pos.get(0).y) < min){
                min = Math.abs(pos.get(0).x) + Math.abs(pos.get(0).y);
            }
        }
        return min;
    }

    public int smallestNumberSteps(List<List<Position>> positions){
        int min = Integer.MAX_VALUE;
        for (List<Position> pos: positions){
            System.out.println(pos.get(0) + " / " + pos.get(1));
            if (pos.get(0).steps + pos.get(1).steps < min){
                min = pos.get(0).steps + pos.get(1).steps;
            }
        }
        return min;
    }

    private class Position {
        private int x;
        private int y;
        private int steps;

        public Position() {
            this.x = 0;
            this.y = 0;
            this.steps = 0;
        }

        public Position(int x, int y, int steps){
            this.x = x;
            this.y = y;
            this.steps = steps;
        }

        public Position move(char direction){
            switch (direction){
                case 'L':
                    this.x -= 1;
                    this.steps +=1;
                    break;
                case 'R':
                    this.x += 1;
                    this.steps +=1;
                    break;
                case 'U':
                    this.y += 1;
                    this.steps +=1;
                    break;
                case 'D':
                    this.y -= 1;
                    this.steps +=1;
                    break;
            }
            return new Position(this.x, this.y, this.steps);
        }

        public String toString(){
            return "Position: " + this.x + ", " + this.y;
        }

        public boolean equal(Position position){
            return (this.x == position.x && this.y == position.y);
        }
    }

}
