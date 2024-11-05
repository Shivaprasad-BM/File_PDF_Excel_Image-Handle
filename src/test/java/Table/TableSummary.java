package Table;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TableSummary {

    public static void main(String[] args) {
        // Replace with the path to your text file
        String filePath = "C:\\Users\\user\\Desktop\\tableOUTnew.txt";

        // Call method to read the file and process the table data
        processTableData(filePath);
    }

    public static void processTableData(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            int tableCount = 0;
            int rowCount = 0;
            int columnCount = 0;
            boolean insideTable = false;

            // Read file line by line
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Check if a new table starts
                if (line.startsWith("Table")) {
                    if (insideTable) {
                        // Output the previous table's summary
                        System.out.println("Table " + tableCount + " > Rows: " + rowCount + " > Columns: " + columnCount);
                    }
                    // Reset counts for new table
                    insideTable = true;
                    tableCount++;
                    rowCount = 0;
                    columnCount = 0;
                } else if (line.startsWith("Row")) {
                    rowCount++;
                    int currentRowColumnCount = countColumnsInRow(line, reader);
                    columnCount = Math.max(columnCount, currentRowColumnCount); // Track the maximum column count
                }
            }

            // Output the final table summary after the loop ends
            if (insideTable) {
                System.out.println("Table " + tableCount + " > Rows: " + rowCount + " > Columns: " + columnCount);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to count columns in a row
    public static int countColumnsInRow(String rowLine, BufferedReader reader) throws IOException {
        int columns = 0;
        String line;

        // Count how many columns are present in this row
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.startsWith("Column")) {
                columns++;
            } else if (line.startsWith("Row") || line.startsWith("Table")) {
                // Stop counting when we reach the next row or table
                break;
            }
        }

        return columns;
    }
}
