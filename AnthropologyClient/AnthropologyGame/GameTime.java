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
			} else {
				return; //This is for speed
			}
			if (Minutes >= 60)
			{
				Hours++;
				Minutes = 0;
			} else {
				return; //This is for speed
			}
			if (Hours > 23)
			{
				IncrementCalender();
				TotalDays++;
				Hours = 0;
			} 
		}
                if(lastUpdateCall == null)
                {
                    lastUpdateCall = getTimeStructure();
                }
                newUpdateCall = getTimeStructure();
	}

	private void IncrementCalender() {
		// TODO Auto-generated method stub
		
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
		return new Time(this.Hours, this.Minutes, this.Seconds);
	}

	

	
	public Time getElapsedTime() {
		// TODO Auto-generated method stub
		return newUpdateCall.absDifference(lastUpdateCall); // >23 1
	}

	public boolean isNightTime() {
		// TODO Auto-generated method stub
		return false;
	}
	


}
