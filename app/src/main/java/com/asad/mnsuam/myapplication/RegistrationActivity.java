package com.asad.mnsuam.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.asad.mnsuam.myapplication.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    MaterialEditText etNameR, etPasswordR, etEmailR, etNumber,etAdd;
    Button btnRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etNameR = (MaterialEditText) findViewById(R.id.etName);
        etAdd = (MaterialEditText)findViewById(R.id.etAdd);
        etEmailR = (MaterialEditText)findViewById(R.id.etEmail);
        etNumber = (MaterialEditText) findViewById(R.id.etMob);
        etPasswordR = (MaterialEditText) findViewById(R.id.etPass);
        btnRegist = (Button) findViewById(R.id.btnSignUp);
        etNameR.addTextChangedListener(txtWatch);
        etEmailR.addTextChangedListener(txtWatch);
        etAdd.addTextChangedListener(txtWatch);
        etPasswordR.addTextChangedListener(txtWatch);
        etNumber.addTextChangedListener(txtWatch);


        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmInput(v);


            }
        });

    }
    private TextWatcher txtWatch = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nameInput = etNameR.getText().toString().trim();
            String passInput = etPasswordR.getText().toString().trim();
            String addInput = etAdd.getText().toString().trim();
            String noInput = etNumber.getText().toString().trim();
            String emailInput = etEmailR.getText().toString().trim();
            btnRegist.setEnabled(!nameInput.isEmpty() && !passInput.isEmpty() && !addInput.isEmpty() && !noInput.isEmpty() && !emailInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private boolean validateEmail(){
        String emailInput = etEmailR.getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            etEmailR.setError("Enter Valid Email");
            return false;
        }else{
            etEmailR.setError(null);
            return true;
        }
    }
    private boolean validateNo(){
        String numberInput = etNumber.getText().toString().trim();
        if(numberInput.length()<11){
            etNumber.setError("Enter Valid Number");
            return false;
        }
        else{
            etNumber.setError(null);
            return true;
        }
    }
    private boolean validatePasword(){
        String passwordInput = etPasswordR.getText().toString().trim();

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";
        if (!Pattern.compile(PASSWORD_PATTERN).matcher(passwordInput).matches()){
            etPasswordR.setError("Password Too weak, Use number,upper and lower case letters");
            return false;
        }else{
            etPasswordR.setError(null);
            return true;}
    }

    public void confirmInput(View v){
        //init database
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference user_table = db.getReference("user");
        if(!validateEmail() | !validateNo() | !validatePasword() ){
            btnRegist.setEnabled(false);
        }else{
            final ProgressDialog mDialog = new ProgressDialog(RegistrationActivity.this);
            mDialog.setMessage("Please Wait...");
            mDialog.show();


            user_table.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Check if already exist
                    if (dataSnapshot.child(etNumber.getText().toString()).exists()){
                        mDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this, "Phone Number Already Registered", Toast .LENGTH_SHORT).show();
                        System.out.println("onDataChange");
                    }else{
                        mDialog.dismiss();
                        User user = new User(etNameR.getText().toString(),etPasswordR.getText().toString(),etAdd.getText().toString(),
                                etEmailR.getText().toString());
                        user_table.child(etNumber.getText().toString()).setValue(user);
                        Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(RegistrationActivity.this, "Signed Up Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                        stopService(intent);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}

