package com.ultimate.infits;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class LiveAct extends AppCompatActivity {

    String username = "";

    String date = "";

    WebView webView;

    boolean isPeerConnected = true;

    boolean isAudio = true;

    boolean isVideo = true;

    LinearLayout callControlLayout;

    TextView count;

    ImageView toggleAudioBtn,toggleVideoBtn,sendText;
    ImageButton close;

    ArrayList<String> userName = new ArrayList<>();
    ArrayList<Bitmap> chatPics = new ArrayList<>();
    EditText chatBox;

    RecyclerView recyclerView;

    Socket sock;
    private String room;
    {
        try {
            sock = IO.socket("http://192.168.141.91:8000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private final Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
                    sock.emit("join-room",room,username);
//                    sock.on("view-count", view->{
//                        count.setText(view[0].toString());
//                    });
                }
            });
        }
    };

    ArrayList<String> messagesList = new ArrayList<>();

    private final Emitter.Listener onReceived = args -> runOnUiThread(() -> {
        try{
            JSONObject res = (JSONObject)args[0];
            String sender = res.getString("name");
            String message = res.getString("message");
            String photo = res.getString("photo");
            userName.add(sender);
            messagesList.add(message);
            byte[] qrimage = Base64.decode(photo,0);
            Bitmap sendPhoto = BitmapFactory.decodeByteArray(qrimage,0,qrimage.length);
            chatPics.add(sendPhoto);
            recyclerView.setAdapter(new LiveMessageAdapter(getApplicationContext(),messagesList,userName,chatPics));
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }catch (JSONException jsonException){
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        recyclerView = findViewById(R.id.live_chat);
        sendText = findViewById(R.id.send_live_text);
        chatBox = findViewById(R.id.live_chat_box);

        close = findViewById(R.id.close);

        toggleAudioBtn = findViewById(R.id.toggleAudioBtn);

        toggleVideoBtn = findViewById(R.id.toggleVideoBtn);

        webView = findViewById(R.id.webView);

        count = findViewById(R.id.view_count);

        Intent get = getIntent();

//        username = get.getStringExtra("username");
        username =DataFromDatabase.dietitianuserID;
        room = get.getStringExtra("room");
        date = get.getStringExtra("date");
//        user = get.getBooleanExtra("user",false);
        sock.on(Socket.EVENT_CONNECT,onConnect);
        sock.connect();
        sock.emit("new-user",username);
        sock.on("chat-message",onReceived);
        sock.on("view-count", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count.setText(args[0].toString());
                    }
                });
            }
        });

        sock.on("user-connected",userCon->{
                for (Object o : userCon) {
                    callJavaScriptFunction(String.format("javascript:startCall(\"%s\")", o.toString()));
                    Log.d("Azar", o.toString());
                }
        });
        toggleAudioBtn.setOnClickListener (v->{
            isAudio = !isAudio;
            callJavaScriptFunction(String.format("javascript:toggleAudio(\"%s\")",isAudio));
            if (isAudio){
                toggleAudioBtn.setImageResource(R.drawable.mic_on);
            }
            else {
                toggleAudioBtn.setImageResource(R.drawable.mic_off);
            }
        });

        toggleVideoBtn.setOnClickListener(v-> {
            isVideo = !isVideo;
            callJavaScriptFunction(String.format("javascript:toggleVideo(\"%s\")",isVideo));
            if (isVideo){
                toggleVideoBtn.setImageResource(R.drawable.video_on);
            }
            else{
                toggleVideoBtn.setImageResource(R.drawable.video_off);
            }
        });
        setUpWebView();
        sendText.setOnClickListener(v->{
            String message = chatBox.getText().toString();
            if (!message.equals("")){
                userName.add(DataFromDatabase.dietitianuserID);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Bitmap dir = BitmapFactory.decodeResource(getResources(), R.drawable.achivements);
                dir.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                String base64String = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                sock.emit("send-live-chat",room,message,username,base64String);
                messagesList.add(message);
                chatPics.add(DataFromDatabase.profile);
                recyclerView.setAdapter(new LiveMessageAdapter(getApplicationContext(),messagesList,userName,chatPics));
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                chatBox.setText("");
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(LiveAct.this);
//                dialog.requestWindowFeature(Window.);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.end_live_dialog);
                Button yes = dialog.findViewById(R.id.end_live);
                Button no = dialog.findViewById(R.id.cancel_live);
                dialog.show();

                System.out.println("Hi");
                yes.setOnClickListener(view->{
                String url = String.format("%slivesave.php",DataFromDatabase.ipConfig);
                StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
                    System.out.println(response);
                    if (response.equals("success")){
                        Toast.makeText(getApplicationContext(), "save success", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.equals("failure")){
                        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                },error -> {
                    Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();}){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        Map<String,String> data = new HashMap<>();
                        data.put("dietitianuserID",DataFromDatabase.dietitianuserID);
                        data.put("dateandtime",date);
                        data.put("title", room);
                        data.put("viewer","9");
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
                finish();
                sock.disconnect();
            });
            no.setOnClickListener(view->{
                dialog.dismiss();
            });
            }
        });
    }

    void setUpWebView(){
        WebView webView = findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                request.grant(request.getResources());
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.addJavascriptInterface(new JavaScriptInterface(this),"Android");
        loadVideoCall();
    }
    private void loadVideoCall() {
        String filePath = "file:///android_asset/call.html";
        webView.loadUrl(filePath);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                initializePeer();
            }
        });
    }
    private void initializePeer() {
        callJavaScriptFunction(String.format("javascript:init('%s')",username));
    }

    private void onCallRequest(String value) {
        if (value == null) {
            return;
        }
        switchToControls();
    }

    private void switchToControls() {
        callControlLayout.setVisibility(View.VISIBLE);
    }

    void callJavaScriptFunction(String functionName){
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.evaluateJavascript(functionName,null);
            }
        });
    }

    public void onPeerConnected() {
        isPeerConnected =true;
//        sock.emit("join-room", room, username);
    }
    private String getUniqueID(){
        return UUID.randomUUID().toString();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        webView.loadUrl("about:blank");
        super.onDestroy();
    }
}