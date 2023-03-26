import java.util.HashMap;
import java.util.TreeMap;


public class Client {
    public String number;
    public String tarif_type;
    public TreeMap<String,Info> all_call=new TreeMap<String,Info>();
    public void  AddAllCall(String call_start, String call_finish,String type){
        Info a=new Info();
        a.date_finish=call_finish;
        a.type=type;
        all_call.put(call_start,a);
    }
}
