package com.kbu.exam.jsonbylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    int type = 1;
    EditText editText;
    Button button;
    TextView textView;

    String lturl;

    {
        try {
            lturl = "https://www.dhlottery.co.kr/common.do/?method="+ URLEncoder.encode("getLottoNumber", "UTF-8") +"&drwNo=";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edittext);
        button = (Button) findViewById(R.id.load);
        textView = (TextView) findViewById(R.id.textview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<GetLotto> getLottos = new ArrayList<>();
                switch (type) {
                    case 1:
                        LottoThread thread = new LottoThread(lturl.concat(editText.getText().toString()), MainActivity.this);
                        thread.start();
                        try {
                            thread.join();
                            String json = thread.getResult();
                            ParsingJson parsingJson = new ParsingJson(MainActivity.this);
                            getLottos = parsingJson.onConvertJson(json);
                            textView.setText(getLottos.get(0).drwNo + "회차(" + getLottos.get(0).drwNoDate + ")" + "당첨번호 \n\n"
                                    + "당첨번호1 :" + getLottos.get(0).drwtNo1
                                    + "\n당첨번호2 :" + getLottos.get(0).drwtNo2
                                    + "\n당첨번호3 :" + getLottos.get(0).drwtNo3
                                    + "\n당첨번호4 :" + getLottos.get(0).drwtNo4
                                    + "\n당첨번호5 :" + getLottos.get(0).drwtNo5
                                    + "\n당첨번호6 :" + getLottos.get(0).drwtNo6
                                    + "\n보너스 번호 :" + getLottos.get(0).bnusNo);
                        } catch (InterruptedException | JSONException e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case 2:
                        LottoAsyncTask asyncTask = new LottoAsyncTask(MainActivity.this);
                        try {
                            String json = asyncTask.execute(lturl.concat(editText.getText().toString())).get();
                            ParsingJson parsingJson = new ParsingJson(MainActivity.this);
                            getLottos = parsingJson.onConvertJson(json);
                            textView.setText(getLottos.get(0).drwNo + "회차(" + getLottos.get(0).drwNoDate + ")" + "당첨번호 \n\n"
                                    + "당첨번호1 :" + getLottos.get(0).drwtNo1
                                    + "\n당첨번호2 :" + getLottos.get(0).drwtNo2
                                    + "\n당첨번호3 :" + getLottos.get(0).drwtNo3
                                    + "\n당첨번호4 :" + getLottos.get(0).drwtNo4
                                    + "\n당첨번호5 :" + getLottos.get(0).drwtNo5
                                    + "\n당첨번호6 :" + getLottos.get(0).drwtNo6
                                    + "\n보너스 번호 :" + getLottos.get(0).bnusNo);

                        } catch (ExecutionException | InterruptedException | JSONException e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        String url = null;
                        try {
                            url = lturl+ URLEncoder.encode(editText.getText().toString(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            if(!response.getString((String) response.names().get(0)).equals("fail")){
                                            String drwNoDate = response.getString((String) response.names().get(2)); // 날짜
                                            String drwNo = response.getString((String) response.names().get(10)); // 회차
                                            String drwNo1 = response.getString((String) response.names().get(13)); //1번 번호
                                            String drwNo2 = response.getString((String) response.names().get(11)); // 2번 번호
                                            String drwNo3 = response.getString((String) response.names().get(12)); // 3번 번호
                                            String drwNo4 = response.getString((String) response.names().get(5)); // 4번 번호
                                            String drwNo5 = response.getString((String) response.names().get(7)); // 5번 번호
                                            String drwNo6 = response.getString((String) response.names().get(4)); // 6번 번호
                                            String bnusNo = response.getString((String) response.names().get(8)); // 보너스 번호

                                            textView.setText(drwNo + "회차(" + drwNoDate + ")" + "당첨번호 \n\n"
                                                    + "당첨번호1 :" + drwNo1
                                                    + "\n당첨번호2 :" + drwNo2
                                                    + "\n당첨번호3 :" + drwNo3
                                                    + "\n당첨번호4 :" + drwNo4
                                                    + "\n당첨번호5 :" + drwNo5
                                                    + "\n당첨번호6 :" + drwNo6
                                                    + "\n보너스 번호 :" + bnusNo);
                                            }else {
                                                Toast.makeText(MainActivity.this, "회차번호가 잘못됐습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                        }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        queue.add(request);
                        break;
                    case 4:
                        try {
                            url = lturl+ URLEncoder.encode(editText.getText().toString(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                        AndroidNetworking.initialize(MainActivity.this);
                        AndroidNetworking.get(url).build().getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(!response.getString((String) response.names().get(0)).equals("fail")){
                                        String drwNoDate = response.getString((String) response.names().get(2));
                                        String drwNo = response.getString((String) response.names().get(10));
                                        String drwNo1 = response.getString((String) response.names().get(13));
                                        String drwNo2 = response.getString((String) response.names().get(11));
                                        String drwNo3 = response.getString((String) response.names().get(12));
                                        String drwNo4 = response.getString((String) response.names().get(5));
                                        String drwNo5 = response.getString((String) response.names().get(7));
                                        String drwNo6 = response.getString((String) response.names().get(4));
                                        String bnusNo = response.getString((String) response.names().get(8));

                                        textView.setText(drwNo + "회차(" + drwNoDate + ")" + "당첨번호 \n\n"
                                                + "당첨번호1 :" + drwNo1
                                                + "\n당첨번호2 :" + drwNo2
                                                + "\n당첨번호3 :" + drwNo3
                                                + "\n당첨번호4 :" + drwNo4
                                                + "\n당첨번호5 :" + drwNo5
                                                + "\n당첨번호6 :" + drwNo6
                                                + "\n보너스 번호 :" + bnusNo);
                                    }else {
                                        Toast.makeText(MainActivity.this, "회차번호가 잘못됐습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });
                        break;
                    case 5:
                        String rootUrl = "https://www.dhlottery.co.kr/common.do/";
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(rootUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        LottoApi lottoApi = retrofit.create(LottoApi.class);

                        Call<GetLotto> call = lottoApi.getLotto(editText.getText().toString());
                        call.enqueue(new Callback<GetLotto>() {
                            @Override
                            public void onResponse(Call<GetLotto> call, retrofit2.Response<GetLotto> response) {
                                GetLotto getlotto = response.body();
                                textView.setText(getlotto.drwNo + "회차(" + getlotto.drwNoDate + ")" + "당첨번호 \n\n"
                                        + "당첨번호1 :" + getlotto.drwtNo1
                                        + "\n당첨번호2 :" + getlotto.drwtNo2
                                        + "\n당첨번호3 :" + getlotto.drwtNo3
                                        + "\n당첨번호4 :" + getlotto.drwtNo4
                                        + "\n당첨번호5 :" + getlotto.drwtNo5
                                        + "\n당첨번호6 :" + getlotto.drwtNo6
                                        + "\n보너스 번호 :" + getlotto.bnusNo);
                            }

                            @Override
                            public void onFailure(Call<GetLotto> call, Throwable t) {
                                Log.e("TAG_ERROR", t.getMessage());
                            }
                        });
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                type = 1;
                break;
            case R.id.item2:
                type = 2;
                break;
            case R.id.item3:
                type = 3;
                break;
            case R.id.item4:
                type = 4;
                break;
            case R.id.item5:
                type = 5;
                break;
        }
        item.setChecked(true);
        return true;
    }
}