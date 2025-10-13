package dev.aston.fill;

import java.util.ArrayList;
import java.util.List;

public class InitCollection {

    List <Object> list = new ArrayList<>();

    public List <Object>  getList() {
        return list;
    }

    public void print(List list){
        list.forEach(System.out::println);
    }
}
