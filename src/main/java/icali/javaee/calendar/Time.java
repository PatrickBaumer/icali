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
public class Time {
    
    Calendar calendar;
    
    public Time(Calendar calendar){
        this.calendar = calendar;
    }
    
    public int getHour(){
        return calendar.get(Calendar.HOUR);        // 12 hour clock
    }
    public int getHourOfDay(){
        return calendar.get(Calendar.HOUR_OF_DAY); // 24 hour clock
    }
    public int getMinute(){
        return calendar.get(Calendar.MINUTE);
    }
    public int getSecond(){
        return calendar.get(Calendar.SECOND);
    }
    public int getMilliescond(){
        return calendar.get(Calendar.MILLISECOND);
    }
}
