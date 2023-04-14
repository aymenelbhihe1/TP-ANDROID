package com.example.tp1_android;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity3 extends AppCompatActivity {

    private static final String TAGNAME = MainActivity3.class.getSimpleName();
    private static final String HTTP_URL = "https://belatar.name/rest/profile.php?login=test&passwd=test&id=9998";

    private static final String HTTP_IMAGES = "http://belatar.name/images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAGNAME, "onStart() called ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAGNAME, "onResume() called ");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, HTTP_URL, null, new Response.Listener<JSONObject>() {
            private Etudiant etd;

            @Override
            public void onResponse(JSONObject response) {
                Log.d(MainActivity3.class.getSimpleName(),response.toString());
                try {
                    etd = new Etudiant(response.getInt("id"),response.getString("nom"),response.getString("prenom"),
                            response.getString("classe"),response.getString("phone"),null);

                    VolleySingleton.getInstance(getApplicationContext()).getImageLoader().get(HTTP_IMAGES + response.getString("photo"), new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                ImageView imageView = findViewById(R.id.ImageProfil);
                                imageView.setImageBitmap(response.getBitmap());
                                etd.setPhoto(response.getBitmap());
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(MainActivity3.class.getSimpleName(), error.getMessage());

                        }
                    });

                    EditText txtnom = findViewById(R.id.edtNom);
                    EditText txtprenom = findViewById(R.id.edtPrenom);
                    EditText txtclasse = findViewById(R.id.edtClasse);

                    txtnom.setText(etd.getNom());
                    txtprenom.setText(etd.getPrenom());
                    txtclasse.setText(etd.getClasse());


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(MainActivity3.class.getSimpleName(), error.getMessage());
            }
        });
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAGNAME, "onPause() called ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAGNAME, "onStop() called ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy() called ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAGNAME, "onRestart() called ");

    }

    public void ClickHandler(View view) {
        Toast.makeText(this,"Boutton clické",Toast.LENGTH_SHORT).show();
    }
}