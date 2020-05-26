import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;


class Activity{
    private int ID, startTime, endTime, value;

    Activity(int ID, int startTime, int endTime, int value){
        this.ID = ID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.value = value;
    }

    public int getID(){
        return this.ID;
    }

    public int getStartTime(){
        return this.startTime;
    }

    public int getEndTime(){
        return this.endTime;
    }

    public int getValue(){
        return this.value;
    }

    public void print(){
        System.out.println(this.ID + " " + this.startTime + " " + this.endTime + " " + this.value);
    }
}

class Homework7 {
    public static int maxEndTime;

    public static Activity[] readData(String filename){
        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);

            String data = reader.nextLine();
            String[] split = data.split("\\s+");
            Activity activities[] = new Activity[Integer.parseInt(split[0])];
            maxEndTime = Integer.parseInt(split[1]);
        
            int i = 0;
            int activityData[] = new int[4];
            while (reader.hasNextLine()) {
                data = reader.nextLine();
                split = data.split("\\s+");

                for (int j = 0; j < split.length; j++){
                    activityData[j] = Integer.parseInt(split[j]);
                }

                activities[i] = new Activity(activityData[0], activityData[1], activityData[2], activityData[3]);
                i++;                
            }
            
            reader.close();
            return activities;
        }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            return null;
        }
    }

    public static void writeData(String filename, String[] results){
        try {
            FileWriter writer = new FileWriter(filename);

            writer.write("Max value: " + results[0] + "\n");
            writer.write("Sequence IDs: " + results[1] + "\n");

            if (Integer.parseInt(results[2]) == 1){
                writer.write("IT HAS A UNIQUE SOLUTION");
            }else{
                writer.write("IT HAS MULTIPLE SOLUTIONS");
            }

            writer.close();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }


    public static String[] findMaxValue(Activity[] activities, int endTime){
        int n = activities.length;
        
        String results[] = new String[3];
        int maxValue = 0;
        String maxSequence = "";
        int solutions = 1;

        // Goes through each possible solution
        for (int i = 0; i < n; i++){
            int start = i;
            int value = 0;
            String sequence = "";

            value += activities[start].getValue();
            sequence += activities[start].getID() + " ";
            
            for (int j = i + 1; j < n; j++){
                if (activities[j].getStartTime() >= activities[start].getEndTime() && 
                    activities[j].getEndTime() <= endTime) { 
                    sequence += activities[j].getID() + " "; 
                    start = j; 
                    value += activities[j].getValue();
                } 
            }

            if(value > maxValue){
                maxValue = value;
                maxSequence = sequence;
                solutions = 1;
            }else if (value == maxValue){
                solutions ++;
            }

        }    

        results[0] = String.valueOf(maxValue);
        results[1] = maxSequence;
        results[2] = String.valueOf(solutions);

        return results;
    }


    public static void main(String[] args) {
        // Load data in
        Activity activities[] = readData(args[0]);

        if (activities == null){
            System.out.println("Something went wrong, please double check your input data");
            return;
        }

        for (int i = 0; i < activities.length; i++){
            activities[i].print();
        }

        // Sort activies by end time
        Arrays.sort(activities, new Comparator<Activity>(){
            @Override
            public int compare(Activity first, Activity second){
                return first.getEndTime() - second.getEndTime();
            }
        });

        // Find max value
        String results[] = findMaxValue(activities, maxEndTime);
        
        // Write output
        writeData(args[1], results);
    }
}