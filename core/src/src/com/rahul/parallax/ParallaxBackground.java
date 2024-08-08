package src.com.rahul.parallax;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

/**
 * A ParallaxBacground encapsulates the logic to Render ParallaxLayers.
 * It owns a collection of ParallaxLayers. 
 * These Layers a rendered on screen showing src.com.rahul.parallax effect based on scrolling characteristics of individual layers.
 * This rendering module does not owns any Batch ,you need to provide one in the draw method. 
 * <p>
 * Also see {@link ParallaxLayer}.
 * @author Rahul Verma
 *
 */
public class ParallaxBackground {
	
	/**
	 * this array contains the src.com.rahul.parallax scrolling layers that are drawn on the screen. Layers are rendered in the order they are present in this array.
	 */
	public Array<ParallaxLayer> layers;
	private Matrix4 cachedProjectionView;
	private Vector3 cachedPos;
	private float cachedZoom;
	private Vector2 vector2 = new Vector2();

	/**
	 * Create a ParallaxBackground without any layers
	 */
	public ParallaxBackground(){
		initialize();
	}
	
	

	/**
	 * Create a ParallaxBackground instance with the layers added
	 * @param layers layers to be added to the parallaxBackground
	 */
	public ParallaxBackground(ParallaxLayer... layers){
		initialize();
		this.layers.addAll(layers);
	}
	
    private void initialize() {
    	layers = new Array<ParallaxLayer>();
		cachedPos = new Vector3();
		cachedProjectionView = new Matrix4();
	}
	
	/**
	 * Add the layers to the {@link #layers} array. These layers are rendered over the layers previously in the layers array
	 * @param layers layers to be added to the parallaxBackground
	 */
	public void addLayers(ParallaxLayer... layers){
		this.layers.addAll(layers);
	}
	
	/**
	 * render the layers held by this module. Of course the layers are rendered in src.com.rahul.parallax scrolling manner. The worldCamera and batch provided are unaffected by the method
	 * @param worldCamera The Orthographic WorldCamera , all layers are rendered relative to its position.
	 * @param batch The batch which is used to render the layers.
	 */
	public void draw(OrthographicCamera worldCamera, Batch batch){
//		debug(1, 0);
		cachedProjectionView.set(worldCamera.combined);
		cachedPos.set(worldCamera.position);
		cachedZoom = worldCamera.zoom;
		
//		debug(1, 1);
		for(int i=0; i<layers.size; i++){
			ParallaxLayer layer = layers.get(i);
			layer.update();
			Vector2 origCameraPos = vector2.set(cachedPos.x, cachedPos.y);
			worldCamera.position.set(origCameraPos.scl(layer.getParallaxRatio()),cachedPos.z);
		    worldCamera.update();
		    batch.setProjectionMatrix(worldCamera.combined);
		    float currentX = (layer.getTileModeX().equals(ParallaxLayer.TileMode.single)?0:((int)((worldCamera.position.x-worldCamera.viewportWidth*.5f*worldCamera.zoom) / layer.getWidth())) * layer.getWidth())-(Math.abs((1-layer.getParallaxRatio().x)%1)*worldCamera.viewportWidth*.5f);
//			debug(1, 2);
		    do{
		            float currentY =  (layer.getTileModeY().equals(ParallaxLayer.TileMode.single)?0:((int)((worldCamera.position.y-worldCamera.viewportHeight*.5f*worldCamera.zoom) / layer.getHeight())) * layer.getHeight())-(((1-layer.getParallaxRatio().y)%1)*worldCamera.viewportHeight*.5f);
//		            debug(1, 3);
		            do{
		               if(!((worldCamera.position.x-worldCamera.viewportWidth*worldCamera.zoom*.5f>currentX+layer.getWidth())||(worldCamera.position.x+worldCamera.viewportWidth*worldCamera.zoom*.5f<currentX)||(worldCamera.position.y-worldCamera.viewportHeight*worldCamera.zoom*.5f>currentY+layer.getHeight())||(worldCamera.position.y+worldCamera.viewportHeight*worldCamera.zoom*.5f<currentY))) {
//		               	debug(1, 4);
		               	layer.draw(batch, currentX, currentY);
//		               	debug(2, 4);
					   }
		               currentY += layer.getHeight();
		               if(layer.getTileModeY().equals(ParallaxLayer.TileMode.single))
			        	     break;
		            }while( currentY < worldCamera.position.y+worldCamera.viewportHeight*worldCamera.zoom*.5f);
//		            debug(2, 3);
		            currentX += layer.getWidth();
		            if(layer.getTileModeX().equals(ParallaxLayer.TileMode.single))
		        	     break;
			}while( currentX < worldCamera.position.x+worldCamera.viewportWidth*worldCamera.zoom*.5f);
//			debug(2,0);
		}
//		debug(2, 2);
		
		worldCamera.combined.set(cachedProjectionView);
		worldCamera.position.set(cachedPos);
		worldCamera.zoom = cachedZoom;
		worldCamera.update();
		batch.setProjectionMatrix(worldCamera.combined);
//		debug(2, 0);
	    
	}
	

}
