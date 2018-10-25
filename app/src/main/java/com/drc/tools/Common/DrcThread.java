package com.drc.tools.Common;

public class DrcThread extends Thread implements Runnable {
    @Override
    public void run() {
        super.run();
        //todo
    }

    public void start(){
        super.start();
        new DrcThread().start();
    }
}
