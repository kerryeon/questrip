package com.levelup.Questrip.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import androidx.exifinterface.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import android.widget.Button;
import android.widget.ImageView;

import com.levelup.Questrip.R;
import com.levelup.Questrip.common.CommonAlert;

import java.io.File;
import java.io.IOException;

/**
 * 이미지 업로드 레이아웃을 담당하는 클래스입니다.
 *
 * 담당자: 김호, 정홍기
 *
 * 역할: 사용자로부터 이미지를 입력받습니다.
 * 이미지를 전송할 것인지 아닌지 결정할 수 있습니다.
 */
class ViewUploadLayout {

    private Activity activity;
    private OnSubmit onSubmit;

    private File imageFile;
    private Uri imageURI;

    final int CODE_INTENT_IMAGE_CAMERA;
    final int CODE_INTENT_IMAGE_GALLERY;

    private static final int MAX_WIDTH = 1920;
    private static final int MAX_HEIGHT = 1080;

    /**
     * 객체를 생성합니다.
     * @param activity 현재 액티비티
     */
    ViewUploadLayout(Activity activity) {
        this.activity = activity;

        // 상수 설정
        Resources resources = activity.getResources();
        CODE_INTENT_IMAGE_CAMERA = resources.getInteger(R.integer.CODE_INTENT_IMAGE_CAMERA);
        CODE_INTENT_IMAGE_GALLERY = resources.getInteger(R.integer.CODE_INTENT_IMAGE_GALLERY);
    }

    /**
     * 필드 및 변수값을 초기화합니다.
     */
    private void init() {
        // 임시 파일을 생성합니다.
        try {
            File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            imageFile = File.createTempFile("temp", ".jpg", storageDir);
            imageURI = FileProvider.getUriForFile(activity, activity.getPackageName(), imageFile);
        } catch (IOException e) {
            // Unreachable
            e.printStackTrace();
            imageFile = null;
            imageURI = null;
        }
    }

    /**
     * 이미지 업로드 프로세스를 시작합니다.
     * 이미지 선택 방식을 나열한 알림창을 띄웁니다.
     */
    void begin(OnSubmit onSubmit) {
        this.init();
        this.onSubmit = onSubmit;
        CommonAlert.choose(activity, R.string.view_button_submit,
                R.array.view_list_upload, this::onSelectUploadMode);
    }

    /**
     * 알림창을 띄워, 업로드할 사진을 전송하기 전 보여줍니다.
     * 사용자는 전송할 것인지 아닌지 결정할 수 있습니다.
     */
    void showImage() {
        showImage(imageURI);
    }

    /**
     * 알림창을 띄워, 업로드할 사진을 전송하기 전 보여줍니다.
     * 사용자는 전송할 것인지 아닌지 결정할 수 있습니다.
     * @param imageURI 이미지 경로.
     */
    void showImage(final Uri imageURI) {
        // 레이아웃
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.layout_view_upload);

        // 필드
        final ImageView mPhoto = dialog.findViewById(R.id.view_upload_photo);
        final Button mSubmit = dialog.findViewById(R.id.view_upload_btn_submit);
        final Button mCancel = dialog.findViewById(R.id.view_upload_btn_cancel);

        // 사진을 불러옵니다.
        final Bitmap bitmap = transformBitmap(getImage(imageURI));

        // 사진을 표시합니다.
        mPhoto.setImageBitmap(bitmap);

        // 이벤트
        mSubmit.setOnClickListener(v -> onClickSubmit(dialog, bitmap));
        mCancel.setOnClickListener(v -> onClickCancel(dialog));

        // 알림창을 띄웁니다.
        dialog.show();
    }

    /**
     * 이미지 선택 방식을 결정한 경우의 이벤트입니다.
     */
    private void onSelectUploadMode(final int chosen) {
        switch (chosen) {
            // 사진 촬영
            case 0:
                getImageByCamera();
                break;
            // 앨범에서 사진 선택
            case 1:
                getImageByGallery();
                break;
        }
    }

    /**
     * 카메라를 통해 이미지를 입력받습니다.
     */
    private void getImageByCamera() {
        // 임시 파일이 있는 경우에만 진행합니다.
        if (imageURI == null) {
            CommonAlert.toast(activity, R.string.common_failure_unknown);
            return;
        }

        // 카메라 모듈을 호출합니다.
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
            activity.startActivityForResult(intent, CODE_INTENT_IMAGE_CAMERA);
        }
    }

    /**
     * 갤러리를 통해 이미지를 입력받습니다.
     */
    private void getImageByGallery() {
        // 갤러리 모듈을 호출합니다.
        Intent intent = new Intent(Intent.ACTION_PICK);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            activity.startActivityForResult(intent, CODE_INTENT_IMAGE_GALLERY);
        }
    }

    /**
     * 임시 파일로부터 이미지를 불러옵니다.
     * @param imageURI 이미지 경로.
     * @return 이미지
     */
    private Bitmap getImage(final Uri imageURI) {
        try {
            return MediaStore.Images.Media.getBitmap(activity.getContentResolver(), imageURI);
        } catch (IOException e) {
            // Unreachable
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 이미지를 본래 방향으로 회전시키고, 크기를 조절합니다.
     * @param bitmap 이미지
     * @return 회전된 이미지
     */
    private Bitmap transformBitmap(Bitmap bitmap) {
        final int degrees = exifOrientationToDegrees();
        // 이미지 회전
        if (degrees != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(exifOrientationToDegrees());
            bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        // 크기 조정
        final int imageW = bitmap.getWidth();
        final int imageH = bitmap.getHeight();
        if (imageW > MAX_WIDTH || imageH > MAX_HEIGHT) {
            final double ratioW = (double) imageW / MAX_WIDTH;
            final double ratioH = (double) imageH / MAX_HEIGHT;
            final double ratio = ratioW > ratioH ? ratioW : ratioH;
            bitmap = Bitmap.createScaledBitmap(bitmap,
                    (int) (imageW / ratio), (int) (imageH / ratio), true);
        }
        return bitmap;
    }

    /**
     * 회전된 이미지의 각도를 구합니다.
     * @return 이미지 회전각
     */
    private int exifOrientationToDegrees() {
        try {
            ExifInterface exif = new ExifInterface(imageFile.getPath());
            final int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
                return 90;
            else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
                return 180;
            else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
                return 270;
            return 0;
        } catch (IOException e) {
            // Unreachable
            return 0;
        }
    }

    /**
     * 서버에 이미지를 업로드하고, 레이아웃을 닫습니다.
     * @param dialog 레이아웃
     * @param bitmap 이미지
     */
    private void onClickSubmit(Dialog dialog, final Bitmap bitmap) {
        onSubmit.run(bitmap);
        dialog.dismiss();
    }

    /**
     * 취소를 누른 경우, 레이아웃을 닫습니다.
     * @param dialog 레이아웃
     */
    private void onClickCancel(Dialog dialog) {
        CommonAlert.toast(activity, R.string.common_alert_canceled);
        dialog.dismiss();
    }

    /**
     * 사용자가 사진을 업로드하려는 경우의 이벤트입니다.
     *
     * 담당자: 김호
     */
    @FunctionalInterface
    interface OnSubmit {
        /**
         * 사진을 업로드합니다.
         * @param bitmap JSON 결과값
         */
        void run(final Bitmap bitmap);
    }

}
