package com.almundo;

 class Call  {
    String nameOfClient; // name of  client
    int timeCompleted;
    int rank = 0;

    public void reply(String message) {
        // play recorded message to the customer
    }

    public void disconnect() {
        reply("Thank you for calling");
    }

    public  Call(String nc) {
        nameOfClient = nc;


        ClientDSP.disp.register(this);
    }

    public int gettimeCompleted() {
        return timeCompleted;
    }


    public int compareTo(Call comparcall) {
        int comparetime=((Call)comparcall).gettimeCompleted();
        /* For Ascending order*/
        //return this.timeCompleted-comparetime;

        /* For Descending order do like this */
        return comparetime-this.timeCompleted;
    }

    //abstract public void call(); // call provided

}
