package com.camel.back2home.presentation;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.camel.back2home.App;
import com.camel.back2home.R;
import com.camel.back2home.Utils;
import com.camel.back2home.model.base.User;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hp on 11/12/2016.
 */
public class MainActivity extends AppCompatActivity {
    private Utils utils;
    private User user;

    private GoogleCloudMessaging gcm;
    private String regid;

    private ImageView imagenGoogle;
    private ProfilePictureView imagenFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        imagenGoogle = ((ImageView) findViewById(R.id.imageView));
        imagenFacebook = (ProfilePictureView) findViewById(R.id.image);

        utils = new Utils(this);

        user = utils.readUser();

        if (user != null) {

            // set circle bitmap
//            startService(new Intent(getBaseContext(), NotificationListener.class));
            if (user.getIdFacebook() != null) {
                imagenFacebook.setCropped(true);
                imagenFacebook.setProfileId(user.getIdFacebook());
            } else {
                new ImageDownloader(imagenGoogle).execute(user.getIdGoogle());
                imagenFacebook.setVisibility(View.GONE);
            }
            ((TextView) findViewById(R.id.tvMainName)).setText(user.getNombre());
        }

        ((TextView) findViewById(R.id.tvCreatePost)).setTypeface(Typeface.createFromAsset(getAssets(), App.Font.ROBOTO_REGULAR));
        ((TextView) findViewById(R.id.tvShowMap)).setTypeface(Typeface.createFromAsset(getAssets(), App.Font.ROBOTO_REGULAR));
        ((TextView) findViewById(R.id.tvFindPets)).setTypeface(Typeface.createFromAsset(getAssets(), App.Font.ROBOTO_REGULAR));
        ((TextView) findViewById(R.id.tvViewPartners)).setTypeface(Typeface.createFromAsset(getAssets(), App.Font.ROBOTO_REGULAR));
        ((TextView) findViewById(R.id.tvMyPets)).setTypeface(Typeface.createFromAsset(getAssets(), App.Font.ROBOTO_REGULAR));
    }

//    public void onClickAlert(View view) {
//
//        Intent postActivity = new Intent(this, PosterActivity.class);
//        postActivity.putExtra("alerta", true);
//        startActivity(postActivity);
//    }
//
//    public void onClickNewPost(View view) {
//        Intent postActivity = new Intent(this, PosterActivity.class);
//        postActivity.putExtra("alerta", false);
//        startActivity(postActivity);
//    }
//
//    public void onClickMap(View view) {
//        Intent mapsActivity = new Intent(this, MapsActivity.class);
//        startActivity(mapsActivity);
//    }
//
//    public void onClickSearch(View view) {
//        Intent searchActivity = new Intent(this, SearchActivity.class);
//        startActivity(searchActivity);
//    }
//
//    public void onClickPartners(View view) {
//        Intent partnersActivity = new Intent(this, PartnersActivity.class);
//        startActivity(partnersActivity);
//    }
//
//    public void onClickMyPosts(View view) {
//        Intent myPetsActivity = new Intent(this, MyPetsActivity.class);
//        startActivity(myPetsActivity);
//    }

    public void onClickLogOut(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Cerrar Sesion?")
                .setCancelable(false)
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginManager.getInstance().logOut();
                        utils.writeUser(null);
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("CANCELAR", null);
        AlertDialog alert = builder.create();
        alert.show();

    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;
    }

    private class Task extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            registerGCM();

            return null;
        }
    }

    private void registerGCM() {
        // revisar si esta instalado el Google Play Services
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(MainActivity.this);
            // es para que siempre devuelva un gcm_id
            try {
                gcm.unregister();
                // nos registramos en los servidores GCM
                regid = gcm.register(App.SENDER_ID);
                String s = regid;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 9000).show();
            } else {
                Log.i("App", "Dispositivo no soportado");
                finish();
            }
            return false;
        }
        return true;
    }

    class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public ImageDownloader(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
