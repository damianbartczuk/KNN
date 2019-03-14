import java.util.*;
import java.util.stream.Collectors;

public class KNN {
    private List<String> listTraining;
    private List<String> listTest;
    private int counter = 0;
    private List<Point> listOfPointsForTestLine = new ArrayList<>();
    private Map<String, List<Point>> map = new HashMap<>();
    private List<Boolean> isCorrect = new ArrayList<>();
    private int ileTrue = 0;
    private double result;
    private double firstCalcDist;
    private double secondCalcDist;
    private int counterSetosa;
    private int counterVersicolor;
    private int counterVirginica;
    private Reader reader;

    public KNN(List<String> listTraining, List<String> listTest, Reader reader) {
        this.listTraining = listTraining;
        this.listTest = listTest;
        this.reader= reader;
    }

    public List<Boolean> getIsCorrect() {
        return isCorrect;
    }

    int getIleTrue() {
        return ileTrue;
    }

    Map<String, List<Point>> getMap() {
        return map;
    }

     List<String> getListTraining() {
        return listTraining;
    }


    String classifyVector(String vector, int numberOfAttributes) {
        List<Point> distanceFromAddedVec = new ArrayList<>();
        List<Point> kNearestVec;
        for (String trainingLine : getListTraining()) {
            distanceFromAddedVec.add(euklidesDistance(vector, trainingLine));
        }
        kNearestVec = distanceFromAddedVec.stream().sorted(Point::compareTo).limit(reader.getK()).collect(Collectors.toList());
        List<String> atrForNewVec = new ArrayList<>();
        kNearestVec.stream().sorted(Point::compareTo).limit(reader.getK()).map(Point::getLine)
                .forEach(s -> atrForNewVec.add(s.split("\\s+")[numberOfAttributes]));
        countsOccurrence(atrForNewVec);
        String atrDecNewVector = getType(counterSetosa, counterVersicolor, counterVirginica);
        System.out.println(atrForNewVec);
        return atrDecNewVector;
    }

    private void countsOccurrence(List<String> atrybutyDlaNowegoWektora) {
        counterSetosa = 0;
        counterVersicolor = 0;
        counterVirginica = 0;
        for (String type : atrybutyDlaNowegoWektora) {
            switch (type) {
                case "Iris-virginica":
                    counterVirginica++;
                    break;
                case "Iris-versicolor":
                    counterVersicolor++;
                    break;
                case "Iris-setosa":
                    counterSetosa++;
                    break;
            }
        }
        System.out.println("Iris-virginica: " + counterVirginica);
        System.out.println("Iris-versicolor: " + counterVersicolor);
        System.out.println("Iris-setosa: " + counterSetosa);
    }

    private String getType(int counterSetosa, int counterVersicolor, int counterVirginica) {
        String atrDecNewVector = "";
        if (counterSetosa >= counterVersicolor && counterSetosa >= counterVirginica ){
            atrDecNewVector = "Iris-setosa";
        } else if(counterVersicolor >= counterSetosa && counterVersicolor >= counterVirginica){
            atrDecNewVector = "Iris-versicolor";
        }else if(counterVirginica >= counterSetosa && counterVirginica >= counterVersicolor){
            atrDecNewVector = "Iris-virginica";
        }
        return atrDecNewVector;
    }


    void calculateTheShortestDistance() {
        for (String testLine : listTest) {
            for (String treningLine : listTraining) {
                euklidesDistance(testLine, treningLine);
            }
            listOfPointsForTestLine = getKNearestPoints();
            List<String> listOfDecissionAttributes = new ArrayList<>();
            fillListOfAttributes(listOfDecissionAttributes);
            String testDecissionAtr = getTestDecissionAtr(testLine);
            for (String str : listOfDecissionAttributes) {
                if (str.equals(testDecissionAtr))
                    counter++;
            }
            if ((counter / (double) reader.getK()) >= 0.5) {
                isCorrect.add(true);
            } else {
                isCorrect.add(false);
            }
            ileTrue = getNumOfWellAssignedFlower();
            counter = 0;
            map.put(testLine, listOfPointsForTestLine);
            listOfPointsForTestLine = new ArrayList<>();
        }
    }

    private List<Point> getKNearestPoints() {
        return listOfPointsForTestLine.stream().sorted(Point::compareTo).limit(reader.getK()).collect(Collectors.toList());
    }

    private int getNumOfWellAssignedFlower() {
        return (int) isCorrect.stream().filter(con -> con).count();
    }

    private String getTestDecissionAtr(String testLine) {
        return testLine.split("\\s+")[testLine.split("\\s+").length - 1];
    }

    private void fillListOfAttributes(List<String> listOfDecissionAttributes) {
        for (Point decissionAtr : listOfPointsForTestLine) {
            listOfDecissionAttributes.add(getTestDecissionAtr(decissionAtr.getLine()));
        }
    }

    private Point euklidesDistance(String lineFromTest, String lineFromTraining) {
        String[] values1 = lineFromTest.split("\\s+");
        String[] values2 = lineFromTraining.split("\\s+");
        values1 = Arrays.copyOfRange(values1, 0, values1.length - 1);
        values2 = Arrays.copyOfRange(values2, 0, values2.length - 1);
        result = 0;
        for (int i = 0; i < values1.length; i++) {
            firstCalcDist = Double.parseDouble(values1[i].replace(',', '.'));
            secondCalcDist = Double.parseDouble(values2[i].replace(',', '.'));
            result += Math.pow((firstCalcDist - secondCalcDist), 2);
        }
        listOfPointsForTestLine.add(new Point(lineFromTraining, result));
        return new Point(lineFromTraining, result);
    }
}
