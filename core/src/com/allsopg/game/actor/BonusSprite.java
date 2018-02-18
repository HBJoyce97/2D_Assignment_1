package com.allsopg.game.actor;

import com.allsopg.game.sound.SoundLink;
import com.allsopg.game.utility.TweenData;
import com.allsopg.game.utility.TweenDataAccessor;
import com.allsopg.game.utility.UniversalResource;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by gja on 13/11/2017.
 * Updated by gja on 19/10/2017
 */

public class BonusSprite extends AnimationMulti {
    private TweenData tweenData;
    private TweenManager tweenManager;
    private SoundLink soundLink;

    public BonusSprite(String atlasString, Texture t, Vector2 pos, Animation.PlayMode loopType){
        super(atlasString, t, loopType);
        this.setPosition(pos.x,pos.y);
        initTweenData();
        soundLink = new SoundLink();
    }

    private void initTweenData(){
        tweenData = new TweenData();
        tweenData.setXY(this.getX(),this.getY());
        tweenData.setColour(this.getColor());
        tweenData.setScale(this.getScaleX());
        tweenManager = UniversalResource.getInstance().tweenManager; //tweenManager;
    }

    private TweenData getTweenData(){return tweenData;}


    @Override
    public void update(float stateTime){
        super.update(stateTime);
        this.setX(tweenData.getXY().x);
        this.setY(tweenData.getXY().y);
        this.setColor(tweenData.getColour());
        this.setScale(tweenData.getScale());
        this.setRotation(tweenData.getRotation());
    }

    public void destroyRoutine(){
        Tween.to(tweenData, TweenDataAccessor.TYPE_POS,250f)
                .target(200,100).start(tweenManager)
                .to(tweenData, TweenDataAccessor.TYPE_ROTATION,250f)
                .target(180).start(tweenManager)
                .to(tweenData,TweenDataAccessor.TYPE_SCALE,250f)
                .target(.15f)
                .setCallback(new TweenCallback() {
                   @Override
                   public void onEvent(int type, BaseTween<?> source) {
                       animation = ammo;
                       soundLink.play(3);
                       soundLink.play(1);
                       soundLink.play(2);
                       endRoutine();
                   }
                }).start(tweenManager);
    }

    public void endRoutine(){
        Tween.to(tweenData, TweenDataAccessor.TYPE_POS,250f)
                .target(0.15f).to(tweenData,TweenDataAccessor.TYPE_COLOUR,150f)
                .target(1f, 1f, 1f, .0f).start(tweenManager);
    }
}
