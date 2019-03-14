import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Reader {
    private int numberOfAttributes;
    private BufferedReader bufferedReader;
    private int k = 0;

    int getNumberOfAttributes() {
        return numberOfAttributes;
    }

    int getK() {
        return k;
    }

    void readFromFile(String path, List<String> lista) {
        String line;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(path)));
            int firstAppend = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.matches("\\s+.*")) {
                    for (int i = 0; i < line.length(); i++)
                        if (line.charAt(i) > 32) {
                            firstAppend = i;
                            break;
                        }
                    line = line.substring(firstAppend);
                    firstAppend = 0;
                }
                lista.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    int readK(Scanner scanner) {
        System.out.println("podaj wartosc k");
        this.k = scanner.nextInt();
        scanner.nextLine();
        return k;
    }

    String readLineFromConsole(Scanner scanner) {
        int i = 0;
        String nextLineFromConsole="";
        while (i < getNumberOfAttributes()) {
            System.out.println(" " + (i + 1) + "/" + getNumberOfAttributes());
            nextLineFromConsole += scanner.nextLine();
            nextLineFromConsole += ' ';
            i++;
        }
        return nextLineFromConsole;
    }

    public void setNumberOfAttributes(List<String> list) {
        this.numberOfAttributes = list.get(0).split("\\s+").length-1;
    }
}
