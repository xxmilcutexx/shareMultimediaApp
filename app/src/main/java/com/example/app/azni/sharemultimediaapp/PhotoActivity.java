package com.example.app.azni.sharemultimediaapp;


import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PhotoActivity extends AppCompatActivity {

    Button btnCamera, btnShare, btnGallery;
    ImageView iv;
    private static final int CAM_REQUEST = 1;
    private static final int SELECT_FILE = 2;
    private File imageFile;
    private String selectedImagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        btnCamera = (Button) findViewById(R.id.buttonCamera);
        btnShare = (Button) findViewById(R.id.buttonShare);
        btnGallery = (Button) findViewById(R.id.buttonGallery);
        iv = (ImageView) findViewById(R.id.imageView);
        btnCamera.setOnClickListener(new btnCameraClicker());
        btnShare.setOnClickListener(new btnShareClicker());
        btnGallery.setOnClickListener(new btnGalleryClicker());

        //set button to false is camera isn't used
        btnShare.setEnabled(false);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", "");

        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK )
        {
            galleryAddPic();
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//            bitmapOptions.inJustDecodeBounds = true;
            //imageFile.getAbsolutePath()
            Bitmap bMap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), bitmapOptions);
            iv.setImageBitmap(bMap);

//            Log.d("Image File Path:", imageFile.getAbsolutePath());

//            Toast.makeText(this, "bMap:" + bMap, Toast.LENGTH_LONG).show();
            Toast.makeText(this, "saved" + imageFile.getAbsolutePath(), Toast.LENGTH_LONG).show();

        }
        else if(requestCode == SELECT_FILE && resultCode == RESULT_OK)
        {
            Uri selectedImageUri = data.getData();
            String[] projection = { MediaStore.MediaColumns.DATA };

            CursorLoader cursorLoader = new CursorLoader(this,selectedImageUri, projection, null, null,
                    null);
            Cursor cursor =cursorLoader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();

            //use the global var outside
            selectedImagePath = cursor.getString(column_index);

//Log.d("selectedImageUri", selectedImageUri.toString());
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            Bitmap bMap = BitmapFactory.decodeFile(selectedImagePath, bitmapOptions);
            iv.setImageBitmap(bMap);
            btnShare.setEnabled(true);

            imageFile = new File(selectedImagePath);

        }
    }


    class btnGalleryClicker implements View.OnClickListener
    {
        public void onClick(View v)
        {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        }
    }

    class btnShareClicker implements View.OnClickListener {
        public void onClick(View v) {
            if(imageFile!=null)
            {
                Uri imagePath = Uri.fromFile(imageFile);
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                sharingIntent.setType("image/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, imagePath);
                startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));
            }
//            else
//            {
//                File selectedImage = new File(selectedImagePath);
////                Uri bmpUri = getLocalBitmapUri(iv);
//                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//                sharingIntent.setType("image/*");
//                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(selectedImage));
//                startActivity(Intent.createChooser(sharingIntent, "Share Image Using"));
//            }
        }
    }

//    // Returns the URI path to the Bitmap displayed in specified ImageView
//    public Uri getLocalBitmapUri(ImageView imageView) {
//        // Extract Bitmap from ImageView drawable
//        Drawable drawable = imageView.getDrawable();
//        Bitmap bmp = null;
//        if (drawable instanceof BitmapDrawable){
//            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        } else {
//            return null;
//        }
//        // Store image to default external storage directory
//        Uri bmpUri = null;
//        try {
//            // Use methods on Context to access package-specific directories on external storage.
//            // This way, you don't need to request external read/write permission.
//            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
//            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
//            FileOutputStream out = new FileOutputStream(file);
//            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
//            out.close();
//            bmpUri = Uri.fromFile(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bmpUri;
//    }

    class btnCameraClicker implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + ".jpg";
            imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imageFileName);
            Uri value = Uri.fromFile(imageFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, value);
            startActivityForResult(cameraIntent, CAM_REQUEST);
//            Log.d("AAAAAAAA","11");

        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(imageFile);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
//        Log.d("imageFile URI",mediaScanIntent.toString() );
        //set share button enabled
        btnShare.setEnabled(true);
    }
}

