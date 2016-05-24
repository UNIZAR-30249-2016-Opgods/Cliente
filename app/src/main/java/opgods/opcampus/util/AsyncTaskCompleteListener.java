package opgods.opcampus.util;

/**
 * Interfaz para la realización del callback una vez hayan finalizado los AsyncTask
 *
 * @param <T>
 */
public interface AsyncTaskCompleteListener<T> {
    /**
     * Llamada de callback que tendrán que impolementar todos los que quieran realizar un AsyncTask
     *
     * @param result resultado del AsyncTask
     */
    void onTaskComplete(T result);
}
