package com.example.lab8.adt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab8.R;
import com.example.lab8.dao.monhoc_dao;
import com.example.lab8.monhoc;

import java.util.ArrayList;

public class ad_monhoc extends  RecyclerView.Adapter<ad_monhoc.Viewhodel>{
 private Context context;
 private ArrayList<monhoc> list;
  private monhoc_dao monhoc_dao;

    public ad_monhoc(Context context, ArrayList<monhoc> list,monhoc_dao monhoc_dao) {
        this.context = context;
        this.list = list;
        this.monhoc_dao = monhoc_dao;

    }

    @NonNull
    @Override
    public Viewhodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();//hiển thị list
        View view = inflater.inflate(R.layout.llist_item_sanpham,parent,false);//hiển thị list
        return new Viewhodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhodel holder, int position) {
        holder.txtmamh.setText(list.get(position).getMamh());
        holder.txttenmh.setText(list.get(position).getTenmh());
        holder.txtsotiet.setText(String.valueOf(list.get(position).getSotiet()));//hiển thị list

        //xóa
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdle(list.get(holder.getAdapterPosition()).getMamh(),
                        list.get(holder.getAdapterPosition()).getId());//phải là kiểu int
            }
        });
        //update
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialogsua(list.get(holder.getAdapterPosition()));

            }
        });
    }
       //dành cho xóa
    private void showdle(String mamh, int id ){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setIcon(R.drawable.ic_warn);
        builder.setMessage("Bạn có muốn xóa sản phẩm:\"" + mamh +"\" không?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean check = monhoc_dao.delete(id);
                if(check){
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list = monhoc_dao.getDs();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewhodel extends RecyclerView.ViewHolder{
        //hiển thị list
        TextView txtmamh, txttenmh, txtsotiet;
        ImageView btn_delete, btn_edit;
        public Viewhodel(@NonNull View itemView) {
            super(itemView);
            txtmamh = itemView.findViewById(R.id.txtmamh);
            txttenmh = itemView.findViewById(R.id.txttenmh);
            txtsotiet = itemView.findViewById(R.id.txtsotiet);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_edit = itemView.findViewById(R.id.btn_edit);

        }
    }

    private void opendialogsua(monhoc dv) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        //tạo view gán layout
        LayoutInflater inflater=((Activity) context).getLayoutInflater();
        View view=inflater.inflate(R.layout.item_update,null);
        builder.setView(view);//gán view vào hôp thoại
        Dialog dialog=builder.create();//tạo hộp thoại
        dialog.show();

        //ánh xạ các thành phần widget
        EditText ed_mamh=view.findViewById(R.id.up_mamh);
        EditText ed_tenmh=view.findViewById(R.id.up_tenmh);
        EditText ed_sotiet=view.findViewById(R.id.up_sotiet);
        Button btn_sua=view.findViewById(R.id.btn_update);
        Button btn_huy=view.findViewById(R.id.btn_huy);

        //gán dữ liệu lên các widget
        ed_mamh.setText(dv.getMamh());
        ed_tenmh.setText(dv.getTenmh());
        ed_sotiet.setText(dv.getSotiet()+"");


//        edtnoidung.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar lich=Calendar.getInstance();//tạo đối tượng để lấy ngày giờ hiện tại
//                int year=lich.get(Calendar.YEAR);
//                int month=lich.get(Calendar.MONTH);
//                int day=lich.get(Calendar.DAY_OF_MONTH);
//                //Tạo đối tượng DatePickerDialog và show nó
//                DatePickerDialog datedg=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        edtnoidung.setText(String.format("%d/%d/%d",dayOfMonth,month,year));
//                    }
//                },year,month,day);
//                datedg.show();
//            }
//        });

        ed_tenmh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Chọn mức độ khó cho công việc");
                String loai[] = {"Java", "Web"};
                builder1.setItems(loai, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_tenmh.setText(loai[which]);
                    }
                });
                AlertDialog alertDialog = builder1.create();
                alertDialog.show();
            }
        });

        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maa = String.valueOf(ed_mamh.getText());
                for(monhoc mh : monhoc_dao.getDs()){
                    if(mh.getMamh().equalsIgnoreCase(maa)){
                        Toast.makeText(context, "Mã môn học không được trùng", Toast.LENGTH_SHORT).show();
                        ed_mamh.requestFocus();
                        return;
                    }
                }
                if (TextUtils.isEmpty(ed_tenmh.getText().toString())||TextUtils.isEmpty(ed_mamh.getText().toString())||TextUtils.isEmpty(ed_sotiet.getText().toString())){
                    Toast.makeText(context, "Yêu cầu nhập đầy đủ", Toast.LENGTH_SHORT).show();
                }else{

                    try {
                        Integer so  = Integer.parseInt(ed_sotiet.getText().toString());
                        if (so < 0 || so > 10) {
                            Toast.makeText(context, "Yêu cầu số tiết từ 1 đến 10", Toast.LENGTH_SHORT).show();
                            return ;
                        }
                    }catch (Exception e){
                        Toast.makeText(context, "Số tiết phải là số ", Toast.LENGTH_SHORT).show();

                    }
                    try {
                        dv.setMamh(ed_mamh.getText().toString());
                        dv.setTenmh(ed_tenmh.getText().toString());
                        dv.setSotiet(Integer.parseInt(ed_sotiet.getText().toString()));

                        if (monhoc_dao.update(dv)){
                            list.clear();
                            list.addAll(monhoc_dao.getDs());
                            notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Update thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(context, "Tiền phải là số", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }
}


