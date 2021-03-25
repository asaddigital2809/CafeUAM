package com.asad.mnsuam.myapplication.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.asad.mnsuam.myapplication.Cart;
import com.asad.mnsuam.myapplication.OrderStatus;
import com.asad.mnsuam.myapplication.R;
import com.asad.mnsuam.myapplication.ui.tools.ToolsViewModel;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel=
        ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                Toast.makeText(getContext(), "this", Toast.LENGTH_SHORT).show();
                goToAttract();
            }
        });
        return root;
    }
    public void goToAttract()
    {
        Intent intent = new Intent(getContext(), OrderStatus.class);
        startActivity(intent);
    }




}