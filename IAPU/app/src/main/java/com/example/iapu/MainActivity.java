package com.example.iapu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.iapu.databinding.ActivityMainBinding;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static final int CAMERA = 1;
    private String caminhoDaImagem;
    private Uri uri;
    private BitmapFactory.Options options;
    private Bitmap bmp = null;

    private String currentPhotoPath;
    private Uri photoURI;
    private String dir;

    private String files = new String();
    int cont = 0;

    private ProgressDialog dialog = null;

    /**********  File Path *************/
    private String uploadFilePath;
    //private final String uploadFileName = "service_lifecycle.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.image.setImageResource(R.drawable.camera);

        binding.button.setOnClickListener(
                view -> {
                    usarCamera(view);
                }
        );

        binding.button2.setOnClickListener(
                view ->{
                    enviarFoto(view);
                }
        );

    }


    public void usarCamera(View view) {

        File diretorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        dir = diretorio.getPath();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File imagem = new File(diretorio.getPath() + "/" + imageFileName + ".jpg");
        uri = Uri.fromFile(imagem);

        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intentCamera, CAMERA);
    }

    public void enviarFoto (View view){
    //terminar essa porra!!!!!!!!!!!!!!!!!!!!
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA && resultCode == RESULT_OK) {

            Intent novaIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            sendBroadcast(novaIntent);
            caminhoDaImagem = uri.getPath();
            Log.e("teste2", caminhoDaImagem);
            files = caminhoDaImagem;
            try {
                bmp = BitmapFactory.decodeFile(caminhoDaImagem);
            } catch (Exception e) {
                e.printStackTrace();
            }
            binding.image.setImageBitmap(bmp);
        }

    }



}