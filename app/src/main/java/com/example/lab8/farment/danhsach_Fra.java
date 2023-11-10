package com.example.lab8.farment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab8.R;
import com.example.lab8.adt.ad_monhoc;
import com.example.lab8.dao.monhoc_dao;
import com.example.lab8.monhoc;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;


public class danhsach_Fra extends Fragment {
    private RecyclerView recyclerView;
    private FloatingActionButton floatadd;

    private monhoc_dao monhoc_dao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //lấy danh sách
        View view = inflater.inflate(R.layout.fragment_danhsach_,container);
        recyclerView = view.findViewById(R.id.rcvCongViec);
        floatadd = view.findViewById(R.id.btnAdd);
        monhoc_dao = new monhoc_dao(getContext());
        ArrayList<monhoc> list = monhoc_dao.getDs();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ad_monhoc adMonhoc = new ad_monhoc(getContext(),list,monhoc_dao);
        recyclerView.setAdapter(adMonhoc);

        //add
        floatadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogAdd();
            }
        });
        return view;
    }

    private void showDialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.view_add,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //xử lý chức năng thêm
        EditText mamh = view.findViewById(R.id.ad_mmh);
        EditText tenmh = view.findViewById(R.id.ad_tenmh);
        EditText sotiet = view.findViewById(R.id.ad_sotiet);
        Button btnadd = view.findViewById(R.id.btn_Add);
        Button btnhuy = view.findViewById(R.id.btn_Close);

        tenmh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setTitle("Chọn mức độ khó cho công việc");
                String loai[] = {"Java", "Web"};
                builder1.setItems(loai, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tenmh.setText(loai[which]);
                    }
                });
                AlertDialog alertDialog = builder1.create();
                alertDialog.show();
            }
        });

//        sdtgia.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar lich=Calendar.getInstance();//tạo đối tượng để lấy ngày giờ hiện tại
//                int year=lich.get(Calendar.YEAR);
//                int month=lich.get(Calendar.MONTH);
//                int day=lich.get(Calendar.DAY_OF_MONTH);
//                //Tạo đối tượng DatePickerDialog và show nó
//                DatePickerDialog datedg=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        sdtgia.setText(String.format("%d/%d/%d",dayOfMonth,month,year));
//                    }
//                },year,month,day);
//                datedg.show();
//            }
//        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mamhh = mamh.getText().toString();
                String tenmhh = tenmh.getText().toString();
                String sotieth = sotiet.getText().toString();

                String ma = String.valueOf(mamh.getText());
                for(monhoc mh : monhoc_dao.getDs()){
                    if(mh.getMamh().equalsIgnoreCase(ma)){
                        Toast.makeText(getActivity(), "Mã môn học không được trùng", Toast.LENGTH_SHORT).show();
                        mamh.requestFocus();
                        return;
                    }
                }

                if (TextUtils.isEmpty(mamh.getText().toString())||TextUtils.isEmpty(tenmh.getText().toString())||TextUtils.isEmpty(sotiet.getText().toString())){
                    Toast.makeText(getActivity(), "Yêu cầu nhập đầy đủ", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        Integer so  = Integer.parseInt(sotiet.getText().toString());
                        if (so < 0 || so > 10) {
                            Toast.makeText(getActivity(), "Yêu cầu số tiết từ 1 đến 10", Toast.LENGTH_SHORT).show();
                            return ;
                        }
                    }catch (Exception e){
                        Toast.makeText(getContext(), "Số tiết phải là số ", Toast.LENGTH_SHORT).show();

                    }
                    try {
                    monhoc monhocdao = new monhoc(mamhh,tenmhh,Integer.parseInt(sotieth));
                        if(monhoc_dao.add(monhocdao)){

                            ArrayList<monhoc> list = monhoc_dao.getDs();
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(layoutManager);
                            ad_monhoc adMonhoc = new ad_monhoc(getContext(),list,monhoc_dao);
                            recyclerView.setAdapter(adMonhoc);
                            alertDialog.dismiss();
                            Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                }catch (Exception e){
                        Toast.makeText(getContext(), "Số tiết phải là số ", Toast.LENGTH_SHORT).show();
                    }}
            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//
                mamh.setText("");
                tenmh.setText("");
                sotiet.setText("");
                alertDialog.dismiss();
            }
        });

    }

}