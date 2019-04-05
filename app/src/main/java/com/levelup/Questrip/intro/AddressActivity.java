package com.levelup.Questrip.intro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.levelup.Questrip.R;

/**
 * 사용자의 집주소를 불러오는 프로세스를 담당하는 클래스입니다.
 *
 * 담당자: 정홍기
 *
 * 역할: 사용자의 집주소를 불러옵니다.
 *
 * 예제는 다음과 같습니다.
 * @see <a href="http://dailyddubby.blogspot.com/2018/02/2-api.html" />
 */
public class AddressActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        // WebView 를 초기화합니다.
        initWebview();
    }

    /**
     * WebView 를 초기화 하기 위한 함수
     */
    public void initWebview() {
        // WebView 설정
        mWebView = findViewById(R.id.address_web_view);
        // JavaScript 허용
        mWebView.getSettings().setJavaScriptEnabled(true);
        // JavaScript 의 window.open 허용
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // JavaScript 이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        // 두 번째 파라미터는 사용될 php 에도 동일하게 사용해야함
        mWebView.addJavascriptInterface(new AndroidBridge(), "TestApp");
        // web client 를 chrome 으로 설정
        mWebView.setWebChromeClient(new WebChromeClient());
        // webview url load
        mWebView.loadUrl("http://questrip.ivyro.net/getAddress.php");
    }

    /**
     * 사용자가 입력한 집주소를 형식를 갖추어 저장합니다.
     * @param address 집주소
     * @param building 건물명
     */
    private void onGetAddress(final String address, final String building) {
        String value = address;
        // 건물명이 있다면, 주소에 건물명을 추가합니다.
        if (building.length() > 0)
            value += " (" + building + ")";
        // 결과물을 회원가입 액티비티로 전달합니다.
        sendAddress(value);
        // WebView 를 초기화하여 다시 사용할 수 있게 합니다.
        initWebview();
    }

    /**
     * 가공된 집주소 정보를 상위 액티비티로 넘깁니다.
     */
    private void sendAddress(String value) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        intent.putExtra("value", value);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Javascript 와 Android 를 연결해주는 중간클래스입니다.
     *
     * 담당자: 정홍기
     */
    private class AndroidBridge {

        /**
         * 사용자가 입력한 집주소를 형식에 맞춰 가져옵니다.
         * @param zip 우편번호
         * @param address 집주소
         * @param building 건물명
         */
        @JavascriptInterface
        public void setAddress(final String zip, final String address, final String building) {
            onGetAddress(address, building);
        }

    }

}
