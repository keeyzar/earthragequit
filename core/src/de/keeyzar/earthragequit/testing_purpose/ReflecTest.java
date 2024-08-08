package de.keeyzar.earthragequit.testing_purpose;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author = Keeyzar on 13.03.2016
 */
public class ReflecTest {

    public static void main(String[] args) {
        Class<MathRechner> mathRechnerClass = MathRechner.class;
        Constructor<?>[] constructors = mathRechnerClass.getConstructors();
        System.out.println(constructors[0].getName());
        Constructor<?> constructor = constructors[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        System.out.println(parameterTypes.length);
        try {
            constructor.newInstance("String");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
