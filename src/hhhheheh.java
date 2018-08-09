import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by ofeke on 7/30/2018.
 */
public class hhhheheh {
    public static void main(String[] args) throws Exception{
        AppGetter app = AppGetter.init();
        app.start();
        while(!app.doesHavePiles()){

        }
        System.out.println("We Are Here");
        app.setPiles();
        System.out.println("I am Here");
        System.out.println("FBYFYGYVGF"+(app.getPilesCargo(true).get(0).get(0) == Cargo.Barrel));

    }

    private static void setPiles(JSONObject jo, ArrayList<ArrayList<Integer>> arr) throws JSONException{
        JSONArray ja = jo.getJSONArray("Piles");
        JSONObject curJo;
        ArrayList<Integer> al;
        for(int x = 0; x < jo.getInt("Length"); x++){
            al = new ArrayList<Integer>();
            curJo = ja.getJSONObject(x);
          //  print("cur json length"+ curJo.getInt("Length"));
            for(int y = 0 ; y < curJo.getInt("Length"); y++){
                al.add(curJo.getInt("Cargo"+ y));
            }
            arr.add(al);
        }
        print(arr.toString());
 //       hasPile = true;
    }
    public static <T> void print(T str){
        System.out.println(str);
    }
}
