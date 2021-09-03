package com.example.findusersservice.utils;

import com.example.findusersservice.models.User;

public class TestFixtures {

    public static final User stubUserJeff = new User(
            1,
            "Jeff",
            "GoldBlum",
            "jeffgold@hotmail.com",
            "111.11.111.111",
            1.123,
            1.456
    );

    public static final User stubUserBill = new User(
            2,
            "Bill",
            "Murray",
            "billmurr@gmail.com",
            "222.22.222.222",
            2.123,
            2.456
    );

}
