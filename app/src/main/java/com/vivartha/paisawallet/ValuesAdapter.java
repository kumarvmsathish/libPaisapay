package com.vivartha.paisawallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ANANTH on 19-09-2017.
 */

public class ValuesAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private View view;
    private ArrayList<Values> arrayList;
    private Values values;


    public ValuesAdapter(Context mContext, ArrayList<Values> arrayList) {
        this.context = mContext;
        this.arrayList = arrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        values = arrayList.get(position);
        view = convertView;
        if (null == view) {
            view = layoutInflater.inflate(R.layout.values_content_layout, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
//        format.setCurrency(Currency.getInstance(Locale.US));
//        format.setCurrency(Currency.getInstance(Currency.getInstance("USD").getSymbol(Locale.US)));
//        format.setCurrency(Currency.getInstance(Currency.getInstance(new Locale("hi", "IN")).getSymbol()));
        format.setMinimumFractionDigits(4);
//        viewHolder.imgCurrencyType.setImageResource(values.getCurrencyTypeImage());
        viewHolder.txtCurrencyPoints.setText(String.valueOf(values.getCurrencyPoints()));
        viewHolder.txtCurrencyValues.setText(String.valueOf(format.format(values.getCurrencyValues())));
        viewHolder.txtCurrencySymbol.setText(values.getCurrencySymbol());
        if (values.getCurrencyPoints() > 0) {
            viewHolder.imgCurrencyStatus.setImageResource(R.drawable.up_arrow);
        } else {
            viewHolder.imgCurrencyStatus.setImageResource(R.drawable.down_arrow);
        }

        return view;
    }

    class ViewHolder {

        private ImageView imgCurrencyType;
        private TextView txtCurrencyPoints;
        private TextView txtCurrencyValues;
        private ImageView imgCurrencyStatus;
        private TextView txtCurrencySymbol;

        public ViewHolder(View view) {
            imgCurrencyType = (ImageView) view.findViewById(R.id.img_currency_type_id);
            txtCurrencySymbol = (TextView) view.findViewById(R.id.txt_currency_name_id);
            txtCurrencyPoints = (TextView) view.findViewById(R.id.txt_currency_point_id);
            txtCurrencyValues = (TextView) view.findViewById(R.id.txt_currency_value_id);
            imgCurrencyStatus = (ImageView) view.findViewById(R.id.img_currency_status_id);
        }
    }
}
