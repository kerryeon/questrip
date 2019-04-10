package com.levelup.Questrip.common;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;
import com.levelup.Questrip.R;

/**
 * 사진을 전체화면으로 보여주는 클래스입니다.
 *
 * 담당자: 김호
 *
 * 역할: 사진을 전체화면으로 보여줍니다.
 */
public class PhotoActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_PATH = "path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        loadImage();
    }

    /**
     * 이미지를 불러와 화면에 띄웁니다.
     */
    private void loadImage() {
        final PhotoView mPhotoView = findViewById(R.id.photo_view);
        final String imagePath = getIntent().getStringExtra(EXTRA_IMAGE_PATH);
        ImageManager.load(this, mPhotoView, imagePath);
    }

}
