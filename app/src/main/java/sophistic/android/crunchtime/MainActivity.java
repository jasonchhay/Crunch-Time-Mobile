package sophistic.android.crunchtime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.CountDownTimer;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static int index = 0;

    private EditText hourText;
    private EditText minText;
    private EditText secText;

    private EditText breakHourText;
    private EditText breakMinText;
    private EditText breakSecText;

    private TextView timerValue;
    private TextView breakValue;

    private TextView timeListView;
    private TextView nameListView;

    private Button startButton;
    private Button pauseButton;
    private Button stopButton;

    private ImageButton longTermButton;

    private DownTimer mTimer;

    private static ArrayList mTimerList = new ArrayList<DownTimer>();

    private int i = 0;

    private boolean isRunning = false;

    private int timerIndex = 0;

    private TextView colon1;
    private TextView colon2;
    private TextView colon3;
    private TextView colon4;

    private Timer t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        hourText = (EditText) findViewById(R.id.time_hr);
        minText = (EditText) findViewById(R.id.time_min);
        secText = (EditText) findViewById(R.id.time_sec);

        breakHourText = (EditText) findViewById(R.id.break_hr);
        breakMinText = (EditText) findViewById(R.id.break_min);
        breakSecText = (EditText) findViewById(R.id.break_sec);

        timerValue = (TextView) findViewById(R.id.timer_text);
        breakValue = (TextView) findViewById(R.id.break_text);

        timeListView = (TextView) findViewById(R.id.time_list);
        nameListView = (TextView) findViewById(R.id.name_list);

        startButton = (Button) findViewById(R.id.start_button);
        pauseButton = (Button) findViewById(R.id.pause_button);
        stopButton = (Button) findViewById(R.id.stop_button);

        colon1 = (TextView) findViewById(R.id.colon1);
        colon2 = (TextView) findViewById(R.id.colon2);
        colon3 = (TextView) findViewById(R.id.colon3);
        colon4 = (TextView) findViewById(R.id.colon4);

        longTermButton = (ImageButton) findViewById(R.id.longterm_button);

        t = new Timer();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimerList.add(makeTimer(hourText, minText, secText, timerValue));
                mTimerList.add(makeTimer(breakHourText, breakMinText, breakSecText, breakValue));

                hourText.setVisibility(View.GONE);
                minText.setVisibility(View.GONE);
                secText.setVisibility(View.GONE);

                breakHourText.setVisibility(View.GONE);
                breakMinText.setVisibility(View.GONE);
                breakSecText.setVisibility(View.GONE);

                colon1.setVisibility(View.GONE);
                colon2.setVisibility(View.GONE);
                colon3.setVisibility(View.GONE);
                colon4.setVisibility(View.GONE);

                timerValue.setVisibility(View.VISIBLE);
                timerValue.setText(((DownTimer)mTimerList.get(0)).toString());
                breakValue.setVisibility(View.VISIBLE);
                breakValue.setText(((DownTimer)mTimerList.get(1)).toString());

                setIsRunning(true);

                int size = mTimerList.size();

                Runnable timeRunnable = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        int timerIndex = 0;

                        startTimer();
                    }
                };

                Thread timeThread = new Thread(timeRunnable);
                timeThread.start();

                timerValue.setText("Enter a time:");
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mTimer.pause();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               mTimer.cancel();
               setIsRunning(false);
               mTimerList.clear();
               timerClear();
           }
       });

        longTermButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int test = 0;
                Intent i = LongTermActivity.newIntent(MainActivity.this);
                startActivityForResult(i, test);
            }
        });
    }

    public static ArrayList<DownTimer> getTimerList()
    {
        return mTimerList;
    }

    public static int getIndex()
    {
        return index;
    }

    public DownTimer makeTimer(EditText hourText, EditText minText, EditText secText, TextView textView)
    {
        int hour = 0;
        int minute = 0;
        int second = 0;

        //Checks whether or not the values are null, sets them to zero otherwise
        if (!hourText.getText().toString().equals("")) {
            hour = Integer.parseInt(hourText.getText().toString());
        }
        if (!minText.getText().toString().equals("")) {
            minute = Integer.parseInt(minText.getText().toString());
        }
        if (!secText.getText().toString().equals("")) {
            second = Integer.parseInt(secText.getText().toString());
        }

        hourText.setText("");
        minText.setText("");
        secText.setText("");

        //creates the timer
        return (new DownTimer(((hour * 3600) + (minute * 60) + second) * 1000, 1000, textView));
    }

    public boolean isRunning()
    {
        return isRunning;
    }

    public void setIsRunning(boolean b)
    {
        isRunning = b;
    }

    public int getTimerIndex()
    {
        return timerIndex;
    }

    public void setTimerIndex(int i)
    {
        timerIndex = i;
    }

    public void timerClear()
    {
        hourText.setVisibility(View.VISIBLE);
        minText.setVisibility(View.VISIBLE);
        secText.setVisibility(View.VISIBLE);

        breakHourText.setVisibility(View.VISIBLE);
        breakMinText.setVisibility(View.VISIBLE);
        breakSecText.setVisibility(View.VISIBLE);

        colon1.setVisibility(View.VISIBLE);
        colon2.setVisibility(View.VISIBLE);
        colon3.setVisibility(View.VISIBLE);
        colon4.setVisibility(View.VISIBLE);

        timerValue.setVisibility(View.GONE);
        breakValue.setVisibility(View.GONE);
    }

    public void startTimer()
    {
        mTimer = (DownTimer) mTimerList.get(timerIndex);
        mTimer.start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                setTimerIndex(getTimerIndex()^1);
                startTimer();
            }
        }, mTimer.getMillis());
    }
}
