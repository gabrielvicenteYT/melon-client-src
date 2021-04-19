package com.mysql.cj.protocol;

public class InternalTimestamp extends InternalDate
{
    private int hours;
    private int minutes;
    private int seconds;
    private int nanos;
    private int scale;
    
    public InternalTimestamp() {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.nanos = 0;
        this.scale = 0;
    }
    
    public InternalTimestamp(final int year, final int month, final int day, final int hours, final int minutes, final int seconds, final int nanos, final int scale) {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.nanos = 0;
        this.scale = 0;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.nanos = nanos;
        this.scale = scale;
    }
    
    public int getHours() {
        return this.hours;
    }
    
    public void setHours(final int hours) {
        this.hours = hours;
    }
    
    public int getMinutes() {
        return this.minutes;
    }
    
    public void setMinutes(final int minutes) {
        this.minutes = minutes;
    }
    
    public int getSeconds() {
        return this.seconds;
    }
    
    public void setSeconds(final int seconds) {
        this.seconds = seconds;
    }
    
    public int getNanos() {
        return this.nanos;
    }
    
    public void setNanos(final int nanos) {
        this.nanos = nanos;
    }
    
    @Override
    public boolean isZero() {
        return super.isZero() && this.hours == 0 && this.minutes == 0 && this.seconds == 0 && this.nanos == 0;
    }
    
    public int getScale() {
        return this.scale;
    }
    
    public void setScale(final int scale) {
        this.scale = scale;
    }
}
