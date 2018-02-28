package deep.com.deepinsertcalendar;

import java.util.TimeZone;

/**
 * Created by wangfei on 2018/2/28.
 */

public class Account {
    public String id = "";
    public String name = "";
    public String account_name = "";
    public String visible = "";
    public String account_type = "";
    public String calendar_displayName = "";
    public String calendar_access_level = "700";
    public String ownerAccount = "Local";
    public String calendar_color = "";
    public String sync_events = "";
    public String maxReminders = "5";
    public String calendar_timezone = TimeZone.getDefault().getID();
    public String canOrganizerRespond = "1";
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("id = ").append(id).append(",");
        sb.append("name = ").append(name).append(",");
        sb.append("visible = ").append(visible).append(",");
        sb.append("account_name = ").append(account_name).append(",");
        sb.append("account_type = ").append(account_type).append(",");
        sb.append("calendar_displayName = ").append(calendar_displayName).append(",");
        sb.append("calendar_access_level = ").append(calendar_access_level).append(",");
        sb.append("ownerAccount = ").append(ownerAccount).append(",");
        sb.append("calendar_color = ").append(calendar_color).append(",");
        sb.append("sync_events = ").append(sync_events).append(",");
        sb.append("maxReminders = ").append(maxReminders).append(".");
        sb.append("calendar_timezone = ").append(calendar_timezone).append(".");
        sb.append("canOrganizerRespond = ").append(canOrganizerRespond).append(".");
        return sb.toString();
    }

}
