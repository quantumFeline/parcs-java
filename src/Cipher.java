import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import parcs.*;

public class Cipher {

    public static void main(String[] args) throws Exception {
        task curTask = new task();
        curTask.addJarFile("StringConverter.jar");
        Data data = fromFile(curTask.findFile("input"));

        AMInfo info = new AMInfo(curTask, null);
        splitTask(info, data);

        curTask.end();
    }

    public static void splitTask(AMInfo info, Data data) {
        List<channel> channels = new ArrayList<>();
        int message_len = data.message.length();
        int workers_n = data.workers_n;

        long time = System.currentTimeMillis();
        for (int i = 0; i < workers_n; i++) {
            String chunk = i == workers_n - 1 ?
                    data.message.substring(i * (message_len/workers_n)):
                    data.message.substring(i * (message_len/workers_n), (i+1) * (message_len/workers_n));
            Data worker_data = new Data(data.scheme, chunk, i);

            System.out.println(i + " worker string: " + chunk);

            point newPoint = info.createPoint();
            channel newChannel = newPoint.createChannel();
            System.out.println("Channel created");

            newChannel.write(worker_data);
            newPoint.execute("StringConverter");
            System.out.println("Execution has started. Waiting for result...");

            channels.add(newChannel);
        }
        System.out.println("All channels started. Time: " + (System.currentTimeMillis() - time) + "ms");

        long time2 = System.currentTimeMillis();

        for (int i = 0; i < workers_n; i++) {
            System.out.println("Result: " + channels.get(i).readObject().toString());
        }
        System.out.println("Time from all channels starting: " + (System.currentTimeMillis() - time2) + "ms");
        System.out.println("Full execution time: " + (System.currentTimeMillis() - time) + "ms");
    }

    public static Data fromFile(String filename) throws Exception {
        String __;
        Scanner sc = new Scanner(new File(filename));
        int workers_n = sc.nextInt();
        __ = sc.nextLine();
        String alphabet = sc.nextLine();
        String counterparts = sc.nextLine();
        __ = sc.nextLine();
        CipherScheme cipherScheme = new CipherScheme(alphabet, counterparts);
        String message = sc.nextLine();
        sc.close();
        return new Data(cipherScheme, message, workers_n);
    }
}
