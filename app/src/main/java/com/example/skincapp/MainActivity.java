
package com.example.skincapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.skincapp.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button selectBtn, captureBtn, predictBtn;
    TextView result;
    ImageView imageView;
    Bitmap bitmap;
    int imageSize = 256;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPermission();

        selectBtn = findViewById(R.id.selectBtn);
        captureBtn = findViewById(R.id.captureBtn);
        //predictBtn = findViewById(R.id.predictBtn);
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);

        captureBtn.setOnClickListener(this::onClick);

        selectBtn.setOnClickListener(view -> {
            Intent intent = new Intent(); //Helps get to gallery of phone being used
            intent.setAction(Intent.ACTION_GET_CONTENT); // The action is set to get images on the device
            intent.setType("image/*");// Specify the type of contents to be accessed by this action
            startActivityForResult(intent, 10);//method for starting the intent and request code is any integer
        });


    }

    void getPermission(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
        if (checkCallingOrSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 11);
        }
        }
    }
    @Override //Permission TO ACCESS Camera
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==11){
            if(grantResults.length>0){
                if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void classifyImage(Bitmap bitmap) {
        //predictBtn.setOnClickListener(view -> {//Predictions are done here
        try {
            Model model = Model.newInstance(MainActivity.this);

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 256, 256, 3}, DataType.FLOAT32);

            bitmap =Bitmap.createScaledBitmap(bitmap,256,256,true);
            TensorImage image=new TensorImage(DataType.FLOAT32);
            image.load(bitmap);
            ByteBuffer byteBuffer=image.getBuffer();
            inputFeature0.loadBuffer(byteBuffer);

            //ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            //byteBuffer.order(ByteOrder.nativeOrder());
            //inputFeature0.loadBuffer(TensorImage.fromBitmap(bitmap).getBuffer());//convert bitmap into ByteBuffer


            int[] intValues = new int[imageSize * imageSize];
            bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

            int pixels = 0;
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValues[pixels++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f));
                    byteBuffer.putFloat(((val & 0xFF) * (1.f)));
                }
            }
            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();//results stored in output Feature0

            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Malign", "Benign"};
            result.setText(classes[maxPos]);

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
            System.out.println(e);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);//contents got from selct image activity stored in Bitmap
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmap =Bitmap.createScaledBitmap(bitmap,256,256,true);

               classifyImage(bitmap);
            }
        }
        else if(requestCode==12){
            bitmap = (Bitmap) Objects.requireNonNull(data).getExtras().get("data");
            int dimension = Math.min(bitmap.getWidth(),bitmap.getHeight());
            bitmap = ThumbnailUtils.extractThumbnail(bitmap,dimension,dimension);
            imageView.setImageBitmap(bitmap);


            classifyImage(bitmap);
        }
            super.onActivityResult(requestCode, resultCode, data);
        }

    private void onClick(View view) {
        if (MainActivity.this.checkCallingOrSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            MainActivity.this.startActivityForResult(intent, 12);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 11);
        }
    }
}
