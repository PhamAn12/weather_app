package com.example.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.weather.Fragments.Today;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ListView list;
    ArrayAdapter<String> adapter;
    SearchView editSearch;
    ImageView buttonSearch;
    EditText editTextView;
    TextView txtLocation;
    ListView listView;
    String tempUnit = "°C";
    String windUnit = "m/s";
    ImageView imageBack;
    String cityLocation = "";
    String city = "";
    List<String> listTP = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        buttonSearch = (ImageView) findViewById(R.id.buttonSearch);
        txtLocation = (TextView) findViewById(R.id.txt_city_location);
        editTextView = (EditText) findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.listviewSearch);
        imageBack = (ImageView) findViewById(R.id.imageBack);
        Intent intent = getIntent();
        if(intent.getStringExtra("tempUnit") !=null) {
            tempUnit = intent.getStringExtra("tempUnit");
            windUnit = intent.getStringExtra("windUnit");
        }
        if(intent.getStringExtra("cityLocation") !=null) {
            Log.d("search", intent.getStringExtra("cityLocation"));
            if (!intent.getStringExtra("cityLocation").isEmpty()) {
                cityLocation = intent.getStringExtra("cityLocation");
                Log.d("main", "temp:" + cityLocation);
                txtLocation.setText(cityLocation + (" (Current position)"));
                txtLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                        intent.putExtra("city",cityLocation);
                        intent.putExtra("tempUnit",tempUnit);
                        intent.putExtra("windUnit",windUnit);
                        startActivity(intent);

                        Bundle bundle = new Bundle();
                        bundle.putString("dataC",cityLocation);
                        Today today = new Today();
                        today.setArguments(bundle);
                        Log.d("data", "onClick: " + cityLocation);
                    }

                });
            }
            else {
                txtLocation.setText("Restart with GPS");
                txtLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this,WaitingScreenActivity.class);
                        startActivity(intent);
                    }

                });
            }
        }
        else {
            txtLocation.setText("Restart with GPS");
            txtLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchActivity.this,WaitingScreenActivity.class);
                    startActivity(intent);
                }

            });
        }
        if(intent.getStringExtra("city") !=null) {
            city = intent.getStringExtra("city");
        }

        final String[] tenThanhPho = {"Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh", "Bến Tre", "Bình Định",
                "Bình Dương", "Bình Thuận", "Cà Mau", "Cao Bằng", "Cần Thơ", "Đà Nẵng", "Đắk Lắk", "Đắk Nông", "Điện Biên",
                "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang", "Hà Nam", "Hà Nội", "Hà Tĩnh", "Hải Dương", "Hải Phòng", "Hậu Giang", "Hòa Bình",
                "Hưng Yên", "Khánh Hòa", "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An", "Nam Định", "Nghệ An",
                "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Phú Yên", "Quảng Bình", "Quảng Nam", "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng",
                "Sơn La", "Tây Ninh", "Thái Bình", "Thái Nguyên", "Thanh Hóa", "Thanh pho Ho Chi Minh", "Thừa Thiên Huế", "Tiền Giang",
                "Trà Vinh", "Tuyên Quang", "Vĩnh Long", "Vĩnh Phúc", "Yên Bái"};

        for(String s : tenThanhPho) {
            listTP.add(s);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listTP){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

                /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(getResources().getColor(R.color.common));

                return view;
            }
        };
        listView.setAdapter(adapter);

        editTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SearchActivity.this.adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editTextView.setText(adapter.getItem(i));
            }
        });

//        buttonSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String data = editTextView.getText().toString();
//                if (data.equals("")){
//                    return;
//                }
//                Intent intent = new Intent();
//                intent.putExtra("data", data);
//                setResult(810, intent);
//                Log.d("data", "data " + data);
//                finish();
//            }
//        });
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editTextView.getText().toString();
                if(data.equals("")) return;

                Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                intent.putExtra("cityName",data);
                intent.putExtra("city",cityLocation);
                intent.putExtra("tempUnit",tempUnit);
                intent.putExtra("windUnit",windUnit);
                startActivity(intent);

                Bundle bundle = new Bundle();
                bundle.putString("dataC",data);
                Today today = new Today();
                today.setArguments(bundle);
                Log.d("data", "onClick: " + data);
            }

        });
        if (cityLocation.isEmpty() && city.isEmpty()) {
            Log.d("search", "not null" + cityLocation + ", " + city);;

            imageBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            });
        }
        else {
            Log.d("search", "null");
            imageBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("data", "hanoi");
                    setResult(810, intent);
                    finish();
                }
            });
        }
    }



}
