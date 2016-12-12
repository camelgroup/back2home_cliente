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
import com.camel.back2home.business.BUser;
import com.camel.back2home.model.base.User;
import com.facebook.login.LoginManager;

/**
 * Created by mendo on 11/12/2016.
 */

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private User user = new User();
    private Button btnRegistrar;
    private TextView edtNombre, edtEmail, edtPassword;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.registrarse));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edtNombre = (TextView) findViewById(R.id.edt_registrar_usuario_nombre);
        edtEmail = (TextView) findViewById(R.id.edt_registrar_usuario_email);
        edtPassword = (TextView) findViewById(R.id.edt_registrar_usuario_password);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPass = (TextInputLayout) findViewById(R.id.input_layout_pass);

        btnRegistrar = (Button) findViewById(R.id.btn_registrar_usuario);
        btnRegistrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_registrar_usuario:
                onClickRegistrarUsuario();
                break;
        }
    }

    private void onClickRegistrarUsuario() {
        if (!submitForm()) return;
        user = new User();
        user.setNombre(edtNombre.getText().toString());
        user.setEmail(edtEmail.getText().toString());
        user.setPassword(edtPassword.getText().toString());
        user.setIdFacebook("");
        user.setIdFirebase("");
        user.setIdGoogle("");
        new SendTask().execute(user);
    }

    private boolean submitForm() {
        if (!validateName()) {
            return false;
        }

        if (!validateEmail()) {
            return false;
        }

        if (!validatePassword()) {
            return false;
        }
        return true;
    }

    private boolean validateName() {
        if (edtNombre.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(edtNombre);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
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
        String wexd;


        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(RegistroActivity.this);
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
                user = new BUser(RegistroActivity.this).login(users[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (user.getPkusuario() != 0) {
                new Utils(RegistroActivity.this).writeUser(user);
                startActivity(new Intent(RegistroActivity.this, MainActivity.class));
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
                                new Utils(RegistroActivity.this).writeUser(null);
                            }
                        });
                snackbar.show();

            }
            progressDialog.dismiss();
        }
    }
}
