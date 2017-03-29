package com.mygdx.game.Pieces;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utilities.TouchReader;

/**
 * Created by peter on 3/29/17.
 */

public class TouchMove {

    private Touchable piece;
    private TouchReader touchReader;
    private boolean pieceIsSelected=false;                                 // piece is really selected
    // if there was touch in previous call, we need this because we do polling
    private boolean wasTouching=false;
    //  positions of last touch and new touch
    private Vector2 oldTouchPosition;
    private Vector2 newTouchPosition;
    //  touchPosition of touch as average (dragging) and displacement
    public Vector2 touchPosition;
    public Vector2 deltaTouchPosition;
    public boolean somethingChanged=false;

    /**
     * create
     *
     * @param piece       or collection of pieces,its rendering, selection and moving methods get called,
     * @param touchReader  for reading touch touchPosition, depends on camera
     */
    public TouchMove(Touchable piece, TouchReader touchReader) {
        this.piece = piece;
        this.touchReader = touchReader;
        oldTouchPosition = new Vector2();
        newTouchPosition = new Vector2();
        touchPosition = new Vector2();
        deltaTouchPosition = new Vector2();
    }

    /**
     * set average touch position and change in touch
     */
    private void updateTouchPosition() {
        touchPosition.set(oldTouchPosition).add(newTouchPosition).scl(0.5f);
        deltaTouchPosition.set(newTouchPosition).sub(oldTouchPosition);
    }

    /**
     * update touch data and call events
     * make sure all touchPosition data is well defined, even when not used
     */
    public void update() {
        boolean isTouching = touchReader.isTouching();
        somethingChanged=false;
        if (!isTouching&&!wasTouching){
            return;                                     // totally out of touch
        }
        // update touch positions
        if (isTouching&&!wasTouching){                                     // new touch - oldPosition is invalid
            touchReader.getPosition(newTouchPosition);
            oldTouchPosition.set(newTouchPosition);
        }
        else if (isTouching&&wasTouching){                         // continue touching
            oldTouchPosition.set(newTouchPosition);
            touchReader.getPosition(newTouchPosition);
        }
        else {                     // !isTouching&&wasTouching   - end of touch, position undefined
            oldTouchPosition.set(newTouchPosition);
        }
        // relevant position is average, and get change in position
        touchPosition.set(oldTouchPosition).add(newTouchPosition).scl(0.5f);
        deltaTouchPosition.set(newTouchPosition).sub(oldTouchPosition);
        // for new touch see if a touchable piece has been selected
        if (isTouching&&!wasTouching){
            pieceIsSelected=piece.contains(touchPosition);
        }
        // act
        if (pieceIsSelected) {
            if (isTouching&&!wasTouching) {
                    somethingChanged = piece.touchBegin(touchPosition);
            }
            else if (isTouching&&wasTouching){
                somethingChanged = piece.touchDrag(touchPosition, deltaTouchPosition);
            }
            else {                     // !isTouching&&wasTouching
                somethingChanged = piece.touchEnd();
                pieceIsSelected = false;
            }
        }
        wasTouching = isTouching;
    }

}
