package com.example.lab8;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.lab8.farment.danhsach_Fra;
import com.example.lab8.farment.thongtin_fra;


public class ViewPageAdapter extends FragmentStateAdapter {
    public ViewPageAdapter(@NonNull MainActivity fragment) {

        super(fragment);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new danhsach_Fra();
            case 1: return new thongtin_fra();
        }
        return new danhsach_Fra();
    }
    @Override
    public int getItemCount() {
        return 2;
    }
}
