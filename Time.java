// Assignment 4
// partner1-Leyi Qiang
// partner1-Drunkbug
// partner2-Ranran He
// partner2-heranran

import tester.*;
// to represent different times
class Time {
    int hour;
    int minute;
    int second;
    
    // the constructor for a valid time
    // hour between 1 and 23, 
    // minutes between 0 and 59,
    // second between 0 and 59
    Time(int hour, int minute, int second) {
        this.hour = new Utils().checkTimes(0, 23, hour, " hour");
        this.minute = new Utils().checkTimes(0, 59, minute, " minute");
        this.second = new Utils().checkTimes(0, 59, second, " second");
    }
    
    /* Template
     Field:
     ... this.hour ...  -- int
     ... this.minute ...  -- int
     ... this.second ...  -- int
     */
    
    // the constructor for a time with default value 0
    Time(int hour, int minute) {
        this(hour, minute, 0);
    }
    
    /* Template
    Field:
    ... this.hour ...  -- int
    ... this.minute ...  -- int
    ... this.second ...  -- int
    */
    
    // hour between 1 and 12, 
    // minutes between 0 and 59, 
    // and a boolean flag isAM that is true for the morning hours, 
    // and initializes the Time fields accordingly
    Time(int hour, int minute, boolean isAM) {
        this(hour, minute);
        if (isAM && hour == 12) {
            this.hour = 0;
        } 
        if (!isAM && hour != 12) {
            this.hour = hour + 12;
        }
    }
    
    /* Template
    Field:
    ... this.hour ...  -- int
    ... this.minute ...  -- int
    ... this.second ...  -- int
    */
    
    /* Template
    Method:
    ... this.sameTime(Time that) ...  -- boolean
    */
    
    // is this time the same as that time?
    boolean sameTime(Time that) {
        return this.hour == that.hour && this.minute == that.minute && this.second == that.second;
    }
}

// Utils for useful method to restrict time
class Utils {
    // check if time is valid or not
    int checkTimes(int low, int high, int data, String msg) {
        if (data >= low && data < high) {
            return data;
        } 
        else {
            throw new IllegalArgumentException("Invalid" + msg + ": " + Integer.toString(data));
        }
    }
}

// Examples of time
class ExamplesTimes {
    Time time1 = new Time(2, 13, 0);
    Time time11 = new Time(2, 13);
    Time time111 = new Time(2, 13, 0);
    Time time2 = new Time(12, 45);
    Time time5 = new Time(12, 03, true);
    Time time6 = new Time(12, 03, false);
    Time time7 = new Time(0, 03, true);
    // test the constructor for the classes that represent invalid time
    boolean testDataConstructor(Tester t) {
        return t.checkConstructorException(new IllegalArgumentException(
                "Invalid second: -1"), "Time", 10, 13, -1)
                && t.checkConstructorException(new IllegalArgumentException(
                        "Invalid minute: 93"), "Time", 1, 93, 61)
                && t.checkConstructorException(new IllegalArgumentException(
                        "Invalid hour: 111"), "Time", 111, 13, true);
    }
    // test the method sameTime for the classes that represent time
    boolean testSameTime(Tester t) {
        return t.checkExpect(this.time1.sameTime(this.time11), true)
                && t.checkExpect(this.time2.sameTime(this.time1), false)
                && t.checkExpect(this.time5.sameTime(this.time6), false)
                && t.checkExpect(this.time5.sameTime(this.time7), true)
                && t.checkExpect(this.time11.sameTime(this.time111), true);
    }
}
