package com.levelup.Questrip.intro;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.levelup.Questrip.R;
import com.levelup.Questrip.common.AddressManager;

public class AddressActivity extends AppCompatActivity {
    private WebView webView;
    private Handler handler;

    public static String result_Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        // WebView 초기화
        init_webView();

        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();
    }

    // WebView 를 초기화 하기 위한 함수
    public void init_webView() {
        // WebView 설정
        webView = (WebView) findViewById(R.id.webView);
        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);
        // JavaScript의 window.open 허용
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        // 두 번째 파라미터는 사용될 php에도 동일하게 사용해야함
        webView.addJavascriptInterface(new AndroidBridge(), "TestApp");
        // web client 를 chrome 으로 설정
        webView.setWebChromeClient(new WebChromeClient());
        // webview url load
        webView.loadUrl("http://questrip.ivyro.net/getAddress.php");
    }
    private class AndroidBridge {
        @JavascriptInterface
        // 선택한 주소를 받아들임, 우편번호, 지번 및 도로주소, 빌딩주소 순서로 받음
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // 선택하여 받아들인 주소를 하나로 합침
                    result_Address = arg2 + arg3;
                    OnSuccess();
                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();
                }
            });
        }
    }

    // 선택한 주소를 받아들이기 성공했을 시
    private void OnSuccess() {
        Intent intent =new Intent(getApplicationContext(), SignUpActivity.class);
        AddressManager Addr = new AddressManager(result_Address);
        intent.putExtra("class", Addr);
        setResult(RESULT_OK, intent);
        finish();
    }
}
