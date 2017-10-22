package com.mygdx.game.utilities;

/**
 * A timer that updates and acts explicitly in the render method ...
 * Will not interfere with box2d physics step
 */

public class Timer {
    Runnable event;
    float waitingTime;
    float initialDelay;
    public boolean starting;
    float timeInterval;
    int remainingEvents=0;

    /**
     * create a new timer, no events
     */
    public Timer(){
    }

    /**
     * start the timer with new initial delay, time interval and number of events
     *
     * @param event Runnable, does it
     * @param initialDelay float, time between start and first event
     * @param timeInterval float, time between events in sec
     * @param numberOfEvents int, number of events
     */
    public void start(Runnable event,float initialDelay,float timeInterval,int numberOfEvents){
        this.event=event;
        waitingTime=initialDelay;
        starting=true;
        this.initialDelay=initialDelay;
        this.timeInterval =timeInterval;
        remainingEvents=numberOfEvents;
    }

    /**
     * start the timer with time interval and number of events
     *
     * @param event Runnable, does it
     * @param timeInterval float, time between events in sec
     * @param numberOfEvents int, number of events
     */
    public void start(Runnable event,float timeInterval,int numberOfEvents){
        start(event,timeInterval,timeInterval,numberOfEvents);
    }

    /**
     * make a single delayed event
     *
     * @param event Runnable, does it
     * @param initialDelay float, time delay for event
     */
    public void singleEvent(Runnable event,float initialDelay){
        start(event,initialDelay,10000,1);
    }

    /**
     * repeat events forever
     *
     * @param event Runnable, does it
     * @param initialDelay float, time between start and first event
     * @param timeInterval float, time delay for event
     */
    public void repeatForever(Runnable event,float initialDelay,float timeInterval){
        start(event,initialDelay,timeInterval,Integer.MAX_VALUE);
    }

    /**
     * stop the timer. cancel events
     */
    public void stop(){
        remainingEvents=0;
    }

    /**
     * call in render-loop to advance time and do something
     */
    public void update(){
        if (remainingEvents>0) {
            waitingTime -= TimeU.getTrueDeltaTime();
            if (waitingTime<0){
                starting=false;
                waitingTime=timeInterval;
                remainingEvents--;
                event.run();
            }
        }
    }
}
