import java.io.Serializable;

public class Data implements Serializable {
    int workers_n;
    CipherScheme scheme;
    String message;

    public Data(CipherScheme scheme, String message, int workers_n) {
        this.scheme = scheme;
        this.message = message;
        this.workers_n = workers_n;
    }
}