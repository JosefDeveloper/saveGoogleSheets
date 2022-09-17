package com.josefdev.savegooglesheets.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.josefdev.savegooglesheets.R;
import com.josefdev.savegooglesheets.adapters.PeopleAdapter;
import com.josefdev.savegooglesheets.models.IGoogleSheets;
import com.josefdev.savegooglesheets.models.People;
import com.josefdev.savegooglesheets.utils.Common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultScreen extends AppCompatActivity {
    IGoogleSheets iGoogleSheets;
    private List<People> peopleList;
    private RecyclerView recyclerPeople;
    ProgressDialog progressDialog;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_screen);

        recyclerPeople = findViewById(R.id.recycler_people);

        fab = findViewById(R.id.fab_register);

        peopleList = new ArrayList<>();

        iGoogleSheets = Common.iGSGetMethodClient(Common.BASE_URL);
        loadDataFromGoogleSheets();
    }

    private void loadDataFromGoogleSheets() {
        String pathUrl;
        progressDialog = ProgressDialog.show(ConsultScreen.this,
                "Cargando resultados",
                "Espere por favor",
                true,
                false);

        try {
            pathUrl = "exec?spreadsheetId=" + Common.GOOGLE_SHEET_ID + "&sheet=" + Common.SHEET_NAME;
            iGoogleSheets.getPeople(pathUrl).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    try {
                        assert response.body() != null;
                        JSONObject responseObject = new JSONObject(response.body());
                        JSONArray peopleArray = responseObject.getJSONArray("personas");

                        for (int i = 0; i < peopleArray.length(); i++) {
                            JSONObject object = peopleArray.getJSONObject(i);
                            String id = object.getString("id");
                            String name = object.getString("nombre");
                            String surname = object.getString("apellido");
                            String age = object.getString("edad");

                            People people = new People(id, name, surname, age);
                            peopleList.add(people);

                            setPeopleAdapter(peopleList);
                            progressDialog.dismiss();
                        }

                        int size = peopleList.size();
                        goToRegisterScreen(size);

                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPeopleAdapter(List<People> peopleList) {
        LinearLayoutManager manager = new LinearLayoutManager(ConsultScreen.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        PeopleAdapter peopleAdapter = new PeopleAdapter(ConsultScreen.this, peopleList);
        recyclerPeople.setLayoutManager(manager);
        recyclerPeople.setAdapter(peopleAdapter);
    }

    private void goToRegisterScreen(int size) {
        fab.setOnClickListener(v -> startActivity(new Intent(ConsultScreen.this, RegisterScreen.class)
                .putExtra("count", size)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
    }

}