package de.keeyzar.earthragequit.saving;

/**
 * Implement this, and your class can be saved.
 * Saving occurs on many points like:
 * game is paused
 * game is
 * @author = Keeyzar on 12.02.2016.
 */
public interface Safeable {
    void save();
    void reset();
}
