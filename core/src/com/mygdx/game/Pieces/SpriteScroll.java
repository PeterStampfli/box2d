package com.mygdx.game.Pieces;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 4/19/17.
 * scroll, mouse et given position, by given amount. Only for pc.
 */

public interface SpriteScroll {
     boolean scroll(ExtensibleSprite sprite, Vector2 position, int amount);
}
