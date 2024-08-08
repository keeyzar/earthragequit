package de.keeyzar.earthragequit.tools;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/**
 * @author = Keeyzar on 28.03.2016
 */
public class BodyEditorAssetLoader extends SynchronousAssetLoader<BodyEditorLoader, BodyEditorAssetLoader.BodyEditorParameter>{
    public BodyEditorAssetLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, BodyEditorParameter parameter) {
        return null;
    }

    @Override
    public BodyEditorLoader load(AssetManager assetManager, String fileName, FileHandle file, BodyEditorParameter parameter) {
        BodyEditorLoader bodyEditorLoader = new BodyEditorLoader(file);
        return bodyEditorLoader;
    }

    public static class BodyEditorParameter extends AssetLoaderParameters<BodyEditorLoader>{
        /** Atlas file name. */
        public String atlasFile;
        /** Optional prefix to image names **/
        public String atlasPrefix;
        /** Image directory. */
        public FileHandle imagesDir;
    }
}
