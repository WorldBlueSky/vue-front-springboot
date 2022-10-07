package vuefront.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormat {

    public static String format(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        return  simpleDateFormat.format(date);
    }
}
