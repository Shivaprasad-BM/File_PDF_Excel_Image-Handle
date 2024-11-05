package textFile;
import java.io.*;
import java.util.*;

public class HeadingValidatorFromFile1 {
    public static void main(String[] args) {
        // Path to the input and output files
        String inputFilePath = "C:\\Users\\user\\Desktop\\heading level test.txt";
        String outputFilePath = "C:\\Users\\user\\Desktop\\heading level testout.txt";

        List<String> processedLines = new ArrayList<>();
        Stack<Integer> levelStack = new Stack<>();
        boolean firstHeading = true; // Flag to handle the first heading

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;

            // Read all lines from the input file
            while ((line = reader.readLine()) != null) {
                int currentLevel = extractHeadingLevel(line);
                
                if (currentLevel > 0) {
                    if (firstHeading) {
                        processedLines.add(line);
                        levelStack.push(currentLevel);
                        firstHeading = false;
                    } else {
                        // Validate heading order
                        while (!levelStack.isEmpty() && levelStack.peek() >= currentLevel) {
                            levelStack.pop();
                        }
                        if (levelStack.isEmpty() || levelStack.peek() != currentLevel - 1) {
                            processedLines.add(line + " [ERROR: Invalid heading order]");
                        } else {
                            processedLines.add(line);
                        }
                        levelStack.push(currentLevel);
                    }
                } else {
                    // If not a heading line, keep it unchanged
                    processedLines.add(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write back the processed lines to the output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (String line : processedLines) {
                writer.write(line);
                writer.newLine();  // Add a new line after each line
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("File processed successfully. Check the output file for results.");
    }

    // Method to extract the heading level from the line
    public static int extractHeadingLevel(String line) {
        if (line.contains("heading") && line.contains("level")) {
            try {
                // Extract the number after "heading level" (e.g., "heading level 2" -> 2)
                String levelString = line.substring(line.indexOf("heading level") + 14, line.indexOf("heading level") + 15);
                return Integer.parseInt(levelString);
            } catch (Exception e) {
                // Handle any parsing errors
                return 0;
            }
        }
        return 0; // Return 0 if it's not a heading line
    }
}
