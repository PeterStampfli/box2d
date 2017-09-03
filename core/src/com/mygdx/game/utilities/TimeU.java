package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Measure elapsed time
 */

public class TimeU {
    private static float timeOfLastFrame = 10;

    /**
     * Get the absolute time in seconds
     *
     * @return float, time in seconds
     */
    public static float getTime() {
        return MathUtils.nanoToSec * TimeUtils.nanoTime();
    }

    /**
     * Get time passed since an earlier moment.
     *
     * @param time of earlier moment in seconds
     * @return float, time passed in seconds
     */
    public static float getTimeSince(float time) {
        return MathUtils.nanoToSec * TimeUtils.nanoTime() - time;
    }

    /**
     * Get the smoothed time interval between render calls
     *
     * @return float, time interval in seconds
     */
    public static float getAverageDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    /**
     * Get true measured time interval between current render call and previous render call.
     *
     * @return float, time interval in seconds
     */
    public static float getTrueDeltaTime() {
        return Gdx.graphics.getRawDeltaTime();
    }

    /**
     * Check if a time passed is smaller than a given value in seconds. Adds up the time between
     * render calls. If the total is larger than a limit, then returns true and resets the accumulated time to zero.
     * Use for debugging and for reducing the frame drawing rate.
     *
     * @param timeLimit in seconds
     * @return boolean, false if accumulated time is smaller than the time limit.
     */
    public static boolean timeSinceLastFrameIsLargerThan(float timeLimit) {
        timeOfLastFrame += getTrueDeltaTime();
        if (timeOfLastFrame >= timeLimit) {
            timeOfLastFrame = 0;
            return true;
        }
        return false;
    }
}
