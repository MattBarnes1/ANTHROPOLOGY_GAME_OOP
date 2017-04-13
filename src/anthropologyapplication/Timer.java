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
    public Timer(int Days, int Hours, int Minutes, int Seconds)
    {
        this.Days = Days;
        this.Hours = Hours;
        this.Minutes = Minutes;
        this.Seconds = Seconds;
    }

    

    private void CorrectTime()
    {
        while(Seconds >= 60)
        {
            int Remainder = Seconds % 60;
            int totalMinutes = (int)((Seconds-Remainder)/60);
            Seconds = (short)Remainder;
            Minutes += totalMinutes;
        }
        while(Minutes >= 60)
        {
            int Remainder = Minutes % 60;
            int totalHours = (int)((Minutes-Remainder)/60);
            Minutes = (short)Remainder;
            Hours += totalHours;
        }
        while(Hours >= 24)
        {
            int Remainder = Hours % 24;
            int totalDays = (int)((Hours-Remainder)/24);
            Hours = (short)Remainder;
            Days += totalDays;
        }
    }
    int subtractLocalDay;
    int subtractLocalHours;
    int subtractLocalMinutes;
    int subtractLocalSeconds;
            
    public Timer subtract(Time elapsedTime) {
	int subtractLocalDay = this.Days - elapsedTime.Days;
	int subtractLocalHours = this.Hours - elapsedTime.Hours;
	int subtractLocalMinutes = this.Minutes - elapsedTime.Minutes;
	int subtractLocalSeconds = this.Seconds - elapsedTime.Seconds;
        CheckDays();
        CheckHours();
        CheckMinutes();
        CheckSeconds(); 
        return new Timer(subtractLocalDay,subtractLocalHours,subtractLocalMinutes,subtractLocalSeconds);
    }

    private void CheckDays()
    {
        if(subtractLocalDay < 0)
        {
            subtractLocalDay = 0;
            subtractLocalHours = 0;
            subtractLocalMinutes = 0;
            subtractLocalSeconds = 0;
        }
    }
    
    private void CheckHours()
    {
        while(subtractLocalHours < 0)
        {
            subtractLocalDay--;
            subtractLocalHours += 24;
            CheckDays();
        }
    }
    
    private void CheckMinutes() {
        while(subtractLocalMinutes < 0)
        {
            --subtractLocalHours;
            subtractLocalMinutes+=60;
        }
        CheckHours();
        CheckDays();
    }
    
    private void CheckSeconds() {
        while(subtractLocalSeconds < 0)
        {
            --subtractLocalMinutes;
            subtractLocalSeconds += 60;
        }
        CheckMinutes();
        CheckHours();
        CheckDays();
    }

    
    
    @Override
    public String toString()
    {
        return "Days: " + this.Days + "Hours: " + this.Hours + "Minutes: " + this.Minutes;
    }
    
    
    
    public boolean EqualTo(Timer timer) {
       return (timer.Hours == this.Hours && timer.Minutes == this.Minutes && this.Seconds == timer.Seconds && this.Days == timer.Days);
    }

    

    

}

