import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by ofeke on 7/30/2018.
 */
public class testing {

    public static void main(String[] args) throws  Exception{
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        JSONObject jo = new JSONObject();
        jo.put("Length", 10);
        JSONArray ja = new JSONArray();
        for(int i = 0; i < jo.getInt("Length"); i++){
            JSONObject j = new JSONObject();
            j.put("Pile", i);
            j.put("Length", 1);
            j.put("Cargo0", 5);
            ja.put(j);
        }
        jo.put("Piles", ja);
        print(getPiles(arr, jo).toString());
    }

    public static ArrayList<ArrayList<Integer>> getPiles(ArrayList<ArrayList<Integer>> arr, JSONObject jo){
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

    public static <T> void print(T str){
        System.out.println(str);
    }
}
