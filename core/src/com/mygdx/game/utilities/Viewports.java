package com.mygdx.game.utilities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by peter on 3/26/17.
 */

public class Viewports {

    /**
     * create an extended viewport, centered image
     *  resize: 		viewport.update(width, height);
     * @param minWidth
     * @param minHeight
     * @param camera
     * @return
     */
    static public Viewport createExtendViewport(float minWidth, float minHeight,OrthographicCamera camera){
        Viewport viewport=new ExtendViewport(minWidth,minHeight,camera);
        camera.setToOrtho(false,minWidth,minHeight);
        return viewport;
    }

    /**
     * create an extended viewport with its own orthographic camera, centered image
     *  resize: 		viewport.update(width, height);
     * @param minWidth
     * @param minHeight
     * @return
     */
    static public Viewport createExtendViewport(float minWidth, float minHeight){

        return createExtendViewport(minWidth, minHeight, new OrthographicCamera());
    }

    /**
     *  create an fit viewport with its own orthographic camera, centered image
     *  resize: 		viewport.update(width, height);
     * @param minWidth
     * @param minHeight
     * @param camera
     * @return
     */
    static public Viewport createFitViewport(float minWidth, float minHeight, OrthographicCamera camera){
        Viewport viewport=new FitViewport(minWidth,minHeight,camera);
        camera.setToOrtho(false,minWidth,minHeight);
        return viewport;
    }

    /**
     *  create an fit viewport with its own orthographic camera, centered image
     *  resize: 		viewport.update(width, height);
     * @param minWidth
     * @param minHeight
     * @return
     */
    static public Viewport createFitViewport(float minWidth, float minHeight){
        return createFitViewport(minWidth, minHeight,new OrthographicCamera());
    }

}
