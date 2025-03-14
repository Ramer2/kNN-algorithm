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

    private static String evalPrecision(TestingSet testingSet) {
        int total = testingSet.getTestingVectors().size();
        int correct = 0;

        for (int i = 0; i < total; i++) {
            if (testingSet.getTestingVectors().get(i).className
                    .equals(testingSet.getTestingSetClassNames().get(i))) correct++;
        }

        double accuracy = ((double) correct / total) * 100;
        return String.format("Result accuracy: %.2f%%", accuracy);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TrainingSet trainingSet;
        TestingSet testingSet;

        System.out.println("In case of updating the sets, please, restart the program.");
        System.out.println("You can type \"exit\" anytime to exit the program.");
        while (true) {
            int k;

            System.out.print("Would you like to use the automatically calculated k value? (Y/N): ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("Y")) {
                k = -1;
            } else if (input.equalsIgnoreCase("N")) {
                System.out.print("In this case please input your natural k value: ");
                k = sc.nextInt();
                sc.nextLine();
            } else if (input.equalsIgnoreCase("exit")) return;
            else {
                System.out.println("Unexpected input.");
                break;
            }

            System.out.print("Now please enter the name of the file containing your training set: ");
            input = sc.nextLine();
            trainingSet = new TrainingSet("./src/data/" + input);

            if (k == -1) k = (int) Math.sqrt(trainingSet.getTrainingVectors().size());

            System.out.print("Would you like to run the testing set? (Y/N): ");
            input = sc.nextLine();

            if (input.equalsIgnoreCase("Y")) {
                System.out.print("In this case please enter the name of the file containing your testing set: ");
                input = sc.nextLine();
                testingSet = new TestingSet("./src/data/" + input);
                evalTestingSet(k, trainingSet, testingSet);
                System.out.println(evalPrecision(testingSet));
                testingSet.reset();

            } else if (input.equalsIgnoreCase("N")) {
                System.out.print("In this case input your single test vector in format (x1,x2,x3...,xn,className): ");
                input = sc.nextLine();
                String[] parts = input.split(",");

                double[] components = new double[parts.length - 1];
                for (int i = 0; i < parts.length - 1; i++) components[i] = Double.parseDouble(parts[i]);

                String className = parts[parts.length - 1];

                // checks
                if (components.length != trainingSet.getTrainingVectors().getFirst().components.length) {
                    System.out.println("Looks like you entered less values than in the training set. Please, start over.");
                    break;
                }
                Vector testVector = new Vector(components);
                evalSingleVector(k, trainingSet, testVector);
                if (testVector.className.equals(className)) {
                    System.out.println("The test vector's classname was evaluated as the same as given.");
                } else {
                    System.out.println("The test vector's classname is different (" + testVector.className + ") than the one given (" + className + ").");
                }
            } else if (input.equalsIgnoreCase("exit")) return;
            else {
                System.out.println("Unexpected input.");
                break;
            }

            System.out.println();
        }
    }
}
