package ro.pub.cs.systems.eim.lab07.googlesearcher.network;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import ro.pub.cs.systems.eim.lab07.googlesearcher.general.Constants;

public class GoogleSearcherAsyncTask extends AsyncTask<String, Void, String> {

    private WebView googleResultsWebView;

    public GoogleSearcherAsyncTask(WebView googleResultsWebView) {
        this.googleResultsWebView = googleResultsWebView;
    }

    @Override
    protected String doInBackground(String... params) {

        // TODO exercise 6b)
        // create an instance of a HttpClient object
        // create an instance of a HttpGet object, encapsulating the base Internet address (http://www.google.com) and the keyword
        // create an instance of a ResponseHandler object
        // execute the request, thus generating the result
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(Constants.GOOGLE_INTERNET_ADDRESS + params[0]);
        HttpResponse httpResponse;
        String result = "";
        try {
            httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
            Integer currentLineNumber = 0;
            String currentLineContent;
            while ((currentLineContent = bufferedReader.readLine()) != null) {
                currentLineNumber++;
                result = result.concat(currentLineNumber.toString()).concat(": ").concat(currentLineContent).concat("\n");
            }
        } catch (Exception exception) {
            Log.e(Constants.TAG, exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public void onPostExecute(String content) {

        // TODO exercise 6b)
        // display the result into the googleResultsWebView through loadDataWithBaseURL() method
        // - base Internet address is http://www.google.com
        // - page source code is the response
        // - mimetype is text/html
        // - encoding is UTF-8
        // - history is null
        googleResultsWebView.loadDataWithBaseURL("http://www.google.com", content, "text/html", "UTF-8", null);
    }
}
