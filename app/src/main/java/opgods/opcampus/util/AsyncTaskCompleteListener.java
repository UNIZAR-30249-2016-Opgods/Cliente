package opgods.opcampus.util;

/**
 * Interfaz para la realización del callback una vez hayan finalizado los AsyncTask
 *
 * @param <T>
 */
public interface AsyncTaskCompleteListener<T> {
    void onTaskComplete(T result);
}
