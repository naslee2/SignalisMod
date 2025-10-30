package data.scripts.plugins;

import org.apache.log4j.Logger;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignClockAPI;
import com.fs.starfarer.api.util.IntervalUtil;

import java.util.GregorianCalendar;

public class Eusan_Nation_TimeCheckScript implements EveryFrameScript {

    //Logger logger = Global.getLogger(Eusan_Nation_TimeCheckScript.class);
    //protected float daysElapsed = 0f;
    private IntervalUtil dayInterval = new IntervalUtil(1f, 1f);

    //long data = Global.getSector().getClock().getTimestamp();


    @Override
    public void advance(float amount) {
        CampaignClockAPI clock = Global.getSector().getClock();

        //float days = clock.getDay(); //gives an int result. Example is 3.0
        //float days = clock.convertToDays(amount); //gives a decimal result: 0.0016666667 which is the time in seconds elapsed
        //float days = clock.getElapsedDaysSince(data); //gives float value of time since timestamp
        //String days = clock.getShortDate(); //Returns the string 206.3.3
        //String days = clock.getCycleString(); //Returns the string 206
        String days = clock.getShortMonthString();
        //int days = clock.getCycle();
        //int days = clock.getHour();
        //GregorianCalendar days = clock.getCal();
        //logger.info("Logger active. data: " + days); //java.util.GregorianCalendar[time=-55661212942000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=sun.util.calendar.ZoneInfo[id="America/Los_Angeles",offset=-28800000,dstSavings=3600000,useDaylight=true,transitions=185,lastRule=java.util.SimpleTimeZone[id=America/Los_Angeles,offset=-28800000,dstSavings=3600000,useDaylight=true,startYear=0,startMode=3,startMonth=2,startDay=8,startDayOfWeek=1,startTime=7200000,startTimeMode=0,endMode=3,endMonth=10,endDay=1,endDayOfWeek=1,endTime=7200000,endTimeMode=0]],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=206,MONTH=2,WEEK_OF_YEAR=10,WEEK_OF_MONTH=2,DAY_OF_MONTH=2,DAY_OF_YEAR=61,DAY_OF_WEEK=1,DAY_OF_WEEK_IN_MONTH=1,AM_PM=1,HOUR=3,HOUR_OF_DAY=15,MINUTE=57,SECOND=38,MILLISECOND=0,ZONE_OFFSET=-28800000,DST_OFFSET=0]

        //int days = clock.getDay(); //return float number. Example 3.0

        //dayInterval.advance(days);

        //if(dayInterval.intervalElapsed()){

            //logger.info("Logger active. Initial Timestamp is: " + data);
            //logger.info("Logger active. Number of days is: " + days);
        //}
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }


}
