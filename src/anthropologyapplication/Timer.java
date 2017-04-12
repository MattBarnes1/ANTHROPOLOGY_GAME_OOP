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

    Timer(int i, int i0, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    Timer subtract(Time elapsedTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    boolean EqualTo(Timer timer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}

