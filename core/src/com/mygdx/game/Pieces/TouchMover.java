package com.mygdx.game.Pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utilities.Device;

/**
 * Poll touch events and transmit to touchable objects. Touchable piece is typically a TouchableCollection.
 */

public class TouchMover extends InputAdapter {
    private Touchable touchable;
    public Device device;
    //  touchPosition of touch as average (dragging) and displacement
    public Vector2 touchPosition=new Vector2();
    public Vector2 deltaTouchPosition=new Vector2();
    //  positions of last touch and new touch
    private Vector2 oldTouchPosition=new Vector2();
    private Vector2 newTouchPosition=new Vector2();
    public Vector2 scrollPosition=new Vector2();
    private boolean isSelected = false;                                 // piece is really selected
    // if there was touch in previous call, we need this because we do polling
    private boolean wasTouching = false;

    /**
     * Create a TouchMove controller with its touch reader
     * @param device with camera and touch reader
     */
    public TouchMover(Device device) {
        this.device=device;
    }

    /**
     * set the touchable that will be controlled by the touchMover.
     * Sets wasTouching to false as a reset and because this touchable most probably has not been touched at call.
     * Call this method in the show() method of screens.
     *
     * @param touchable Touchable object, root for objects to move
     */
    public void setTouchable(Touchable touchable) {
        this.touchable = touchable;
        wasTouching=false;
    }

    /**
     * Set the average touch position and change in touch position.
     */
    private void updateTouchPosition() {
        touchPosition.set(oldTouchPosition).add(newTouchPosition).scl(0.5f);
        deltaTouchPosition.set(newTouchPosition).sub(oldTouchPosition);
    }

    /**
     * Poll the touch data. Set touch position and change in touch. Have them defined at all times.
     * At touch down see if a touchable is selected. Put it in front. Apply touch begin, drag and
     * end commands on this object.
     *
     */
    public void update() {
        boolean isTouching = device.touchReader.isTouching();
        // update touch positions
        if (isTouching){
            if (wasTouching) {
                oldTouchPosition.set(newTouchPosition);       // continue touching
                device.touchReader.getPosition(newTouchPosition);
            }
            else {
                device.touchReader.getPosition(newTouchPosition);  // new touch - oldPosition is invalid
                oldTouchPosition.set(newTouchPosition);
            }
        }
        else {
            if (wasTouching){
                // !isTouching&&wasTouching   - end of touch, take last defined positions
                oldTouchPosition.set(newTouchPosition);
            }
            else {
                return;                                     // totally out of touch
            }
        }
        // relevant touchPosition is average, and get change in touchPosition
        touchPosition.set(oldTouchPosition).add(newTouchPosition).scl(0.5f);
        deltaTouchPosition.set(newTouchPosition).sub(oldTouchPosition);
        // for new touch see if a touchable piece has been selected
        if (isTouching && !wasTouching) {
            isSelected = touchable.contains(touchPosition);
        }
        // act
        if (isSelected) {
            if (isTouching && !wasTouching) {
                touchable.touchBegin(touchPosition);
            } else if (isTouching && wasTouching) {
                if (!deltaTouchPosition.isZero()) {
                    touchable.touchDrag(touchPosition, deltaTouchPosition);
                }
            } else {                     // !isTouching&&wasTouching
                touchable.touchEnd();
                isSelected = false;
            }
        }
        wasTouching = isTouching;
    }

    /**
     * Set this as the inputProcessor (or part of multiplexer) to be able to use scroll wheel.
     */
    public void asInputProcessor() {
        Gdx.input.setInputProcessor(this);
    }

    /**
     * Scroll the element that contains the current mouse position.
     * Note: scroll occurs only on PC, where touch (mouse) position is always defined.
     *
     * @param amount int, tells if scrolling up or down
     * @return boolean, true if input was processed
     */
    @Override
    public boolean scrolled(int amount) {
        device.touchReader.getPosition(scrollPosition);
        touchable.scroll(scrollPosition, amount);
        return true;
    }

}
