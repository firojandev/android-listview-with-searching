package com.onlineicttutor.mylistviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView lv;

    private EditText inputSearch;

    String products[] = {"Samsung Mobile","Symphony","Nexus One","HP Laptop","Android Mobile","iPhone","Del Computer","Asus Computer","A4Tech Mouse", "G Mobile", "i Mobile"};

    private CustomAdapter adapter;

    ArrayList<HashMap<String, Object>> searchResults;

    ArrayList<HashMap<String, Object>> originalValues = new ArrayList<HashMap<String, Object>>();;

    HashMap<String, Object> temp = new HashMap<String, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylistactivity);

        lv = (ListView) findViewById(R.id.listView_item);

        inputSearch = (EditText) findViewById(R.id.editTextSearch);

        getMyValue();

        adapter = new CustomAdapter(MainActivity.this, R.layout.listview_row,
                searchResults);
        lv.setAdapter(adapter);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                // get the text in the EditText
                String searchString = inputSearch.getText().toString();
                int textLength = searchString.length();
                searchResults.clear();

                for (int i = 0; i < originalValues.size(); i++) {
                    String wCode = originalValues.get(i).get("pname")
                            .toString();
                    if (textLength <= wCode.length()) {
                        // compare the String in EditText with Names in the
                        // ArrayList
                        if (searchString.equalsIgnoreCase(wCode.substring(0,
                                textLength)))
                            searchResults.add(originalValues.get(i));
                    }
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });



    }

    private void getMyValue(){
        originalValues.clear();

        for (int j=0;j<products.length;j++){

            temp = new HashMap<String, Object>();
            temp.put("pname",products[j]);

            // add the row to the ArrayList
            originalValues.add(temp);
        }

        searchResults = new ArrayList<HashMap<String, Object>>(originalValues);
    }

    // define your custom adapter
    private class CustomAdapter extends ArrayAdapter<HashMap<String, Object>> {
        LayoutInflater inflater;

        public CustomAdapter(Context context, int textViewResourceId,
                             ArrayList<HashMap<String, Object>> Strings) {
            super(context, textViewResourceId, Strings);
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        // class for caching the views in a row
        private class ViewHolder {

            TextView productNameTxt;
        }

        ViewHolder viewHolder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.listview_row, null);
                viewHolder = new ViewHolder();


                viewHolder.productNameTxt = (TextView) convertView
                        .findViewById(R.id.product_name);


                // link the cached views to the convertview
                convertView.setTag(viewHolder);

            } else
                viewHolder = (ViewHolder) convertView.getTag();

            viewHolder.productNameTxt.setText(searchResults.get(position).get("pname")
                    .toString());

            // return the view to be displayed
            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
