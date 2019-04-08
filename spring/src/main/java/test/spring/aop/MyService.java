package com.test;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    public void throw1() throws DataAccessException {
        throw new MyDataAccessException("test");
    }

    public void throw2()  {
        throw new NullPointerException();
    }
    
    public People sayHello(String str) {
        String name = "Hello, " + str;
        People p = new People(name);
        System.out.println(p.getName());
        System.out.println("obj in Service:" + p + " hascode:" + p.hashCode());
        int i = 1/0;
        return p;
        //throw new RuntimeException("runtime error...");
    }

    static class MyDataAccessException extends DataAccessException {

        public MyDataAccessException(String msg) {
            super(msg);
        }

    }
}