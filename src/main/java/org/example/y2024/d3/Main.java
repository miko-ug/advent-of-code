package org.example.y2024.d3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        var reg = Pattern.compile("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)");
        var controlReg = Pattern.compile("do(n't)?\\(\\)");
        var memory = Files.readString(Path.of("memory.txt"));

        var memoryBuilder = new StringBuilder();
        var seekIndex = 0;
        var state = true;
        for (MatchResult x : controlReg.matcher(memory).results().toList()){
            var newState = x.group().equals("do()");

            if(state == newState) continue;
            if(!newState) {
                memoryBuilder.append(memory, seekIndex, x.start());
            }
            state = newState;
            seekIndex = x.end();
        }
        if(state) memoryBuilder.append(memory, seekIndex,memory.length());

        var filteredMomory = memoryBuilder.toString();
        var matcher = reg.matcher(filteredMomory);
        var result = 0;
        for (MatchResult x : matcher.results().toList()){
            var arg1 = Integer.parseInt(x.group(1));
            var arg2 = Integer.parseInt(x.group(2));
            result += (arg1*arg2);
        }
        System.out.println(result);
    }
}
