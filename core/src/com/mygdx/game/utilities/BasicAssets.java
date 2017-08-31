package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
            if (soundMap.get(name)==null){
                soundMap.put(name, assetManager.get(name + "." + soundFileType, Sound.class));
            }
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
            if (musicMap.get(name)==null){
                musicMap.put(name, assetManager.get(name + "." + musicFileType, Music.class));
            }
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
     * Get the texture atlases and extract the textureAtlas.AtlasRegions (extend texture region).
     * Single images in the atlas (someImage.png) result in a single AtlasRegion (region.name=someImage and region.index=-1).
     * Multiple images (animation_01.png, animation_02.png, ...) give an Array of AtlasRegions named animation.
     * This is because animation_01.png gives region_name=animation, region_index=01 in the atlas.
     */
    public void getAllAtlases() {
        TextureAtlas atlas;
        Array<TextureAtlas.AtlasRegion> newRegions;
        String regionName;
        ObjectMap.Keys<String> atlasNames = atlasMap.keys();
        for (String atlasName : atlasNames) {
            if (atlasMap.get(atlasName)==null) {
                atlas = assetManager.get(atlasName + ".atlas", TextureAtlas.class);
                atlasMap.put(atlasName,atlas);
                // do the regions of each atlas
                newRegions = atlas.getRegions();
                for (TextureAtlas.AtlasRegion newRegion : newRegions) {
                    regionName = newRegion.name;
                    if (newRegion.index == -1) {  // a single image of given name
                        atlasRegionMap.put(regionName, newRegion);
                    } else {                     // multiple images
                        if (!atlasRegionArrayMap.containsKey(regionName)) {  // if not already done, create atlasRegionArray
                            atlasRegionArrayMap.put(regionName, atlas.findRegions(regionName));
                        }
                    }
                }
            }
        }
    }

    /**
     * get the entire TextureAtlas of a given name (for particle effects)
     *
     * @param name String name of the atlas
     * @return TextureAtlas
     */
    public TextureAtlas getAtlas(String name) {
        return atlasMap.get(name);
    }

    /**
     * Get the atlas region of given name with a single image. AtlasRegion extends TextureRegion.
     *
     * @param name String, name of the region.
     * @return the AtlasRegion
     */
    public TextureAtlas.AtlasRegion getAtlasRegion(String name) {
        return atlasRegionMap.get(name);
    }


    /**
     * Get atlas region of a multiple image in the array of given name and index (for still images).
     *
     * @param name String, the name of the image
     * @param index int, index of the image
     * @return the TextureAtlas.AtlasRegion
     */
    public TextureAtlas.AtlasRegion getAtlasRegion(String name, int index) {
        return atlasRegionArrayMap.get(name).get(index);
    }

    /**
     * For multiple images get the atlas region array of given name (for animation).
     *
     * @param name String, name of the regions
     * @return Array of TextureAtlas.AtlasRegion
     */
    public Array<TextureAtlas.AtlasRegion> getAtlasRegionArray(String name) {
        return atlasRegionArrayMap.get(name);
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
     * @return the textureRegion or null (if nothing found)
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
     * Load a font. There has to be a *.fnt file for the metrics and composition.
     * The glyph images can be in an atlas.
     *
     * @param name String, name of the font.
     */
    public void loadBitmapFont(String name) {
        assetManager.load(name, BitmapFont.class);
    }

    /**
     * Load a tiledMap. Ads the TmxMapLoader to the assetManager.
     *
     * @param name String, name of the map
     */
    public void loadTmxMap(String name) {
        if (assetManager.getLoader(TiledMap.class) == null) {
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        }
        assetManager.load(name, TiledMap.class);
    }
}
