import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<String> listTraining = new ArrayList<>();
        List<String> listTest = new ArrayList<>();
        Reader  reader = new Reader();
        KNN knn = new KNN(listTraining,listTest, reader);
        reader.readFromFile("iris_training.txt", listTraining);
        reader.readFromFile("iris_test.txt", listTest);
        reader.setNumberOfAttributes(listTest);
        Scanner scanner = new Scanner(System.in);
        reader.readK(scanner);
        System.out.println("Czy chcesz wprowadzic wektor : jesli tak to: '1' a jesli nie to '0'");
        String nextLineFromConsole = scanner.nextLine();
        while (nextLineFromConsole.equals("1")) {
            System.out.println("Jest to typ = " + knn.classifyVector(reader.readLineFromConsole(scanner), reader.getNumberOfAttributes()));
            System.out.println("Czy chcesz wprowadzic wektor : jesli tak to: '1' a jesli nie to '0'");
            nextLineFromConsole = scanner.nextLine();
        }
        knn.calculateTheShortestDistance();
        knn.getMap().entrySet().stream().forEach(System.out::println);
        double stosunekTrafien = knn.getIleTrue()/ (double)knn.getIsCorrect().size();
        System.out.println("stusunek = " + (stosunekTrafien*100) + "%");
    }
}
