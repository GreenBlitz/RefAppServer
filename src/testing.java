import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by ofeke on 7/30/2018.
 */
public class testing {

    public static void main(String[] args) throws  Exception{
        ServerSocket serSocket = new ServerSocket(4590);
        print("have server");
        AppServer ap = new AppServer(serSocket);
        ap.start();
        print("thread have started");
        ap.setTime(210);
        print("what");
        while(!ap.hasPiles()){}
        Thread.sleep(100);
        ArrayList<ArrayList<Integer>> ar = ap.getPiles();
        print("array" + ar.toString());
    }

    public static <T> void print(T str){
        System.out.println(str);
    }
}
