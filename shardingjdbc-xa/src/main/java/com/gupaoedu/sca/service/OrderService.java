package com.gupaoedu.sca.service;

import com.gupaoedu.sca.dao.OrderDAO;
import com.gupaoedu.sca.dao.UserDemoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private UserDemoDAO userDemoDAO;



    public void insertOne(int userId) {

        orderDAO.insertUserOne(userId);

    }

    public void insertTwo(int userId) {
//        int a = 1/0;
        orderDAO.insertUserTwo(userId);

    }

    public void insertUser(String name) {

        userDemoDAO.insertUserDemo(name);

    }


}
