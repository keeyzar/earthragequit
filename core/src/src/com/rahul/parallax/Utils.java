package src.com.rahul.parallax;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * utility class for common calculations
 * @author Rahul Verma
 *
 */
public class Utils {

	public enum WH{
		width, height
	}
	
	/**
	 * calculate new width/height maintaining aspect ratio
	 * @param wh what oneDimen represents
	 * @param oneDimen either width or height 
	 * @param region the texture region
	 * @return if oneDimen is width then height else width
	 */
	public static float calculateOtherDimension(WH wh,float oneDimen,TextureRegion region){
		float result=0;
		switch (wh){
		    // height_specified
		    case width:
    		    result = region.getRegionHeight()*(oneDimen/region.getRegionWidth());
		    	break;
		    // width_specified
	    	case height:
	    		result = region.getRegionWidth()*(oneDimen/region.getRegionHeight());
		    	break;
		
		}
		
		return result;
		
	}
	
	/**
	 * calculate new width/height maintaining aspect ratio
	 * @param wh what oneDimen represents
	 * @param oneDimen either width or height 
	 * @param originalWidth the original width
	 * @param originalHeight the original height
	 * @return if oneDimen is width then height else width
	 */
	public static float calculateOtherDimension(WH wh,float oneDimen,float originalWidth, float originalHeight){
		float result=0;
		switch (wh){
		    case width:
    		    result = originalHeight*(oneDimen/originalWidth);
		    	break;
	    	case height:
	    		result = originalWidth*(oneDimen/originalHeight);
		    	break;
		
		}
		
		return result;
		
	}

//
//	public static long old;
//	public static long tmp;
//	public static long newI;
	public static void debug(int which){
		debug(which, 0);
	}

	public static long old[];
	public static long tmp[];
	public static long newI[];

    /**
     * debug for different times
     * @param which
     */
	public static void debug(int which, int vgl){
	    if(old == null){
	        old = new long[10];
	        tmp = new long[10];
	        newI = new long[10];
        }
		newI[vgl] = TimeUtils.nanoTime();
		tmp[vgl] = newI[vgl] - old[vgl];
		if(which != 1) System.out.println(vgl + " / " + which + " had this length: " + tmp[vgl]);
		old[vgl] = TimeUtils.nanoTime();
	}
	
}
