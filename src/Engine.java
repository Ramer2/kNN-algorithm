import java.util.ArrayList;
import java.util.Scanner;

public class Engine {
    // generally works
    private static ArrayList<Double> calculateDistance(ArrayList<Vector> vectors, Vector testVector) {
        ArrayList<Double> distances = new ArrayList<>();

        for (Vector vector : vectors) {
            double distance = 0;

            for (int i = 0; i < testVector.components.length; i++)
                distance += Math.pow(vector.components[i] - testVector.components[i], 2);

            distances.add(Math.sqrt(distance));
        }

        return distances;
    }

    private static void evalClassNames() {

    }

    private double evalPrecision() {
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter k: ");
        int k = sc.nextInt();

        /*ArrayList<Vector> vectors = new ArrayList<>();

        Vector testVector = new Vector(new double[]{5.0, 3.2, 1.2, 0.2});

        Vector vector1 = new Vector(new double[]{5.1,3.5,1.4,0.2});
        Vector vector2 = new Vector(new double[]{4.9,3.0,1.4,0.2});
        Vector vector3 = new Vector(new double[]{4.7,3.2,1.3,0.2});
        Vector vector4 = new Vector(new double[]{4.6,3.1,1.5,0.2});
        Vector vector5 = new Vector(new double[]{5.0,3.6,1.4,0.2});

        vectors.add(vector1);
        vectors.add(vector2);
        vectors.add(vector3);
        vectors.add(vector4);
        vectors.add(vector5);

        System.out.println(calculateDistance(vectors, testVector));

        VectorSet trainingSet = new VectorSet("./src/wdbc.data");
        System.out.println(trainingSet.vectors.toString());
        System.out.println(trainingSet.classes.toString());
        System.out.println(trainingSet.NUMBER_OF_CLASSES);

        System.out.println();

        VectorSet testSet = new VectorSet("./src/iris.test.data");
        System.out.println(testSet.vectors.toString());
        System.out.println(testSet.classes.toString());
        System.out.println(testSet.NUMBER_OF_CLASSES);*/


    }
}
