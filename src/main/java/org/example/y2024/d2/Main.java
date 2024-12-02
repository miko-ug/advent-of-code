package org.example.y2024.d2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("redRudolfReport.txt"));
        var safeReports = lines.stream().map(s -> s.split(" ")).filter(Main::isSafe).count();
        System.out.printf("There are %d safe reports\n",safeReports);
    }
    public static boolean isRaising(List<Integer> report){
        var raising = 0;
        for (int i = 0; i < report.size()-1; i++) {
            var localDiff = report.get(i)-report.get(i+1);
            if(localDiff<=0) raising++;
        }
        return raising>(report.size()/2);
    }
    public static boolean isSafe(List<Integer> report,boolean allowBadLevelRetry){
        var isGrowing = isRaising(report);

        for (int i = 0; i < report.size()-1; i++) {
            var localDiff = report.get(i)-report.get(i+1);

            if(isGrowing && !(inBounds(localDiff,-3,-1)))
                return allowBadLevelRetry && retrySafetyCheck(report, i+1);

            if(!isGrowing && !(inBounds(localDiff,1,3)))
                return allowBadLevelRetry && retrySafetyCheck(report, i+1);
        }
        return true;
    }
    public static boolean retrySafetyCheck(List<Integer> report,int failedIndex){
        var intReport = new ArrayList<>(report);
        intReport.remove(failedIndex);
        var isFallbackSafe = isSafe(intReport,false);
        if(!isFallbackSafe){
                //if we failed on the second level, there's a chance to fix it by removing first element instead
                var trimmedReport = new ArrayList<>(report);
                trimmedReport.remove(failedIndex-1);
                var isTrinnedSafe = isSafe(trimmedReport,false);
//                if(!isTrinnedSafe){
//                    System.out.print(report);
//                    System.out.print(" -> ");
//                    System.out.print(intReport);
//                    System.out.print(" (at "+failedIndex+")");
//                    System.out.print(" -> ");
//                    System.out.print(trimmedReport);
//                    System.out.println();
//                }
                return isTrinnedSafe;
        }
        return isFallbackSafe;
    }


    public static boolean isSafe(String[] report){
        var intReport = Arrays.stream(report).map(Integer::parseInt).toList();
        return isSafe(intReport,true);
    }

    public static boolean inBounds(int value,int min,int max){
        return (value>=min) && (value <=max);
    }
}
