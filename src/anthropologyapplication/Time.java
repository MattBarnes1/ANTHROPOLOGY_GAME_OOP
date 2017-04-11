/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthropologyapplication;

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

	protected int Hours = 0;
	protected int Minutes = 0;
	protected int Seconds = 0;
        protected float Milliseconds;
	protected boolean isAM = false;
	protected boolean is24Hour = true;
	public Time(int Hours, int Minutes, int Seconds)
	{
		this.Hours = Hours;
		this.Minutes = Minutes;
		this.Seconds = Seconds;
	}
	
	
        String[] CalendarDayNames = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
        String[] CalendarMonthNames = { "Month1", "Month2", "Month3" };
        short[] numberOfDaysPerCalendarMonth = { 30, 30, 30};
        int Year = 0000;
        
        int currentMonthNameIndex = 0;
        int currentDayNameIndex = 0;
        int currentDayNumber = 0;

    public Time(int year, int Month, int Day, int Hours, int Minutes, int Seconds, float MS) {
        this.Year = year;
        this.currentMonthNameIndex = Month;
        assert(this.currentMonthNameIndex < CalendarMonthNames.length);
        this.currentDayNumber = Day;
	if(Day > 0)
	{
        	this.currentDayNameIndex = Day % currentDayNumber;
	} else {
		this.currentDayNameIndex = 0;
	}
        assert(this.currentMonthNameIndex < CalendarDayNames.length);
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
	assert(this.LesserThan(lastUpdateCall));
	if(this.LesserThan(lastUpdateCall))        
	{
		localYear = this.Year - lastUpdateCall.Year;
		localMonth = this.currentMonthNameIndex - lastUpdateCall.currentMonthNameIndex;
		localDay = this.currentDayNumber - lastUpdateCall.currentDayNumber;
		localHours = this.Hours - lastUpdateCall.Hours;
		localMinutes = this.Minutes - lastUpdateCall.Minutes;
		localSeconds = this.Seconds - lastUpdateCall.Seconds;
		localMilliseconds = this.Milliseconds - lastUpdateCall.Milliseconds;
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






    public String getTimeString() {
        return Hours + ":" + Minutes + ":" + Seconds + ":" + Milliseconds;
    }

    private void correctTime() {
         if (Milliseconds >= 1000)
        {
            
            int Remainder = (int)Milliseconds % 1000;
            int totalSeconds = (int)((Milliseconds-Remainder)/1000);
            Milliseconds = Remainder;
            Seconds += totalSeconds;
        }
        if (Seconds >= 60)
        {
            
            int Remainder = Seconds % 60;
            int totalMinutes = (int)((Seconds-Remainder)/60);
            Seconds = (short)Remainder;
            Minutes += totalMinutes;
        }
        if (Minutes >= 60)
        {
            int Remainder = Minutes % 60;
            int totalHours = (int)((Minutes-Remainder)/60);
            Minutes = (short)Remainder;
            Hours += totalHours;
        }
        if (Hours >= 24)
        {
            int Remainder = Hours % 24;
            int totalDays = (int)((Hours-Remainder)/24);
            Hours = (short)Remainder;
            currentDayNumber += totalDays;
        }
        
        if(currentDayNumber > numberOfDaysPerCalendarMonth[currentMonthNameIndex])
        {
                int Remainder = currentDayNumber % numberOfDaysPerCalendarMonth[currentMonthNameIndex];
                int totalMonths = (int)((currentDayNumber-Remainder)/numberOfDaysPerCalendarMonth[currentMonthNameIndex]);
                currentDayNumber = Remainder;
                currentDayNameIndex = Remainder % CalendarDayNames.length;
                currentMonthNameIndex += totalMonths;
        }
        
        if(currentMonthNameIndex > CalendarMonthNames.length)
        {
		int Remainder = currentMonthNameIndex % CalendarMonthNames.length;
		int totalYears = (currentMonthNameIndex-Remainder)%CalendarMonthNames.length;
		currentMonthNameIndex = Remainder;
            	Year += totalYears;
            Year++;
        }
    }
}
