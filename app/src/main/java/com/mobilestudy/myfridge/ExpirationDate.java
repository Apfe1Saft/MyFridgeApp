package com.mobilestudy.myfridge;

import java.util.Date;

public class ExpirationDate {
    private Date startExpirationDate;
    private Date endExpirationDate;

    public ExpirationDate(Date start, Date end){
        this.endExpirationDate = end;
        this.startExpirationDate = start;
    }

    public ExpirationDate(){
    }

    public Date getEndExpirationDate() {
        return endExpirationDate;
    }

    public Date getStartExpirationDate() {
        return startExpirationDate;
    }

    public void setEndExpirationDate(Date endExpirationDate) {
        this.endExpirationDate = endExpirationDate;
    }

    public void setStartExpirationDate(Date startExpirationDate) {
        this.startExpirationDate = startExpirationDate;
    }
}
