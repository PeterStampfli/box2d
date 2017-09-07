package com.mygdx.game.physics;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.Sprite.ExtensibleSpriteBuilder;
import com.mygdx.game.Sprite.SpriteActions;
import com.mygdx.game.utilities.Device;

/**
 * Extension of extensibleSpriteBuilder to make physicalSprites that use a box2D body for moving.
 * The setter methods of the ExtensibleSpriteBuilder can still be used and chained,
 * but they return an extensibleSpriteBuilder.
 * Build methods give ExtensibleSprites. BuildPhysical methods give physical sprites.
 * NEEDS the BOX2D EXTENSION.
 */

public class PhysicalSpriteBuilder extends ExtensibleSpriteBuilder {
    Physics physics;
    public PhysicalSpriteActions physicalSpriteActions;
    public BodyDef.BodyType masterBodyType;
    private MouseJointMover mouseJointMover;
    private KinematicTranslate kinematicTranslate;
    public PhysicalSpritePrepareTimeStep spritePrepareTimeStep;

    /**
     * Create the builder with a device that has a glyphLayout pool.
     * Set minimal default actions.
     *
     * @param device Device, with pools.
     */
    public PhysicalSpriteBuilder(Device device,Physics physics){
        super(device);
        this.physics=physics;
        physicalSpriteActions=new PhysicalSpriteActions();
        setContains(PhysicalSpriteActions.bodyContains);
        reset();
    }

    /**
     * reset, including bodyType=dynamic body
     */
    @Override
    public void reset(){
        super.reset();
        setBodyType(BodyDef.BodyType.DynamicBody);
        setPhysicalSpriteStep(PhysicalSpriteActions.spriteUpdateNull);
    }


    /**
     * set the body type for future sprites
     *
     * @param bodyType BodyDef.BodyType, static, dynamic or kinematic
     */
    public void setBodyType(BodyDef.BodyType bodyType){
        this.masterBodyType=bodyType;
    }

    /**
     * set the object with the update routine for preparing steps
     *
     * @param spriteUpdate PhysicalSpriteUpdate
     */
    public void setPhysicalSpriteStep(PhysicalSpritePrepareTimeStep spriteUpdate){
        spritePrepareTimeStep =spriteUpdate;
    }
    /**
     * Use the mouseJoint to move the sprite. Use the bodies shapes for contains method.
     * Sets the basic methods for physical sprite.
     *
     * @param useStaticBodies boolean, true for making non-moving bodies static
     */
    public void setMouseJointMover(boolean useStaticBodies){
        if (mouseJointMover==null){
            mouseJointMover=new MouseJointMover();
        }
        mouseJointMover.useStaticBodies=useStaticBodies;
        if (useStaticBodies){
            setBodyType(BodyDef.BodyType.StaticBody);
        }
        else {
            setBodyType(BodyDef.BodyType.DynamicBody);
        }
        setContains(PhysicalSpriteActions.bodyContains);
        setKeepVisible(SpriteActions.keepVisibleNull);
        setDraw(SpriteActions.draw);
        setTouchBegin(mouseJointMover);
        setTouchDrag(mouseJointMover);
        setTouchEnd(mouseJointMover);
        setScroll(SpriteActions.scrollNull);
        setPhysicalSpriteStep(PhysicalSpriteActions.spriteUpdateNull);
    }

    /**
     * Use a kinematic body to move the sprite, and a kinematicTranslate instance
     */
    public void setKinematicTranslate(){
        if (kinematicTranslate==null){
            kinematicTranslate=new KinematicTranslate();
        }
        setBodyType(BodyDef.BodyType.KinematicBody);
        setContains(PhysicalSpriteActions.bodyContains);
        setKeepVisible(SpriteActions.keepVisibleNull);
        setDraw(SpriteActions.draw);
        setTouchBegin(kinematicTranslate);
        setTouchDrag(kinematicTranslate);
        setTouchEnd(kinematicTranslate);
        setScroll(SpriteActions.scrollNull);
        setPhysicalSpriteStep(kinematicTranslate);
    }

    /**
     * Get a physical sprite from the sprite pool. Set image and other data.
     * Attach a body and create its fixtures from shape.
     * Set local origin of the sprite from center of mass of the body.
     * Position and angle of sprite results from body (BodyBuilder).
     * If body has mouseJointMover then make it static.
     *
     * Note: We can't set the position of the origin without knowing the center of
     * mass of the body. Thus we have to create the body before setting its position.
     *
     * @param textureRegion TextureRegion, the sprites image
     * @param shape Shape2D shape for the sprite and the body fixtures.
     * @param body Body, will get masterBodyType
     * @return PhysicalSprite
     */
    public PhysicalSprite buildPhysical(TextureRegion textureRegion, Shape2D shape, Body body){
        PhysicalSprite sprite=physics.physicalSpritePool.obtain();
        setup(sprite,textureRegion,shape);                         // ExtensibleSprite
        sprite.physics=physics;
        sprite.spriteUpdate = spritePrepareTimeStep;
        sprite.body=body;
        body.setUserData(sprite);
        body.setType(BodyDef.BodyType.DynamicBody);              // dynamical body type to get its local origin
        physics.fixtureBuilder.setIsSensor(false).build(body,shape);
        sprite.setLocalOriginFromBody();
        sprite.readPositionAngleOfBody();
        sprite.interpolatePositionAngleOfBody(1);
        body.setType(masterBodyType);
        return sprite;
    }

    /**
     * Get a physical sprite from the sprite pool. Set image and other data.
     * Create a body with current settings of physics.bodyBuilder.
     * Attach the body to the sprite and create its fixtures from shape.
     * Set local origin of the sprite from center of mass of the body.
     * Position and angle of sprite and body will be set afterwards.
     *
     * Note: We can't set the position of the origin without knowing the center of
     * mass of the body. Thus we have to create the body before setting its position.
     *
     * @param textureRegion TextureRegion, the sprites image
     * @param shape Shape2D shape for the sprite and the body.
     * @return PhysicalSprite
     */
    public PhysicalSprite buildPhysical(TextureRegion textureRegion, Shape2D shape){
        Body body=physics.bodyBuilder.buildDynamicalBody(null);
        return buildPhysical(textureRegion,shape,body);
    }
}