package com.vivartha.paisawallet;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ValuesScreen extends AppCompatActivity implements HttpProcessor.HttpResponser {

    private ValuesAdapter valuesAdapter;
    private ArrayList<Values> arrayList;
    private Values values;
    private Context mContext;
    private ListView listViewValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_values_screen);
        initUI();
        getCurrencyValues();
    }


    private void initUI() {
        mContext = ValuesScreen.this;
        listViewValues = (ListView)findViewById(R.id.values_list_id);
    }


    private void getCurrencyValues() {
        RequestBody requestBody = new FormEncodingBuilder()
                .build();
        HttpProcessor httpProcessor = new HttpProcessor(mContext, false, API.BASE_URL + "" + API.EXCHANGE, HttpProcessor.GET, requestBody);
        httpProcessor.executeRequest(API.EXCHANGE);
        httpProcessor.setHttpResponserListener(this);
    }

    @Override
    public void responseResult(JSONObject result, String TAG) {
        if (API.EXCHANGE.equals(TAG)) {
            JSONArray jsonArray = result.optJSONArray("rows");
            arrayList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                values = new Values();
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                values.setId(jsonObject.optString("id"));
                values.setCurrencyName(jsonObject.optString("name"));
                values.setCurrencyValues(jsonObject.optDouble("price_usd"));
                values.setCurrencySymbol(jsonObject.optString("symbol"));
                values.setCurrencyPoints(jsonObject.optDouble("percent_change_1h"));
                arrayList.add(values);
            }

            valuesAdapter = new ValuesAdapter(mContext, arrayList);
            listViewValues.setAdapter(valuesAdapter);

        }
    }
}
