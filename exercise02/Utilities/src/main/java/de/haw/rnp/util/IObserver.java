package de.haw.rnp.util;

/**
 * Generic Interface for Observers.
 *
 * @param <T> Type of the Generic
 */
public interface IObserver<T> {

    void update(T elem);

}
