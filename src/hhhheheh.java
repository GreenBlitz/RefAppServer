import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ofeke on 7/30/2018.
 */
public class hhhheheh {
    public static void main(String[] args) throws Exception{
        ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>>();
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        jo.put("Message", "Piles");
        JSONObject njo;
        Random rand = new Random();
        int len  =100;
        jo.put("Length", len);
        for(int i= 0 ; i< len;i++){
            njo  = new JSONObject();
            njo.put("Pile", i);
            int l = rand.nextInt(10)+1;
            njo.put("Length", l);
            for (int x = 0; x< l; x++){
                njo.put("Cargo"+x, rand.nextInt(5));
            }
            ja.put(njo);
        }
        jo.put("Piles",ja);

        setPiles(jo, arr);
        print(arr.toString());
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
