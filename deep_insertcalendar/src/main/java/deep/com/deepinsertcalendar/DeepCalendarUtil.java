package deep.com.deepinsertcalendar;

import java.util.ArrayList;
import java.util.TimeZone;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.text.TextUtils;

/**
 * Created by wangfei on 2018/2/28.
 */

public class DeepCalendarUtil {
    private static String CALANDER_URL = "content://com.android.calendar/calendars";
    private static String CALANDER_EVENT_URL = "content://com.android.calendar/events";
    private static String CALANDER_REMIDER_URL = "content://com.android.calendar/reminders";

    public static ArrayList<Account> getAccount(Context context) {
        ArrayList<Account> accounts = new ArrayList<>();
        Cursor userCursor = context.getContentResolver().query(Uri.parse(CALANDER_URL), null, null, null, null);
        try {
            if (userCursor == null)//查询返回空值
            {
                return null;
            } else {
                while (userCursor.moveToNext()) {
                    Account account = new Account();
                    account.id = userCursor.getString(getColumnIndex(userCursor, CalendarContract.Calendars._ID));
                    account.name = userCursor.getString(getColumnIndex(userCursor, CalendarContract.Calendars.NAME));
                    account.account_type = userCursor.getString(getColumnIndex(userCursor,  Calendars.ACCOUNT_TYPE));
                    account.calendar_access_level = userCursor.getString(
                        getColumnIndex(userCursor,  Calendars.CALENDAR_ACCESS_LEVEL));
                    account.ownerAccount = userCursor.getString(getColumnIndex(userCursor,  Calendars.OWNER_ACCOUNT));
                    account.calendar_color = userCursor.getString(getColumnIndex(userCursor,  Calendars.CALENDAR_COLOR));
                    account.calendar_displayName = userCursor.getString(
                        getColumnIndex(userCursor,  Calendars.CALENDAR_DISPLAY_NAME));
                    account.calendar_timezone = userCursor.getString(
                        getColumnIndex(userCursor,  Calendars.CALENDAR_TIME_ZONE));
                    account.sync_events = userCursor.getString(getColumnIndex(userCursor,  Calendars.SYNC_EVENTS));
                    account.maxReminders = userCursor.getString(getColumnIndex(userCursor,  Calendars.MAX_REMINDERS));
                    account.canOrganizerRespond = userCursor.getString(getColumnIndex(userCursor,  Calendars.CAN_ORGANIZER_RESPOND));
                    accounts.add(account);
                }

            }

        } catch (Throwable e) {
            Log.E("error:" + e.getMessage());
        } finally {
            if (userCursor != null) {
                userCursor.close();
            }
        }
        return accounts;
    }

    private static int getColumnIndex(Cursor cursor, String name) {
        int r = cursor.getColumnIndex(name);
        if (r < 0) {
            return 0;
        } else {
            return r;
        }
    }

    private static int checkCalendarAccount(Context context) {
        Cursor userCursor = context.getContentResolver().query(Uri.parse(CALANDER_URL), null, null, null, null);
        try {
            if (userCursor == null)//查询返回空值
            { return -1; }
            int count = userCursor.getCount();
            Log.E("count = " + count);
            if (count > 0) {//存在现有账户，取第一个账户的id返回
                userCursor.moveToFirst();

                Log.E("user = " + userCursor.getString(userCursor.getColumnIndex("account_name")));
                return userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID));
            } else {
                return -1;
            }
        } finally {
            if (userCursor != null) {
                userCursor.close();
            }
        }
    }

    //检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再查询
    private static int checkAndAddCalendarAccount(Context context) {
        int oldId = checkCalendarAccount(context);
        if (oldId >= 0) {
            return oldId;
        } else {

            return -1;

        }
    }

    public static long addAccount(Context context, Account account) {
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();
        value.put(CalendarContract.Calendars.NAME, account.name);
        value.put(CalendarContract.Calendars.ACCOUNT_NAME, account.account_name);
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, account.account_type);
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, account.calendar_displayName);
        value.put(CalendarContract.Calendars.VISIBLE, account.visible);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, account.calendar_color);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, account.calendar_access_level);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, account.sync_events);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, account.calendar_timezone);
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, account.ownerAccount);
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, account.canOrganizerRespond);

        Uri calendarUri = Uri.parse(CALANDER_URL);
        calendarUri = calendarUri.buildUpon()
            .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, account.account_name)
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, account.account_type)
            .build();

        Uri result = context.getContentResolver().insert(calendarUri, value);
        long id = result == null ? -1 : ContentUris.parseId(result);
        return id;
    }
    //public static int delAccount(Context context, Account account) {
    //
    //    Uri calendarUri = Uri.parse(CALANDER_URL);
    //    calendarUri = calendarUri.buildUpon()
    //        .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
    //        .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, account.account_name)
    //        .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, account.account_type)
    //        .build();
    //
    //    return context.getContentResolver().delete(calendarUri, Calendars.ACCOUNT_NAME + " = ? and "+ Calendars.ACCOUNT_TYPE+" = ? ",new String[]{account.account_name,account.account_type});
    //}
    //private static long addCalendarAccount(Context context) {
    //    TimeZone timeZone = TimeZone.getDefault();
    //    ContentValues value = new ContentValues();
    //    value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME);
    //
    //    value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME);
    //    value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE);
    //    value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME);
    //    value.put(CalendarContract.Calendars.VISIBLE, 1);
    //    value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
    //    value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
    //    value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
    //    value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
    //    value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME);
    //    value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);
    //
    //    Uri calendarUri = Uri.parse(CALANDER_URL);
    //    calendarUri = calendarUri.buildUpon()
    //        .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
    //        .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
    //        .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
    //        .build();
    //
    //    Uri result = context.getContentResolver().insert(calendarUri, value);
    //    long id = result == null ? -1 : ContentUris.parseId(result);
    //    return id;
    //}
    public static void addCalendarEvent(Context context,Clock clock) {
        // 获取日历账户的id
        if (clock == null){
            return;
        }
        int calId = checkAndAddCalendarAccount(context);
        if (!TextUtils.isEmpty(clock.id)){
            calId = Integer.valueOf(clock.id);
        }



        ContentValues event = new ContentValues();
        event.put("title", clock.title);
        event.put("description", clock.description);
        // 插入账户的id
        event.put("calendar_id", calId);

        event.put(CalendarContract.Events.DTSTART, clock.dtstart);
        event.put(CalendarContract.Events.DTEND,  clock.dtend);
        event.put(CalendarContract.Events.HAS_ALARM, clock.hasclock);//设置有闹钟提醒
        event.put(CalendarContract.Events.EVENT_TIMEZONE, clock.eventTimezone);  //这个是时区，必须有，
        //添加事件
        Uri newEvent = context.getContentResolver().insert(Uri.parse(CALANDER_EVENT_URL), event);
        if (newEvent == null) {
            // 添加日历事件失败直接返回
            return;
        }
        //事件提醒的设定
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, ContentUris.parseId(newEvent));
        // 提前10分钟有提醒
        values.put(CalendarContract.Reminders.MINUTES, 10);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri uri = context.getContentResolver().insert(Uri.parse(CALANDER_REMIDER_URL), values);
        if (uri == null) {
            // 添加闹钟提醒失败直接返回
            return;
        }
    }

    public static void deleteCalendarEvent(Context context, String title) {
        Cursor eventCursor = context.getContentResolver().query(Uri.parse(CALANDER_EVENT_URL), null, null, null, null);
        try {
            if (eventCursor == null)//查询返回空值
            { return; }
            if (eventCursor.getCount() > 0) {
                //遍历所有事件，找到title跟需要查询的title一样的项
                for (eventCursor.moveToFirst(); !eventCursor.isAfterLast(); eventCursor.moveToNext()) {
                    String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                    if (!TextUtils.isEmpty(title) && title.equals(eventTitle)) {
                        int id = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID));//取得id
                        Uri deleteUri = ContentUris.withAppendedId(Uri.parse(CALANDER_EVENT_URL), id);
                        int rows = context.getContentResolver().delete(deleteUri, null, null);
                        if (rows == -1) {
                            //事件删除失败
                            return;
                        }
                    }
                }
            }
        } finally {
            if (eventCursor != null) {
                eventCursor.close();
            }
        }
    }
}
