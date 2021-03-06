package com.allsopg.game.actor;

import com.allsopg.game.sound.SoundLink;
import com.allsopg.game.utility.Constants;
import com.allsopg.game.utility.TweenData;
import com.allsopg.game.utility.TweenDataAccessor;
import com.allsopg.game.utility.UniversalResource;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;


/**
 * Created by Harry on 18/02/2018.
 */

public class AnimationMulti extends Sprite {
    public Animation idle, ammo, animation; // Variables used to set the current animation
    private TextureAtlas atlas_1, atlas_2; // Variables used to set up the two animation atlases

    private TweenData tweenData; // Used to tween the animation
    private TweenManager tweenManager; // Used to tween the animation

    private SoundLink soundLink; // Variable used to set up the soundLink which will enable sounds from the IntMap in the 'SoundLink' class to be played

    // Sets up the two atlases and their regions used for the animation change. atlas_1 will be set to the 'idle' animation; atlas_2 will be set to the 'anim' animation
    public AnimationMulti(String atlasString_1, String atlasString_2, Texture t, Vector2 pos, Animation.PlayMode loopType){
       super(t);
        atlas_1 = new TextureAtlas(Gdx.files.internal(atlasString_1));
        Array<TextureAtlas.AtlasRegion> regions_1 = new
                Array<TextureAtlas.AtlasRegion>(atlas_1.getRegions());
        regions_1.sort(new RegionComparator());
        idle = new Animation(Constants.FRAME_DURATION, regions_1, loopType);

        atlas_2 = new TextureAtlas(Gdx.files.internal(atlasString_2));
        Array<TextureAtlas.AtlasRegion> regions_2 = new
                Array<TextureAtlas.AtlasRegion>(atlas_2.getRegions());
        regions_2.sort(new RegionComparator());
        ammo = new Animation(Constants.FRAME_DURATION, regions_2, loopType);

        animation = idle; // Animation is set to 'idle' by default, so the crate remains closed at the beginning of the animation

        this.setPosition(300, 300);
        initTweenData(); // Initializes the tween
        soundLink = new SoundLink(); // Intializes the SoundLink
    }

    public void update(float deltaTime){
        this.setRegion((TextureAtlas.AtlasRegion) animation.getKeyFrame(deltaTime)); // Updates the region every frame

        // Updates the following tween data to be used in the tween
        this.setX(tweenData.getXY().x);
        this.setY(tweenData.getXY().y);
        this.setColor(tweenData.getColour());
        this.setScale(tweenData.getScale());
        this.setRotation(tweenData.getRotation());
    }

    private static class RegionComparator implements Comparator<TextureAtlas.AtlasRegion> {

        @Override
        public int compare(TextureAtlas.AtlasRegion region1, TextureAtlas.AtlasRegion region2) {
            return region1.name.compareTo(region2.name); // Compares the regions of the atlases
        }
    }

    // Initializes the tween data and the tweenManager
    private void initTweenData(){
        tweenData = new TweenData();
        tweenData.setXY(this.getX(),this.getY());
        tweenData.setColour(this.getColor());
        tweenData.setScale(this.getScaleX());
        tweenManager = UniversalResource.getInstance().tweenManager; //tweenManager;
    }

    // The following code is the main animation for the sprite on screen
    public void destroyRoutine(){
        Tween.to(tweenData, TweenDataAccessor.TYPE_POS,0f) // Sets the start position of the sprite (off screen in the center)
                .target(825,1200).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_POS,100f) // Sets the sprite to move in the -y direction by 1200
                .target(825, 0).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_ROTATION,100f) // Whilst falling, the sprite will rotate 720 degrees. Synced with the tween above so the rotation and position are relative
                .target(720).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_POS,40f) // Delays the sprite before moving in the y direction by 50
                .delay(150f)
                .target(825, 50).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_ROTATION,80f) // Delays the sprite before rotating anti-clockwise by 20 degrees
                .delay(200f)
                .target(740).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_ROTATION,80f) // Delays the sprite before rotating clockwise by 40 degrees
                .delay(250f)
                .target(700).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_ROTATION,80f) // Delays the sprite before rotating anti-clockwise by 40 degrees
                .delay(300f)
                .target(740).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_ROTATION,80f) // Delays the sprite before rotating clockwise by 40 degrees
                .delay(350f)
                .target(700).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_ROTATION,80f) // Delays the sprite before rotating anti-clockwise by 20 degrees. The above tweens were used to simulate a shaking of the sprite
                .delay(400f)
                .target(720).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_POS,50f) // Delays the sprite before moving in the -y direction by 50
                .delay(500f)
                .target(825, 0).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_POS,50f) // Delays the sprite before the animation is changed
                .delay(1000f)
                .target(825, 0)
                .setCallback(new TweenCallback() { // Initializes the callback
                    @Override
                    public void onEvent(int type, BaseTween<?> source) { // The following event causes the animation change
                        soundLink.play(1); // Play the first sound in the IntMap (Whoosh)
                        animation = ammo; // Change the animation from 'idle' to 'ammo'
                        soundLink.play(2); // Play the second sound in the IntMap (Fire)
                    }
                }).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_COLOUR,150f) // Delays the colour from changing until the animation has finished. Once finished, the sprite turns black, disappearing from the screen
                .delay(3600f)
                .target(1f,1f,1f,.0f).start(tweenManager);
    }

    private TweenData getTweenData(){return tweenData;} // Returns tweenData
}
