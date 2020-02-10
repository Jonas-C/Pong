package ec.pong.sprites;

public interface Observable {

    void register(Observer obj);
    void unregister(Observer obj);
    void notifyObservers();
}
