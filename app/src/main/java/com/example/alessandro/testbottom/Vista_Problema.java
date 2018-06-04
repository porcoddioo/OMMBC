package com.example.alessandro.testbottom;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.github.kexanie.library.MathView;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class Vista_Problema extends AppCompatActivity {

    private Problema nproblema;
    private int favorito = 0;
    private FloatingActionButton fab_favoritos;
    private FloatingActionButton fab_ayuda;
    private FloatingActionButton fab_enviar;
    private final int REQUEST_CAMERA = 0, SELECT_FILE = 1,REQUEST_GALLERY_READ=2,REQUEST_GALLERY_WRITE = 4;
    private String imgPath = null;
    private ImageView imageView = null;
    private Bitmap bitmap = null;
    private InputStream inputStreamImg = null;
    private File destination = null;
    private MathView desc;
    private LaTex texto_problema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista__problema);
        fab_ayuda = (FloatingActionButton)findViewById(R.id.fab_ayuda);
        fab_enviar = (FloatingActionButton)findViewById(R.id.fab_enviar);
        fab_favoritos = (FloatingActionButton)findViewById(R.id.fab_favoritos);
        desc = (MathView) findViewById(R.id.formula_one);
        nproblema = (Problema) getIntent().getSerializableExtra("Problema");
        getSupportActionBar().setTitle(nproblema.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        texto_problema = new LaTex(nproblema.getDesc());
        desc.setText(texto_problema.get());

        fab_ayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","correo@ejemplo.mx", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ayuda problema "+nproblema.getName());
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Necesito ayuda con el problema "+nproblema.getName());
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
        fab_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();            }
        });
        fab_favoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favorito==0) {
                    fab_favoritos.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                    favorito = 1;
                }
                else {
                    fab_favoritos.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                    favorito = 0;
                }
            }
        });
    }

    public void ShowToast (String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectImage() {
        try {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if ( permissionCheck == PERMISSION_GRANTED)  {
                final CharSequence[] options = {"Tomar Fotografía", "Seleccionar de la galería","Cancelar"};
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setTitle("Seleccionar Opción");
                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Tomar Fotografía")) {
                            dialog.dismiss();
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_CAMERA);
                        } else if (options[item].equals("Seleccionar de la galería")) {
                            dialog.dismiss();
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, SELECT_FILE);
                        } else if (options[item].equals("Cancelar")) {
                            dialog.dismiss();
                        }
                    }

                });
                builder.show();
            } else{
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error without check", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;
        if (requestCode == REQUEST_CAMERA) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Camera::>>> ");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                destination = new File(Environment.getExternalStorageDirectory() + "/" +
                        getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                FileOutputStream fo;

                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgPath = destination.getAbsolutePath();
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == SELECT_FILE ) {
            if(data != null){
                Uri selectedImage = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                    Log.e("Activity", "Pick from Gallery::>>> ");
                    imgPath = getRealPathFromURI(selectedImage);
                    destination = new File(imgPath.toString());
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else{
                this.closeContextMenu();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}
