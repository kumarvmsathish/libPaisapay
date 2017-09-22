package com.vivartha.paisawallet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by comp17 on 5/8/15.
 */
public class HttpProcessor {

    public final static String GET = "GET";
    public final static String POST = "POST";

    private Context context;
    private RequestBody requestBody;
    private String url;
    private String method;
    private boolean isShowDialog;
    private AsyncTask httpRequest;


    public HttpProcessor(Context context, boolean showDialog, String url, String method, RequestBody requestBody) {

        this.context = context;
        this.url = url;
        this.method = method;
        this.requestBody = requestBody;
        this.isShowDialog = showDialog;

    }


    public AsyncTask executeRequest(String TAG) {
        if (isConnected()) {
            httpRequest = new HttpRequest(TAG).execute(requestBody);
        }
        else
        {
            Toast toast= Toast.makeText(context, "Please Check Your Internet Connection", Toast.LENGTH_SHORT);
            toast.show();

        }
        return httpRequest;

    }


    public class HttpRequest extends AsyncTask<RequestBody, String, String> {

//        TransparentProgressDialog progressDialog;
        String TAG="";

        public HttpRequest(String TAG) {
            this.TAG = TAG;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (isShowDialog) {
//                progressDialog = new TransparentProgressDialog(context);
////                progressDialog.setIndeterminate(true);
//                progressDialog.setCanceledOnTouchOutside(false);
//                progressDialog.show();
            }


        }

        @Override
        protected String doInBackground(RequestBody... requestBody) {
            String result = "";

            if (method == POST) {
                result = makePostRequest(requestBody[0]);

            }
            else if (method == GET) {
                result = makeGetRequest();
            }

            return result;

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
//            if (null != progressDialog && progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }
        }

        @SuppressLint("NewApi")
        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (null != result && null != httpResponserListener) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    httpResponserListener.responseResult(jsonObject,TAG);
//                    if (null != progressDialog && progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
                } catch (JSONException je) {
                    je.printStackTrace();
//                    if (null != progressDialog && progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
//                    if (null != progressDialog && progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                    }
                }

            }

        }

    }

    private String makePostRequest(RequestBody requestBody) {
        String result = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try {
            Response response;
            response = client.newCall(request).execute();

            result = response.body().string();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//            Log.e("TAG", e.getMessage());
        }
        return result;

    }


    private String makeGetRequest() {
        String result = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response;
            response = client.newCall(request).execute();

            result = response.body().string();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("TAG", e.getMessage());
        }
        return result;
    }

    // check network connection
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    public HttpResponser httpResponserListener;

    public void setHttpResponserListener(HttpResponser httpResponserListener) {
        this.httpResponserListener = httpResponserListener;
    }

    public interface HttpResponser {

        public void responseResult(JSONObject result, String TAG);
    }


}


