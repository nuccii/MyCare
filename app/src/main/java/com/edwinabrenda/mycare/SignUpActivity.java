package com.edwinabrenda.mycare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {

    EditText username,email,contact,password,cpassword;
    Button btnsignup;
    TextView signin;
    TextInputLayout textInputLayoutUsername,textInputLayoutEmail,textInputLayoutContact,textInputLayoutPassword,textInputLayoutCpassword;
    SqliteHelper sqliteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sqliteHelper=new SqliteHelper(this);
        initTextViewLogin();
        initViews();

        btnsignup=findViewById(R.id.btnsignup);
        signin=findViewById(R.id.textViewLogin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Redirecting...",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),HomePage.class);
                startActivity(intent);
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String userName=username.getText().toString();
                    String Email=email.getText().toString();
                    String Contact=contact.getText().toString();
                    String Password=password.getText().toString();
                    String Cpassword=cpassword.getText().toString();
                    if(!sqliteHelper.isEmailExists(Email)){
                        sqliteHelper.addUser(new User(null,userName,Email,Contact,Password,Cpassword));
                        Snackbar.make(btnsignup,"User Registered Successfully! Please Login",Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }

                        },Snackbar.LENGTH_LONG);
                    }else{
                        Snackbar.make(btnsignup,"User already exists with same email",Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    public void initTextViewLogin(){
        TextView textViewLogin=findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void initViews(){
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        contact=findViewById(R.id.contact);
        password=findViewById(R.id.password);
        cpassword=findViewById(R.id.cpassword);
        btnsignup=findViewById(R.id.btnsignup);
        textInputLayoutUsername=findViewById(R.id.textInputLayoutUsername);
        textInputLayoutEmail=findViewById(R.id.textInputLayoutEmail);
        textInputLayoutContact=findViewById(R.id.textInputLayoutContact);
        textInputLayoutPassword=findViewById(R.id.textInputLayoutPassword);
        textInputLayoutCpassword=findViewById(R.id.textInputLayoutCpassword);
    }
    public boolean validate(){
        boolean valid=false;
        String Username=username.getText().toString();
        String Email=email.getText().toString();
        String Contact=contact.getText().toString();
        String Password=password.getText().toString();
        String Cpassword=cpassword.getText().toString();

        if(Username.isEmpty()){
            valid=false;
            textInputLayoutUsername.setError("Please enter valid username!");
        }else{
            if(Username.length()>3){
                valid=true;
                textInputLayoutUsername.setError(null);
            }else{
                valid=false;
                textInputLayoutUsername.setError("Username is too short!");
            }
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            valid=false;
            textInputLayoutEmail.setError("Please enter a valid email!");
        }else{
            valid=true;
            textInputLayoutEmail.setError(null);
        }
        if(Contact.isEmpty()){
            valid=false;
            textInputLayoutContact.setError("Please enter a valid contact!");
        }else{
            if(Password.length()>15){
                valid=false;
                textInputLayoutContact.setError("Invalid Contact.too large");
            }else{
                valid=true;
                textInputLayoutContact.setError(null);
            }
        }
        if(Password.isEmpty()){
            valid=false;
            textInputLayoutPassword.setError("Please enter a valid password!");
        }else{
            if(Password.length()>5){
                valid=true;
                textInputLayoutPassword.setError(null);
            }else{
                valid=false;
                textInputLayoutPassword.setError("Password is too short");
            }
        }
        if(Cpassword.isEmpty()){
            valid=false;
            textInputLayoutPassword.setError("Please enter a valid password!");
        }else{
            if(Cpassword.length()>5){
                valid=true;
                textInputLayoutPassword.setError(null);
            }else{
                valid=false;
                textInputLayoutPassword.setError("Password is too short");
            }
        }
        return valid;
    }
}