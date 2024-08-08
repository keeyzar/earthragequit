package de.keeyzar.earthragequit.testing_purpose;

import com.badlogic.gdx.math.MathUtils;

/**
 * @author = Keeyzar on 01.03.2016
 */
public class MathRechner {

    public MathRechner(String x){
    }

    public static void main(String[] args) {
        MathRechner mathRechner = new MathRechner();
    }

    public MathRechner(){
        for(int i = 0; i<10; i++){
            System.out.println(MathUtils.ceil(i / 3) + " - " + i);
        }
    }
}
