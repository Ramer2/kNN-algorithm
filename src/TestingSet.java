import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TestingSet {
    // file where the testing set was saved
    String fileName;

    ArrayList<Vector> testingVectors;
    ArrayList<String> testingSetClassNames;
    ArrayList<String> classes;
    int NUMBER_OF_CLASSES;

    public TestingSet(String fileName) {
        this.fileName = fileName;
        testingVectors = new ArrayList<>();
        testingSetClassNames = new ArrayList<>();
        classes = new ArrayList<>();
        NUMBER_OF_CLASSES = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // vector components
                double[] components = new double[parts.length - 1];
                for (int i = 0; i < components.length; i++) {
                    components[i] = Double.parseDouble(parts[i]);
                }

                // class
                String className = parts[parts.length - 1];
                if (!classes.contains(className)) {
                    classes.add(className);
                    NUMBER_OF_CLASSES++;
                }

                testingVectors.add(new Vector(components));
                testingSetClassNames.add(className);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading file.");
            throw new RuntimeException(e);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public ArrayList<Vector> getTestingVectors() {
        return testingVectors;
    }

    public ArrayList<String> getClasses() {
        return classes;
    }

    public int getNUMBER_OF_CLASSES() {
        return NUMBER_OF_CLASSES;
    }
}
