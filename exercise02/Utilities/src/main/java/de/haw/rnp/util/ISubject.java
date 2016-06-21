package de.haw.rnp.util;

/**
 * Subject of the Observer Pattern.
 */
public interface ISubject {

    /**
     * Registers the Observer.
     *
     * @param observer Observer
     */
    void registerObserver(IObserver observer);

    /**
     * Removes the Observer
     *
     * @param observer Observer
     */
    void removeObserver(IObserver observer);

    /**
     * Notifies the Observer if Subject changed.
     */
    void notifyObservers();

}
