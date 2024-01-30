public interface ISubject {

    void registerObserver(IObserver observer);
    void removeObserve(IObserver observer);
    void broadcast();

}