package ssjk.cafein;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;
import java.util.List;


public class CafeInformation extends Activity {
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cafeinformation);

        String cafe_name = getIntent().getStringExtra("CafeName");

        try{
            // 'data/data'에 생성된 db파일 읽어오기
            SQLiteDatabase cafedb = openOrCreateDatabase("cafesd1.db", Context.MODE_PRIVATE, null);
            //MainActivity에서 호출된 카페의 이름과 동일한 db를 모두 불러온다
            String select = String.format("SELECT * FROM Cafein WHERE Cafe_Name='%s'", cafe_name);
            // 쿼리로 db의 커서 획득
            Cursor cur = cafedb.rawQuery(select,null);

            // 처음 레코드로 이동
            cur.moveToFirst();

            // 읽은값 출력
            TextView C_info = (TextView)findViewById(R.id.cafeName);
            C_info.setText(cur.getString(1));

            C_info = (TextView)findViewById(R.id.Note);
            C_info.setText(cur.getString(6));
            C_info = (TextView)findViewById(R.id.Phone_Num);
            C_info.setText(cur.getString(7));
            C_info = (TextView)findViewById(R.id.operHours);
            C_info.setText(cur.getString(8));

            printdrink(cafe_name, "COFFEE", cafedb);
            printdrink(cafe_name, "ICE BLENDED", cafedb);
            printdrink(cafe_name, "TEA", cafedb);
            printdrink(cafe_name, "BEVERAGE", cafedb);


        }catch(Exception e){
            Log.i("_)",""+e.toString());
        }
/*
        ScrollView parentScrollView = (ScrollView)findViewById(R.id.parentScrollView);
        ScrollView childScrollView = (ScrollView)findViewById(R.id.childScrollView);


        parentScrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event)
            {
                findViewById(R.id.childScrollView).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        childScrollView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event)
            {

// Disallow the touch request for parent scroll on touch of
// child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/

    }

    public void printdrink (String cafe_name, String drink_kind, SQLiteDatabase cafedb){

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;

        String select = String.format("SELECT * FROM Cafein WHERE Cafe_Name='%s' and Drink_Kind = '%s' ", cafe_name, drink_kind);
        Cursor cur = cafedb.rawQuery(select, null);
        int count = cur.getCount();
        if (count > 0) {
            cur.moveToFirst();

            LinearLayout layout = (LinearLayout) findViewById(R.id.drinklayout);

            TextView Drink_Kind = new TextView(this);
            Drink_Kind.setText(cur.getString(2));
            Drink_Kind.setTextSize(15);
            Drink_Kind.setGravity(Gravity.CENTER);
            Drink_Kind.setHeight(35*height/630);
            Drink_Kind.setTypeface(null, Typeface.BOLD);
            layout.addView(Drink_Kind);

            List<TextView> textList = new ArrayList<TextView>(count);
            for (int i = 0; i < count; i++) {
                LinearLayout innerlayout = new LinearLayout(this);
                innerlayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                innerlayout.setOrientation(LinearLayout.HORIZONTAL);
                layout.addView(innerlayout);

                TextView C_item = new TextView(this);
                C_item.setText(cur.getString(3));
                C_item.setTextSize(15);
                C_item.setGravity(Gravity.CENTER);
                C_item.setWidth(200*width/350);

                TextView C_Hprice = new TextView(this);
                C_Hprice.setText(cur.getString(4));
                C_Hprice.setTextSize(15);
                C_Hprice.setGravity(Gravity.CENTER);
                C_Hprice.setWidth(50*width/350);

                TextView C_Cprice = new TextView(this);
                C_Cprice.setText(cur.getString(5));
                C_Cprice.setTextSize(15);
                C_Cprice.setGravity(Gravity.CENTER);
                C_Cprice.setWidth(50*width/350);

                innerlayout.addView(C_item);
                textList.add(C_item);
                innerlayout.addView(C_Hprice);
                textList.add(C_Hprice);
                innerlayout.addView(C_Cprice);
                textList.add(C_Cprice);
                cur.moveToNext();
            }
        }

    }
}