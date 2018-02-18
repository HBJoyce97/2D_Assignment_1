package com.allsopg.game.actor;

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
    public Animation idle, ammo, animation;
    private TextureAtlas atlas_1, atlas_2;

    private TweenData tweenData;
    private TweenManager tweenManager;

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

        animation = idle;

        this.setPosition(300, 300);
        initTweenData();
    }

    public void update(float deltaTime){
        this.setRegion((TextureAtlas.AtlasRegion) animation.getKeyFrame(deltaTime));

        this.setX(tweenData.getXY().x);
        this.setY(tweenData.getXY().y);
        this.setColor(tweenData.getColour());
        this.setScale(tweenData.getScale());
        this.setRotation(tweenData.getRotation());
    }

    private static class RegionComparator implements Comparator<TextureAtlas.AtlasRegion> {

        @Override
        public int compare(TextureAtlas.AtlasRegion region1, TextureAtlas.AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }

    private void initTweenData(){
        tweenData = new TweenData();
        tweenData.setXY(this.getX(),this.getY());
        tweenData.setColour(this.getColor());
        tweenData.setScale(this.getScaleX());
        tweenManager = UniversalResource.getInstance().tweenManager; //tweenManager;
    }

    public void destroyRoutine(){
        Tween.to(tweenData, TweenDataAccessor.TYPE_POS,0f)
                .target(850,1200).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_POS,100f)
                .target(850, 0).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_ROTATION,100f)
                .target(720).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_POS,40f)
                .delay(150f)
                .target(850, 50).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_ROTATION,80f)
                .delay(200f)
                .target(740).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_ROTATION,80f)
                .delay(250f)
                .target(700).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_ROTATION,80f)
                .delay(300f)
                .target(740).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_ROTATION,80f)
                .delay(350f)
                .target(700).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_ROTATION,80f)
                .delay(400f)
                .target(720).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_POS,50f)
                .delay(500f)
                .target(850, 0).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_POS,50f)
                .delay(1000f)
                .target(850, 0)
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        animation = ammo;
                    }
                }).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_COLOUR,150f)
                .delay(4500f)
                .target(1f,1f,1f,.0f).start(tweenManager);
    }

    private TweenData getTweenData(){return tweenData;}
}
