package ssjk.cafein;

/**
 * Created by wqe13 on 2016-11-23.
 */

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SearchView.OnSuggestionListener;
import android.support.v7.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.CursorAdapter;
import android.widget.Toast;


public class BeginSearchActivity extends AppCompatActivity{

    SQLiteDatabase cafedb;
    public String select;
    public Cursor cursor;
    ListView layout;
    CustomListAdapter m_Adapter;
    public SearchView searchView;
    SuggestionsDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);
        layout = (ListView) findViewById(R.id.searchList);
        m_Adapter = new CustomListAdapter();
        layout.setAdapter(m_Adapter);
        database = new SuggestionsDatabase(this);
        /*mAdapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);*/

            cafedb = openOrCreateDatabase("cafesd1.db", Context.MODE_PRIVATE, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {

                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Toast.makeText(BeginSearchActivity.this,"onSuggestionClick",Toast.LENGTH_LONG).show();
                SQLiteCursor cursor = (SQLiteCursor) searchView.getSuggestionsAdapter().getItem(position);
                int indexColumnSuggestion = cursor.getColumnIndex( SuggestionsDatabase.FIELD_SUGGESTION);

                searchView.setQuery(cursor.getString(indexColumnSuggestion), false);

                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String inquery) {
                String query = inquery.toUpperCase();
                String type;
                select = String.format("SELECT * FROM Cafein WHERE Cafe_Name='%s' and Drink_Kind = 'Cafe_Info'", query);
                cursor = cafedb.rawQuery(select, null);
                cursor.moveToFirst();
                int morethanone = 0;
                String secondtype = null;
                if (cursor.getCount() == 0) {
                    select = String.format("SELECT * FROM Cafein WHERE Drink_Name ='%s'", query);
                    cursor = cafedb.rawQuery(select, null);
                    cursor.moveToFirst();
                    type = cursor.getString(9);
                    String s = String.format("SELECT * FROM Cafein WHERE Drink_Name='%s' and Drink_Kind_app != '%s'", query, type);
                    Cursor c  = cafedb.rawQuery(s, null);
                    if (c.getCount() != 0){
                        c.moveToFirst();
                        morethanone = 1;
                        secondtype = c.getString(9);
                    }
                }else {
                    type = "CAFE";
                }
                if (cursor == null || cursor.getCount() == 0) {
                    Toast.makeText(BeginSearchActivity.this, "No records found!", Toast.LENGTH_LONG).show();
                } else {
                    if (type.equals("CAFE"))
                    m_Adapter.add(cursor.getString(1), type);
                    else {
                        Toast.makeText(BeginSearchActivity.this, "Search found!", Toast.LENGTH_LONG).show();
                        m_Adapter.add(cursor.getString(3), type);
                        if (morethanone == 1){
                            m_Adapter.add(cursor.getString(3), secondtype);
                        }
                    }
                }
                //customAdapter.swapCursor(cursor);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String innewText) {
                String newText = innewText.toUpperCase();
                newText.toUpperCase();
                Cursor cursor = database.getSuggestions(newText);
                if(cursor.getCount() != 0)
                {
                    String[] columns = new String[] {SuggestionsDatabase.FIELD_SUGGESTION };
                    int[] columnTextId = new int[] { android.R.id.text1};

                    SuggestionSimpleCursorAdapter simple = new SuggestionSimpleCursorAdapter(getBaseContext(),
                            android.R.layout.simple_list_item_1, cursor,
                            columns , columnTextId
                            , 0);

                    searchView.setSuggestionsAdapter(simple);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
