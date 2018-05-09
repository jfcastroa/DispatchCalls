package com.almundo;

import java.util.*;
import java.util.concurrent.TimeUnit;


class NotFound extends Exception {}
public class Dispatcher extends Thread  {


    List<Call> listCalls = new ArrayList<Call>();
    static final int LEVELS = 3; // we have 3 levels of employees
    Queue<Call>[] callQueues = new LinkedList[LEVELS];
    static final int NUM_OPERATORS = 5; // we have 5 Operators
    ArrayList<Employee>[] employeeLevels = new ArrayList[LEVELS];
    private Thread t;
    private String threadName;
    ArrayList<Employee> operators = new ArrayList(NUM_OPERATORS);
    ArrayList<Employee> tls = new ArrayList(1);
    ArrayList<Employee> pms = new ArrayList(1);
    int timeCall=0;

     public  Dispatcher(String name) {

        threadName = name;
        //System.out.println("Creating " +  threadName );

        // create operators
        if(operators.isEmpty())
        for (int k = 0; k < NUM_OPERATORS - 1; k++) {
            operators.add(new Operator());
        }
        employeeLevels[0] = operators;

        // create supervisor
        if(tls.isEmpty())
        tls.add(new Supervisor()); // we have 1 supervisor
        employeeLevels[1] = tls;

        // create director
        if(tls.isEmpty())
        pms.add(new Director()); // we have 1 director
        employeeLevels[1] = pms;

    }


     Employee getCallHandler(Call call) {
        for (int level = call.rank; level < LEVELS - 1; level++) {
            ArrayList<Employee> employeeLevel = employeeLevels[level];
            for (Employee emp : employeeLevel) {
                if (emp.free) {
                    return emp;
                }
            }
        }
        return null;
    }
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public synchronized void  register(Call obj) {

        timeCall=getRandomNumberInRange(5,10);
        obj.timeCompleted=timeCall;
        listCalls.add(obj);
    }
    public void run() {
        //System.out.println("Running " +  threadName );
            for (Call i:listCalls
                    ) {

                dispatchCall(i);
                listCalls.remove(i);

                //System.out.println("Thread: " + threadName + ", Client" + i.nameOfClient);
                try {

                    TimeUnit.SECONDS.sleep(timeCall);
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        //System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        //System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }

    // routes the call to an available employee, or saves in a queue
    // if no employee available
    synchronized void   dispatchCall(Call call) {
        // try to route the call to an employee with minimal rank
        Employee emp = getCallHandler(call);
        //System.out.println("Emp free?: " +  emp.free);
        if (emp != null) {
            emp.ReceiveCall(call);
            System.out.println("Call: " +  call.nameOfClient + " Attended by: "+ emp.rank);
            emp.CallHandled(call);

        } else {

                callQueues[call.rank].add(call);
            this.getNextCall(emp);



        }
    }


    void getNextCall(Employee emp) {
        // check the queues, starting from the highest rank this
        // employee can serve
        for (int rank = emp.rank; rank >= 0; rank--) {
            Queue<Call> que = callQueues[rank];
            Call call = que.poll(); // remove the first call, if any
            if (call != null) {
                emp.ReceiveCall(call);
                return;
            }
        }
    }
}
