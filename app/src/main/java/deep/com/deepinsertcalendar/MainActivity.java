package deep.com.deepinsertcalendar;

import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button getAccountBtn,insertEventBtn,insetAccountBtn,insertEventByACCBtn,delAccountBtn,delEventBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAccountBtn = (Button)findViewById(R.id.get);
        getAccountBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
              ArrayList<Account> list =  DeepCalendarUtil.getAccount(MainActivity.this);
             if(list!=null){
                 for (Account a:list){
                     Log.E(a.toString());
                 }
             }

            }
        });
        insetAccountBtn = (Button)findViewById(R.id.insertaccount);
        insetAccountBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = new Account();
                account.name = "aaq";
                account.account_name = "aaq";
                account.calendar_displayName = "ccc";
                account.account_type = "test";
                DeepCalendarUtil.addAccount(MainActivity.this,account);
            }
        });
        insertEventByACCBtn = (Button)findViewById(R.id.insertbyuser);
        insertEventByACCBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Clock clock = new Clock("123","456","1");
                DeepCalendarUtil.addCalendarEvent(MainActivity.this,clock);
            }
        });

        delEventBtn = (Button)findViewById(R.id.delevent);
        delEventBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DeepCalendarUtil.deleteCalendarEvent(MainActivity.this,"aaaa");
            }
        });
        insertEventBtn = (Button)findViewById(R.id.insert);
        insertEventBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Clock clock = new Clock("123", "456");
                DeepCalendarUtil.addCalendarEvent(MainActivity.this, clock);
            }
        });

    }
}
