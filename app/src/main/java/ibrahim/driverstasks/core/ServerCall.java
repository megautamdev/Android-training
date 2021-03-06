package ibrahim.driverstasks.core;

/**
 * Created by Ibrahim on 2/5/2015.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
public class ServerCall {


    public static void makeCall(){
        try {
            new HttpAsyncTask().execute().get();
        }catch(Exception c){
            Log.v("EXXCEPPTION ","new HttpAsyncTask().execute().get()");
            c.printStackTrace();;
        }
    }


    public static void getDataFromSrver( ){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(APIConstant.cuurentPath));
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null)
                 convertInputStreamToString(inputStream);
            else{
                APIConstant.responseStatus=false;
//                result = "Did not work!";
                Log.v("getDataFromSrver faild","NO RESPONSE FROM SERVER");
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

//        return result;
    }

    private static void  convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();

        APIConstant.responseText=result;
        try {
            APIConstant.response = new JSONObject(result);
            Log.d("SERVER CALL RESULT", APIConstant.response.toString());
            APIConstant.responseStatus=true;

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
            APIConstant.responseStatus=false;
        }

    }


}
