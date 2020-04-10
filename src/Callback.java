public interface Callback<T> {

    void  onSuccess(T data);
    void onFailed(Throwable throwable);

}
