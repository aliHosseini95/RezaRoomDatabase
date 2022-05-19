package com.prt.rezaroomdatabase.view;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.prt.rezaroomdatabase.R;

import java.io.IOException;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class FileProviderActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> fileResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_provider);
        fileResultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent()
                , new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null) {
                            Bitmap bitmap = null;
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (bitmap != null) {
                                ImageView imageView = findViewById(R.id.image_view);
                                imageView.setImageBitmap(bitmap);
                            }
                        }
                    }
                });

        findViewById(R.id.get_file_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileResultLauncher.launch("image/*");
            }
        });
    }
}
