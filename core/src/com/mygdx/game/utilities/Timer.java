package com.mygdx.game.utilities;

/**
 * A timer that updates and acts explicitly in the render method ...
 * Will not interfere with box2d physics step
 */

public class Timer {
    Runnable event;
    float timeSinceLastAction;
    float initialDelay;
    public boolean starting;
    float timeInterval;
    int remainingEvents=0;

    /**
     * create a new timer, no events happening, set the event method
     *
     * @param event Runnable, the run method does the timer action
     */
    public Timer(Runnable event){
        setEvent(event);
    }

    /**
     * create a new timer, no events
     */
    public Timer(){
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
     * start the timer with new initial delay, time interval and number of events
     *
     * @param initialDelay float, time between start and first event
     * @param timeInterval float, time between events in sec
     * @param numberOfEvents int, number of events
     */
    public void start(float initialDelay,float timeInterval,int numberOfEvents){
        timeSinceLastAction=0;
        starting=true;
        this.initialDelay=initialDelay;
        this.timeInterval =timeInterval;
        remainingEvents=numberOfEvents;
    }

    /**
     * start the timer with time interval and number of events
     *
     * @param timeInterval float, time between events in sec
     * @param numberOfEvents int, number of events
     */
    public void start(float timeInterval,int numberOfEvents){
        start(timeInterval,timeInterval,numberOfEvents);
    }

    /**
     * make a single delayed event
     *
     * @param initialDelay float, time delay for event
     */
    public void singleEvent(float initialDelay){
        start(initialDelay,10000,1);
    }

    /**
     * repeat events forever
     *
     * @param initialDelay float, time between start and first event
     * @param timeInterval float, time delay for event
     */
    public void repeatForever(float initialDelay,float timeInterval){
        start(initialDelay,timeInterval,Integer.MAX_VALUE);
    }

    /**
     * stop the timer. cancel events
     */
    public void stop(){
        remainingEvents=0;
    }

    /**
     * make an event and update timer data
     */
    public void runEvent(){
        timeSinceLastAction=0;
        remainingEvents--;
        event.run();
    }

    /**
     * call in render-loop to advance time and do something
     */
    public void update(){
        if (remainingEvents>0) {
            timeSinceLastAction += TimeU.getTrueDeltaTime();
            if (starting){
                if (timeSinceLastAction>initialDelay){
                    starting=false;
                    runEvent();
                }
            }
            else if (timeSinceLastAction> timeInterval){
                runEvent();
            }
        }
    }
}
