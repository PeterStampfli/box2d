package com.mygdx.game.Pieces;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilities.TouchReader;

/**
 * Created by peter on 3/29/17.
 */

public class TouchMove extends InputAdapter{

    public Touchable piece;
    public TouchReader touchReader;
    public Camera camera;
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
     * @param camera
     */
    public TouchMove(Touchable piece, TouchReader touchReader, Camera camera) {
        this.piece = piece;
        this.touchReader = touchReader;
        this.camera=camera;
        oldTouchPosition = new Vector2();
        newTouchPosition = new Vector2();
        touchPosition = new Vector2();
        deltaTouchPosition = new Vector2();
    }

    /**
     * create
     *
     * @param piece       or collection of pieces,its rendering, selection and moving methods get called,
     * @param touchReader  for reading touch touchPosition, depends on camera of viewport
     * @param viewport
     */
    public TouchMove(Touchable piece, TouchReader touchReader, Viewport viewport) {
        this(piece,touchReader,viewport.getCamera());
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
     *
     * returns true if something changed and redraw needed
     */
    public boolean update() {
        boolean isTouching = touchReader.isTouching();
        somethingChanged=false;
        if (!isTouching&&!wasTouching){
            return false;                                     // totally out of touch
        }
        // update touch positions
        if (isTouching&&!wasTouching){                                     // new touch - oldPosition is invalid
            newTouchPosition.set(touchReader.getPosition(camera));
            oldTouchPosition.set(newTouchPosition);
        }
        else if (isTouching&&wasTouching){                         // continue touching
            oldTouchPosition.set(newTouchPosition);
            newTouchPosition.set(touchReader.getPosition(camera));
        }
        else {                     // !isTouching&&wasTouching   - end of touch, take last defined positions
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
                if (!deltaTouchPosition.isZero()) {
                    somethingChanged = piece.touchDrag(touchPosition, deltaTouchPosition);
                }
            }
            else {                     // !isTouching&&wasTouching
                somethingChanged = piece.touchEnd(touchPosition);
                pieceIsSelected = false;
            }
        }
        wasTouching = isTouching;
        return somethingChanged;
    }

    /**
     * set this as inputprocessor (or part of multiplexer) to be able to use scroll wheel
     */
    public void asInputProcessor(){
        Gdx.input.setInputProcessor(this);
    }

    /**
     * scroll draw only by event handling
     * call scroll draw on the piece, together with mouse position
     * Note: scroll occurs only on PC, where mouse position is always defined
     * @param amount
     * @return
     */
    @Override
    public boolean scrolled (int amount) {
        piece.scroll(touchReader.getPosition(camera),amount);
        return false;
    }

}
