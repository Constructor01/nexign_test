import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        //FileInputStream fileIn = new FileInputStream("src/Test1.txt");
        File file = new File("Test1.txt");
        //создаем объект FileReader для объекта File
        FileReader fr = new FileReader(file);
        //создаем BufferedReader с существующего FileReader для построчного считывания
        BufferedReader reader = new BufferedReader(fr);
        // считаем сначала первую строку
        ArrayList<String> all_lines=new ArrayList<String>();
        String line = reader.readLine();
        all_lines.add(line);
        while (line != null) {
            System.out.println(line);
            // считываем остальные строки в цикле
            //line = reader.readLine();
            all_lines.add(line);
        }
        all_lines.remove(all_lines.size()-1);
        //StringReader mas=new StringReader(all_lines);
    }
}