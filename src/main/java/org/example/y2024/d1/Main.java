package org.example.y2024.d1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    public static boolean useTask2Technique = true;
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("elfLists.txt"));
        var list1 = new ArrayList<Integer>();
        var list2 = new ArrayList<Integer>();
        lines.stream().map(s -> s.split(" {3}")).forEach(x ->{
            list1.add(Integer.parseInt(x[0]));
            list2.add(Integer.parseInt(x[1]));
        });
        if(!useTask2Technique){
            list1.sort(Integer::compareTo);
            list2.sort(Integer::compareTo);
        }
        var diff = 0;
        for (int i = 0; i < list1.size(); i++) {
            if(useTask2Technique) {
                var item = list1.get(i);
                int occurrences = (int) list2.stream().filter(item::equals).count();
                diff += item*occurrences;
            }
            else diff += Math.abs(list1.get(i)-list2.get(i));
        }
        System.out.println(diff);
    }
}