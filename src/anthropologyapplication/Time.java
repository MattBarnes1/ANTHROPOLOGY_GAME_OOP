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

public class Time implements java.io.Serializable {

	protected int Hours = 0;
	protected int Minutes = 0;
	protected int Seconds = 0;
        protected float Milleseconds;
	protected boolean isAM = false;
	protected boolean is24Hour = true;
	public Time(int Hours, int Minutes, int Seconds)
	{
		this.Hours = Hours;
		this.Minutes = Minutes;
		this.Seconds = Seconds;
	}
	
	public Time(int Hours, int Minutes, int Seconds, boolean isAM)
	{
		this.Hours = Hours;
		this.Minutes = Minutes;
		this.Seconds = Seconds;
		if(isAM)
		{
			if(Hours == 12)
			{
				this.Hours = 0;
				this.Minutes = Minutes;
				this.Seconds = Seconds;
			} else {
				this.Hours = Hours;
				this.Minutes = Minutes;
				this.Seconds = Seconds;
			}
		} else {
			this.Hours = Hours + 12;
			this.Minutes = Minutes;
			this.Seconds = Seconds;
		}
		
	}

    

    public Time(int Hours, int Minutes, int Seconds, float MS) {
        this.Milleseconds = MS;
        this.Hours = Hours;
        this.Minutes = Minutes;
        this.Seconds = Seconds;
        correctTime();
    }
	
	public String getTimeString12Hour() {
		String retString;
			if(Hours < 12 && Hours > 0)
			{
				retString = Hours + ":" + CorrectMinutes() + " am";
			} 
			else if (Hours == 12)
			{
				retString = Hours + ":" +  CorrectMinutes() + " pm";
			} 
			else if ( Hours != 0)
			{
				retString = (Hours - 12) + ":" +  CorrectMinutes() + " pm";
			}
			else 
			{
				retString = "12:" +  CorrectMinutes() + " am";
			}
		
	
		return retString;
	}
	

	private String CorrectMinutes() {
		if(Minutes > 10)
		{
			return Minutes + "";
		} else {
			return "0" + Minutes;
		}
	}
	
	public boolean GreaterEqualTo(Time time) {
		return (GreaterThan(time) || EqualTo(time));
	}
	public boolean LessOrEqualTo(Time time) {
		return (LesserThan(time) || EqualTo(time));
	}
	
	public boolean EqualTo(Time time) {
		return(getTimeString12Hour().compareTo(time.getTimeString12Hour()) == 0);
	}
	
	public boolean LesserThan(Time time) {
		if (this.is24Hour && time.is24Hour)
		{
			if(this.Hours < time.Hours)
			{
				return true;
			} 
			else if (this.Hours == time.Hours)
			{
				if(this.Minutes < time.Minutes)
				{
					return true;
				} else {
					if (this.Seconds < time.Seconds)
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}

	public boolean GreaterThan(Time time) {
			if(this.Hours > time.Hours)
			{
				return true;
			} 
			else if (this.Hours == time.Hours)
			{
				if(this.Minutes > time.Minutes)
				{
					return true;
				} else {
					if (this.Seconds > time.Seconds)
					{
						return true;
					}
				}
			}
		
		
		return false;
	}

    public Time absDifference(Time lastUpdateCall) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    public float toMS()
    {
        return (((21600000*this.Hours + Minutes*360000) + Seconds*60000)*1 + Milleseconds);
    }
    
    public Time subtract(Time elapsedTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getTimeString() {
        return Hours + ":" + Minutes + ":" + Seconds + ":" + Milleseconds;
    }

    private void correctTime() {
        if (Milleseconds > 1000)
        {
            
            float Remainder = Milleseconds % 1000;
            int totalSeconds = (int)((Milleseconds-Remainder)/1000);
            Milleseconds = Remainder;
            Seconds += totalSeconds;
        }
        if (Seconds > 60)
        {
            
            int Remainder = Seconds % 60;
            int totalMinutes = (int)((Seconds-Remainder)/60);
            Seconds = Remainder;
            Minutes += totalMinutes;
        }
        if (Minutes > 60)
        {
            
            int Remainder = Minutes % 60;
            int totalHours = (int)((Minutes-Remainder)/60);
            Minutes = Remainder;
            Hours += totalHours;
        }
    }
}