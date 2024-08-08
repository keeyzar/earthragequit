package de.keeyzar.earthragequit.testing_purpose;


import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * Created by Keeyzar on 12.02.2016.
 */
public class TexturePackeryo {
    public static void main (String[] args) throws Exception {
        TexturePacker.process("gameAssets", "android/assets/data/gameAssets", "gameAssets");
    }
}
