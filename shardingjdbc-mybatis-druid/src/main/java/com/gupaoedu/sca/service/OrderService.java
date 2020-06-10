package com.gupaoedu.sca.service;

import com.gupaoedu.sca.dao.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;


    public void insertOne(int userId) {

        orderDAO.insertUserOne(userId);

    }

    public void insertTwo(int userId) {
//        int a = 1/0;
        orderDAO.insertUserTwo(userId);

    }


}
