/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.calendar;

import java.util.Calendar;

/**
 *
 * @author x7
 */
public class CurrentTime {
    
    Calendar calendar;
    
    public CurrentTime(Calendar calendar){
        this.calendar = calendar;
    }
    
    public int getCurrentHour(){
        return calendar.get(Calendar.HOUR);        // 12 hour clock
    }
    public int getCurrentHourOfDay(){
        return calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
    }
    public int getCurrentMinute(){
        return calendar.get(Calendar.MINUTE);
    }
    public int getCurrentSecond(){
        return calendar.get(Calendar.SECOND);
    }
    public int getCurrentMilliescond(){
        return calendar.get(Calendar.MILLISECOND);
    }
}
