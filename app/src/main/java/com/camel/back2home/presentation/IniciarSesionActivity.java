package com.camel.back2home.presentation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.camel.back2home.R;
import com.camel.back2home.Utils;
import com.camel.back2home.business.BUser;
import com.camel.back2home.model.base.User;
import com.facebook.login.LoginManager;

/**
 * Created by mendo on 11/12/2016.
 */

public class IniciarSesionActivity extends AppCompatActivity implements View.OnClickListener {

    private User user = new User();
    private Button btnEntrar;
    private TextView edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_email);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.iniciar_sesion));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edtEmail = (TextView) findViewById(R.id.edt_login_email);
        edtPassword = (TextView) findViewById(R.id.edt_login_password);
        btnEntrar = (Button) findViewById(R.id.btn_iniciar_sesion);
        btnEntrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_iniciar_sesion:
                    onClickIniciarSession();
                break;
        }
    }

    private void onClickIniciarSession() {
        user = new User();
        user.setEmail(edtEmail.getText().toString());
        user.setPassword(edtPassword.getText().toString());
        new SendTask().execute(user);
    }

    public class SendTask extends AsyncTask<User, Void, Void> {
        ProgressDialog progressDialog;
        long idResponse = 0;
        String wexd;


        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(IniciarSesionActivity.this);
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
//                wexd = new BUser(IniciarSesionActivity.this).logins(users[0]);
                user = new BUser(IniciarSesionActivity.this).login(users[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (user.getPkusuario() != 0) {
                new Utils(IniciarSesionActivity.this).writeUser(user);
                startActivity(new Intent(IniciarSesionActivity.this, MainActivity.class));
                //startService(new Intent(getBaseContext(), NotificationListener.class));
                finish();
            } else {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.rlLogin), "Ocurrio un error, intente nuevamente", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new IniciarSesionActivity.SendTask().execute(user);
                            }
                        })
                        .setAction("NO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LoginManager.getInstance().logOut();
                                new Utils(IniciarSesionActivity.this).writeUser(null);
                            }
                        });
                snackbar.show();

            }
            progressDialog.dismiss();
        }
    }
}
