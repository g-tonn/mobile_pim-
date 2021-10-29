package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    Button   mButton;
    EditText mEdit;
    EditText mEdit2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);
        OkHttpClient client = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://177.70.244.192:14245/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Cliente>> call = jsonPlaceHolderApi.getClientes();

        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if(!response.isSuccessful())
                {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Cliente> clientes = response.body();
                mButton = (Button)findViewById(R.id.btnlogin);
                mEdit   = (EditText)findViewById(R.id.txtnomelogin);
                mEdit2   = (EditText)findViewById(R.id.txtsenhalogin);

                mButton.setOnClickListener(
                    new View.OnClickListener()
                    {
                        public void onClick(View view)
                        {
                            String usuario =  mEdit.getText().toString();
                            String senha =  mEdit2.getText().toString();
                            Log.v("EditText", mEdit.getText().toString());

                            for (Cliente cliente : clientes)
                            {
                                if (usuario.equals(cliente.getUsuario()) && senha.equals(cliente.getSenha()))
                                {
                                    Intent intent = new Intent(MainActivity.this, Form_principal.class);
                                    startActivity(intent);
                              //  String content = "";
                              //  content += "ID: " + cliente.getCod_clnt() + "\n";
                              //  content += "User ID: " + cliente.getUsuario() + "\n";
                               // content += "Password: " + cliente.getSenha() + "\n";

                               // textViewResult.append(content);
                                }else {
                                    textViewResult.append("erro ao buscar dados.");
                                }
                            }
                        }
                    });


            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}