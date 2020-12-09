package com.xin.dao;

import com.xin.model.Daily;

public interface DailyDao {
    Daily getDaily(String server);
    void insertDaily(Daily daily);
}
