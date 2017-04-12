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
        private static int currentDayNumber = 0;
        
        public static void updateCalendar(GameTime aTime)
        {
           currentYear = aTime.getYear();
           currentMonthNameIndex = aTime.getMonthNameIndex();
           currentDayNameIndex = aTime.getDayIndex();
           currentDayNumber = aTime.getDayIndex();
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
        
}
