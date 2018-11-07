public interface IMemento {
    IMemento SaveState();
    void RestoreState(IMemento memento);
}
