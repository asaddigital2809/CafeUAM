package com.asad.mnsuam.myapplication.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.asad.mnsuam.myapplication.OrderStatus;
import com.asad.mnsuam.myapplication.R;


public class OrderActFragment extends Fragment {
    private OrderViewModel orderViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderViewModel=
                ViewModelProviders.of(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        orderViewModel.getText().observe(this, new Observer<String>() {
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
