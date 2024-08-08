package de.keeyzar.earthragequit.tools;

/**
 * @author = Keeyzar on 02.02.2017.
 */
public abstract class BackNavigationInterface {
    /**
     * please take caution: remove this listener, when user navigates "back" without pressing back!!
     */
    abstract public void backPressed();

    /**
     * whether or not, if the default behaviour should be used (kill App, on double Backpress)
     */
    public boolean shouldUseDefaultBehaviour(){return false;}

    /**
     * if this method is implemented, make sure, that it is removed from Stack, if going to another screen!
     */
    public boolean shouldRemoveFromStack(){ return true;}
}
