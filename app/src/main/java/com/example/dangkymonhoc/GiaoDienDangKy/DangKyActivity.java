package com.example.dangkymonhoc.GiaoDienDangKy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.dangkymonhoc.Adapter.LopHocAdapter;
import com.example.dangkymonhoc.Adapter.MonHocAdapter;
import com.example.dangkymonhoc.Model.LopHoc;
import com.example.dangkymonhoc.Model.MonHoc;
import com.example.dangkymonhoc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DangKyActivity extends AppCompatActivity {

    String selectMon,selectLop;
    String value;
    Spinner spinner,spinnerLop;
    ArrayList<MonHoc> listMonHoc;
    ArrayList<LopHoc> listLopHoc;
    TextView tvGiangVien,tvCaHoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        spinner = findViewById(R.id.spinner);
        spinnerLop = findViewById(R.id.spinnerLop);
        tvGiangVien = findViewById(R.id.tvGiangVien);
        tvCaHoc = findViewById(R.id.tvCaHoc);
        DangKy();




    }
    private void DangKy() {
        String url = "https://dangkymonhoc.000webhostapp.com/API/getMonHocTheoNganh.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAGRegister",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (jsonObject.getInt("resultCode") == 1){
                                listMonHoc = new ArrayList<MonHoc>();
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject data = jsonArray.getJSONObject(i);
                                    MonHoc monHoc = new MonHoc();

                                    monHoc.setIdMonHoc(data.getInt("IdMonHoc"));
                                    monHoc.setMaMon(data.getString("MaMonHoc"));
                                    monHoc.setMonHoc(data.getString("MonHoc"));
                                    listMonHoc.add(monHoc);

                                }
                                MonHocAdapter monHocAdapter = new MonHocAdapter(DangKyActivity.this,R.layout.items_monhoc,listMonHoc);

                                spinner.setAdapter(monHocAdapter);

                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        selectMon = String.valueOf(listMonHoc.get(i).getIdMonHoc());
                                        LopHoc(selectMon);
                                        Toast.makeText(DangKyActivity.this," "+listMonHoc.get(i).getIdMonHoc(),Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                        selectMon = "";
                                        LopHoc(selectMon);
                                    }
                                });

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
//                Log.d("AAAA111","1");
                params.put("idNganh","1");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void LopHoc(final String idMonHoc){
        String url = "https://dangkymonhoc.000webhostapp.com/API/getLopHocTheoMon.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (jsonObject.getInt("resultCode") == 1){
                                Toast.makeText(DangKyActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                listLopHoc = new ArrayList<LopHoc>();
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject data = jsonArray.getJSONObject(i);
                                    LopHoc lopHoc = new LopHoc();
//
                                    lopHoc.setIdLopHoc(data.getInt("Id_LopHoc"));
                                    lopHoc.setLopHoc(data.getString("LopHoc"));
                                    lopHoc.setTenMonHoc(data.getString("MonHoc"));
                                    lopHoc.setGiangVien(data.getString("GiangVien"));
                                    lopHoc.setCaHoc(data.getString("CaHoc"));
                                    listLopHoc.add(lopHoc);

                                }
                                LopHocAdapter lopHocAdapter = new LopHocAdapter(DangKyActivity.this,R.layout.items_lophoc,listLopHoc);
                                spinnerLop.setAdapter(lopHocAdapter);
                                spinnerLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        tvGiangVien.setText(listLopHoc.get(i).getGiangVien());
                                        tvCaHoc.setText(listLopHoc.get(i).getCaHoc());
                                        Toast.makeText(DangKyActivity.this,listLopHoc.get(i).getLopHoc(),Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });



                            }else{
                                Toast.makeText(DangKyActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR",error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idMonHoc",idMonHoc);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


}