package com.mysql.cj.protocol;

public class InternalTime
{
    private int hours;
    private int minutes;
    private int seconds;
    private int nanos;
    private int scale;
    
    public InternalTime() {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.nanos = 0;
        this.scale = 0;
    }
    
    public InternalTime(final int hours, final int minutes, final int seconds, final int nanos, final int scale) {
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.nanos = 0;
        this.scale = 0;
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
    
    public boolean isZero() {
        return this.hours == 0 && this.minutes == 0 && this.seconds == 0 && this.nanos == 0;
    }
    
    public int getScale() {
        return this.scale;
    }
    
    public void setScale(final int scale) {
        this.scale = scale;
    }
}
