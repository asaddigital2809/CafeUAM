package com.asad.mnsuam.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.asad.mnsuam.myapplication.Model.Common;
import com.asad.mnsuam.myapplication.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin,btnSignUp;
    EditText etPhone,etPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etPhone = (MaterialEditText)findViewById(R.id.mob);
        etPass = (MaterialEditText)findViewById(R.id.pass);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        etPhone.addTextChangedListener(txtWatch);
        etPass.addTextChangedListener(txtWatch);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference user_table = db.getReference("user");

        btnSignUp = (Button)findViewById(R.id.btnSign);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Please Wait...");
                mDialog.show();

                user_table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check User exist or not
                        if (dataSnapshot.child(etPhone.getText().toString()).exists()) {

                            //Get user Info
                            mDialog.dismiss();
                            User user = dataSnapshot.child(etPhone.getText().toString()).getValue(User.class);
                            user.setPhone(etPhone.getText().toString());
                            if (user.getPassword().equals(etPass.getText().toString())) {
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                Common.saveCurrentUser = user;
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        }else
                        {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "User Does Not Exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
    private TextWatcher txtWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String passInput = etPass.getText().toString().trim();
            String noInput = etPass.getText().toString().trim();
            btnLogin.setEnabled( !passInput.isEmpty() && !noInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
