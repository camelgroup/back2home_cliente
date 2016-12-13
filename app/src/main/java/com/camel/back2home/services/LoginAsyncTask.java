package com.camel.back2home.services;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.camel.back2home.Utils;
import com.camel.back2home.business.BUser;
import com.camel.back2home.model.base.User;

/**
 * Created by hp on 12/12/2016.
 */
public class LoginAsyncTask extends AsyncTask<User, Void, Void> {
    private Context context;
    private ProgressDialog progressDialog;
    private boolean isForgotten;
    private OnCompletedLogin onCompletedLogin;
    private User user;

    public LoginAsyncTask(Context context, OnCompletedLogin onCompletedLogin, boolean isForgotten) {
        this.context = context;
        this.isForgotten = isForgotten;
        this.onCompletedLogin = onCompletedLogin;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Iniciando Sesion...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(10);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(User... params) {
        try {
            user = new BUser(context).login(params[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (user == null) {
            progressDialog.dismiss();
            /**
             * progress dialog?
             */
            return;
        } else {
            if (user.getPkusuario() != 0) {
                new Utils(context).writeUser(user);
                onCompletedLogin.OnCorrectLogin(user);
                //startService(new Intent(getBaseContext(), NotificationListener.class));
            } else {
                Exception exception = null;
                onCompletedLogin.OnFailLogin(exception);

            }
            progressDialog.dismiss();
        }
    }


    public interface OnCompletedLogin {
        void OnCorrectLogin(Object object);

        void OnFailLogin(Exception exception);
    }

}
