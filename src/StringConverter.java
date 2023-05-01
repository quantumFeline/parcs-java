import parcs.AM;
import parcs.AMInfo;

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
        //System.out.println("[" + scheme.toString() + "] Schema set.");
        //System.out.println("[" + message + "] Message processed...");
        String result = convert(message);
        //System.out.println("[" + result + "] done.");
        return result;
    }

    public void setScheme(CipherScheme scheme) {
        this.scheme = scheme;
    }


    @Override
    public void run(AMInfo amInfo) {

//	System.out.println("Running worker...");

        Data data = (Data)amInfo.parent.readObject();

//	System.out.println("Read data: " + data);
	
//	System.out.println("Read data: " + data.scheme);
//	System.out.println("Read data: " + data.message);
//	System.out.println("Read data: " + data.workers_n);

        CipherScheme cipherScheme = data.scheme;
//      System.out.println("[" + cipherScheme.toString() + "] Schema set.");
        String message = data.message;
        String result = process(cipherScheme, message);
//	System.out.println("Writing result...");

        amInfo.parent.write(result);
//	System.out.println("over and out.");
    }
}
