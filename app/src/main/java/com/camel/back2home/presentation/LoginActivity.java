package com.camel.back2home.presentation;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.camel.back2home.App;
import com.camel.back2home.R;
import com.camel.back2home.Utils;
import com.camel.back2home.business.BUser;
import com.camel.back2home.model.base.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 0;
    private CallbackManager callbackManager;
    private boolean mIntentInProgress;
    private LoginButton loginButton;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_login);

        ((TextView) findViewById(R.id.tvApp)).setTypeface(Typeface.createFromAsset(getAssets(), App.Font.ROBOTO_BOLD));
        ((Button) findViewById(R.id.btnFacebook)).setTypeface(Typeface.createFromAsset(getAssets(), App.Font.ROBOTO_REGULAR));
        ((Button) findViewById(R.id.btnGoogle)).setTypeface(Typeface.createFromAsset(getAssets(), App.Font.ROBOTO_REGULAR));

        if (new Utils(this).readUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
        }

//        loginButton = (LoginButton) findViewById(R.id.login_button);
//        loginButton.setReadPermissions("public_profile, email, user_friends");
//        callbackManager = CallbackManager.Factory.create();
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(JSONObject object, GraphResponse response) {
//                        try {
//                            User user = new User();
////                            TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//                            String mPhoneNumber = "";
////                            getMy10DigitPhoneNumber();
//
//                            String id = object.getString("id");
//                            String personName = object.getString("name");
//                            if (mPhoneNumber != null)
//                                user.setNroTelefono(mPhoneNumber);
//                            user.setNroTelefono("");
//                            user.setNombre(personName);
//                            user.setEmail("");
//                            user.setIdFacebook(id);
//                            Firebase firebase = new Firebase(App.FIREBASE_APP);
//
//                            //Pushing a new element to firebase it will automatically create a unique id
//                            Firebase newFirebase = firebase.push();
//
//                            //Creating a map to store name value pair
//                            Map<String, String> val = new HashMap<>();
//
//                            //pushing msg = none in the map
//                            val.put("msg", "none");
//
//                            //saving the map to firebase
//                            newFirebase.setValue(val);
//
//                            //Getting the unique id generated at firebase
//                            String uniqueId = newFirebase.getKey();
//                            user.setIdFirebase(uniqueId);
//                            Log.i("QWD", uniqueId);
//
////                            user.setPkusuario(0);
//                            //////
////                            new Utils(LoginActivity.this).writeUser(user);
////                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
////                            finish();
//                            new SendTask().execute(user);
//
//                        } catch (Exception e) {
//                            LoginManager.getInstance().logOut();
//                            new Utils(LoginActivity.this).writeUser(null);
//                            e.printStackTrace();
//                        }
//                    }
//                }).executeAsync();
//            }
//
//            @Override
//            public void onCancel() {
//                Log.i("Facebook", "Login cancelado");
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                exception.printStackTrace();
//            }
//        });

    }

    private String getMyPhoneNumber() {
        TelephonyManager mTelephonyMgr;
        mTelephonyMgr = (TelephonyManager)
                getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyMgr.getLine1Number();
    }

    private String getMy10DigitPhoneNumber() {
        String s = getMyPhoneNumber();
        return s.substring(2);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Esta seguro que desea salir?")
                .setCancelable(false)
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        LoginActivity.this.finish();
                    }
                })
                .setNegativeButton("CANCELAR", null);
        AlertDialog alert = builder.create();
        alert.show();
    }


    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
//        callbackManager.onActivityResult(requestCode, responseCode, intent);

        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;
        }
        if (callbackManager != null)
            callbackManager.onActivityResult(requestCode, responseCode, intent);
    }

    public void onClickFacebook(View view) {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile, email, user_friends"));
    }

    public void onClickGoogle(View view) {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
//        Snackbar snackbar = Snackbar
//                .make(findViewById(R.id.rlLogin), "Proximamente ;)", Snackbar.LENGTH_LONG);
//        snackbar.show();
    }

    public class SendTask extends AsyncTask<User, Void, Void> {
        ProgressDialog progressDialog;
        long idResponse = 0;
        String wexd;


        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("Iniciando Sesion...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(10);
            progressDialog.setMax(100);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(User... users) {
            try {
//                wexd = new BUser(LoginActivity.this).logins(users[0]);
                user = new BUser(LoginActivity.this).login(users[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (user.getPkusuario() != 0) {
                new Utils(LoginActivity.this).writeUser(user);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                //startService(new Intent(getBaseContext(), NotificationListener.class));
                finish();
            } else {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.rlLogin), "Ocurrio un error, intente nuevamente", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new SendTask().execute(user);
                            }
                        })
                        .setAction("NO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LoginManager.getInstance().logOut();
                                new Utils(LoginActivity.this).writeUser(null);
                            }
                        });
                snackbar.show();

            }
            progressDialog.dismiss();
        }
    }

}
