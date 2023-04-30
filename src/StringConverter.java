import parcs.AM;
import parcs.AMInfo;
import parcs.channel;
import parcs.point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StringConverter implements AM {
    private CipherScheme scheme;

    public String convert(String message) throws IllegalStateException{
        if (scheme == null) {
            throw new IllegalStateException("Scheme not set");
        }
        StringBuilder builder = new StringBuilder();
        for (Character c : message.toCharArray()) {
            builder.append(scheme.convertLetter(c));
        }
        return builder.toString();
    }

    private String process(CipherScheme scheme, String message) {
        this.setScheme(scheme);
        System.out.println("[" + scheme.toString() + "] Schema set.");
        System.out.println("[" + message + "] Message processed...");
        String result = convert(message);
        System.out.println("[" + result + "] done.");
        return result;
    }

    public void setScheme(CipherScheme scheme) {
        this.scheme = scheme;
    }


    @Override
    public void run(AMInfo amInfo) {

        Data data = (Data)amInfo.parent.readObject();

        CipherScheme cipherScheme = data.scheme;
        System.out.println("[" + scheme.toString() + "] Schema set.");
        String message = data.message;
        String result = process(cipherScheme, message);

        amInfo.parent.write(result);
    }
}
