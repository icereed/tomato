package tomato;

public interface Observable<E> {
	public void addObserver(E e);

	public void removeObserver(E e);
}
