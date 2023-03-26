import java.util.ArrayList;
import java.util.HashMap;

public class StringReader {
    HashMap<String,Client> all_clients;
    StringReader(ArrayList<String> all_lines){
        for(int j=0; j<all_clients.size();j++){
            StringBuilder str=new StringBuilder();
            System.out.println(all_lines.get(j));
            /*for(int i=0; i<line.length();i++){
                if(line.charAt(i)==' ' || line.length()-1==i){
                    System.out.println(str);
                    if(str.length()==11){
                    }
                    str=null;
                    continue;
                }
                str.insert(i,line.charAt(i));
            }*/
        }
    }
}
