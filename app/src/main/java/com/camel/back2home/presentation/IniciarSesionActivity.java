package com.camel.back2home.presentation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.camel.back2home.R;
import com.camel.back2home.Utils;
import com.camel.back2home.business.BAuth;
import com.camel.back2home.model.base.User;
import com.facebook.login.LoginManager;

/**
 * Created by mendo on 11/12/2016.
 */

public class IniciarSesionActivity extends AppCompatActivity implements View.OnClickListener {

    private User user = new User();
    private Button btnEntrar;
    private TextView edtEmail, edtPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPass;

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

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPass = (TextInputLayout) findViewById(R.id.input_layout_pass);

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
        if (!submitForm()) return;
        user = new User();
        user.setEmail(edtEmail.getText().toString());
        user.setPassword(edtPassword.getText().toString());
        new SendTask(false).execute(user);
    }

    private boolean submitForm() {
        if (!validateEmail()) {
            return false;
        }

        if (!validatePassword()) {
            return false;
        }
        return true;
    }

    public void onClickForgotPassword(View view) {
        if (validateEmail()) {
            user = new User();
            user.setEmail(edtEmail.getText().toString());
            new SendTask(true).execute(user);
        }
        else{
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.rlLogin), "Necesita un correo registrado", Snackbar.LENGTH_SHORT)
                    .setAction("OK", null);
            snackbar.show();
        }
    }

    private boolean validateEmail() {
        String email = edtEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(edtEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (edtPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPass.setError(getString(R.string.err_msg_password));
            requestFocus(edtPassword);
            return false;
        } else {
            inputLayoutPass.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public class SendTask extends AsyncTask<User, Void, Void> {
        ProgressDialog progressDialog;
        long idResponse = 0;
        boolean isForgotten = false;

        public SendTask(boolean b) {
            isForgotten = b;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(IniciarSesionActivity.this);
            progressDialog.setMessage("Ejecutando...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(10);
            progressDialog.setMax(100);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(User... users) {
            try {
                if (!isForgotten)
                    user = new BAuth(IniciarSesionActivity.this).login(users[0]);
                else
                    user.setPkusuario(new BAuth(IniciarSesionActivity.this).forgotPassword(users[0]));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (isForgotten) {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.rlLogin), "Se ha enviado un correo al correo solicitado, verifique por favor", Snackbar.LENGTH_LONG)
                        .setAction("OK", null);
                snackbar.show();
            } else {
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
                                    new IniciarSesionActivity.SendTask(false).execute(user);
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
            }
        }
    }
}
