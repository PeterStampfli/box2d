package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Simplify using the asset manager
 */

public class BasicAssets {
    public Device device;
    public AssetManager assetManager;
    public String soundFileType = "wav";
    public String musicFileType = "mp3";                                // or "ogg"
    private ObjectMap<String, Sound> soundMap = new ObjectMap<String, Sound>();
    private ObjectMap<String, Music> musicMap = new ObjectMap<String, Music>();
    private ObjectMap<String, TextureAtlas.AtlasRegion> atlasRegionMap = new ObjectMap<String, TextureAtlas.AtlasRegion>();
    private ObjectMap<String, Array<TextureAtlas.AtlasRegion>> atlasRegionArrayMap = new ObjectMap<String, Array<TextureAtlas.AtlasRegion>>();
    private ObjectMap<String, TextureAtlas> atlasMap = new ObjectMap<String, TextureAtlas>();

    /**
     * Create basic assets with the assetManager and disposer of device.
     *
     * @param device holds the assetManager and the disposer
     */
    public BasicAssets(Device device) {
        this.device = device;
        this.assetManager = device.assetManager;
    }

    /**
     * Stop everything and wait until the assetManager has finished loading.
     */
    public void finishLoading() {
        assetManager.finishLoading();
    }

    /**
     * Kick the assetManager to load more resources and check if it has finished.
     *
     * @return boolean, true if everything has been loaded
     */
    public boolean update() {
        return assetManager.update();
    }

    /**
     * Get progress as a fraction of completion.
     *
     * @return float progress, going from 0 to 1.
     */
    public float getProgress() {
        return assetManager.getProgress();
    }

    // get loaded asset with: assetManager.get(String filename);
    // remove asset with: assetManager.unload(String filename);

    // sounds

    /**
     * Load one or more sounds. Names without extensions.
     * Assuming that the resource files have extension given by soundFileType
     * (default is "wav, can be changed).
     *
     * @param names String... or String[] of sound names
     */
    public void loadSounds(String... names) {
        for (String name : names) {
            assetManager.load(name + "." + soundFileType, Sound.class);
            soundMap.put(name, null);
        }
    }

    /**
     * Get all sounds and put them in the sound map.
     * call after assetManager has finished loading and before using sounds.
     */
    public void getAllSounds() {
        ObjectMap.Keys<String> names = soundMap.keys();
        for (String name : names) {
            soundMap.put(name, assetManager.get(name + "." + soundFileType, Sound.class));
        }
    }

    /**
     * get a sound by its name without extension. Call after getAllSounds.
     *
     * @param name String, the name of the sound
     * @return Sound
     */
    public Sound getSound(String name) {
        return soundMap.get(name);
    }

    /**
     * Unloads (deletes) a sound given by its name without extension. Removes it from the sound map.
     *
     * @param name String, the name of the sound
     */
    public void unloadSound(String name) {
        soundMap.remove(name);
        assetManager.unload(name + "." + soundFileType);
    }

    // musics

    /**
     * Loads one or more musics with their name, no extension.
     * Assumes that they all have the same type extension musicFileType (can be changed)
     *
     * @param names String... or String[] of music names
     */
    public void loadMusics(String... names) {
        for (String name : names) {
            assetManager.load(name + "." + musicFileType, Music.class);
            musicMap.put(name, null);
        }
    }

    /**
     * Get all loaded musics and put them in a map.
     */
    public void getAllMusics() {
        ObjectMap.Keys<String> names = musicMap.keys();
        for (String name : names) {
            musicMap.put(name, assetManager.get(name + "." + musicFileType, Music.class));
        }
    }

    /**
     * Get a music by its name without extension.
     *
     * @param name String, for the music.
     * @return Music
     */
    public Music getMusic(String name) {
        return musicMap.get(name);
    }

    /**
     * Unloads (deletes) a music given by its name without extension. Removes it from the music map.
     *
     * @param name String, the name of the music
     */
    public void unloadMusic(String name) {
        musicMap.remove(name);
        assetManager.unload(name + "." + musicFileType);
    }

    // textureAtlases

    /**
     * Load texture atlases by name only, requires name.png and name.atlas files.
     *
     * @param names String... or String[] of names of atlases
     */
    public void loadAtlases(String... names) {
        for (String name : names) {
            assetManager.load(name + ".atlas", TextureAtlas.class);
            atlasMap.put(name, null);
        }
    }

    /**
     * get texture atlases and extract the texture regions (extends texture region) as
     * either simple atlas region or array of atlas regions (for animations)
     * <p>
     * multiple images format example: animation_01.png gives region_name=animation, region_index=01
     * this will give an Array<AtlasRegion> named animation
     * others: someImage.png give region_name=someImage, region_index=-1
     */
    public void getAtlases() {
        TextureAtlas atlas;
        Array<TextureAtlas.AtlasRegion> newRegions;
        String regionName;
        ObjectMap.Keys<String> atlasNames = atlasMap.keys();
        for (String atlasName : atlasNames) {
            atlas = assetManager.get(atlasName + ".atlas", TextureAtlas.class);
            // do the regions of each atlas
            newRegions = atlas.getRegions();
            for (TextureAtlas.AtlasRegion newRegion : newRegions) {
                regionName = newRegion.name;
                if (newRegion.index == -1) {  // a single masterTextureRegion of given name
                    atlasRegionMap.put(regionName, newRegion);
                } else {                     // multiple images
                    if (!atlasRegionArrayMap.containsKey(regionName)) {  // if not already done, create atlasRegionArray
                        atlasRegionArrayMap.put(regionName, atlas.findRegions(regionName));
                    }
                }
            }
        }
    }

    /**
     * get the atlas region of given name, extends textureRegion, has more info than textureRegion
     *
     * @param name
     * @return
     */
    public TextureAtlas.AtlasRegion getAtlasRegion(String name) {
        return atlasRegionMap.get(name);
    }

    /**
     * To simplify debugging and production:
     * first tries to find an masterTextureRegion *.png or *.jpg and makes it a textureregion
     * if not found gets a textureRegion as part of an atlas,
     * Thus I do not need to have made the atlas already in debugging, can use instead simple images
     * later I can use an atlas without changing code details
     * I can override the masterTextureRegion in the atlas region with a simple masterTextureRegion
     *
     * @param name
     * @return
     */
    public TextureRegion getTextureRegion(String name) {
        TextureRegion result;
        FileHandle fileHandle;
        fileHandle = Gdx.files.internal(name + ".png");                    // try to find as a png
        if (!fileHandle.exists()) {
            fileHandle = Gdx.files.internal(name + ".jpg");           // if not found try jpg
        }
        if (fileHandle.exists()) {
            Texture texture = new Texture(fileHandle);
            device.disposer.add(texture, name + " texture");
            result = new TextureRegion(texture);        // if found load the masterTextureRegion as texture->textureRegion
        } else {
            result = getAtlasRegion(name);                        // if not found look for atlas region
        }
        return result;
    }

    /**
     * get atlas region array of given name (for animation)
     *
     * @param name
     * @return
     */
    public Array<TextureAtlas.AtlasRegion> getAtlasRegionArray(String name) {
        return atlasRegionArrayMap.get(name);
    }

    /**
     * get atlas region in an array of given name and index (for still images)
     *
     * @param name
     * @param index
     * @return
     */
    public TextureAtlas.AtlasRegion getAtlasRegionArrayElement(String name, int index) {
        return atlasRegionArrayMap.get(name).get(index);
    }

    /**
     * get the entire TextureAtlas of a given name (for particle effects)
     *
     * @param name
     * @return
     */
    // for particle effects
    public TextureAtlas getAtlas(String name) {
        return atlasMap.get(name);
    }


    /**
     * a loader for fonts, using a png and a fnt file.
     * Better use a texture region instead of a png-file that makes its own texture.
     *
     * @param name
     */
    public void loadBitmapFont(String name) {
        assetManager.load(name, BitmapFont.class);
    }

    /**
     * load a tiledMap with extension tmx (?)
     * Ads the TmxMapLoader to the assetmanager
     *
     * @param name
     */
    public void loadTmxMap(String name) {
        if (assetManager.getLoader(TiledMap.class) == null) {
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        }
        assetManager.load(name, TiledMap.class);
    }

    /**
     * create an animation using an AtlasRegionArray
     *
     * @param frameDuration
     * @param name
     * @param playMode
     * @return
     */
    public Animation createAnimation(float frameDuration, String name, Animation.PlayMode playMode) {
        return new Animation(frameDuration, getAtlasRegionArray(name), playMode);
    }

    /**
     * create a particle effect
     *
     * @param effectName name of the effect file, uses file type extension p
     * @param atlasName  name of atlas with the images
     * @return
     */
    public ParticleEffect createParticleEffect(String effectName, String atlasName) {
        ParticleEffect particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal(effectName + ".p"), getAtlas(atlasName));
        return particleEffect;
    }
}
