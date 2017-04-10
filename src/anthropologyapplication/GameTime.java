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
			}/*else {
                            if(lastUpdateCall == null)
                            {
                                lastUpdateCall = getTimeStructure();
                            }
                            newUpdateCall = getTimeStructure();
                            return;
			}*/
			
			if (Minutes >= 60)
			{
				Hours++;
				Minutes = 0;
			} /*else {
                            if(lastUpdateCall == null)
                            {
                                lastUpdateCall = getTimeStructure();
                            }
                            newUpdateCall = getTimeStructure();
                            return;
			}*/
			if (Hours > 23)
			{
				IncrementCalender();
				TotalDays++;
				Hours = 0;
			} 
                    } else {
                        Millisecond += i;
                        correctTime();
                        
                        
                    }
                if(lastUpdateCall == null)
                {
                    lastUpdateCall = getTimeStructure();
                }
                newUpdateCall = getTimeStructure();
            }
	}

        String[] CalendarDayNames = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
        String[] CalendarMonthNames = { "Month1", "Month2", "Month3" };
        short[] numberOfDaysPerCalendarMonth = { 30, 30, 30};
        int Year = 0000;
        
        int currentMonthNameIndex = 0;
        int currentDayNameIndex = 0;
        int currentDayNumber = 0;
        
        
        public String getDayName()
        {
            return CalendarDayNames[currentDayNameIndex];
        }
        
        public int getDayIndex()
        {
            return currentDayNameIndex;
        }
        
        public int getMonthNameIndex()
        {
            return currentMonthNameIndex;
        }
        
        public String getMonthName()
        {
            return CalendarMonthNames[currentMonthNameIndex];
        }
        
        public int getNumberOfDaysIntoMonth()
        {
            return currentDayNumber + 1; //Plus one to fix 0
        }
        
        public String getDateString()
        {
            return getDayName() + " the " + currentDayNumber + " of " + getMonthName();
        }
        
	private void IncrementCalender() {
                if(currentDayNumber + 1 > numberOfDaysPerCalendarMonth[currentMonthNameIndex])
                {
                    currentDayNumber = 0;
                    currentDayNameIndex = 0;
                    if(currentMonthNameIndex + 1 > CalendarMonthNames.length)
                    {
                        currentMonthNameIndex = 0;
                        Year ++;
                    } else {
                        currentMonthNameIndex++;
                    }
                } else {
                    currentDayNumber++;
                    currentDayNameIndex = currentDayNumber % CalendarDayNames.length;
                }
	}

        private void correctTime() {
        if (Millisecond > 1000)
        {
            
            int Remainder = Millisecond % 1000;
            int totalSeconds = (int)((Millisecond-Remainder)/1000);
            Millisecond = Remainder;
            Seconds += totalSeconds;
        } else {
            return;
        }
        if (Seconds > 60)
        {
            
            int Remainder = Seconds % 60;
            int totalMinutes = (int)((Seconds-Remainder)/60);
            Seconds = (short)Remainder;
            Minutes += totalMinutes;
        } else {
            return;
        }
        if (Minutes > 60)
        {
            int Remainder = Minutes % 60;
            int totalHours = (int)((Minutes-Remainder)/60);
            Minutes = (short)Remainder;
            Hours += totalHours;
        } else {
            return;
        }
        if (Hours > 24)
        {
            int Remainder = Hours % 24;
            int totalDays = (int)((Hours-Remainder)/24);
            Hours = (short)Remainder;
            currentDayNumber += totalDays;
        } else {
            return;
        }
        
        if(currentDayNumber > numberOfDaysPerCalendarMonth[currentMonthNameIndex])
        {
                int Remainder = currentDayNumber % numberOfDaysPerCalendarMonth[currentMonthNameIndex];
                int totalMonths = (int)((currentDayNumber-Remainder)/numberOfDaysPerCalendarMonth[currentMonthNameIndex]);
                currentDayNumber = Remainder;
                currentDayNameIndex = Remainder % CalendarDayNames.length;
                currentMonthNameIndex += totalMonths;
        } else {
            return;
        }
        
        if(currentMonthNameIndex > CalendarMonthNames.length)
        {
            currentMonthNameIndex = 0;
            Year++;
        } else {
            currentMonthNameIndex++;
        }
        
    }
        
        
	public String getTimeString() {
		if(Minutes > 10)
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
		return new Time(this.Year, this.currentMonthNameIndex, this.currentDayNumber, this.Hours, this.Minutes, this.Seconds, this.Millisecond);
	}

	

	
	public Time getElapsedTime() {
		// TODO Auto-generated method stub
                System.out.println(newUpdateCall.absDifference(lastUpdateCall).toString());
		return newUpdateCall.subtract(lastUpdateCall); // >23 1
	}

	public boolean isNightTime() {
		// TODO Auto-generated method stub
		return false;
	}
	


}
