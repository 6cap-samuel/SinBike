package com.example.sinbike.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinbike.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ReportFaultsActivity extends AppCompatActivity implements OnClickListener {

    Button uploadImageBtn, scanQrBtn, submitButton;
    private Bitmap bitmap = null;
    public ImageView imageview;
    private ImageButton gearImageButton, wheelImageButton, saddleImageButton, pedalImageButton, brakeImageButton, otherImageButton;
    private EditText inputDescription;
    String tempDescription, tempFaultCategory, image, description, faultCategory, tag, scanResult, path;
    Long faultId;
    DatabaseReference rootRef, bicycleRef;
    private static final String IMAGE_DIRECTORY = "/SinBike Images";
    private int GALLERY = 1, CAMERA = 2;

    public TextView qrCodeResult;
    private ArrayList<String> lst = new ArrayList<>();
    ArrayList<String> tempImage = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_faults);

        requestMultiplePermissions();

        initViews();

        FirebaseApp.initializeApp(this);

        bicycleRef = FirebaseDatabase.getInstance().getReference("bicycle");

        bicycleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot result) {
                for (DataSnapshot dsp : result.getChildren()) {
                    Item listItem = dsp.getValue(Item.class); //add result into array list
                    if (listItem != null) {
                        lst.add(listItem.getBicycleID());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //database reference pointing to root of database
        rootRef = FirebaseDatabase.getInstance().getReference("faults");

        //database reference pointing to demo node
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    faultId = (dataSnapshot.getChildrenCount());
                getQrResult();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Report Faulty Bicycle");

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportFaultsActivity.this , ManageDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidation()) {
                    // getImage();

                    for(int i = 0; i<tempImage.size(); i++){
                        rootRef.child(String.valueOf(faultId)).child("imageName"+i).setValue(tempImage.get(i));
                    }

                    description = getDescription();
                    faultCategory = tempFaultCategory;

                    rootRef.child(String.valueOf(faultId)).child("bicycleID").setValue(qrCodeResult.getText());
                    rootRef.child(String.valueOf(faultId)).child("inputDescription").setValue(description);
                    rootRef.child(String.valueOf(faultId)).child("faultsCategory").setValue(faultCategory);
                    showAddItemDialog(ReportFaultsActivity.this);
                }
            }
        });
    }

    public boolean getQrResult (){
        Bundle qrcodes = getIntent().getExtras();
        if(qrcodes!=null) {
            for (int i = 0; i < lst.size(); i++) {
                scanResult = qrcodes.getString("barcode");
                if (scanResult != null) {
                    if (scanResult.trim().equals(lst.get(i).trim())) {
                        qrCodeResult.setText(scanResult);
                        return true;
                    }
                }
            }
        }Toast.makeText(ReportFaultsActivity.this, "Invalid Qrcode. Please scan again!",
                Toast.LENGTH_SHORT).show(); return false;
    }

    private void showAddItemDialog(Context c) {
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setMessage("Thank you for your feedback. " +
                        "Our operation team will collect, fix the bike, and redeploy for further use as soon as possible. " +
                        "Further clarifications, you can email to us at support.SinBike@hotmail.com")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(ReportFaultsActivity.this, ManageDashboardActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .create();
        dialog.show();
    }

    public boolean checkValidation() {
        if (qrCodeResult.length() <=0 || bitmap == null || inputDescription.length() <=0 || tempFaultCategory == null){
            Toast.makeText(ReportFaultsActivity.this, "Please fill up all the information!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public String getDescription() {
        tempDescription = inputDescription.getText().toString();
        return tempDescription;
    }

    private void initViews() {
        imageview = findViewById(R.id.image);
        uploadImageBtn = findViewById(R.id.uploadImageBtn);
        scanQrBtn = findViewById(R.id.scanQrBtn);
        inputDescription = findViewById(R.id.inputDescription);
        qrCodeResult = findViewById(R.id.qrCodeResult);
        inputDescription = findViewById(R.id.inputDescription);
        submitButton = findViewById(R.id.submitButton);
        gearImageButton = findViewById(R.id.gearImageButton);
        wheelImageButton = findViewById(R.id.wheelImageButton);
        saddleImageButton = findViewById(R.id.saddleImageButton);
        pedalImageButton = findViewById(R.id.pedalImageButton);
        brakeImageButton = findViewById(R.id.brakeImageButton);
        otherImageButton = findViewById(R.id.otherImageButton);
        gearImageButton.setTag("gear");
        wheelImageButton.setTag("wheel");
        saddleImageButton.setTag("saddle");
        pedalImageButton.setTag("pedal");
        brakeImageButton.setTag("brake");
        otherImageButton.setTag("other");

        gearImageButton.setOnClickListener(this);
        wheelImageButton.setOnClickListener(this);
        saddleImageButton.setOnClickListener(this);
        pedalImageButton.setOnClickListener(this);
        brakeImageButton.setOnClickListener(this);
        otherImageButton.setOnClickListener(this);

        uploadImageBtn.setOnClickListener(this);
        scanQrBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.uploadImageBtn:
                showPictureDialog();
                break;
            case R.id.scanQrBtn:
                startActivity(new Intent(ReportFaultsActivity.this, ScannedBarcodeActivity.class));
                break;
        }
        tag = String.valueOf(v.getTag());
        switch (tag) {
            case "gear":
                gearImageButton.setSelected(true);
                wheelImageButton.setSelected(false);
                saddleImageButton.setSelected(false);
                pedalImageButton.setSelected(false);
                brakeImageButton.setSelected(false);
                otherImageButton.setSelected(false);
                tempFaultCategory = gearImageButton.getTag().toString();
                break;
            case "wheel":
                wheelImageButton.setSelected(true);
                gearImageButton.setSelected(false);
                saddleImageButton.setSelected(false);
                pedalImageButton.setSelected(false);
                brakeImageButton.setSelected(false);
                otherImageButton.setSelected(false);
                tempFaultCategory = wheelImageButton.getTag().toString();
                break;
            case "saddle":
                saddleImageButton.setSelected(true);
                wheelImageButton.setSelected(false);
                gearImageButton.setSelected(false);
                pedalImageButton.setSelected(false);
                brakeImageButton.setSelected(false);
                otherImageButton.setSelected(false);
                tempFaultCategory = saddleImageButton.getTag().toString();
                break;
            case "pedal":
                pedalImageButton.setSelected(true);
                wheelImageButton.setSelected(false);
                saddleImageButton.setSelected(false);
                gearImageButton.setSelected(false);
                brakeImageButton.setSelected(false);
                otherImageButton.setSelected(false);
                tempFaultCategory = pedalImageButton.getTag().toString();
                break;
            case "brake":
                brakeImageButton.setSelected(true);
                wheelImageButton.setSelected(false);
                saddleImageButton.setSelected(false);
                pedalImageButton.setSelected(false);
                gearImageButton.setSelected(false);
                otherImageButton.setSelected(false);
                tempFaultCategory = brakeImageButton.getTag().toString();
                break;
            case "other":
                otherImageButton.setSelected(true);
                wheelImageButton.setSelected(false);
                saddleImageButton.setSelected(false);
                pedalImageButton.setSelected(false);
                brakeImageButton.setSelected(false);
                gearImageButton.setSelected(false);
                tempFaultCategory = otherImageButton.getTag().toString();
                break;
        }
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Upload Image");
        String[] pictureDialogItems = {
                "Choose photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
        onResume();
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
        onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //uploading.show();
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    path = saveImage(bitmap);
                    tempImage.add(path);
                    Toast.makeText(ReportFaultsActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();

                    final LinearLayout myGallery;
                    myGallery = findViewById(R.id.gallery);

                    final ImageView imageView = new ImageView(getApplicationContext());
                    imageView.setLayoutParams(new LayoutParams(250, 250));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setImageBitmap(bitmap);

                    final ImageButton removeBtn = new ImageButton(getApplicationContext());
                    removeBtn.setLayoutParams(new LayoutParams(70, 70));
                    removeBtn.setBackgroundResource(R.drawable.close_button_background);
                    removeBtn.setBackgroundColor(Color.TRANSPARENT);
                    removeBtn.setScaleType(ImageButton.ScaleType.CENTER_CROP);
                    removeBtn.setImageResource(R.drawable.close);

                    myGallery.addView(imageView);
                    myGallery.addView(removeBtn);

                    removeBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myGallery.removeView(imageView);
                            myGallery.removeView(removeBtn);

                            File file = new File(path);
                            if(file.exists())
                                file.delete();
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(path))));
                        }
                    });

                }catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(ReportFaultsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            path = saveImage(bitmap);
            tempImage.add(path);
            Toast.makeText(ReportFaultsActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();

            final LinearLayout myGallery;
            myGallery = findViewById(R.id.gallery);

            final ImageView imageView = new ImageView(getApplicationContext());
            imageView.setLayoutParams(new LayoutParams(250, 250));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(bitmap);

            final ImageButton removeBtn = new ImageButton(getApplicationContext());
            removeBtn.setLayoutParams(new LayoutParams(70, 70));
            removeBtn.setBackgroundResource(R.drawable.close_button_background);
            removeBtn.setBackgroundColor(Color.TRANSPARENT);
            removeBtn.setScaleType(ImageButton.ScaleType.CENTER_CROP);
            removeBtn.setImageResource(R.drawable.close);

            myGallery.addView(imageView);
            myGallery.addView(removeBtn);

            removeBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    myGallery.removeView(imageView);
                    myGallery.removeView(removeBtn);

                    File file = new File(path);
                    if(file.exists())
                        file.delete();

                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(path))));
                }
            });
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'_'HH:mm:ss");
            String timeStamp = dateFormat.format(new Date());
            File f = new File(wallpaperDirectory, timeStamp + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
}