package dev.aston.fill;

import java.util.ArrayList;
import java.util.List;

public class InitCollection {

    List <Object> list = new ArrayList<>();

    public List <Object>  getList() {
        return list;
    }

    public List<Object> setList(List<Object> list) {
        this.list = list;
        return this.list;
    }

    public void print(List list){
        list.forEach(System.out::println);
    }
}
