package com.allsopg.game;

import com.allsopg.game.actor.AnimationMulti;
import com.allsopg.game.utility.Constants;
import com.allsopg.game.utility.UniversalResource;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    private Viewport view;
    private SpriteBatch batch;
	private AnimationMulti bp; // Variable used to set up the animation change
    private float animationTime;
	private BitmapFont font; // Variable used to initialize and draw the bitmap font
	private String text; // Variable used to set the String text for the font
	private static GlyphLayout glyphLayout = new GlyphLayout(); // Initializes a new glyphlayout
	@Override
	public void create () {
		font = new BitmapFont(Gdx.files.internal("font/Ammo.fnt")); // Initializes the bitmap font using the following .fnt file created in Hiero
		text = "Ammo Package Received"; // Sets the String 'text' to the following. This is what will be displayed on the screen
		camera = new OrthographicCamera(); // Initializes the camera
		view = new FitViewport(800,600,camera); // Initializes the size of the viewport, setting it up with the 'camera'
		batch = new SpriteBatch(); // Initializes the batch
		Texture small = new Texture(Gdx.files.internal("smallSize.png")); // Initializes one of the textures used to size the sprite
		Texture medium = new Texture(Gdx.files.internal("mediumSize.png")); // Initializes one of the textures used to size the sprite
        bp = new AnimationMulti("gfx/idle/idle_assets.atlas", "gfx/animation/anim_assets.atlas", medium,
                new Vector2(Constants.SCENE_WIDTH/2,Constants.SCENE_HEIGHT/2), Animation.PlayMode.LOOP); // Initializes the animation change. Passes in both animation atlases, the texture to size it, sets the vector position and the animation playmode
        bp.destroyRoutine(); // Calls the 'destroyRoutine' in the 'AnimationMulti' class. This is what plays/changes the animation
	}
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        animationTime +=Gdx.graphics.getDeltaTime();
        UniversalResource.getInstance().tweenManager.update(animationTime);
        batch.begin(); // Starts the batch
		glyphLayout.setText(font, text); // Sets up the bitmap font and the String that will be used i.e. "Ammo Package Received"
		font.draw(batch, glyphLayout, // Draws the text to the screen using the font specified
				Gdx.graphics.getWidth()/2 - (glyphLayout.width/2), // Sets the width of the text and its position on screen
				Gdx.graphics.getHeight()/2 - (glyphLayout.height/2)); // Sets the height of the text and its position on screen
        bp.update(animationTime); // Updates the animation every frame
        bp.draw(batch); // Draws the animation to the screen
		batch.end(); // Ends the batch
	}
	@Override
	public void dispose () {
		batch.dispose();
	} // Gets rid of the batch
}
