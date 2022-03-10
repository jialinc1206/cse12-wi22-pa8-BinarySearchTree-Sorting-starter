/**
 * Name: Jialin Chen
 * Email: jic053@ucsd.edu
 * Sources used: comments from:
 * https://docs.google.com/document/d/e/2PACX-1vT7pL-puGeW-r0YERAyPRqvHWbPLuVeVSYUjho64FKnyyQ99rYjwKRbfhloQXc7y9iDLaDdsBWGEs0f/pub#h.bz1b4mb6a8mf
 * 
 * this file is representaiotn of calender that stores
 * the start and end time of an event
 */

public class MyCalendar {
    MyTreeMap<Integer, Integer> calendar;
    
    public MyCalendar() {
        this.calendar = new MyTreeMap<Integer, Integer>();
    }
    
    public boolean book(int start, int end) {
        if(start < 0 || start >= end)
            throw new IllegalArgumentException();

        if(calendar.ceilingKey(start) != null) {
            // if end time lies between booked start time
            // and end time then doube booking happens
            if(end > calendar.ceilingKey(start)
            && end < calendar.get(calendar.ceilingKey(start)))
                return false;
        }
        if(calendar.floorKey(start) != null) {
            if(calendar.get(calendar.floorKey(start)) > start)
                return false;
        }
        calendar.put(start, end);
        
        return true;
    }

    public MyTreeMap getCalendar(){
        return this.calendar;
    }
}