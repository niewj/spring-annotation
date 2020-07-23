package com.niewj.util;

import org.springframework.stereotype.Component;

@Component
public class Service1 {

    public Service2 getService2() {
        return service2;
    }

    public void setService2(Service2 service2) {
        this.service2 = service2;
    }

    private Service2 service2;

    public void doBusiness(){
        System.out.println("Service1.doBusiness");
        service2.doSth();
    }

}
