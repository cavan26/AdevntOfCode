package day6;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class OrbitMap {


    public static void main(String[] args) {
        OrbitMap orbitMap = new OrbitMap();
        System.out.println(orbitMap.getNumberOrbits(orbitMap.getOrbit()));
        System.out.println(orbitMap.getNumberOrbitJumps(orbitMap.getOrbit(), "SAN", "YOU"));
    }

    public List<String> getOrbit() {
        ArrayList<String> orbit = new ArrayList<>();

        try{
            InputStream inputStream = OrbitMap.class.getResourceAsStream("data.txt");
            Scanner scan = new Scanner(inputStream).useDelimiter("\n");
            while (scan.hasNext()) {
                orbit.add(scan.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orbit;
    }

    public int getNumberOrbits(List<String> orbits){
        Graph graph = new Graph(orbits);
        return graph.getTotalNumberOrbits();
    }

    public int getNumberOrbitJumps(List<String> orbits, String object1, String object2){
        Graph graph = new Graph(orbits);
        return graph.findNumberOrbitalTransfers(object1, object2) + 2;
    }

    private class Graph{
        private HashMap<String, List<String>> parents = new HashMap<>();
        private HashMap<String, List<String>> children = new HashMap<>();


        public Graph(List<String> orbits){
            for (String orbit: orbits){
                String parent = orbit.split("\\)")[0];
                String child = orbit.split("\\)")[1];
                if (parents.containsKey(child)){
                    List<String> item = parents.get(child);
                    item.add(parent);
                    parents.put(child, item);
                } else {
                    parents.put(child, new ArrayList<>(Arrays.asList(parent)));
                }
                if (children.containsKey(parent)){
                    List<String> item = children.get(parent);
                    item.add(child);
                    children.put(parent, item);
                } else {
                    children.put(parent, new ArrayList<>(Arrays.asList(child)));

                }
            }
        }

        public int getTotalNumberOrbits(){
            Set<String> nodes = new HashSet<>();
            for (String orbit: this.parents.keySet()){
                nodes.add(orbit);
            }
            for (String orbit: this.children.keySet()){
                nodes.add(orbit);
            }
            int totalOrbits = 0;
            for (String node: nodes){
                totalOrbits += getNumberOfParents(node);
            }
            return totalOrbits;
        }

        public int getNumberOfParents(String orbit){
            return getSubsumers(orbit, 0);
        }

        private int getSubsumers(String orbit, int sum){
            if (parents.containsKey(orbit)){
                for (String parent: parents.get(orbit)){
                    sum += getSubsumers(parent, sum) + 1;
                }
                return sum;
            } else {
                return 0;
            }
        }

        private List<String> getListParent(String object, List<String> list){
            if (parents.containsKey(object)) {
                for (String parent : parents.get(object)) {
                    list.add(parent);
                    getListParent(parent, list);
                }
            }
            return list;
        }

        public int findNumberOrbitalTransfers(String object1, String object2){
            object1 = parents.get(object1).get(0);
            object2 = parents.get(object2).get(0);

            List<String> parents1 = getListParent(object1, new ArrayList<>());
            List<String> parents2 = getListParent(object2, new ArrayList<>());

            ArrayList<String> orbitToJump = new ArrayList<>();
            for (String node: parents1){
                if (!parents2.contains(node)){
                    orbitToJump.add(node);
                } else {
                    break;
                }
            }
            for (String node: parents2){
                if (!parents1.contains(node)){
                    orbitToJump.add(node);
                } else {
                    break;
                }
            }
            System.out.println(orbitToJump);
            return orbitToJump.size();
        }
    }
}
