package com.kbu.exam.jsonbylibrary;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParsingJson {
    private Context context;

    public ParsingJson(Context context) {
        this.context = context;
    }

    @NonNull
    public ArrayList<GetLotto> onConvertJson(String json) throws JSONException {
        ArrayList<GetLotto> getLottos = new ArrayList<>();
        GetLotto getLotto = new GetLotto();
        JSONObject root = new JSONObject(json);
        String status = root.getString((String) root.names().get(1));
        String drwNoDate = root.getString((String) root.names().get(2));
        String drwNo = root.getString((String) root.names().get(10));
        String drwNo1 = root.getString((String) root.names().get(13));
        String drwNo2 = root.getString((String) root.names().get(11));
        String drwNo3 = root.getString((String) root.names().get(12));
        String drwNo4 = root.getString((String) root.names().get(5));
        String drwNo5 = root.getString((String) root.names().get(7));
        String drwNo6 = root.getString((String) root.names().get(4));
        String bnusNo = root.getString((String) root.names().get(8));
        if(status.equals("success")) {
            getLotto.setdrwNo(drwNo);
            getLotto.setdrwNoDate(drwNoDate);
            getLotto.setdrwtNo1(drwNo1);
            getLotto.setdrwtNo2(drwNo2);
            getLotto.setdrwtNo3(drwNo3);
            getLotto.setdrwtNo4(drwNo4);
            getLotto.setdrwtNo5(drwNo5);
            getLotto.setdrwtNo6(drwNo6);
            getLotto.setBnusNo(bnusNo);
            getLottos.add(getLotto);
        } else {
            Toast.makeText(context, "입력하신 회차는 없는 회차이거나 \n오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
        }
        return getLottos;
    }
}
