package SuperLee.BusinessLayer;


/**
 *
 * This class object is an ordered pair, used for transporting
 * information between the service and business layers.
 * @param <T>
 * @param <E>
 */
public class Pair<T,E>{

    private T first;
    private E second;

    public Pair (T first, E second){
        this.first = first;
        this.second = second;
    }

    public E getSecond() {
        return second;
    }

    public T getFirst() {
        return first;
    }

}
