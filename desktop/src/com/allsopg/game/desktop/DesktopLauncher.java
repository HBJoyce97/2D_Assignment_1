package com.allsopg.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.allsopg.game.MyGdxGame;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		TexturePacker.process("../assets/gfx/animation", "../assets/gfx/animation", "anim_assets");
		TexturePacker.process("../assets/gfx/idle", "../assets/gfx/idle", "idle_assets");
		new LwjglApplication(new MyGdxGame(), config);
	}
}
