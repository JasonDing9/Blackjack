package jason.games.blackjack;

public interface ChangeObservable {
	void addChangeListener(ChangeListener listener);
	void removeChangeListener(ChangeListener listener);
}
