package com.example.maxim.inbd;

/**
 * Created by Maxim on 15.03.2016.
 */
public interface Subject {
     void registerObserver(Observer o);
     void removeObserver(Observer о);
     void notifyObservers();

}
