package sophistic.android.crunchtime;

import android.os.CountDownTimer;
import android.widget.TextView;

import javax.xml.transform.Templates;

/**
 * Created by Jason on 5/11/2017.
 */

public class DownTimer extends CountDownTimer
{
    private String name;
    private long hour, minute, second;
    private long totalTime;
    private boolean isRunning = true;

    private TextView timerValue;

    public DownTimer(int millisInFuture, long countDownInterval, TextView timerView)
    {
        super(millisInFuture, countDownInterval);
        totalTime = millisInFuture;
        timerValue = timerView;

        second = millisInFuture / countDownInterval;
        hour = second/3600;
        second = second % 3600;
        minute = second/60;
        second = second % 60;
    }

    public String getTime()
    {
        return hour + ":" + minute + ":" + second;
    }

    public String getName()
    {
        return name;
    }

    public long getMillis() { return totalTime;}

    public boolean isRunning() {return isRunning;}
    /**
     *
     * @param millisUntilFinished Amount of time until finished
     */
    public void onTick(long millisUntilFinished)
    {
        second = millisUntilFinished / 1000;
        hour = second/3600;
        second = second % 3600;
        minute = second/60;
        second = second % 60;

        timerValue.setText(convertTime(hour,minute,second));
    }

    public String toString()
    {
        second = totalTime / 1000;
        hour = second/3600;
        second = second % 3600;
        minute = second/60;
        second = second % 60;

        return convertTime(hour,minute,second);
    }

    public String convertTime(long h, long m, long s)
    {
        StringBuilder timeStr = new StringBuilder();

        if(h < 10)
        {
            timeStr.append("0" + h + ":");
        }
        else
        {
            timeStr.append(h + ":");
        }

        if(m < 10)
        {
            timeStr.append("0" + m + ":");
        }
        else
        {
            timeStr.append(m + ":");
        }

        if(s < 10)
        {
            timeStr.append("0" + s);
        }
        else
        {
            timeStr.append(s);
        }

        return timeStr.toString();
    }




    /**
     *  Callback fired when the time is up
     */
    public void onFinish() {
        timerValue.setText(toString());
        isRunning = false;
        cancel();
    }

}
