import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ofeke on 7/30/2018.
 */
public class AppServer extends Thread {
    private int cannonState = 0; // 0 noun have been fired, 1 one have been fired, both have been used.
    private static AppServer instance;
    private int time;
    private boolean running = true;
    private Thread thread;
    private String threadName;
    private int[] cargoDelta;
    private int fouls = 0;
    private boolean accepted;
    private int penaltys = 0;
    private int curscore = 0;
    private ServerSocket serSocket;
    private Socket socket;
    private boolean haveTeam;
    private JSONObject jo;
    private int id;
    private boolean dead;
    private Boolean isRed;
    private boolean newAdding;
    private ArrayList<ArrayList<Integer>> arr;
    private boolean hasPile = false;
    private boolean[] newCargo;

    public AppServer(ServerSocket s, int id){
        cargoDelta = new int[5];
        threadName = "RefAppServer"+id;
        this.id = id;
        time = 0;
        dead = false;
        newCargo = new boolean[5];
        for(int i =0;i < newCargo.length;i++)
            newCargo[i] = false;
        serSocket = s;
        time = 0;
        accepted = false;
        arr = new ArrayList<ArrayList<Integer>>();
        haveTeam = false;
        hasPile = false;
    }

    public boolean isRed(){
        if(isRed != null)
            return isRed;
        return true;
    }

    public boolean haveTeam(){
        return haveTeam;
    }

    public int getID(){
        return id;
    }

    public void mainFun() throws Exception {
        print("main have start");
        if(socket == null) {
            socket = serSocket.accept();
        }
        accepted  = true;
        print("socket have been accepted");
        PrintStream pw = new PrintStream(socket.getOutputStream());
        Scanner scan = new Scanner(socket.getInputStream());

        Printer help = new Printer("Printer"+getID(), pw, instance);
        help.start();
        print("start printing");

        JSONObject jo;
        String mes;
        while (running) {
            print("start waiting");
            while (!scan.hasNext()) {}

            mes = scan.next();

            print("got input "+mes);

            jo = new JSONObject(mes);
            switch (jo.getString("Message")){
                case "Team":
                    if(jo.has("CargoType")) {
                        isRed = jo.getString("CargoType") != "Red";
                        haveTeam = true;
                    }
                    break;
                case "AddCargo":
                    addCargo(jo.getString("CargoType"), true);
                    break;
                case "RemoveCargo":
                    addCargo(jo.getString("CargoType"), false);
                    break;
                case "Enable":
                    //TODO enable
                    break;
                case "Disable":
                    //TODO disable
                    break;
                case "AddFoul":
                    fouls++;
                    AppGetter.init().addFoul(isRed);
                    break;
                case "RemoveFoul":
                    AppGetter.init().removeFoul(isRed);
                    if(fouls > 0)
                        fouls--;
                    break;
                case "AddPenalty":
                    AppGetter.init().addPenaltys(isRed);
                    penaltys++;
                    break;
                case "RemovePenalty":
                    AppGetter.init().removePenaltys(isRed);
                    if(penaltys > 0)
                        penaltys--;
                    break;
                case "Cannon":
                    curscore+=10;
                    cannonState++;
                    //TODO need to shut down the bridge for 15 seconds
                    break;
                case "Piles":
                    print("piles are here!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    this.jo = jo;
                    if(!hasPile)
                        AppGetter.init().addPileToStorage(getPiles(), isRed);
                    hasPile = true;
                    print("piles are there!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    running = false;
                    break;

            }
        }
        pw.close();
        scan.close();
        socket.close();
        dead = true;
    }

    public boolean isDead(){
        return dead;
    }

    @Override
    public void finalize() {
        try {
            stopRunning();
        }catch (Exception x){
        }
    }

    public void kill(){
        running = false;
    }

    public int getTime(){
        return time;
    }

    public String getTeam(){
        if(isRed == null){

        }
        return isRed ? "Red" : "Blue";
    }

    public void setTime(int time){
        this.time = time;
    }

    public void stopRunning(){
        running = false;
    }

    @Override
    public void run() {
        try {
            mainFun();
        }catch (Exception x){

        }
    }

    public void start () {
        //    System.out.println("Starting " +  threadName );
        if (thread == null) {
            thread = new Thread (this, threadName);
            thread.start ();
        }
    }

    public void addCargo(String car, boolean added){
        int i = new Integer(5);
        switch (car){
            case "AllianceCargo":
                i = 0;
                break;
            case "Barrel":
                i = 1;
                break;
            case "Box":
                i = 2;
                break;
            case "Crate":
                i = 3;
                break;
            case "Treasure":
                i = 4;
                break;
        }
        AppGetter.init().addCargo(i,added, isRed);
        if(added){
            cargoDelta[i] ++;
            return;
        }
        if(cargoDelta[i] > 0)
            cargoDelta[i] --;
        return;
    }

    public int getBarrel(){
        return cargoDelta[1];
    }

    public int getAlliance(){
        return cargoDelta[0];
    }

    public int getBox(){
        return cargoDelta[2];
    }

    public int getCrate(){
        return cargoDelta[3];
    }

    public int getTreasure(){
        return cargoDelta[4];
    }

    public int getCannon(){
        return cannonState;
    }

    public int getFouls(){
        return fouls;
    }

    public int getPenaltys(){
        return penaltys;
    }

    public boolean hasPiles(){
        return  hasPile;
    }

    public boolean isAccepted(){
        return accepted;
    }

    public ArrayList<ArrayList<Integer>> getPiles(){
        try {
            if (arr != null && arr.size() != jo.getInt("Length")) {
                JSONArray ja = jo.getJSONArray("Piles");
                JSONObject curJo;
                ArrayList<Integer> al;
                for (int x = 0; x < jo.getInt("Length") -1; x++) {
                    al = new ArrayList<Integer>();
                    curJo = ja.getJSONObject(x);
                    //print("cur json length"+ curJo.getInt("Length"));
                    for (int y = 0; y < curJo.getInt("Length"); y++) {
                        al.add(curJo.getInt("Cargo" + y));
                    }
                    arr.add(al);
                    //print(arr.toString());
                }
            }
        }catch (JSONException x){
            x.printStackTrace();
        }
        //print(arr.toString());
        return arr;
    }

    @Deprecated
    public int getScore(){
        return curscore;
    }

    public static <T> void print(T str){
        if(AppGetter.init().isDebbuging()) {
            System.out.println(str);
        }
    }
}
