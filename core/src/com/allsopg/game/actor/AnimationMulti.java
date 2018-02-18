package com.allsopg.game.actor;

import com.allsopg.game.utility.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.Comparator;


/**
 * Created by Harry on 18/02/2018.
 */

public class AnimationMulti extends Sprite {
    private Animation idle, ammo, animation;
    private TextureAtlas atlas_1, atlas_2;

    public AnimationMulti(String atlasString_1, String atlasString_2, Texture t, Animation.PlayMode loopType){
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
    }

    private static class RegionComparator implements Comparator<TextureAtlas.AtlasRegion> {

        @Override
        public int compare(TextureAtlas.AtlasRegion region1, TextureAtlas.AtlasRegion region2) {
            return region1.name.compareTo(region2.name);
        }
    }
}
