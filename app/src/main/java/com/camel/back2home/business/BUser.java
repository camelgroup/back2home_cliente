package com.camel.back2home.business;

import android.content.Context;
import android.util.Log;

import com.camel.back2home.App;
import com.camel.back2home.model.base.User;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 11/12/2016.
 */
public class BUser {
    /**
     * clase para controlar el login de usuarios
     */
    private Context context;

    public BUser(Context context) {
        this.context = context;
    }

    public User login(User obj) throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(App.NUEVO_USUARIO);
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
            nameValuePairs.add(new BasicNameValuePair("email", obj.getEmail()));
            nameValuePairs.add(new BasicNameValuePair("id_firebase", obj.getIdFirebase()));
            nameValuePairs.add(new BasicNameValuePair("nro_telefono", obj.getNroTelefono()));
            nameValuePairs.add(new BasicNameValuePair("id_facebook", obj.getIdFacebook()));
            nameValuePairs.add(new BasicNameValuePair("nombre", obj.getNombre()));
            nameValuePairs.add(new BasicNameValuePair("password", obj.getPassword()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            Gson gson = new Gson();
            User user = gson.fromJson(result, User.class);
            Log.i("TAG", result);
            return user;
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        return null;
    }
}
