package com.josefdev.savegooglesheets.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.josefdev.savegooglesheets.R;
import com.josefdev.savegooglesheets.models.IGoogleSheets;
import com.josefdev.savegooglesheets.utils.Common;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterScreen extends AppCompatActivity {
    EditText etName, etSurname, etAge;
    AppCompatButton btnRegister;

    int lastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        etName = findViewById(R.id.et_name);
        etSurname = findViewById(R.id.et_surname);
        etAge = findViewById(R.id.et_age);
        btnRegister = findViewById(R.id.btn_register);

        lastId = getIntent().getIntExtra("count", 0);

        btnRegister.setOnClickListener(v -> registerPerson());
    }

    private void registerPerson() {
        ProgressDialog progressDialog = ProgressDialog.show(this,
                "Registrando nueva persona",
                "Espere por favor",
                true,
                false);

        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        String age = etAge.getText().toString();

        AsyncTask.execute(() -> {
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://script.google.com/macros/s/AKfycbyJfkyxt5hyccVQigB2ybYvmpBaTJhI2gt22VUY4JOEmGRw9ddzTvaBzIxhGGIlMIcZ/")
                        .build();

                IGoogleSheets iGoogleSheets = retrofit.create(IGoogleSheets.class);

                int id = lastId + 1;

                String jsonRequest = "{\n" +
                        "    \"spreadsheet_id\": \"" + Common.GOOGLE_SHEET_ID + "\",\n" +
                        "    \"sheet\": \"" + Common.SHEET_NAME + "\",\n" +
                        "    \"rows\": [\n" +
                        "        [\n" +
                        "            \"" + id + "\",\n" +
                        "            \"" + name + "\",\n" +
                        "            \"" + surname + "\",\n" +
                        "            \"" + age + "\"\n" +
                        "        ]\n" +
                        "    ]\n" +
                        "}";

                Call<String> call = iGoogleSheets.getStringRequestBody(jsonRequest);

                Response<String> response = call.execute();
                int code = response.code();

                progressDialog.dismiss();
                if (code == 200) {
                    startActivity(new Intent(RegisterScreen.this, ConsultScreen.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}