package com.camel.back2home;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.camel.back2home.model.Entity;
import com.camel.back2home.model.base.User;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hp on 7/9/2016.
 */
public class Utils {

    private Context context;

    public Utils(Context context) {
        this.context = context;
    }

    public void setPreference(String key, Entity entity) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, new Gson().toJson(entity));
        editor.commit();
    }

    public void setPreferenceNull(String key, Entity entity) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, null);
        editor.commit();
    }


    public User readUser() {
        SharedPreferences prefs = this.context.getSharedPreferences("user", this.context.MODE_PRIVATE);
        String s = prefs.getString("user", null);
        return s != null ? new Gson().fromJson(s, User.class) : null;
    }

    public void writeUser(User user) {
        SharedPreferences prefs = this.context.getSharedPreferences("user", this.context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("user", new Gson().toJson(user));
        ed.commit();
    }

    public <T extends Entity> T getPreference(String key, Class<T> obj) {
        SharedPreferences settings = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (settings.getString(key, null) != null) {
            return new Gson().fromJson(settings.getString(key, null), obj);
        }
        return null;
    }

    public void getKeyHash() {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.camel.back2home",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
