/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

/**
 *
 * @author Duke
 */
public class Timer {

    private int Days;
    private int Hours;
    private int Minutes;
    private int Seconds;
    private int Milliseconds = 0;

    public static final Timer Zero = new Timer(0,0,0,0);
    
    public Timer(int Days, int Hours, int Minutes, int Seconds) {
        this.Days = Days;
        this.Hours = Hours;
        this.Minutes = Minutes;
        this.Seconds = Seconds;
        CorrectTime();
    }

    private Timer(int Days, int Hours, int Minutes, int Seconds, int MS) {
        this.Days = Days;
        this.Hours = Hours;
        this.Minutes = Minutes;
        this.Seconds = Seconds;
        this.Milliseconds = MS;
        CorrectTime();
    }

    private void CorrectTime() {
        while(Milliseconds >= 1000)
        {
            int Remainder = Milliseconds % 1000;
            int totalSeconds = (int)((Milliseconds-Remainder)/1000);
            Milliseconds = Remainder;
            Seconds += totalSeconds; 
        }
        while (Seconds >= 60) {
            int Remainder = Seconds % 60;
            int totalMinutes = (int) ((Seconds - Remainder) / 60);
            Seconds = Remainder;
            Minutes += totalMinutes;
        }
        while (Minutes >= 60) {
            int Remainder = Minutes % 60;
            int totalHours = (int) ((Minutes - Remainder) / 60);
            Minutes =  Remainder;
            Hours += totalHours;
        }
        while (Hours >= 24) {
            int Remainder = Hours % 24;
            int totalDays = (int) ((Hours - Remainder) / 24);
            Hours = Remainder;
            Days += totalDays;
        }
    }
    int subtractLocalDay;
    int subtractLocalHours;
    int subtractLocalMinutes;
    int subtractLocalSeconds;
    float subtractLocalMilliseconds;

    public Timer subtract(Time elapsedTime) {
        subtractLocalDay = this.Days - elapsedTime.Days;
        subtractLocalHours = this.Hours - elapsedTime.Hours;
        subtractLocalMinutes = this.Minutes - elapsedTime.Minutes;
        subtractLocalSeconds = this.Seconds - elapsedTime.Seconds;
        subtractLocalMilliseconds = this.Milliseconds - elapsedTime.Milliseconds;
        CheckDays();
        CheckHours();
        CheckMinutes();
        CheckSeconds();
        CheckMilliseconds();
        return new Timer(subtractLocalDay, subtractLocalHours, subtractLocalMinutes, subtractLocalSeconds, (int)subtractLocalMilliseconds);
    }

    private void CheckDays() {
        if (subtractLocalDay < 0) {
            subtractLocalDay = 0;
            subtractLocalHours = 0;
            subtractLocalMinutes = 0;
            subtractLocalSeconds = 0;
        }
    }

    private void CheckHours() {
        while (subtractLocalHours < 0) {
            subtractLocalDay--;
            subtractLocalHours += 24;
            CheckDays();
        }
    }

    private void CheckMinutes() {
        while (subtractLocalMinutes < 0) {
            --subtractLocalHours;
            subtractLocalMinutes += 60;
        }
        CheckHours();
        CheckDays();
    }

    private void CheckSeconds() {
        while (subtractLocalSeconds < 0) {
            --subtractLocalMinutes;
            subtractLocalSeconds += 60;
        }
        CheckMinutes();
        CheckHours();
        CheckDays();
    }

    private void CheckMilliseconds() {
        while (subtractLocalMilliseconds < 0) {
            --subtractLocalSeconds;
            subtractLocalMilliseconds += 1000;
        }
        CheckSeconds();
        CheckMinutes();
        CheckHours();
        CheckDays();
    }

    @Override
    public String toString() {
        return "Days: " + this.Days + "Hours: " + this.Hours + "Minutes: " + this.Minutes + "Seconds: " + this.Seconds + "Milliseconds: " + this.Milliseconds;
    }

    public boolean EqualTo(Timer timer) {
        return (timer.Hours == this.Hours && timer.Minutes == this.Minutes && this.Seconds == timer.Seconds && this.Days == timer.Days);
    }

    public double dividedBy(Timer BuildTimeToBuild) {
        //Convert into one value;
        return 1 - internalDivide(BuildTimeToBuild);
    }

    private double internalDivide(Timer BuildTimeToBuild) {
        double thisValue = convertTogetherIntoLowestCommonValue(BuildTimeToBuild);
        double BuildTime = BuildTimeToBuild.convertTogetherIntoLowestCommonValue(this);
        return thisValue / BuildTime;
    }

    private double convertTogetherIntoLowestCommonValue(Timer BuildTimeToBuild) {
        if (Milliseconds != 0 || BuildTimeToBuild.Milliseconds != 0) {
            return (((((Days*24 + Hours)*60 + Minutes)*60 + Seconds)*1000) + Milliseconds);
        }
        if (Seconds != 0 || BuildTimeToBuild.Seconds != 0) {
            return (((Days*24 + Hours)*60 + Minutes)*60 + Seconds);
        }
        if (Minutes != 0 || BuildTimeToBuild.Minutes != 0) {
            return (((Days*24 + Hours)*60 + Minutes)*60);
        }
        if (Hours != 0 || BuildTimeToBuild.Hours != 0) {
            return ((Days*24 + Hours)*60);
        }
        if (Days != 0 || BuildTimeToBuild.Days != 0) {
            return Days;
        }
        return 0;
    }

    public Timer multiply(double Ratio) {
        return new Timer((int)Math.floor(this.Days*Ratio), (int)Math.floor(this.Hours * Ratio),(int)Math.floor(this.Minutes*Ratio), (int)Math.floor(this.Seconds*Ratio), (int)Math.floor(this.Milliseconds*Ratio));
    }

}
