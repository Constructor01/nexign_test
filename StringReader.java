import java.time.ZoneOffset;
import java.util.*;
import java.io.*;
import static java.lang.Integer.parseInt;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.ZoneId;
public class StringReader {
    TreeMap<String,Client> all_clients=new TreeMap<>();
    StringReader(ArrayList<String> all_lines){
        for(String line:all_lines){
            Client cl=new Client();
            String type=new String();
            String number=new String();
            String time_start=new String();
            String time_end=new String();
            String type_tarif=new String();
            int i=0;
            //тип вызова
            for(;i!=line.length();i++){
                if(line.charAt(i)==','){
                    break;
                }
                type+=line.charAt(i);

            }
            i+=2;
            //номер
            for(;i!=line.length();i++){
                if(line.charAt(i)==','){
                    break;
                }
                number+=line.charAt(i);
            }
            i+=2;
            //дата начала
            for(;i!=line.length();i++){
                if(line.charAt(i)==','){
                    break;
                }
                time_start+=line.charAt(i);
            }
            i+=2;
            //дата конца
            for(;i!=line.length();i++){
                if(line.charAt(i)==','){
                    break;
                }
                time_end+=line.charAt(i);
            }
            i+=2;
            //тип тарифа
            for(;i!=line.length();i++){
                type_tarif+=line.charAt(i);
            }
            //добавляет информацию в зависимости от нахождения в контейнере
            if(all_clients.containsKey(number)){
                all_clients.get(number).AddAllCall(time_start,time_end,type);
            }else{
                cl.number=number;
                cl.tarif_type=type_tarif;
                cl.AddAllCall(time_start,time_end,type);
                all_clients.put(number,cl);
            }
        }
    }
    void ClientCount(){
        Set<String> all_key=all_clients.keySet();
        for(String key:all_key){
            Client cl=all_clients.get(key);
            //тип 6
            if(cl.tarif_type.equals("06")){
                try(FileWriter writer = new FileWriter("reports/"+cl.number+".txt", false)){
                    //writer.write(text);
                    //время разговора на данный момент
                    int time_second=0;
                    //всего денег потрачено
                    double all_money=100;
                    writer.write("Tarif index:"+cl.tarif_type+"\n");
                    writer.write("----------------------------------------------------------------------------\n");
                    writer.write("Report for phone number " +cl.number+":"+"\n");
                    writer.write("----------------------------------------------------------------------------\n");
                    writer.write("| Call Type |   Start Time        |     End Time        | Duration  | Cost   \n");
                    writer.write("----------------------------------------------------------------------------\n");
                    Set<String> one_client=cl.all_call.keySet();
                    for(String key_one:one_client) {
                        //разница дат
                        ZoneId zid1 = ZoneOffset.UTC;
                        int start_year = parseInt(key_one.substring(0, 4));
                        int start_mounth = parseInt(key_one.substring(4, 6));
                        int start_day = parseInt(key_one.substring(6, 8));
                        int start_hour = parseInt(key_one.substring(8, 10));
                        int start_minute = parseInt(key_one.substring(10, 12));
                        int start_second = parseInt(key_one.substring(12, 14));
                        ZonedDateTime nn = ZonedDateTime.of(start_year,
                                start_mounth,
                                start_day,
                                start_hour,
                                start_minute,
                                start_second, 0, zid1);
                        int end_year = parseInt(cl.all_call.get(key_one).date_finish.substring(0, 4));
                        int end_mounth = parseInt(cl.all_call.get(key_one).date_finish.substring(4, 6));
                        int end_day = parseInt(cl.all_call.get(key_one).date_finish.substring(6, 8));
                        int end_hour = parseInt(cl.all_call.get(key_one).date_finish.substring(8, 10));
                        int end_minute = parseInt(cl.all_call.get(key_one).date_finish.substring(10, 12));
                        int end_second = parseInt(cl.all_call.get(key_one).date_finish.substring(12, 14));
                        ZonedDateTime nn2 = ZonedDateTime.of(end_year,
                                end_mounth,
                                end_day,
                                end_hour,
                                end_minute,
                                end_second, 0, zid1);
                        Duration duration = Duration.between(nn, nn2);
                        //вывод начала даты
                        writer.write("|     "+cl.all_call.get(key_one).type+"    |");
                        writer.write(" "+start_year+"-"+key_one.substring(4, 6)+"-"+key_one.substring(6, 8));
                        writer.write(" "+key_one.substring(8, 10)+":"+key_one.substring(10, 12)+":"+key_one.substring(12, 14));
                        //вывод конца даты
                        writer.write(" | ");
                        writer.write(cl.all_call.get(key_one).date_finish.substring(0, 4)+"-"
                                +cl.all_call.get(key_one).date_finish.substring(4, 6)+"-"
                                +cl.all_call.get(key_one).date_finish.substring(6, 8)+" ");
                        writer.write(cl.all_call.get(key_one).date_finish.substring(8, 10)+":"
                                +cl.all_call.get(key_one).date_finish.substring(10, 12)+":"
                                +cl.all_call.get(key_one).date_finish.substring(12, 14));
                        writer.write("  | ");
                        //всего говорили
                        int sec=(int)duration.getSeconds()-(int)duration.getSeconds()/60*60;
                        int minute=(int)duration.getSeconds()/60%60;
                        int hour=(int)duration.getSeconds()/3600%24;
                        String str_sec=new String();
                        String str_min=new String();
                        String str_hour=new String();
                        if(hour<10){
                            str_hour+="0";
                        }
                        if(minute<10){
                            str_min+="0";
                        }
                        if(sec<10){
                            str_sec+="0";
                        }
                        str_hour+=hour;
                        str_min+=minute;
                        str_sec+=sec;
                        writer.write(str_hour+":"+str_min+":"+str_sec);
                        //списание денег в зависимости от минут
                        if (18000 >= time_second + duration.getSeconds()) {
                            //стоимость
                            writer.write("   |  "+0.0+"\n");
                        }else if(18000<time_second){
                            //стоимость
                            double count_money=(double)duration.getSeconds()/60.0;
                            all_money+=count_money;
                            writer.write("   |  "+count_money+"\n");
                        }else{
                            //стоимость
                            double count_money=((double)duration.getSeconds()-18000)/60.0;
                            all_money+=count_money;
                            writer.write("   |  "+count_money+"\n");
                        }
                        time_second+=duration.getSeconds();
                        //System.out.println(duration);

                    }
                    writer.write("----------------------------------------------------------------------------\n");
                    writer.write("|                                           Total Cost:  |     "+all_money+"\n");
                    writer.write("----------------------------------------------------------------------------\n");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            //тип 3
            if(cl.tarif_type.equals("03")){
                try(FileWriter writer = new FileWriter("reports/"+cl.number+".txt", false)){
                    //writer.write(text);
                    //время разговора на данный момент
                    int time_second=0;
                    //всего денег потрачено
                    double all_money=0;
                    writer.write("Tarif index:"+cl.tarif_type+"\n");
                    writer.write("----------------------------------------------------------------------------\n");
                    writer.write("Report for phone number " +cl.number+":"+"\n");
                    writer.write("----------------------------------------------------------------------------\n");
                    writer.write("| Call Type |   Start Time        |     End Time        | Duration  | Cost   \n");
                    writer.write("----------------------------------------------------------------------------\n");
                    Set<String> one_client=cl.all_call.keySet();
                    for(String key_one:one_client) {
                        //разница дат
                        ZoneId zid1 = ZoneOffset.UTC;
                        int start_year = parseInt(key_one.substring(0, 4));
                        int start_mounth = parseInt(key_one.substring(4, 6));
                        int start_day = parseInt(key_one.substring(6, 8));
                        int start_hour = parseInt(key_one.substring(8, 10));
                        int start_minute = parseInt(key_one.substring(10, 12));
                        int start_second = parseInt(key_one.substring(12, 14));
                        ZonedDateTime nn = ZonedDateTime.of(start_year,
                                start_mounth,
                                start_day,
                                start_hour,
                                start_minute,
                                start_second, 0, zid1);
                        int end_year = parseInt(cl.all_call.get(key_one).date_finish.substring(0, 4));
                        int end_mounth = parseInt(cl.all_call.get(key_one).date_finish.substring(4, 6));
                        int end_day = parseInt(cl.all_call.get(key_one).date_finish.substring(6, 8));
                        int end_hour = parseInt(cl.all_call.get(key_one).date_finish.substring(8, 10));
                        int end_minute = parseInt(cl.all_call.get(key_one).date_finish.substring(10, 12));
                        int end_second = parseInt(cl.all_call.get(key_one).date_finish.substring(12, 14));
                        ZonedDateTime nn2 = ZonedDateTime.of(end_year,
                                end_mounth,
                                end_day,
                                end_hour,
                                end_minute,
                                end_second, 0, zid1);
                        Duration duration = Duration.between(nn, nn2);
                        //вывод начала даты
                        writer.write("|     "+cl.all_call.get(key_one).type+"    |");
                        writer.write(" "+start_year+"-"+key_one.substring(4, 6)+"-"+key_one.substring(6, 8));
                        writer.write(" "+key_one.substring(8, 10)+":"+key_one.substring(10, 12)+":"+key_one.substring(12, 14));
                        //вывод конца даты
                        writer.write(" | ");
                        writer.write(cl.all_call.get(key_one).date_finish.substring(0, 4)+"-"
                                +cl.all_call.get(key_one).date_finish.substring(4, 6)+"-"
                                +cl.all_call.get(key_one).date_finish.substring(6, 8)+" ");
                        writer.write(cl.all_call.get(key_one).date_finish.substring(8, 10)+":"
                                +cl.all_call.get(key_one).date_finish.substring(10, 12)+":"
                                +cl.all_call.get(key_one).date_finish.substring(12, 14));
                        writer.write("  | ");
                        //всего говорили
                        int sec=(int)duration.getSeconds()-(int)duration.getSeconds()/60*60;
                        int minute=(int)duration.getSeconds()/60%60;
                        int hour=(int)duration.getSeconds()/3600%24;
                        String str_sec=new String();
                        String str_min=new String();
                        String str_hour=new String();
                        if(hour<10){
                            str_hour+="0";
                        }
                        if(minute<10){
                            str_min+="0";
                        }
                        if(sec<10){
                            str_sec+="0";
                        }
                        str_hour+=hour;
                        str_min+=minute;
                        str_sec+=sec;
                        writer.write(str_hour+":"+str_min+":"+str_sec);
                        //списание денег в зависимости от минут = 1.5 руб/мин
                        double count_money=(double)duration.getSeconds()/60.0;
                        all_money+=count_money;
                        writer.write("   |  "+count_money+"\n");
                        time_second+=duration.getSeconds();
                        //System.out.println(duration);

                    }
                    writer.write("----------------------------------------------------------------------------\n");
                    writer.write("|                                           Total Cost:  |     "+all_money+"\n");
                    writer.write("----------------------------------------------------------------------------\n");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            if(cl.tarif_type.equals("11")){
                try(FileWriter writer = new FileWriter("reports/"+cl.number+".txt", false)){
                    //writer.write(text);
                    //время разговора на данный момент
                    int time_second=0;
                    //всего денег потрачено
                    double all_money=0;
                    writer.write("Tarif index:"+cl.tarif_type+"\n");
                    writer.write("----------------------------------------------------------------------------\n");
                    writer.write("Report for phone number " +cl.number+":"+"\n");
                    writer.write("----------------------------------------------------------------------------\n");
                    writer.write("| Call Type |   Start Time        |     End Time        | Duration  | Cost   \n");
                    writer.write("----------------------------------------------------------------------------\n");
                    Set<String> one_client=cl.all_call.keySet();
                    for(String key_one:one_client) {
                        //разница дат
                        ZoneId zid1 = ZoneOffset.UTC;
                        int start_year = parseInt(key_one.substring(0, 4));
                        int start_mounth = parseInt(key_one.substring(4, 6));
                        int start_day = parseInt(key_one.substring(6, 8));
                        int start_hour = parseInt(key_one.substring(8, 10));
                        int start_minute = parseInt(key_one.substring(10, 12));
                        int start_second = parseInt(key_one.substring(12, 14));
                        ZonedDateTime nn = ZonedDateTime.of(start_year,
                                start_mounth,
                                start_day,
                                start_hour,
                                start_minute,
                                start_second, 0, zid1);
                        int end_year = parseInt(cl.all_call.get(key_one).date_finish.substring(0, 4));
                        int end_mounth = parseInt(cl.all_call.get(key_one).date_finish.substring(4, 6));
                        int end_day = parseInt(cl.all_call.get(key_one).date_finish.substring(6, 8));
                        int end_hour = parseInt(cl.all_call.get(key_one).date_finish.substring(8, 10));
                        int end_minute = parseInt(cl.all_call.get(key_one).date_finish.substring(10, 12));
                        int end_second = parseInt(cl.all_call.get(key_one).date_finish.substring(12, 14));
                        ZonedDateTime nn2 = ZonedDateTime.of(end_year,
                                end_mounth,
                                end_day,
                                end_hour,
                                end_minute,
                                end_second, 0, zid1);
                        Duration duration = Duration.between(nn, nn2);
                        //вывод начала даты
                        writer.write("|     "+cl.all_call.get(key_one).type+"    |");
                        writer.write(" "+start_year+"-"+key_one.substring(4, 6)+"-"+key_one.substring(6, 8));
                        writer.write(" "+key_one.substring(8, 10)+":"+key_one.substring(10, 12)+":"+key_one.substring(12, 14));
                        //вывод конца даты
                        writer.write(" | ");
                        writer.write(cl.all_call.get(key_one).date_finish.substring(0, 4)+"-"
                                +cl.all_call.get(key_one).date_finish.substring(4, 6)+"-"
                                +cl.all_call.get(key_one).date_finish.substring(6, 8)+" ");
                        writer.write(cl.all_call.get(key_one).date_finish.substring(8, 10)+":"
                                +cl.all_call.get(key_one).date_finish.substring(10, 12)+":"
                                +cl.all_call.get(key_one).date_finish.substring(12, 14));
                        writer.write("  | ");
                        //всего говорили
                        int sec=(int)duration.getSeconds()-(int)duration.getSeconds()/60*60;
                        int minute=(int)duration.getSeconds()/60%60;
                        int hour=(int)duration.getSeconds()/3600%24;
                        String str_sec=new String();
                        String str_min=new String();
                        String str_hour=new String();
                        if(hour<10){
                            str_hour+="0";
                        }
                        if(minute<10){
                            str_min+="0";
                        }
                        if(sec<10){
                            str_sec+="0";
                        }
                        str_hour+=hour;
                        str_min+=minute;
                        str_sec+=sec;
                        writer.write(str_hour+":"+str_min+":"+str_sec);
                        //списание денег в зависимости от минут
                        if(cl.all_call.get(key_one).type.equals("02")){
                            writer.write("   |  "+0.0+"\n");
                        }else{
                            if (6000 >= time_second + duration.getSeconds()) {
                                //стоимость
                                double count_money=(double)duration.getSeconds()/60.0*0.5;
                                all_money+=count_money;
                                writer.write("   |  "+count_money+"\n");
                            }else if(6000<time_second){
                                //стоимость
                                double count_money=(double)duration.getSeconds()/60.0*1.5;
                                all_money+=count_money;
                                writer.write("   |  "+count_money+"\n");
                            }else{
                                //стоимость
                                double count_money=(time_second+duration.getSeconds()-6000)/60.0*1.5+
                                        (6000-time_second)/60*0.5;
                                all_money+=count_money;
                                writer.write("   |  "+count_money+"\n");
                            }
                            time_second+=duration.getSeconds();
                        }
                        //System.out.println(duration);

                    }
                    writer.write("----------------------------------------------------------------------------\n");
                    writer.write("|                                           Total Cost:  |     "+all_money+"\n");
                    writer.write("----------------------------------------------------------------------------\n");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


}
