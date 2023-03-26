import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("Test1.txt");
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        ArrayList<String> all_lines=new ArrayList<String>();
        String line = reader.readLine();
        all_lines.add(line);
        while (line != null) {
            line = reader.readLine();
            all_lines.add(line);
        }
        all_lines.remove(all_lines.size()-1);
        StringReader mas=new StringReader(all_lines);
        mas.ClientCount();
    }
}