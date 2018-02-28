package deep.com.deepinsertcalendar;

import java.util.Calendar;

/**
 * Created by wangfei on 2018/2/28.
 */

public class Clock {
    public String id = "";
    public String title = "";
    public String description = "";
    public String calendar_id = "";
    public int hasclock = 1;
    public String eventTimezone = "Asia/Beijing";
    public long dtstart = 0;
    public long dtend = 0;
    public Clock(String title,String description){
        this.title = title;
        this.description = description;
        init();
    }
    public Clock(String title,String description,String id){
       this.id = id;
        this.title = title;
        this.description = description;
        init();
    }
    private void init(){
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());//设置开始时间
        dtstart = mCalendar.getTime().getTime();
        mCalendar.setTimeInMillis(dtstart + 24 * 60 * 60 * 1000);//设置终止时间
        dtend = mCalendar.getTime().getTime();
    }
}
