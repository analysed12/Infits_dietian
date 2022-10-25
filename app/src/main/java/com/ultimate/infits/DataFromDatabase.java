package com.ultimate.infits;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFromDatabase {
    public static Bitmap profile;
    public static String about_me,experience;
    public static Bitmap profileClient;
    public static String foodName;
    HashMap<String,String> map = new HashMap<>();
    public static ArrayList<String> clientsID = new ArrayList<>();
    String url = "http://192.168.1.5/getdietitiandetails.php";
    public static boolean flag= false;
    public static String clientuserID;
    public static String ipConfig = "http://192.168.137.170/infits/";
    public static String profilePhoto="iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAM1BMVEXk5ueutLfn6eqrsbTp6+zg4uOwtrnJzc" +
            "\\/j5earsbW0uby4vcDQ09XGyszU19jd3+G\\/xMamCvwDAAAFLklEQVR4nO2d2bLbIAxAbYE3sDH\\/\\/7WFbPfexG4MiCAcnWmnrzkjIRaD2jQ" +
            "MwzAMwzAMwzAMwzAMwzAMwzAM" +
            "wzAMwzAMwzAMw5wQkHJczewxZh2lhNK\\/CBOQo1n0JIT74\\/H\\/qMV0Z7GU3aCcVPuEE1XDCtVLAh" +
            "gtpme7H0s1N1U7QjO0L8F7llzGeh1hEG\\/8Lo7TUmmuSrOfns9xnGXpXxsONPpA\\/B6OqqstjC6Ax\\/0ujkNdYQQbKNi2k64qiiEZ+o" +
            "hi35X+2YcZw\\/WujmslYewiAliVYrxgJYrdwUmwXsU+RdApUi83oNIE27YvrfB\\/ZPg8+BJETXnqh9CVzBbTQHgojgiCvtqU9thFJg\\/CKz" +
            "3VIMKMEkIXxIWqIpIg2SkjYj+xC816mrJae2aiWGykxRNsW0UwiJghJDljYI5CD8GRiCtIsJxizYUPQ2pzItZy5pcisTRdk\\/a9m4amtN" +
            "NfBuQkdVhSaYqfpNTSFGfb9GRIakrE2Pm+GFLaCQPqiu0OpWP+HMPQQcgQMiQprWXNmsVwIjQjYi\\/ZrhAqNTCgr2gu0Jnz85RSSjso0HkMFZ0" +
            "YZjKkc26a\\/jlmh9JiDyDxi9oeorTYAzZkwwoMz19pzj9bnH\\/GP\\/+qbchjSGflneWYhtTuKdMOmNKZcJ5TjInQKcYXnESd\\/jQxy0ENpUL" +
            "TNGOGgxpap\\/oyw9pbUAqhfx2Dbkhovvfgz4iUzoM9+GlK6\\/Mh4q29hyC1mwro30hpVVLPF9wYQr71RazOeM5\\/cw81iBRD+A03aM9\\/C\\" +
            "/obbrKjbYSpCmIVG3qT\\/Q8oeUo3Rz0IL7vI1tEbCB9pSiu8I\\/aV8x3Kg\\/BGWrWp4ZVs0nZfmAoEG4h\\/61yHYIJiFSl6Q0Vk6tTW1N8kYp" +
            "8hdOkfHYYMXd2Qft+8CYwqYDSKvqIh+MCF8Wgca2u\\/cwdgeW3TtuVn6+1oBs3yLo5C2JpK6CvQzGpfUkz9UG\\/87gCsi5o2LIXolxN0FbwAsjOL" +
            "Er+YJmXn7iR6N0BCt5p5cMxm7eAsfS+\\/CACQf4CTpKjzgkvr2cVarVTf96372yut7XLJ1sa7lv6VcfgYrWaxqr3Wlo1S6pvStr22sxOtTNPLzdY" +
            "3nj20bPP+ejFdJYkLsjGLdtPBEbe\\/mr2bQKiXWJDroA+vtzc0p9aahuwqHMDYrQEXHEw9jwQl3drMpts9JBU1SdktPe5FBRdJQ6bwXBpa57ib2A" +
            "8kukQDzMjh++Uo7Fo6Wd02Pkf4fknqoo4HtvAIjsqUcjx6DIPgWCaOML9rKI\\/oqD9\\/lgNrn+eF+p7j8tnzHBiR7+kdUGw\\/+V1Kzkc75mMy" +
            "6U+FMaxjPibiM1U1uGM+puInHpmALZCgP4pt7i840MV8+0R1zPsRB6UTcqpizncYwZ89syDydfyWCwXB1l8\\/zRNGWbTG\\/GHKUm9AkxHMc\\/E" +
            "GSk3z2+ArEhPEV5TUBLEvUGFcjEUH80J\\/jveTGOAJEljJbILWGQT3zRYiwuKsUXN1EEJAzBhRJFll7mBUG7KD8EqPkKekBREaL8hMDZLQSG6AQjt" +
            "HPYmvTQnX0TtpC1SYCe2YdkkyLP3jj5BSbKiuR585eQhTgoje6yIb0Yb0C+mV6EYvebqw5SDy2WmubogZiF2AVxPC2FpDf8H2Q9QWo6IkjUxTWV" +
            "EI3WY\\/wrCeSuqJ+eRWzXR\\/JXwgVjUMozbCOfoEZiSiKVGepqv5CJ8RyR4D7xBeamqa7z3BJ\\/z17JxuBPdv93d\\/a2Ki878MMAzDMAzDMA" +
            "zDMAzDMF\\/KP09VUmxBAiI3AAAAAElFTkSuQmCC";

    public static String dietitianuserID,password,name,qualification,email,mobile,location,age,gender;

    public static String categoryChoosen ;
    public static String timeChoosen ;
    public static Bitmap foodImage ;
    public static String nutritions ;
    public static String ingredients ;
    public static String utensil ;

//    private void getDetails(){
//
//        if (flag==true){
//            Log.i("userID",userID);
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//                        JSONObject object =new JSONObject();
//                        String dietitianuserID= object.getString("dietitianuserID");
//                        String name=object.getString("name");
//                        String qualification=object.getString("qualification");
//                        String email=object.getString("email");
//                        String mobile=object.getString("mobile");
//                        String profilePhoto=object.getString("profilePhoto");
//                        String location=object.getString("location");
//                        String age= object.getString("age");
//                        String gender = object.getString("gender");
//
//                        map.put("userID",dietitianuserID);
//                        map.put("name",name);
//                        map.put("qualification",qualification);
//                        map.put("email",email);
//                        map.put("mobile",mobile);
//                        map.put("profilePhoto",profilePhoto);
//                        map.put("location",location);
//                        map.put("age",age);
//                        map.put("gender",gender);
//                        Log.i("data",dietitianuserID+" "+name+" "+qualification+" "+email+" "+
//                                mobile+" "+profilePhoto+" "+location+" "+age+" "+gender);
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(null,error.toString(),Toast.LENGTH_SHORT).show();
//                }
//            });
//            Volley.newRequestQueue(null).add(stringRequest);
//        }
//        else {
//            Log.i("Flag token", String.valueOf(flag));
//        }
//
//    }
}
