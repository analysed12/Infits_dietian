package com.ultimate.infits;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jetbrains.annotations.NotNull;
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
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClientHealthDetails extends Fragment {

    String encoded;
    String type;
    String urlUpload = String.format("%suploadFileForHealth.php", DataFromDatabase.ipConfig);
    String urlGet = String.format("%sgetFiles.php", DataFromDatabase.ipConfig);
    String urlDelete = String.format("%sdelete.php",DataFromDatabase.ipConfig);
    String urlRename = String.format("%srename.php",DataFromDatabase.ipConfig);
    FileDialogInterface openDialog;

    ActivityResultLauncher<String> file = registerForActivityResult(
            new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {

                    if (result != null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    System.out.println(result);
                                    try {
                                        System.out.println("In try");
                                        File file = getFile(getContext(), result);
                                        System.out.println("after file");
                                        Scanner myReader = new Scanner(file);
                                        System.out.println("after scan");
//                     byte[] fileContent = Files.readAllBytes(file.toPath());
                                        int size = (int) file.length();
                                        byte[] bytes = new byte[size];
                                        try {
                                            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                                            buf.read(bytes, 0, bytes.length);
                                            buf.close();
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);

                                        System.out.println(encoded);

                                        String[] arr = getContext().getContentResolver().getType(result).split("/");
                                        type = arr[1];

                                        System.out.println(getContext().getContentResolver().getType(result));
                                        while (myReader.hasNextLine()) {
                                            System.out.println("in while");
                                            String data = myReader.nextLine();
                                            System.out.println(data);
                                        }
                                        myReader.close();
                                    } catch (FileNotFoundException e) {
                                        System.out.println("An error occurred.");
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        System.out.println("some error occurred.");
                                        e.printStackTrace();
                                    }
                                    StringRequest moveFile = new StringRequest(Request.Method.POST, urlUpload, response -> {
                                        System.out.println(response);
                                        if (response.equals("success")) {
                                            Toast.makeText(getContext(), "File uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                        if (response.equals("failure")) {
                                            Toast.makeText(getContext(), "File not uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                    }, error -> {
                                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                    }) {
                                        @Nullable
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            SimpleDateFormat dateFormat = new SimpleDateFormat("d-m-yyyy,H:m:s");

                                            Map<String, String> data = new HashMap<>();

                                            data.put("clientID", ClientDetails.clientID);
                                            data.put("dietianuserID", DataFromDatabase.dietitianuserID);
                                            data.put("upload_date", dateFormat.format(new Date()));
                                            data.put("type", type);
                                            data.put("file", encoded);

                                            return data;
                                        }
                                    };
                                    Volley.newRequestQueue(getContext()).add(moveFile);
                                }catch (Exception memoryError){
                                    Toast.makeText(getActivity().getApplicationContext(),"Too big file",Toast.LENGTH_LONG).show();
                                }
                            }
                        }).start();
                    }
                }
            }
    );

//    public static File dir = new File(new File(Environment.getExternalStorageDirectory(), "bleh"), "bleh");

    RecyclerView filesList;

    ImageView chooseFile;

    TextView edit, link;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ClientHealthDetails() {

    }

    public static ClientHealthDetails newInstance(String param1, String param2) {
        ClientHealthDetails fragment = new ClientHealthDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_health_details, container, false);
        ;
        filesList = view.findViewById(R.id.files_list);
        edit = view.findViewById(R.id.edit_btn);
        link = view.findViewById(R.id.link);
        chooseFile = view.findViewById(R.id.choose_file);

        ArrayList<String> filename = new ArrayList<>();
        ArrayList<String> filedate = new ArrayList<>();
        ArrayList<String> icon = new ArrayList<>();

        StringRequest getClientFiles = new StringRequest(Request.Method.POST, urlGet, response -> {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("files");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject files = jsonArray.getJSONObject(i);
                    String file = files.getString("files");
                    String upload_date = files.getString("upload_date");
                    String type = files.getString("type");

                    filename.add(file);
                    filedate.add(upload_date);
                    icon.add(type);

                    filesList.setAdapter(new ClientHealthFileAdpater(getContext(), filename, filedate, icon, openDialog));
                    filesList.setLayoutManager(new LinearLayoutManager(getContext()));

                    System.out.println(file + " " + upload_date + " " + type);
                }

            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }

        }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> data = new HashMap<>();

                data.put("clientID", ClientDetails.clientID);
                data.put("dietianuserID", DataFromDatabase.dietitianuserID);

                return data;
            }
        };

        Volley.newRequestQueue(getContext()).add(getClientFiles);

        openDialog = position -> {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.files_overlay_actions);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(true);

            TextView filenameTxt = dialog.findViewById(R.id.filename);
            ImageView type = dialog.findViewById(R.id.type);
            LinearLayout delete = dialog.findViewById(R.id.delete);
            LinearLayout rename = dialog.findViewById(R.id.rename);
            LinearLayout download = dialog.findViewById(R.id.download);

            filenameTxt.setText(filename.get(position));

            if (icon.get(position).equals("pdf")) {
                type.setImageDrawable(getContext().getDrawable(R.drawable.pdf_icon));
            }
            if (icon.get(position).equals("docx")) {
                type.setImageDrawable(getContext().getDrawable(R.drawable.word_icon));
            }
            if (icon.get(position).equals("png") | icon.get(position).equals("jpg") | icon.get(position).equals("jpeg")) {
                type.setImageDrawable(getContext().getDrawable(R.drawable.png_icon));
            }

            delete.setOnClickListener(deleteView->{
                StringRequest deleteRequest = new StringRequest(Request.Method.POST,urlDelete,response -> {
                    System.out.println(response);
                    if (response.equals("success")){
                        Toast.makeText(getContext(), "File deleted", Toast.LENGTH_SHORT).show();
                    }
                    if (!response.equals("success")){
                        Toast.makeText(getContext(), "File not deleted", Toast.LENGTH_SHORT).show();
                    }

                },error -> {

                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> data = new HashMap<>();

                        data.put("clientID",ClientDetails.clientID);
                        data.put("dietitianID",DataFromDatabase.dietitianuserID);
                        data.put("filename",filename.get(position));
                        data.put("upload_date",filedate.get(position));
                        data.put("type",icon.get(position));

                        return data;
                    }
                };
                Volley.newRequestQueue(getContext()).add(deleteRequest);
            });

            rename.setOnClickListener(deleteView->{

                Dialog dialogRename = new Dialog(getContext());
                dialogRename.setContentView(R.layout.add_link);
                dialogRename.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialogRename.setCancelable(true);

                EditText linkEdt = dialogRename.findViewById(R.id.link);

                TextView done = dialogRename.findViewById(R.id.done);
                TextView cancel = dialogRename.findViewById(R.id.cancel);
                TextView title = dialogRename.findViewById(R.id.title);

                title.setText("Rename");

                cancel.setOnClickListener(can -> {
                    dialogRename.dismiss();
                });

                done.setOnClickListener(don -> {
                    String new_name = linkEdt.getText().toString();
                    StringRequest deleteRequest = new StringRequest(Request.Method.POST,urlRename,response -> {
                        System.out.println(response);
                        if (response.equals("success")){
                            Toast.makeText(getContext(), "File renamed", Toast.LENGTH_SHORT).show();
                        }
                        if (!response.equals("success")){
                            Toast.makeText(getContext(), "File not renamed", Toast.LENGTH_SHORT).show();
                        }

                    },error -> {

                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String> data = new HashMap<>();

                            data.put("clientID",ClientDetails.clientID);
                            data.put("dietitianID",DataFromDatabase.dietitianuserID);
                            data.put("filename",filename.get(position));
                            data.put("upload_date",filedate.get(position));
                            data.put("type",icon.get(position));
                            data.put("new_name",new_name);

                            return data;
                        }
                    };
                    Volley.newRequestQueue(getContext()).add(deleteRequest);
                    dialogRename.dismiss();
                });

                dialogRename.show();
            });

            download.setOnClickListener(downloadView -> {
                System.out.println(icon.get(position));
                String url = String.format("%supload/FilesForHealthDetails/%s.%s", DataFromDatabase.ipConfig, filename.get(position),icon.get(position));
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                String title = URLUtil.guessFileName(url, null, null);

                request.setTitle(title);
                request.setDescription("Downloading file please wait.......");
                String cookie = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookie);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);

                DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);
            });

            dialog.show();
        };

        edit.setOnClickListener(v -> {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.add_link);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setCancelable(true);

            EditText linkEdt = dialog.findViewById(R.id.link);

            TextView done = dialog.findViewById(R.id.done);
            TextView cancel = dialog.findViewById(R.id.cancel);

            cancel.setOnClickListener(can -> {
                dialog.dismiss();
            });

            done.setOnClickListener(don -> {
                link.setText(linkEdt.getText().toString());
            });

            dialog.show();
        });

        chooseFile.setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("*/*");
            file.launch("*/*");
        });

//        filesList.setAdapter(new ClientHealthFileAdpater(getContext()));

        return view;
    }

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
}