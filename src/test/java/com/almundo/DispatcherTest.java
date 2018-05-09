package com.almundo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
class DispatcherTest {





    @org.junit.jupiter.api.Test
    public void getCallHandler() {
        Dispatcher disp= new Dispatcher("Disp1");
        Call call= new Call("calltest");
        Employee emp = disp.getCallHandler(call);
        assertNotNull(emp);
    }

    @org.junit.jupiter.api.Test
    void register() {
        Dispatcher disp= new Dispatcher("Disp1");
        Call call= new Call("calltest");
        disp.register(call);
        assertEquals(1,disp.listCalls.size(),"Adding 1 more call" );

    }


}