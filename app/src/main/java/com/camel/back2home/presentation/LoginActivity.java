package com.camel.back2home.presentation;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.camel.back2home.App;
import com.camel.back2home.R;
import com.camel.back2home.Utils;
import com.camel.back2home.business.BUser;
import com.camel.back2home.model.base.User;
import com.camel.back2home.services.LoginAsyncTask;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, LoginAsyncTask.OnCompletedLogin {

    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient plusClient;

    private CallbackManager callbackManager;
    private boolean mIntentInProgress;

    private com.facebook.login.widget.LoginButton loginButton;
    private SignInButton signInButton;

    private User user = new User();
    private ProgressDialog progressDialog;

    private TextView txtIniciarSesion;
    private TextView txtRegistrarse;

    private LoginAsyncTask loginAsyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        super.onCreate(savedInstanceState);
//        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_login);

        new Utils(this).getKeyHash();
        /**
         * instancing
         */
        loginButton = (LoginButton) findViewById(R.id.login_button);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);

        /**
         * changing typography
         */
        ((TextView) findViewById(R.id.tvApp)).setTypeface(Typeface.createFromAsset(getAssets(), App.Font.ROBOTO_BOLD));

        /**
         * login method
         */
        this.txtIniciarSesion = (TextView) findViewById(R.id.txt_login_iniciar_sesion);
        this.txtIniciarSesion.setTypeface(Typeface.createFromAsset(getAssets(), App.Font.ROBOTO_REGULAR));
        this.txtIniciarSesion.setOnClickListener(this);

        /**
         * register method
         */
        this.txtRegistrarse = (TextView) findViewById(R.id.txt_login_registrarse);
        this.txtRegistrarse.setTypeface(Typeface.createFromAsset(getAssets(), App.Font.ROBOTO_REGULAR));
        this.txtRegistrarse.setOnClickListener(this);

        /**
         * verifying is user is saved on preferences
         */
        if (new Utils(this).readUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
        }

        /**
         * google+ client builder
         */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestEmail()
                .build();

        /**
         * params google plus client
         */
        plusClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Conectando...");


        loginButton.setReadPermissions("public_profile, email, user_friends");

        callbackManager = CallbackManager.Factory.create();
        /**
         * onClickFacebook
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /**
         * facebook login event callback
         */
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);
                        if (bFacebookData != null) {
                            /**
                             * creating user object
                             */
                            User user = new User();
                            user.setIdFacebook(bFacebookData.getString("idFacebook"));
                            user.setNombre(bFacebookData.getString("first_name") + " " + bFacebookData.getString("last_name"));
                            user.setEmail(bFacebookData.getString("email"));
                            user.setPkusuario(0);
//                            new SendTask().execute(user);
                            /**
                             * call web service
                             */
                            loginAsyncTask = new LoginAsyncTask(LoginActivity.this, LoginActivity.this, false);
                            loginAsyncTask.execute(user);
                        }
                    }
                });
                Bundle parameters = new Bundle();
                //facebook params to get
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.i("Facebook", "Login cancelado");
            }

            @Override
            public void onError(FacebookException exception) {
                exception.printStackTrace();
                Toast.makeText(LoginActivity.this, "Verifique su conexion a internet", Toast.LENGTH_LONG).show();
            }
        });

        /**
         * onClickGoogle button
         */
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(plusClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    /**
     * get facebook's values
     *
     * @param object
     * @return
     */
    private Bundle getFacebookData(JSONObject object) {
        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));
            return bundle;
        } catch (JSONException e) {
            Log.d("QWD", "Error parsing JSON");
        }
        return null;
    }


    /**
     * get phone number?
     *
     * @return
     */
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

    /**
     * close app
     */
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

    /**
     * result on google or facebook events
     *
     * @param requestCode
     * @param responseCode
     * @param intent
     */
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
//        callbackManager.onActivityResult(requestCode, responseCode, intent);
        super.onActivityResult(requestCode, responseCode, intent);
        /**
         * if google
         */
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            handleSignInResult(result);
        }
        /**
         * if facebook
         */
        if (callbackManager != null)
            callbackManager.onActivityResult(requestCode, responseCode, intent);
    }

    /**
     * google+ response
     *
     * @param result
     */
    private void handleSignInResult(GoogleSignInResult result) {
        progressDialog.dismiss();

        if (result.isSuccess()) {

            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String personPhotoUrl = "";
            if (acct.getPhotoUrl() != null) {
                personPhotoUrl = acct.getPhotoUrl().toString();
            }
            String email = acct.getEmail();
            String code = acct.getId();
            /**
             * creating object
             */
            User user = new User();
            user.setId(0L);
            user.setNombre(personName);
            user.setIdGoogle(code);
            user.setEmail(email);
            user.setPhotoUrl(personPhotoUrl);


            //new SendTask().execute(user);
            loginAsyncTask = new LoginAsyncTask(LoginActivity.this, LoginActivity.this, false);
            loginAsyncTask.execute(user);

        } else {
            // Signed out, show unauthenticated UI.
            progressDialog.dismiss();
        }
    }

    /**
     * something happened with google sign in
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        try {
            progressDialog.dismiss();
            if (!mIntentInProgress && connectionResult.hasResolution()) {
                try {
                    mIntentInProgress = true;
                    startIntentSenderForResult(connectionResult.getResolution().getIntentSender(),
                            RC_SIGN_IN, null, 0, 0, 0);
                } catch (IntentSender.SendIntentException e) {
                    mIntentInProgress = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * register or login events
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_login_iniciar_sesion:
                startActivity(new Intent(this, IniciarSesionActivity.class));
                break;
            case R.id.txt_login_registrarse:
                startActivity(new Intent(this, RegistroActivity.class));
                break;
        }
    }

    /**
     * events from web login webservice
     *
     * @param object user
     */
    @Override
    public void OnCorrectLogin(Object object) {
        new Utils(LoginActivity.this).writeUser((User) object);
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        this.finish();
    }

    /**
     * if something happened with web service
     *
     * @param exception
     */
    @Override
    public void OnFailLogin(Exception exception) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Ocurrio un error con el servidor")
                .setCancelable(false)
                .setNeutralButton("Aceptar", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * async task to register device
     */
    public class SendTask extends AsyncTask<User, Void, Void> {
        ProgressDialog progressDialog;

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
                user = new BUser(LoginActivity.this).login(users[0]);
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
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.rlLogin), "Verifique su conexion a internet", Snackbar.LENGTH_LONG)
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
                return;
            } else {
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

}
