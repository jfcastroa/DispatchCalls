package com.almundo;

public class ClientDSP {
   public static Dispatcher disp ;
    static int  numberCalls=10;


    public static void main(String args[]) {

        for(int i=0; i < numberCalls ; i++ )
        {
           // new PrintCall("call"+i,"operador");
            disp = new Dispatcher("Threadcall"+i);

            Call call= new Call("call"+i);
            disp.register(call);
            disp.start();
        }


    }
}
