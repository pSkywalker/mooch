package com.app.mooch.modals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpendingDay {

    private List<User> participatingUsers;
    private String mooch;
    private Date spendingDay;
    private List<SpendingItem> spendingItems;

    public SpendingDay( List<User> participatingUsers ){
        this.participatingUsers = participatingUsers;
        this.spendingDay = new Date();
        this.spendingItems = new ArrayList<>();
        this.mooch = null;
    }

    public Date getSpendingDay() {
        return spendingDay;
    }

    public void setSpendingDay(Date spendingDay) {
        this.spendingDay = spendingDay;
    }

    public List<SpendingItem> getSpendingItems() {
        return spendingItems;
    }

    public void addSpendingItem(SpendingItem spendingItem) {
        this.spendingItems.add(spendingItem);


        for( int x = 0; x < this.participatingUsers.size(); x++ ) {

        }
    }


    public class SpendingItem{
        private String userId;
        private double amount;

        public SpendingItem( String userId, double amount){
            this.userId = userId;
            this.amount = amount;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }

}
