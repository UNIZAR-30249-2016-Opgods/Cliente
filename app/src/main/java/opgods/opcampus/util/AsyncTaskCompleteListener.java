package opgods.opcampus.util;

/**
 * Created by URZU on 24/05/2016.
 */
public interface AsyncTaskCompleteListener<T> {
    void onTaskComplete(T result);
}
