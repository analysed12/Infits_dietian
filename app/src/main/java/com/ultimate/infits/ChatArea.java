package com.ultimate.infits;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ultimate.infits.adapter.ChatMessageAdapter;
//import com.ultimate.infits.databinding.ActivityChatAreaBinding;
import com.ultimate.infits.model.ChatMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatArea extends AppCompatActivity {
//
//    private ActivityChatAreaBinding binding;
//    public ChatArea(ActivityChatAreaBinding binding) {
//        this.binding = binding;
//    }

    String urlGetFile = String.format("%sgetfilesmessage.php",DataFromDatabase.ipConfig);


    public static String chat_area_client_name;
    TextView name;
    String encoded;
    EditText message;
    ImageView profile_pic;
    String url = String.format("%smessagesClient.php", DataFromDatabase.ipConfig);
    String url3 = String.format("%smessagesSend.php", DataFromDatabase.ipConfig);
    List<ChatMessage> msg = new ArrayList<>();
    ChatMessageAdapter ad1;
    RecyclerView r1;
    ImageView send;
    DownloadFileForMessages downloadFile;

    String type = "text";

    public ChatArea() {

    }

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.5.91:8080");
        } catch (URISyntaxException e) {
            System.out.println("Something is wrong");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_area);

        r1 = findViewById(R.id.FrameContainerMessages);

        send = findViewById(R.id.send_message_btn);
        chat_area_client_name = DataFromDatabase.clientuserID;
        name = findViewById(R.id.chat_area_client_name);
        name.setText(chat_area_client_name);
        profile_pic = findViewById(R.id.chat_area_profile_pic);
        message = findViewById(R.id.typed_message);

        ActivityResultLauncher<String> file = registerForActivityResult(
                new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            try {
                                File file = getFile(getApplicationContext(), result);
                                File fileToSend = new File(result.getPath());
                                Log.d("File Plain",file.getName());
                                System.out.println("FileTo Send "+fileToSend.getPath()+fileToSend.getName()+"  "+fileToSend.length());
                                if ((file.length() / 1024) / 1024 <= 20) {
                                    try {
                                        Scanner myReader = new Scanner(file);
                                        int size = (int) file.length();
                                        byte[] bytes = new byte[size];
                                        try {
                                            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));

                                            buf.read(bytes, 0, bytes.length);
                                            buf.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);

//                                        System.out.println(encoded);

                                        String[] arr = getApplicationContext().getContentResolver().getType(result).split("/");
                                        type = arr[1];

                                        System.out.println(getApplicationContext().getContentResolver().getType(result));
                                        while (myReader.hasNextLine()) {
                                            String data = myReader.nextLine();
                                        }
                                        if (type.equals("jpg") || type.equals("jpeg") || type.equals("png")) {
                                            Bitmap image =  MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                                            sendImage(image);
                                        }
                                        if (type.equals("pdf")) {
                                            mSocket.emit("file", encoded, file.getName(), DataFromDatabase.dietitianuserID, chat_area_client_name, DataFromDatabase.dietitianuserID + chat_area_client_name, type);
                                        }
                                        myReader.close();
                                    } catch (FileNotFoundException e) {
                                        System.out.println("An error occurred.");
                                        e.printStackTrace();
                                    }
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url3, response -> {
                                        System.out.println(response);
                                        if (response.equals("failure")) {
                                            Toast.makeText(getApplicationContext(), "unable to send message!! try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }, error -> {
                                        Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                                    }) {
                                        @Nullable
                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> data = new HashMap<>();
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
                                            SimpleDateFormat sdfFile = new SimpleDateFormat("yyyyMddhhmmss");
                                            Date date = new Date();
                                            System.out.println(encoded);
                                            System.out.println(file.getName());
                                            data.put("time",sdf.format(date));
                                            data.put("dieticianID", DataFromDatabase.dietitianuserID);
                                            data.put("clientID", chat_area_client_name);
                                            data.put("message", encoded);
                                            data.put("type", type);
                                            data.put("filename",sdfFile.format(date)+file.getName());
                                            data.put("sentBy", "dietian");
                                            return data;
                                        }
                                    };
                                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                    requestQueue.add(stringRequest);
                                    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                                    ChatMessage obj = new ChatMessage(DataFromDatabase.dietitianuserID, chat_area_client_name, encoded, currentTime.substring(0, 5), "dietian", "U", type,file.getName());
                                    msg.add(obj);
                                    ad1 = new ChatMessageAdapter(msg, "deitian",downloadFile);
                                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                                    manager.setStackFromEnd(true);
                                    r1.setLayoutManager(manager);
                                    r1.setAdapter(ad1);
                                    r1.setVisibility(View.VISIBLE);
                                }
                                else{
                                    Toast.makeText(ChatArea.this, "File size too big", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception memoryError) {
                                Toast.makeText(getApplicationContext().getApplicationContext(), "Too big file", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
        );

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        manager.setStackFromEnd(true);

        send.setOnClickListener(v -> {
            sendMessage();
        });
        ImageView i12 = findViewById(R.id.attach_file);
        i12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file.launch("*/*");
            }
        });

        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.connect();
        mSocket.on("message", onReceived);
        mSocket.on("image-recive", onReceivedImages);
        mSocket.on("file-recive", onReceivedFiles);
//        mSocket.on("on typing", onTyping);
        mSocket.emit("new-user", DataFromDatabase.dietitianuserID, DataFromDatabase.dietitianuserID + chat_area_client_name);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            if (!response.equals("failure")) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    String messageby = null;
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String message = jsonObject.getString("message");
                            messageby = jsonObject.getString("messageBy");
                            String time = jsonObject.getString("time").substring(11, 16);
                            String readUnread = "r";
                            String type = jsonObject.getString("type");
                            String fileName = jsonObject.getString("filename");
                            ChatMessage obj = new ChatMessage(DataFromDatabase.dietitianuserID, chat_area_client_name, message, time, messageby, readUnread, type,fileName);
                            msg.add(obj);
                        }
                        ad1 = new ChatMessageAdapter(msg, messageby,downloadFile);
                        r1.setLayoutManager(manager);
                        r1.setAdapter(ad1);
                        r1.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if (response.equals("failure")) {
                Toast.makeText(getApplicationContext(), "ChatArea failed", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
        }) {
            @org.jetbrains.annotations.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("duserID", DataFromDatabase.dietitianuserID);
                data.put("cuserID", chat_area_client_name);
                return data;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

        downloadFile = new DownloadFileForMessages() {
            @Override
            public void downloadFile(String nameOfFile) {
                System.out.println(nameOfFile);
                File fileToDownloaded = new File(Environment.getExternalStorageDirectory()+"/Infits/documents/"+nameOfFile);
                if (fileToDownloaded.exists()){
                    Toast.makeText(ChatArea.this, "File exists", Toast.LENGTH_SHORT).show();
                    openPDF(fileToDownloaded);
                }
                else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,urlGetFile,response -> {
                        try {
                            System.out.println(response);
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            String base = jsonObject.getString("file");
                            System.out.println(base);
                            File file = new File(Environment.getExternalStorageDirectory() + "/Infits/documents/"+nameOfFile);
                            byte[] pdfAsBytes = Base64.decode(base, 0);
                            System.out.println(Arrays.toString(pdfAsBytes));
                            FileOutputStream os;
                            if (file.exists()){
                                Toast.makeText(ChatArea.this, "file already exists", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                os = new FileOutputStream(file, false);
                                os.write(pdfAsBytes);
                                os.flush();
                                os.close();
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    },error -> {
                        System.out.println(error);
                    })
                    {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> data = new HashMap();
                            data.put("filename",nameOfFile);
                            return data;
                        }
                    };
                    new Volley().newRequestQueue(getApplicationContext()).add(stringRequest);
                }
            }
        };
    }

    private void sendMessage() {
        //insert to db
        String typed_message = message.getEditableText().toString().trim();
        message.setText("");
        type = "text";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url3, response -> {
            System.out.println(response);
            if (response.equals("success")) {
                Log.d("ChatArea3", "success");
                Log.d("response ChatArea3", response);
            } else if (response.equals("failure")) {
                Log.d("ChatArea3", "failure");
                Toast.makeText(getApplicationContext(), "unable to send message!! try again", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
                SimpleDateFormat sdfFile = new SimpleDateFormat("yyyyMddhhmmss");
                Date date = new Date();
                data.put("time",sdf.format(date));
                data.put("dieticianID", DataFromDatabase.dietitianuserID);
                data.put("clientID", chat_area_client_name);
                data.put("message", typed_message);
                data.put("type", type);
                data.put("filename","");
                data.put("sentBy", "dietitian");
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        ChatMessage obj = new ChatMessage(DataFromDatabase.dietitianuserID, chat_area_client_name, typed_message, currentTime.substring(0, 5), "dietitian", "U", type,"");
        msg.add(obj);
        ad1 = new ChatMessageAdapter(msg, "dietitian",downloadFile);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        manager.setStackFromEnd(true);
        r1.setLayoutManager(manager);
        r1.setAdapter(ad1);
        r1.setVisibility(View.VISIBLE);
        mSocket.emit("new message", typed_message, DataFromDatabase.dietitianuserID, chat_area_client_name, DataFromDatabase.dietitianuserID + chat_area_client_name);
    }

    private final Emitter.Listener onConnect = args -> runOnUiThread(new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
        }
    });

    private final Emitter.Listener onReceived = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                JSONObject data = (JSONObject) args[0];
                String message, sender, receiver;
                try {
                    sender = data.getString("sender");
                    receiver = data.getString("receiver");
                    message = data.getString("message");
                    if (receiver.equals(DataFromDatabase.dietitianuserID) && sender.equals(chat_area_client_name)) {
                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        ChatMessage obj = new ChatMessage(sender, receiver, message, String.valueOf(currentTime.substring(0, 5)), "dietitian", "U", "text","");
                        if (sender.equals(chat_area_client_name) && receiver.equals(DataFromDatabase.dietitianuserID)) {
                            obj = new ChatMessage(sender, receiver, message, String.valueOf(currentTime.substring(0, 5)), "client", "U", "text","");
                        } else if (receiver.equals(chat_area_client_name) && sender.equals(DataFromDatabase.dietitianuserID)) {
                            obj = new ChatMessage(sender, receiver, message, String.valueOf(currentTime.substring(0, 5)), "dietian", "U", "text","");
                        }
                        msg.add(obj);
                        ad1 = new ChatMessageAdapter(msg, "dietitian",downloadFile);
                        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                        manager.setStackFromEnd(true);
                        r1.setLayoutManager(manager);
                        r1.setAdapter(ad1);
                        r1.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Log.d("exception", String.valueOf(e));
                }
                Log.d("args", String.valueOf(args[0]));
            });
        }
    };
    private final Emitter.Listener onReceivedImages = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                Toast.makeText(ChatArea.this, "Hi", Toast.LENGTH_SHORT).show();
                JSONObject data = (JSONObject) args[0];
                String message, sender, receiver;
                try {
                    sender = data.getString("sender");
                    receiver = data.getString("receiver");
                    message = data.getString("message");
                    String type = data.getString("type");
                    if (receiver.equals(DataFromDatabase.dietitianuserID) && sender.equals(chat_area_client_name)) {
                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        ChatMessage obj = new ChatMessage(sender, receiver, message, String.valueOf(currentTime.substring(0, 5)), "dietitian", "U", type,"");
                        if (sender.equals(chat_area_client_name) && receiver.equals(DataFromDatabase.dietitianuserID)) {
                            obj = new ChatMessage(sender, receiver, message, String.valueOf(currentTime.substring(0, 5)), "client", "U", type,"");
                        } else if (receiver.equals(chat_area_client_name) && sender.equals(DataFromDatabase.dietitianuserID)) {
                            obj = new ChatMessage(sender, receiver, message, String.valueOf(currentTime.substring(0, 5)), "dietian", "U", type,"");
                        }
                        msg.add(obj);
                        ad1 = new ChatMessageAdapter(msg, "dietitian",downloadFile);
                        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                        manager.setStackFromEnd(true);
                        r1.setLayoutManager(manager);
                        r1.setAdapter(ad1);
                        r1.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Log.d("exception", String.valueOf(e));
                }
                Log.d("args", String.valueOf(args[0]));
            });
        }
    };

    private final Emitter.Listener onReceivedFiles = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(() -> {
                Toast.makeText(ChatArea.this, "Hi", Toast.LENGTH_SHORT).show();
                JSONObject data = (JSONObject) args[0];
                String message, sender, receiver;
                try {
                    sender = data.getString("sender");
                    receiver = data.getString("receiver");
                    message = data.getString("message");
                    String type = data.getString("type");
                    String filename = data.getString("filename");
                    if (receiver.equals(DataFromDatabase.dietitianuserID) && sender.equals(chat_area_client_name)) {
                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        ChatMessage obj = new ChatMessage(sender, receiver, message, currentTime.substring(0, 5), "dietitian", "U", type,filename);
                        if (sender.equals(chat_area_client_name) && receiver.equals(DataFromDatabase.dietitianuserID)) {
                            obj = new ChatMessage(sender, receiver, message, currentTime.substring(0, 5), "client", "U", type,filename);
                        } else if (receiver.equals(chat_area_client_name) && sender.equals(DataFromDatabase.dietitianuserID)) {
                            obj = new ChatMessage(sender, receiver, message, currentTime.substring(0, 5), "dietian", "U", type,filename);
                        }
                        msg.add(obj);
                        ad1 = new ChatMessageAdapter(msg, "dietitian",downloadFile);
                        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                        manager.setStackFromEnd(true);
                        r1.setLayoutManager(manager);
                        r1.setAdapter(ad1);
                        r1.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Log.d("exception", String.valueOf(e));
                }
                Log.d("args", String.valueOf(args[0]));
            });
        }
    };

//    Emitter.Listener onTyping = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject data = (JSONObject) args[0];
//                    boolean typing = data.getBoolean("typing");
//                    if ()
//                    name.setText(chat_area_client_name+ " is typing");
//                }
//            });
//        }
//    };

    public static File getFile(Context context, Uri uri) throws IOException {
        File destinationFilename = new File(context.getFilesDir().getPath() + File.separatorChar + queryName(context, uri));
        try (InputStream ins = context.getContentResolver().openInputStream(uri)) {
            createFileFromStream(ins, destinationFilename);
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
        return destinationFilename;
    }

    public static void createFileFromStream(InputStream ins, File destination) {
        try (OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ins.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception ex) {
            Log.e("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String queryName(Context context, Uri uri) {
        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    private void sendImage(Bitmap image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        String base64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sender", DataFromDatabase.dietitianuserID);
            jsonObject.put("receiver", chat_area_client_name);
            jsonObject.put("room", DataFromDatabase.dietitianuserID+chat_area_client_name);
            jsonObject.put("image", base64);
            mSocket.emit("image", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void openPDF(File dwldsPath) {
        Intent intentUrl = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", dwldsPath);
        intentUrl.setDataAndType(uri, "application/pdf");
        intentUrl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentUrl.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intentUrl);
    }

}