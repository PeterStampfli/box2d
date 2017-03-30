package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by peter on 1/22/17.
 */

public class BasicAssets {
    public AssetManager assetManager;
    public String soundFileType="wav";
    public String musicFileType="mp3";
    private ObjectMap<String,Sound> soundMap =new ObjectMap<String, Sound>();
    private ObjectMap<String,Music> musicMap =new ObjectMap<String, Music>();
    private ObjectMap<String,TextureAtlas.AtlasRegion> atlasRegionMap =new ObjectMap<String, TextureAtlas.AtlasRegion>();
    private ObjectMap<String,Array<TextureAtlas.AtlasRegion>> atlasRegionArrayMap =new ObjectMap<String, Array<TextureAtlas.AtlasRegion>>();
    private ObjectMap<String,TextureAtlas> atlasMap =new ObjectMap<String, TextureAtlas>();
    public boolean soundIsOn=true;


    // general

    /**
     * Use the assetManager instance from device object
     * @param assetManager
     */
    public void setAssetManager(AssetManager assetManager){
        this.assetManager=assetManager;
    }

    /**
     * wait until the assetmanager has finished loading
     */
    public void finishLoading(){
        assetManager.finishLoading();
    }

    /**
     * kick the assetmanager to continue loading
     * @return true if everything has been loaded
     */
    public boolean update(){
        return assetManager.update();
    }

    /**
     * return progress as a fraction of completion
     * @return
     */
    public float getProgress(){
        return assetManager.getProgress();
    }

    // basic loaders
    //====================================================================================
    // get loaded asset with: assetManager.get(String filename);
    // remove asset with: assetManager.unload(String filename);

    /**
     * a loader for textures
     * @param name filename string with format extension png or jpg, can include subdirectories
     */
    public void loadTexture(String name){
        assetManager.load(name, Texture.class);
    }

    /**
     * a loader for sounds
     * @param name filename string with format extension wav or (mp3 or ogg), can include subdirectories
     */
    public void loadSound(String name){
        assetManager.load(name, Sound.class);
    }

    /**
     * a loader for musics
     * @param name filename string with extension mp3 or ogg (or wav), can include subdirectories
     */
    public  void loadMusic(String name){
        assetManager.load(name, Music.class);
    }

    /**
     * a loader for fonts, using a png and a fnt file.
     * Better use a texture region instead of a png-file that makes its own texture.
     * @param name
     */
    public void loadBitmapFont(String name) {assetManager.load(name,BitmapFont.class);}

    /**
     * a loader for texture atlas, using a .png and an .atlas file.
     * @param name
     */
    public void loadTextureAtlas(String name) {assetManager.load(name,TextureAtlas.class);}

    /**
     * load a tiledMap with extension tmx (?)
     * Ads the TmxMapLoader to the assetmanager
     * @param name
     */
    public void loadTmxMap(String name){
        if (assetManager.getLoader(TiledMap.class)==null) {
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        }
        assetManager.load(name,TiledMap.class);
    }

    // loading many items at once
    //==========================================================================

    /**
     * loading one or many sounds. Names without extensions.
     * Assuming that they have extension given by soundFileType (default is "wav, can be changed)
     * @param names
     */
    public void addSounds(String... names){
        for (String name: names) {
            assetManager.load(name + "." + soundFileType, Sound.class);
            soundMap.put(name, null);
        }
    }

    /**
     * get all sounds and put them in the sound map
     * call after assetManager has finished loading
     */
    public void getSounds(){
        ObjectMap.Keys<String> names=soundMap.keys();
        for (String name:names) {
            soundMap.put(name,assetManager.get(name+"."+soundFileType,Sound.class));
        }
    }

    /**
     * get a sound by its name without extension
     * @param name
     * @return
     */
    public Sound getSound(String name){
        return soundMap.get(name);
    }

    /**
     * switch sounds on or off
     * @param value
     */
    public void setSoundIsOn(boolean value){
        soundIsOn=value;
    }

    /**
     * play a sound if sound is on
     * @param name
     */
    public void playSound(String name){
        if (soundIsOn){
            getSound(name).play();
        }
    }

    /**
     * loading one or more musics wioth their name, no extension
     * assumes that they all have the same type extension musicFileType (can be changed)
     * @param names
     */
    public void addMusics(String... names){
        for (String name: names) {
            assetManager.load(name + "." + musicFileType, Music.class);
            musicMap.put(name, null);
        }
    }

    /**
     * get all loaded musics in a map
     */
    public void getMusics(){
        ObjectMap.Keys<String> names=musicMap.keys();
        for (String name:names) {
            musicMap.put(name,assetManager.get(name+"."+musicFileType,Music.class));
        }
    }

    /**
     * get a music by its name without extension
     * @param name
     * @return
     */
    public Music getMusic(String name){
        return musicMap.get(name);
    }

    /**
     * load texture atlases by name only, requires png and atlas files of same name
     * @param names
     */
    public void loadAtlases(String... names){
        for (String name: names) {
            assetManager.load(name + ".atlas", TextureAtlas.class);
            atlasMap.put(name,null);
        }
    }

    /**
     * get texture atlases and extract the texture regions (extends texture region) as
     * either simple atlas region or array of atlas regions (for animations)
     *
     * multiple images format example: animation_01.png gives region_name=animation, region_index=01
     *    this will give an Array<AtlasRegion> named animation
     * others: someImage.png give region_name=someImage, region_index=-1
     */
    public void getAtlases(){
        TextureAtlas atlas;
        Array<TextureAtlas.AtlasRegion>newRegions;
        String regionName;
        ObjectMap.Keys<String> atlasNames=atlasMap.keys();
        for (String atlasName:atlasNames){
            atlas=assetManager.get(atlasName+".atlas",TextureAtlas.class);
            // do the regions of each atlas
            newRegions=atlas.getRegions();
            for (TextureAtlas.AtlasRegion newRegion:newRegions){
                regionName=newRegion.name;
                if (newRegion.index==-1){  // a single image of given name
                    atlasRegionMap.put(regionName,newRegion);
                }
                else{                     // multiple images
                    if (!atlasRegionArrayMap.containsKey(regionName)){  // if not already done, create atlasRegionArray
                        atlasRegionArrayMap.put(regionName,atlas.findRegions(regionName));
                    }
                }
            }
        }
    }

    /**
     * get the atlas region of given name
     * @param name
     * @return
     */
    public TextureAtlas.AtlasRegion getAtlasRegion(String name){
        return atlasRegionMap.get(name);
    }

    /**
     * get atlas region array of given name (for animation)
     * @param name
     * @return
     */
    public Array<TextureAtlas.AtlasRegion> getAtlasRegionArray(String name){
        return atlasRegionArrayMap.get(name);
    }

    /**
     * get atlas region in an array of given name and index (for still images)
     * @param name
     * @param index
     * @return
     */
    public TextureAtlas.AtlasRegion getAtlasRegionArrayElement(String name,int index){
        return atlasRegionArrayMap.get(name).get(index);
    }

    /**
     * get the entire TextureAtlas of a given name (for particle effects)
     * @param name
     * @return
     */
    // for particle effects
    public TextureAtlas getAtlas(String name){
        return atlasMap.get(name);
    }

    /**
     * create an animation using an AtlasRegionArray
     * @param frameDuration
     * @param name
     * @param playMode
     * @return
     */
    public Animation createAnimation(float frameDuration,String name,Animation.PlayMode playMode){
        return new Animation(frameDuration,getAtlasRegionArray(name),playMode);
    }

    /**
     * create a particle effect
     * @param effectName name of the effect file, uses file type extension p
     * @param atlasName name of atlas with the images
     * @return
     */
    public ParticleEffect createParticleEffect(String effectName, String atlasName){
        ParticleEffect particleEffect=new ParticleEffect();
        particleEffect.load(Gdx.files.internal(effectName+".p"),getAtlas(atlasName));
        return particleEffect;
    }
}
