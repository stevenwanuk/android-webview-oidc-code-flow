package sven.com.myfirstapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class OidcWebViewActivity extends AppCompatActivity {

    public final static String OAC_URL = "OAC_URL_SHOULD_BE_PUT_HERE";
    public final static String TAG = "OidcWebViewActivity:";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oidc_web_view);

        WebView webView = this.findViewById(R.id.webview);
        webView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(OAC_URL);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            if (Uri.parse(url).getHost().equals(bmjgroup.com)) {
//                // This is my web site, so do not override; let my WebView load the page
//                return false;
//            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            startActivity(intent);
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);

            Log.d(TAG, "Finished loading: " + url);
            CookieSyncManager.getInstance().sync();

            String cookies = CookieManager.getInstance().getCookie(url);
            Log.d(TAG, "cookie name : "+cookies);



            if (cookies != null) {

                String icsSessionId = extractIcsSessoinIdFromCookies(cookies);
                if (icsSessionId != null) {

                    Intent intent = new Intent(OidcWebViewActivity.this, DisplayMessageActivity.class);
                    intent.putExtra(DisplayMessageActivity.KEY_EXTRA_MESSAGE, icsSessionId);
                    startActivity(intent);
                }
            }
        }

        private String extractIcsSessoinIdFromCookies(String cookieString) {

            String[] cookies = cookieString.split(";");
            for (String cookie : cookies) {
                String[] keyValue = cookie.trim().split("=");
                String key = keyValue[0];
                String value = keyValue[1];

                if ("IcsMarker".equalsIgnoreCase(key) || "IcsGuest".equalsIgnoreCase(key)) {
                    return value;
                }
            }
            return null;
        }
    }
}
