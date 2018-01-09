package com.example.owl.tanyadosen.classes;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Owl on 08/01/2018.
 */

public class CustomRequest extends Request<JSONObject> {

    private Response.Listener<JSONObject> listener;
    private Map<String,String> lparams;

    public CustomRequest(int method, String url, Map<String, String> params, Response.Listener<JSONObject> responeListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.lparams = params;
        this.listener = responeListener;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return lparams;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonstring = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return  Response.success(new JSONObject(jsonstring),HttpHeaderParser.parseCacheHeaders(response));
        }catch (UnsupportedEncodingException e)
        {
            return Response.error(new ParseError(e));
        }catch (JSONException e)
        {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }
}
