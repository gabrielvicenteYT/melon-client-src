package com.mysql.cj.protocol;

public class InternalDate
{
    protected int year;
    protected int month;
    protected int day;
    
    public InternalDate() {
        this.year = 0;
        this.month = 0;
        this.day = 0;
    }
    
    public InternalDate(final int year, final int month, final int day) {
        this.year = 0;
        this.month = 0;
        this.day = 0;
        this.year = year;
        this.month = month;
        this.day = day;
    }
    
    public int getYear() {
        return this.year;
    }
    
    public void setYear(final int year) {
        this.year = year;
    }
    
    public int getMonth() {
        return this.month;
    }
    
    public void setMonth(final int month) {
        this.month = month;
    }
    
    public int getDay() {
        return this.day;
    }
    
    public void setDay(final int day) {
        this.day = day;
    }
    
    public boolean isZero() {
        return this.year == 0 && this.month == 0 && this.day == 0;
    }
}
