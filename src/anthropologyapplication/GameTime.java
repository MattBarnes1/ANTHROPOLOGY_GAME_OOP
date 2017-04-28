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
public class GameTime implements java.io.Serializable {
	int Millisecond = 0;
	short Seconds = 0;
	short Minutes = 0;
	long TotalMinutes = 0;
	short Hours = 0;
	long TotalDays = 0;
	Time lastUpdateCall = null;
        Time newUpdateCall = null;
        
	public void Update(int i) {
		if(!PauseTime)
		{
                    if(i < 1000)
                    {
			Millisecond += i;
			if(Millisecond >= 100)
			{
				Millisecond = 0;
				Seconds++;
			}
			if (Seconds >= 60)
			{
				Minutes++;
				Seconds = 0;
			}
			
			if (Minutes >= 60)
			{
				Hours++;
				Minutes = 0;
			} 
			if (Hours > 23)
			{
				CalendarData.addDay();
				Hours = 0;
			} 
                    } else {
                        Millisecond += i;
                        //System.out.println("Before Correct 60000: " + this.toString());
                        correctTime();
                        //System.out.println("After Correct 60000: " + this.toString());
                        int I = 0;
                        
                    }
                if(lastUpdateCall == null)
                {
                    lastUpdateCall = getTimeStructure();
                } else {
                    lastUpdateCall = newUpdateCall;
                }
                newUpdateCall = getTimeStructure();
            }
	}

        public void UpdateAmountDays(int amount)
        {
            
        }
       
        public void UpdateAmountHours(int amount)
        {
            
        }
              

        
	

        private void correctTime() {
        while(Millisecond >= 1000)
        {
            
            int Remainder = Millisecond % 1000;
            int totalSeconds = (int)((Millisecond-Remainder)/1000);
            Millisecond = Remainder;
            Seconds += totalSeconds;
        }
        while(Seconds >= 60)
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
        while(Hours >= 24)
        {
            int Remainder = Hours % 24;
            int totalDays = (int)((Hours-Remainder)/24);
            Hours = (short)Remainder;
            CalendarData.addDays(totalDays);
        }
        
        
       

        
        
    }
        
        
            public String getDateString() {
		return CalendarData.getDateString();
            }
        
	public String getTimeString() {
		if(Minutes >= 10)
		{
			return Hours + ":" + Minutes;
		} else {
			return Hours + ":0" + Minutes;
		}
	}

	boolean PauseTime = false;
	
	public void pause() {
		PauseTime = true;
	}

	public void resume() {
		PauseTime = false;
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
		if(Minutes >= 10)
		{
			return Minutes + "";
		} else {
			return "0" + Minutes;
		}
	}

	public Time getTimeStructure() {
		return new Time(CalendarData.getYear(), CalendarData.getCurrentMonthNameIndex(), CalendarData.getCurrentDayNumber(), this.Hours, this.Minutes, this.Seconds, this.Millisecond);
	}

	@Override
        public String toString()
        {
            return "Years: " + CalendarData.getYear() + "Months: " + CalendarData.getCurrentMonthNameIndex() + "Days: " + CalendarData.getCurrentDayNumber() + "Hours: " + Hours + ":" + Minutes + ":" + Seconds + ":" + this.Millisecond;
        }

	
	public Time getElapsedTime() {
		// TODO Auto-generated method stub
                Time Check = newUpdateCall.subtract(lastUpdateCall);
               ///System.out.println(Check.getTimeStringDHMS());
		return Check; // >23 1
	}

	public boolean isNightTime() {
		// TODO Auto-generated method stub
		return false;
	}

    int getYear() {
        return CalendarData.getYear();
    }
	


}
