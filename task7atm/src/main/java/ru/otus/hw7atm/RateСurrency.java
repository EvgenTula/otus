package ru.otus.hw7atm;

public enum RateСurrency {

    ONE(1),
    FIVE(5),
    TEN(10),
    HOUNDRED(100),
    FIVEHOUNDRED(500),
    THOUSAND(1000),
    FIVETHOUSAND(5000);

    private int _val;

    RateСurrency(int val) {
        this._val = val;
    }

    public int getVal() {
        return this._val;
    }
}
