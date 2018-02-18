package com.allsopg.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.IntMap;

/**
 * Created by hj167 on 16/01/2018.
 */

public class SoundLink
{
    private IntMap<Sound> soundKeys; // Creates the IntMap

    public SoundLink()
    {
        soundKeys = new IntMap<Sound>(); // Initializes the IntMap

        Sound backSnd = Gdx.audio.newSound(Gdx.files.internal("sfx/back.ogg")); // Retrieve the following sound
        Sound diamondSnd = Gdx.audio.newSound(Gdx.files.internal("sfx/diamond.ogg")); // Retrieve the following sound
        Sound lifeSnd = Gdx.audio.newSound(Gdx.files.internal("sfx/life.ogg")); // Retrieve the following sound

        soundKeys.put(1, backSnd); // Add the following sound to the IntMap
        soundKeys.put(2, diamondSnd); // Add the following sound to the IntMap
        soundKeys.put(3, lifeSnd); // Add the following sound to the IntMap
    }

    public boolean play(int keyCode)
    {
        Sound sound = soundKeys.get(keyCode); // Retrieves the key code from soundKeys
        if(sound != null) // If the key code is not null
        {
            sound.play(); // Play the sound associated with the key code
            return true; // Return true
        }
        return false; // Else do nothing and return false
    }

    public void dispose()
    {
        for(Sound sound : soundKeys.values()) // For the values of soundKeys...
        {
            sound.dispose(); // Dispose of the sound
        }
    }
}
