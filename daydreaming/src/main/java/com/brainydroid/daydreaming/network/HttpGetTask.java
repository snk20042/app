package com.brainydroid.daydreaming.network;

import android.os.AsyncTask;
import com.brainydroid.daydreaming.background.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpGetTask extends AsyncTask<HttpGetData, Void, Boolean> {

    private static String TAG = "HttpGetTask";

    private HttpClient client;
    private String serverAnswer;
    private HttpConversationCallback httpConversationCallback;

    @Override
    protected void onPreExecute() {
        Logger.v(TAG, "Doing pre-execution tasks: create client and set " +
                "connection timeout");

        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams,
                ServerConfig.NETWORK_TIMEOUT);
        client = new DefaultHttpClient(httpParams);
    }

    @Override
    protected Boolean doInBackground(HttpGetData... getDatas) {
        Logger.v(TAG, "Starting GET for data");

        try {
            HttpGetData getData = getDatas[0];
            httpConversationCallback = getData.getHttpConversationCallback();
            HttpGet httpGet = new HttpGet(getData.getGetUrl());

            HttpResponse response = client.execute(httpGet);
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {
                try {
                    serverAnswer = EntityUtils.toString(resEntity);
                    Logger.v(TAG, "Answer successfully received and " +
                            "converted to String");
                } catch (IOException e) {
                    Logger.e(TAG, "Error converting response entity to " +
                            "String -> returning failure");
                    serverAnswer = null;
                    return false;
                }
            } else {
                Logger.e(TAG, "resEntity is null");
            }
        } catch (ClientProtocolException e) {
            // FIXME: can we distinguish timeout from other errors? And report back to callback?
            Logger.e(TAG, "ClientProtocolException generated by GET -> " +
                    "returning failure");
            serverAnswer = null;
            return false;
        } catch (IOException e) {
            Logger.e(TAG, "IOException generated by GET -> returning " +
                    "failure");
            serverAnswer = null;
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (httpConversationCallback != null) {
            Logger.v(TAG, "Calling back callback");
            httpConversationCallback.onHttpConversationFinished(success, serverAnswer);
        } else {
            Logger.e(TAG, "Work finished, but no callback to call back");
        }
    }

}