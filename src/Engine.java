import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class Engine {
    // generally works
    private static ArrayList<Pair> calculateDistance(ArrayList<Vector> trainingVectors, Vector testVector) {
        ArrayList<Pair> distances = new ArrayList<>();

        for (Vector vector : trainingVectors) {
            double distance = 0;

            for (int i = 0; i < testVector.components.length; i++)
                distance += Math.pow(vector.components[i] - testVector.components[i], 2);

            distances.add(new Pair(vector, distance));
        }

        return distances;
    }

    private static String getTopClassName(TrainingSet trainingSet, ArrayList<Vector> kClosest) {
        // initialize the map
        HashMap<String, Integer> classesCount = new HashMap<>();
        for (String className : trainingSet.getClasses()) classesCount.put(className, 0);

        // count the number of occurrences for each class
        for (Vector closestVector : kClosest) {
            int count = classesCount.get(closestVector.className);
            classesCount.put(closestVector.className, ++count);
        }

        // find the name of the class with maximum occurrences
        String className = "";
        int count = 0;
        for (String key : classesCount.keySet()) {
            if (count < classesCount.get(key)) {
                className = key;
                count = classesCount.get(key);
            }
        }
        return className;
    }

    private static void evalSingleVector(int k, TrainingSet trainingSet, Vector testVector) {
        ArrayList<Vector> trainingVectors = trainingSet.getTrainingVectors();

        // getting the distances
        ArrayList<Pair> distances = calculateDistance(trainingVectors, testVector);

        // choosing k of the closest
        distances.sort(Comparator.comparingDouble(pair -> pair.distance));
        ArrayList<Vector> kClosest = new ArrayList<>();
        for (int i = 0; i < k; i++) kClosest.add(distances.get(i).vector);

        String className = getTopClassName(trainingSet, kClosest);

        // assign the name
        testVector.setClassName(className);
    }

    // evaluate (and store inside the Vectors) classes for the testingSet
    private static void evalTestingSet(int k, TrainingSet trainingSet, TestingSet testingSet) {
        ArrayList<Vector> testingVectors = testingSet.getTestingVectors();

        for (Vector vector : testingVectors) evalSingleVector(k, trainingSet, vector);
    }

    private double evalPrecision() {
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter k: ");
        int k = sc.nextInt();

        TrainingSet trainingSet = new TrainingSet("./src/data/iris.data");
        TestingSet testingSet = new TestingSet("./src/data/iris.test.data");

//        TrainingSet trainingSet = new TrainingSet("./src/data/wdbc.data");
//        TestingSet testingSet = new TestingSet("./src/data/wdbc.test.data");

        System.out.println(testingSet.getTestingVectors());
        evalTestingSet(k, trainingSet, testingSet);
        System.out.println(testingSet.getTestingVectors());
    }
}
