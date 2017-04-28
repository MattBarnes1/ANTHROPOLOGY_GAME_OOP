/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package anthropologyapplication;

/**
 *
 * @author noone
 */
public class CalendarData {
        public final static String[] CalendarDayNames = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
        public final static String[] CalendarMonthNames = { "Month1", "Month2", "Month3" };
        public final static short[] numberOfDaysPerCalendarMonth = { 30, 30, 30};
        
        private static int currentYear = 0;
        private static int currentMonthNameIndex = 0;
        private static int currentDayNameIndex = 0;
        private static int currentDayNumber = 1;     
        
        public static void addDay()
        {
            addDays(1);
        }
        public static void addDays(int amount)
        {
            currentDayNumber+= amount;
            while(currentDayNumber > CalendarData.numberOfDaysPerCalendarMonth[currentMonthNameIndex])
            {
                    int Remainder = 0;
                    if(currentDayNumber - CalendarData.numberOfDaysPerCalendarMonth[currentMonthNameIndex] > 0)
                    {
                        currentDayNumber = currentDayNumber - CalendarData.numberOfDaysPerCalendarMonth[currentMonthNameIndex];
                        if(currentMonthNameIndex + 1 >= CalendarData.CalendarMonthNames.length)
                        {
                            currentMonthNameIndex = 0;
                            currentYear ++;
                        } else {
                            currentMonthNameIndex++;
                        }
                    }
            }
            
            currentDayNameIndex = currentDayNumber % CalendarData.CalendarDayNames.length;
        }
        
        
        
       
        private static void IncrementCalender() {
                if(currentDayNumber + 1 > CalendarData.numberOfDaysPerCalendarMonth[currentMonthNameIndex])
                {
                    currentDayNumber = 0;
                    currentDayNameIndex = 0;
                    if(currentMonthNameIndex + 1 > CalendarData.CalendarMonthNames.length)
                    {
                        currentMonthNameIndex = 0;
                        currentYear ++;
                    } else {
                        currentMonthNameIndex++;
                    }
                } else {
                    currentDayNumber++;
                    currentDayNameIndex = currentDayNumber % CalendarData.CalendarDayNames.length;
                }
	}
            
        public static String getCalendarMonthNameByIndex(int i)
        {
            if(CalendarMonthNames.length > i && i >= 0)
            {
                return CalendarMonthNames[i];
            } else {
               return null;
            }
        }
        
        public static String getCalendarDayNameByIndex(int i)
        {
            if(CalendarDayNames.length > i && i >= 0)
            {
                return CalendarDayNames[i];
            } else {
               return null;
            }
        }
        
        public static short getDaysPerMonthByIndex(int i)
        {
            if(numberOfDaysPerCalendarMonth.length > i && i >= 0)
            {
                return numberOfDaysPerCalendarMonth[i];
            } else {
               return -1;
            }
        }
        
        public static String getDateString()
        {
            return getDayName() + " the " + currentDayNumber + " of " + getMonthName();
        }
        
        public static String getDayName()
        {
           return CalendarDayNames[currentDayNameIndex];
        }
        
        public static String getMonthName()
        {
            return CalendarMonthNames[currentMonthNameIndex];
        }
        
        public static int getDaysPerMonth()
        {
            return numberOfDaysPerCalendarMonth[currentMonthNameIndex];
        }
        
        public static int getCurrentMonthNameIndex()
        {
            return currentMonthNameIndex;
        }
        
        public static int getCurrentDayNumber()
        {
            return currentDayNumber;
        }
        
        public static int getCurrentYear()
        {
            return currentYear;
        }

    public static int getTotalMonths() {
       return CalendarMonthNames.length;
    }

    public static int getDayIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static int getYear() {
        return currentYear;
    }
        
}
