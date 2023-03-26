import java.util.ArrayList;
import java.util.HashMap;

public class StringReader {
    HashMap<String,Client> all_clients;
    StringReader(ArrayList<String> all_lines){
        for(String line:all_lines){
            StringBuilder str=new StringBuilder();
            for(int i=0; i<line.length();i++){
                if(line.charAt(i)==' ' || line.length()-1==i){
                    if(str.length()==11){

                    }
                    str=null;
                    continue;
                }
                str.insert(i,line.charAt(i));
            }
        }
    }
}
