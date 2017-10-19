package com.mygdx.game.utilities;

/**
 * A timer that updates and acts explicitly in the render method ...
 * Will not interfere with box2d physics step
 */

public class Timer {
    Runnable event;
    float timeSinceLastAction;
    float timeInterval;
    int remainingEvents;

    /**
     * create a new timer, everything is stopped
     *
     * @param event Runnable, the run method does the timer action
     */
    public Timer(Runnable event){
        stop();
        setEvent(event);
    }

    /**
     * set the Runnable event
     *
     * @param event Runnable, to action caused by the timer
     */
    public void setEvent(Runnable event) {
        this.event = event;
    }

    /**
     * start the timer with new time interval and number of events
     *
     * @param timeInterval float, time between events in sec
     * @param numberOfEvents int, number of events
     */
    public void start(float timeInterval,int numberOfEvents){
        timeSinceLastAction=0;
        this.timeInterval =timeInterval;
        remainingEvents=numberOfEvents;
    }

    /**
     * make a single delayed event
     *
     * @param timeInterval float, time delay for event
     */
    public void singleEvent(float timeInterval){
        start(timeInterval,1);
    }

    /**
     * repeat events forever
     *
     * @param timeInterval float, time delay for event
     */
    public void repeatForever(float timeInterval){
        start(timeInterval,Integer.MAX_VALUE);
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
            timeSinceLastAction += TimeU.getTrueDeltaTime();
            if (timeSinceLastAction> timeInterval){
                timeSinceLastAction=0;
                remainingEvents--;
                event.run();
            }
        }
    }
}
