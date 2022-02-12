package com.edwinabrenda.mycare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import static android.text.Html.fromHtml;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText email,password;
    TextView account;
    SqliteHelper sqliteHelper;
    TextInputLayout textInputLayoutEmail,textInputLayoutPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sqliteHelper=new SqliteHelper(this);
        initCreateAccountTextView();
        initViews();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String mail=email.getText().toString();
                    String pass=password.getText().toString();

                    User currentUser=sqliteHelper.Authenticate(new User(null,null,mail,null,pass,null));
                    if(currentUser!=null){
                        Snackbar.make(login,"successfully logged in!",Snackbar.LENGTH_LONG).show();
                        Intent intent=new Intent(LoginActivity.this,HomePage.class);
                        startActivity(intent);
                    }
                    else{
                        Snackbar.make(login,"Failed to logged in!",Snackbar.LENGTH_LONG).show();
                    }
                }

            }
        });
    }
    public void initCreateAccountTextView(){
        account=findViewById(R.id.create);
        account.setText(fromHtml("<font color='#219'>I don't have an account yet.</font><font color='#0c0099'>create one</font>"));
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initViews(){
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        textInputLayoutEmail=findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword=findViewById(R.id.textInputLayoutPassword);
        login=findViewById(R.id.login);
    }
    public boolean validate(){
        boolean valid=false;
        String Email=email.getText().toString();
        String Password=password.getText().toString();
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            valid=false;
            textInputLayoutEmail.setError("Please enter a valid email!");
        }else{
            valid=true;
            textInputLayoutEmail.setError(null);
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
                textInputLayoutPassword.setError("Password is too short!");
            }
        }
        return valid;
    }
}