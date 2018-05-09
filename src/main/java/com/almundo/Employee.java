package com.almundo;

class Employee {
    Dispatcher callHandler;
    int rank; // 0- operator, 1 - supervisor, 2 - operator
    boolean free=true;

    public Employee(int rank) {
        this.rank = rank;
    }

    // start the conversation
    void ReceiveCall(Call call) {
        free = false;
    }

    // the issue is resolved, finish the call
    void CallHandled(Call call) {
        call.disconnect();
        free = true;
        // look if there is a call waiting in queue
        //callHandler.getNextCall(this);
    }

    // the issue is not resolved, escalate the call
    void CannotHandle(Call call) {
        call.rank = rank + 1;
        callHandler.dispatchCall(call);
        free = true;
        // look if there is a call waiting in queue
        callHandler.getNextCall(this);
    }



}


class Operator extends Employee {
    public Operator() {
        super(0);
    }
}
class Supervisor extends Employee {
    public Supervisor() {
        super(1);
    }
}

class Director extends Employee {
    public Director() {
        super(2);
    }
}

