/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Duke
 */

public class Time implements java.io.Serializable {

    public static Time Zero = new Time(0,0,0,0,0,0,0);
	protected int Year = 0;
        protected int Days;
	protected int Hours = 0;
	protected int Minutes = 0;
	protected int Seconds = 0;
        protected float Milliseconds;
	protected boolean isAM = false;
	protected boolean is24Hour = true;
        protected boolean isTimer = false;

	
        CalendarData myData;

        
        int currentMonthNameIndex = 0;
        int currentDayNameIndex = 0;
        int currentDayNumber = 0;

        boolean isCountdown = false;
    //Time References a specific time in history;    
    public Time(int year, int Month, int Day, int Hours, int Minutes, int Seconds, float MS) {
        this.Year = year;
        this.currentMonthNameIndex = Month; //TODO: fix problem with month

        assert(this.currentMonthNameIndex < CalendarData.CalendarMonthNames.length);
        this.currentDayNumber = Day;
	if(Day > 0)
	{
        	this.currentDayNameIndex = Day % currentDayNumber;
	} else {
		this.currentDayNameIndex = 0;
	}
        assert(this.currentMonthNameIndex < CalendarData.CalendarDayNames.length);
        this.Milliseconds = MS;
        this.Hours = Hours;
        this.Minutes = Minutes;
        this.Seconds = Seconds;

	assert(isNonNegative(this));
        
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
	
@Override
public String toString()
{
    return "Y: " + this.Year + " M: " + this.currentMonthNameIndex + "D: " + this.currentDayNumber + "H: " + this.Hours + "M: " + this.Minutes + "S: " + this.Seconds + "MS: " + this.Milliseconds + "\n";
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
		return (GreaterThan(time)|| EqualTo(time));
	}
	public boolean LessOrEqualTo(Time time) {
		return (LesserThan(time) || EqualTo(time));
	}
	
	public boolean EqualTo(Time time) {
		return(this.Year == time.Year && this.currentMonthNameIndex == time.currentMonthNameIndex &&this.currentDayNumber == time.currentDayNumber && this.Hours == time.Hours &&this.Minutes == time.Minutes &&this.Seconds == time.Seconds &&this.Milliseconds == time.Milliseconds);
	}
	
	public boolean LesserThan(Time time) {
            if(this.Year < time.Year)
            {
                return true;
            }
            else if (this.Year == time.Year)
            {
               if(this.currentMonthNameIndex < time.currentMonthNameIndex)
               {
                   return true;
               }
               else if(this.currentMonthNameIndex == time.currentMonthNameIndex)
               {
                    if(this.currentDayNumber < time.currentDayNumber)
                   {
                       return true;
                   }
                   else if(this.currentDayNumber == time.currentDayNumber)
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
                                 } 
                                 else if (this.Minutes == time.Minutes){
                                         if (this.Seconds < time.Seconds)
                                         {
                                                 return true;
                                         }
                                         else if (this.Seconds == time.Seconds)
                                         {
                                             if(this.Milliseconds < time.Milliseconds)
                                             {
                                                 return true;
                                             }
                                         }
                                 }
                         }  

                   }
               }  
            }
           
		return false;
	}

	public boolean GreaterThan(Time time) {
		return !LesserThan(time);
	}

    
    
    public float toMS()
    {
        return (((21600000*this.Hours + Minutes*360000) + Seconds*60000)*1 + Milliseconds);
    }
    

//if less than the other just return 0 because you can't have negative times
    public Time subtract(Time lastUpdateCall) {
	int localYear = 0;
	int localMonth = 0;
	int localDay = 0;
	int localHours = 0;
	int localMinutes = 0;
	int localSeconds = 0;
	float localMilliseconds = 0;
	assert(!this.LesserThan(lastUpdateCall) || this.EqualTo(lastUpdateCall));
	if(lastUpdateCall.LesserThan(this) || this.EqualTo(lastUpdateCall))        
	{
		localMilliseconds = this.Milliseconds - lastUpdateCall.Milliseconds;
                if(localMilliseconds < 0)
                {
                    Seconds--;
                    localMilliseconds += 1000;
                }
		localSeconds = this.Seconds - lastUpdateCall.Seconds;
                if(localSeconds < 0)
                {
                    Minutes--;
                    localSeconds += 60;
                }
		localMinutes = this.Minutes - lastUpdateCall.Minutes;
                if(localMinutes < 0)
                {
                    Hours--;
                    localMinutes += 60;
                }
		localHours = this.Hours - lastUpdateCall.Hours;
                 if(localHours < 0)
                {
                    Days--;
                    localHours += 24;
                }
		localDay = this.currentDayNumber - lastUpdateCall.currentDayNumber;
                if(localDay < 0)
                {
                    localMonth--;
                    this.currentMonthNameIndex--;
                    if(this.currentMonthNameIndex < 0)
                    {
                        currentMonthNameIndex = CalendarData.getTotalMonths()-1;
                    }
                    localDay += CalendarData.getDaysPerMonthByIndex(currentMonthNameIndex);
                    
                }
		localMonth += (this.currentMonthNameIndex - lastUpdateCall.currentMonthNameIndex);
                if(localMonth < 0)
                {
                    localYear--;
                        localMonth += CalendarData.getTotalMonths();
                
                }
		localYear += this.Year - lastUpdateCall.Year;
	}
	
    return new Time(
        Math.abs(localYear),
        Math.abs(localMonth),
        Math.abs(localDay),
        Math.abs(localHours),
        Math.abs(localMinutes),
        Math.abs(localSeconds),
        Math.abs(localMilliseconds)
        );
    }

	private boolean isNonNegative(Time aTime)
	{
		return (aTime.Year < 0 || aTime.currentMonthNameIndex < 0 || aTime.currentDayNumber < 0 
			|| aTime.Hours < 0 || aTime.Minutes < 0 || aTime.Seconds < 0 || aTime.Milliseconds < 0);

	}






    public String getTimeStringDHMS() {
       
        
        return Hours + ":" + Minutes + ":" + Seconds + ":" + Milliseconds;
    }


    
    private void correctTime() {
        while (Milliseconds >= 1000)
        {
            
            int Remainder = (int)Milliseconds % 1000;
            int totalSeconds = (int)((Milliseconds-Remainder)/1000);
            Milliseconds = Remainder;
            Seconds += totalSeconds;
        }
        while (Seconds >= 60)
        {
            
            int Remainder = Seconds % 60;
            int totalMinutes = (int)((Seconds-Remainder)/60);
            Seconds = (short)Remainder;
            Minutes += totalMinutes;
        }
        while (Minutes >= 60)
        {
            int Remainder = Minutes % 60;
            int totalHours = (int)((Minutes-Remainder)/60);
            Minutes = (short)Remainder;
            Hours += totalHours;
        }
        while (Hours >= 24)
        {
            int Remainder = Hours % 24;
            int totalDays = (int)((Hours-Remainder)/24);
            Hours = (short)Remainder;
            currentDayNumber += totalDays;
        }
        
        while (currentDayNumber > CalendarData.numberOfDaysPerCalendarMonth[currentMonthNameIndex])
        {
                int Remainder = 0;
                if(currentDayNumber - CalendarData.getDaysPerMonthByIndex(currentMonthNameIndex) > 0)
                {
                     currentDayNumber =- CalendarData.getDaysPerMonthByIndex(currentMonthNameIndex);
                } else {
                    currentDayNameIndex = currentDayNumber % CalendarData.CalendarDayNames.length;
                }
                if(currentMonthNameIndex + 1 > CalendarData.getTotalMonths()-1)
                {
                    Year++;
                    currentMonthNameIndex = 0;
                } else {
                    currentMonthNameIndex++;
                }
        }
         

    }

     public Time multiply(double Ratio) {
        return new Time(this.Year, this.currentMonthNameIndex, (int)Math.floor(this.Days*Ratio), (int)Math.floor(this.Hours * Ratio),(int)Math.floor(this.Minutes*Ratio), (int)Math.floor(this.Seconds*Ratio), (int)Math.floor(this.Milliseconds*Ratio));
    }

}
