package ssjk.cafein;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wqe13 on 2016-11-22.
 */

public class CoffeeList extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffe_information);

        String coffee_name = getIntent().getStringExtra("CoffeeName");
        String drink_type = getIntent().getStringExtra("DrinkType");
        if (drink_type == null)
            drink_type = "null";

        //Log.i("_-_-_-_-_-)", coffee_name);
        //Log.i("_-_-_-_-_-)", drink_type);
        TextView C_info = (TextView)findViewById(R.id.coffeeName);
        C_info.setText(coffee_name);
        try{
            SQLiteDatabase cafedb = openOrCreateDatabase("cafesd1.db", Context.MODE_PRIVATE, null);
            String select;
            if (drink_type != null){
                select = String.format("SELECT * FROM Cafein WHERE Drink_Name='%s' and Drink_Kind_app is '%s'", coffee_name, drink_type);
            }else{
                select = String.format("SELECT * FROM Cafein WHERE Drink_Name='%s' ", coffee_name);

            }
            Cursor cur = cafedb.rawQuery(select, null);
            int count = cur.getCount();
            if (count > 0) {
                cur.moveToFirst();

                LinearLayout layout = (LinearLayout) findViewById(R.id.coffee_layout);
                int [] pricearray = new int[count];

                String [] cafearray = new String[count];
                for (int i = 0; i < count; i ++){
                    pricearray[i] = cur.getInt(4);
                    if (cur.getInt(4) == 0)
                        pricearray[i] = cur.getInt(5);
                    cafearray[i] = cur.getString(1);
                    cur.moveToNext();
                }

                int tmp;
                String stmp;
                for (int i = 0; i < count - 1; i ++){
                    if(pricearray[i] > pricearray[i+1]){
                        tmp = pricearray[i];
                        stmp = cafearray[i];
                        pricearray[i] = pricearray[i+1];
                        cafearray[i] = cafearray[i+1];
                        pricearray[i+1] = tmp;
                        cafearray[i+1] = stmp;
                        i = -1;
                    }
                }


                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                int width = displaymetrics.widthPixels;

                List<TextView> textList = new ArrayList<TextView>(count);
                for (int i = 0; i < count; i++) {
                    String selectc;
                    if (drink_type != null){
                        selectc = String.format("SELECT * FROM Cafein WHERE Drink_Name='%s' and Cafe_Name='%s' and Drink_Kind_app is '%s'", coffee_name, cafearray[i], drink_type);
                    }else{
                        selectc = String.format("SELECT * FROM Cafein WHERE Drink_Name='%s' and Cafe_Name='%s'", coffee_name, cafearray[i]);

                    }
                    Cursor curc = cafedb.rawQuery(selectc, null);
                    curc.moveToFirst();
                    LinearLayout innerlayout = new LinearLayout(this);
                    innerlayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    innerlayout.setOrientation(LinearLayout.HORIZONTAL);
                    layout.addView(innerlayout);

                    Button myButton = new Button(this);
                    final String text = curc.getString(1);
                    myButton.setId(i);
                    myButton.setText(text);
                    myButton.setWidth(200*width/350);
                    innerlayout.addView(myButton);
                    myButton = ((Button) findViewById(myButton.getId()));
                    myButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent intent = new Intent(CoffeeList.this, CafeInformation.class);
                            intent.putExtra("CafeName", text);
                            startActivity(intent);

                        }
                    });
                    //ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
/*
                    TextView C_name = new TextView(this);
                    C_name.setText(curc.getString(1));
                    C_name.setTextSize(15);
                    C_name.setGravity(Gravity.CENTER);
                    C_name.setWidth(200*width/350);
*/
                    TextView C_Hprice = new TextView(this);
                    C_Hprice.setText(curc.getString(4));
                    C_Hprice.setTextSize(15);
                    C_Hprice.setGravity(Gravity.CENTER);
                    C_Hprice.setWidth(50*width/350);

                    TextView C_Cprice = new TextView(this);
                    C_Cprice.setText(curc.getString(5));
                    C_Cprice.setTextSize(15);
                    C_Cprice.setGravity(Gravity.CENTER);
                    C_Cprice.setWidth(100*width/350);

                    //innerlayout.addView(C_name);
                    //textList.add(C_name);
                    innerlayout.addView(C_Hprice);
                    textList.add(C_Hprice);
                    innerlayout.addView(C_Cprice);
                    textList.add(C_Cprice);
                }
            }
        }catch(Exception e){
            Log.i("_)",""+e.toString());
        }
    }
}