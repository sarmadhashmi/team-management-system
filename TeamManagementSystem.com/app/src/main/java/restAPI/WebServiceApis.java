package restAPI;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WebServiceApis {

    public String LOG_TAG = "REST " + this.getClass().getSimpleName();
    private static final WebServiceApis sInstance = new WebServiceApis();
    public static WebServiceApis getInstance() {
        return sInstance;
    }
    public static String returnMsg = "TEST";
    private WebServiceApis() {
        //TODO:add required stuff;
    }
    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    public String test(RequestParams params){

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://192.168.2.14:43594/test",params ,new AsyncHttpResponseHandler() {
        //client.get("https://www.google.ca/",params ,new AsyncHttpResponseHandler() {

            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {

                try{
                    JSONArray jsonA = new JSONArray(response);

                    for(int i=0;i<jsonA.length();i++) {
                        JSONObject e = jsonA.getJSONObject(i);
                        Log.i("++++++++++++++++++++ ", e.getString("itemid"));
                        Log.i("-------------------- ", e.getString("name"));
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.i("++++++++++++++++++++ ", response);

                returnMsg = response;
            }

            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                // When Http response code is '404'
                if(statusCode == 404){
                    Log.i(LOG_TAG, "ERROR code 404");
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Log.i(LOG_TAG, "ERROR code 500 ");
                }
                // When Http response code other than 404, 500
                else{
                    Log.i(LOG_TAG, "ERROR code " + statusCode);
                }

                returnMsg = content;
            }
        });
        return returnMsg;

    }

}