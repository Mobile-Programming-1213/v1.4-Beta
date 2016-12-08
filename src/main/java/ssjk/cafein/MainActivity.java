package ssjk.cafein;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager mPager;
    private long lastTimeBackPressed;
    private static int check = 1;
    private int inc = 1;
    private SQLiteDatabase cafedb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize(getApplicationContext());
        cafedb = openOrCreateDatabase("cafesd1.db", Context.MODE_PRIVATE, null);
        updatesuggestion(getApplicationContext());
        setLayout();


        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new PagerAdapterClass(getApplicationContext()));
        mPager.setOnPageChangeListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_toCafe:
                setCurrentInflateItem(0);
                break;
            case R.id.btn_toCoffee:
                setCurrentInflateItem(1);
                break;
            case R.id. btn_search:
                Intent intent = new Intent(MainActivity.this, BeginSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPageScrolled(int state, float positionOffset, int positionOffsetPixels){    }

    @Override
    public void onPageSelected(int position){
        switch(position){
            case 0:
                btn_toCafe.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_button_cafe_focused, 0, 0);
                btn_toCoffee.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_button_coffee, 0, 0);
                break;
            case 1:
                btn_toCafe.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_button_cafe, 0, 0);
                btn_toCoffee.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_button_coffee_focused, 0, 0);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state){    }

    private void setCurrentInflateItem(int type){
        if(type==0){
            mPager.setCurrentItem(0);
        }else if(type==1){
            mPager.setCurrentItem(1);
        }
    }


    private Button btn_toCafe;
    private Button btn_toCoffee;
    private Button btn_search;

    private void setLayout(){
        btn_toCafe = (Button) findViewById(R.id.btn_toCafe);
        btn_toCoffee = (Button) findViewById(R.id.btn_toCoffee);
        btn_search = (Button) findViewById(R.id. btn_search);

        btn_search.setOnClickListener(MainActivity.this);
        btn_toCafe.setOnClickListener(MainActivity.this);
        btn_toCoffee.setOnClickListener(MainActivity.this);
    }

    private class PagerAdapterClass extends PagerAdapter {

        private LayoutInflater mInflater;

        public PagerAdapterClass(Context c){
            super();
            mInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object instantiateItem(View pager, int position) {
            View v = null;
            if(position==0){
                v = mInflater.inflate(R.layout.inflate_one, null);
                setfirstview(v);
            }
            else if(position==1){
                v = mInflater.inflate(R.layout.inflate_two, null);
                setsecondview(v);
            }

            ((ViewPager)pager).addView(v, 0);

            return v;
        }

        @Override
        public void destroyItem(View pager, int position, Object view) {
            ((ViewPager)pager).removeView((View)view);
        }

        @Override
        public boolean isViewFromObject(View pager, Object obj) {
            return pager == obj;
        }

        @Override public void restoreState(Parcelable arg0, ClassLoader arg1) {}
        @Override public Parcelable saveState() { return null; }
        @Override public void startUpdate(View arg0) {}
        @Override public void finishUpdate(View arg0) {}
    }


    private void setfirstview(View v){
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.mainscroll);
        String select = String.format("SELECT * FROM TABLE_SUGGESTION WHERE FIELD_TYPE = 'CAFE'");
        Cursor cur = cafedb.rawQuery(select, null);
        int count = cur.getCount();
        cur.moveToFirst();
        String [] cafearray = new String[count];
        String [] alphaarray = new String[count];
        for (int i = 0; i < count; i ++){
            cafearray[i] = cur.getString(1);
            alphaarray[i] = cafearray[i].substring(0,1);
            cur.moveToNext();

        }
        String stmp, stmp2;
        for (int i = 0; i < count - 1; i ++){
            int compare = alphaarray[i].compareTo(alphaarray[i+1]);
            if(compare > 0){
                stmp = cafearray[i];
                stmp2 = alphaarray[i];
                cafearray[i] = cafearray[i+1];
                alphaarray[i] = alphaarray[i+1];
                cafearray[i+1] = stmp;
                alphaarray[i+1] = stmp2;
                i = -1;
            }
        }


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;


        List<TextView> textList = new ArrayList<TextView>(count);
        for (int i = 0; i < count; i++) {
            String selectc = String.format("SELECT * FROM TABLE_SUGGESTION WHERE FIELD_SUGGESTION = '%s' and FIELD_TYPE = 'CAFE'", cafearray[i]);
            Cursor curc = cafedb.rawQuery(selectc, null);
            curc.moveToFirst();
            LinearLayout innerlayout = new LinearLayout(v.getContext());
            innerlayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            innerlayout.setOrientation(LinearLayout.HORIZONTAL);
            layout.addView(innerlayout);

            TextView nullview = new TextView(v.getContext());
            nullview.setWidth(width/5);
            innerlayout.addView(nullview);

            Button myButton = new Button(v.getContext());
            final String text = curc.getString(1);
            myButton.setId(i);
            myButton.setText(text);
            myButton.setWidth(width*3/5);
            //myButton.setBackgroundResource(R.drawable.ic_button_big);
            innerlayout.addView(myButton);
            myButton = ((Button) v.findViewById(myButton.getId()));
            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, CafeInformation.class);
                    intent.putExtra("CafeName", text);
                    startActivity(intent);

                }
            });
        }

        TextView nullview = new TextView(v.getContext());
        nullview.setHeight(15);
        layout.addView(nullview);

    }
    private void setsecondview(View v) {
        setview("COFFEE", v);
        setview("TEA", v );
        setview("ICE BLENDED", v);
        setview("BEVERAGE", v);
    }

    private void setview(final String s, View v){
        LinearLayout layout = null;
        switch(s){
            case "COFFEE":
                layout = (LinearLayout) v.findViewById(R.id.secondscroll_coffee);
                break;
            case "ICE BLENDED":
                layout = (LinearLayout) v.findViewById(R.id.secondscroll_ice);
                break;
            case "TEA":
                layout = (LinearLayout) v.findViewById(R.id.secondscroll_tea);
                break;
            case "BEVERAGE":
                layout = (LinearLayout) v.findViewById(R.id.secondscroll_beverage);
                break;

        }
        String select = String.format("SELECT * FROM TABLE_SUGGESTION WHERE FIELD_TYPE = '%s' and ITEM_NUM > 4", s);
        Cursor cur = cafedb.rawQuery(select, null);
        cur.moveToFirst();
        int count = cur.getCount();
        int [] numarray = new int[count];
        String [] drinkarray = new String[count];
        for (int i = 0; i < count; i ++){
            numarray[i] = cur.getInt(3);
            drinkarray[i] = cur.getString(1);
            cur.moveToNext();
        }
        int tmp;
        String stmp;
        for (int i = 0; i < count - 1; i ++){
            if(numarray[i] < numarray[i+1]){
                tmp = numarray[i];
                stmp = drinkarray[i];
                numarray[i] = numarray[i+1];
                drinkarray[i] = drinkarray[i+1];
                numarray[i+1] = tmp;
                drinkarray[i+1] = stmp;
                i = -1;
            }
        }

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;

        for (int i = 0; i < count; i++) {
            String selectc = String.format("SELECT * FROM TABLE_SUGGESTION WHERE FIELD_SUGGESTION = '%s' and FIELD_TYPE = '%s'", drinkarray[i], s);
            Cursor curc = cafedb.rawQuery(selectc, null);
            curc.moveToFirst();
            if (curc.getCount() != 0) {
                LinearLayout innerlayout = new LinearLayout(v.getContext());
                innerlayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                innerlayout.setOrientation(LinearLayout.HORIZONTAL);
                layout.addView(innerlayout);

                TextView nullview = new TextView(v.getContext());
                nullview.setWidth(width / 5);
                innerlayout.addView(nullview);

                Button myButton = new Button(v.getContext());
                final String text = curc.getString(1);
                myButton.setId(inc++);
                myButton.setText(text);
                myButton.setWidth(width * 3 / 5);
                //myButton.setBackgroundResource(R.drawable.ic_button_big);
                innerlayout.addView(myButton);
                myButton = ((Button) v.findViewById(myButton.getId()));
                //btn_search.setOnClickListener(MainActivity.this);
                myButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, CoffeeList.class);
                        intent.putExtra("CoffeeName", text);
                        intent.putExtra("DrinkType", s);
                        startActivity(intent);

                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-lastTimeBackPressed<1500){
            finish();
            return;
        }

        Toast.makeText(this,"'뒤로' 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();

        lastTimeBackPressed = System.currentTimeMillis();

    }
    public static final String PACKAGE_DIR = "/data/data/ssjk.cafein/";
    public static final String DATABASE_NAME = "cafe.sqlite";
    public static final String COPY2DATABASE_NAME = "cafesd1.db";
    public static void initialize(Context ctx) {

        Toast.makeText(ctx,"Getting Cafe Database...",Toast.LENGTH_SHORT).show();
        // check
        File folder = new File(PACKAGE_DIR + "databases");
        folder.mkdirs();
        File outfile = new File(PACKAGE_DIR + "databases/" + COPY2DATABASE_NAME);

        if (outfile.exists()){
            check = 1;
            outfile.delete();
            outfile = new File(PACKAGE_DIR + "databases/" + COPY2DATABASE_NAME);
        }

        if (outfile.length() <= 0) {
            AssetManager assetManager = ctx.getResources().getAssets();
            try {
                InputStream is = assetManager.open(DATABASE_NAME, AssetManager.ACCESS_BUFFER);
                long filesize = is.available();
                byte [] tempdata = new byte[(int)filesize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();
                FileOutputStream fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void updatesuggestion(Context context) {
        if (check == 1) {
            Toast.makeText(context,"Analyzing Data...",Toast.LENGTH_SHORT).show();
            String select = String.format("SELECT * FROM Cafein");
            Cursor cur = cafedb.rawQuery(select, null);
            cur.moveToFirst();
            while (cur.getString(1).equals("EOF") == false) {
                String text = cur.getString(1);
                String select1 = String.format("SELECT * FROM TABLE_SUGGESTION WHERE FIELD_SUGGESTION= '%s'", text);
                Cursor cur1 = cafedb.rawQuery(select1, null);
                //cur1.moveToFirst();
                if (cur1.getCount() == 0) {
                    ContentValues values = new ContentValues();
                    values.put("FIELD_SUGGESTION", text);
                    //cafedb.insert("TABLE_SUGGESTION", null, values);
                    values.put("FIELD_TYPE", "CAFE");
                    cafedb.insert("TABLE_SUGGESTION", null, values);
                }
                while (text.equals(cur.getString(1)))
                    cur.moveToNext();
            }
            select = String.format("SELECT * FROM Cafein");
            cur = cafedb.rawQuery(select, null);
            cur.moveToFirst();
            while (cur.getString(1).equals("EOF") == false) {
                String text2 = cur.getString(3);
                if (text2 != null) {
                    String select1 = String.format("SELECT * FROM TABLE_SUGGESTION WHERE FIELD_SUGGESTION= '%s'", text2);
                    Cursor cur1 = cafedb.rawQuery(select1, null);
                    //cur1.moveToFirst();
                    if (cur1.getCount() == 0) {
                        ContentValues values = new ContentValues();
                        values.put("FIELD_SUGGESTION", text2);
                        //cafedb.insert("TABLE_SUGGESTION", null, values);
                        values.put("FIELD_TYPE", cur.getString(9));
                        //cafedb.insert("TABLE_SUGGESTION", null, values);
                        String select2 = String.format("SELECT * FROM Cafein WHERE Drink_Name= '%s'", text2);
                        Cursor cur2 = cafedb.rawQuery(select2, null);
                        values.put("ITEM_NUM", cur2.getCount());
                        cafedb.insert("TABLE_SUGGESTION", null, values);
                    }
                    cur1.close();

                }
                cur.moveToNext();
            }
        }
    }
}
// circular buttons size 55dip, padding 4