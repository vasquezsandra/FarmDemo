package io.github.vasquezsandra;

import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;

public class GameTextures {
    //Stores textures in hashmap, retrieves the textures faster.
    private static final HashMap<String, Texture> textures = new HashMap<>();
//Retrieves a texture from the specified file path.
    public static Texture getTexture(String path) {
        if (!textures.containsKey(path)) {
            textures.put(path, new Texture(path));//No duplicates
        }
        return textures.get(path);
    }

    public static void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
        textures.clear();
    }
}