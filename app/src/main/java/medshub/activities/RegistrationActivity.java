package medshub.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medshub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    EditText name,email,password;
    private FirebaseAuth auth;
    Button signupBtn;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

       // getSupportActionBar().hide();

        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();
        }
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signupBtn = findViewById(R.id.button);

        sharedPreferences=getSharedPreferences("onBoardingScreen",MODE_PRIVATE);

        boolean isFirstTime = sharedPreferences.getBoolean("firstTime",true);

        if (isFirstTime){
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putBoolean("firstTime",false);
            editor.commit();

            Intent intent=new Intent(RegistrationActivity.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();

        }

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    public void signup() {
        String userName=name.getText().toString();
        String userEmail=email.getText().toString();
        String userPassword=password.getText().toString();


        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Enter Name!",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Enter Email Address!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Enter Password!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(userPassword.length()<6){
            Toast.makeText(this, "Password too short,enter minimum 6 characters!",Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this,"Successfully Register",Toast.LENGTH_SHORT);
                            startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                        }else{
                            Toast.makeText(RegistrationActivity.this, "Registration Failed"+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void signin(View view) {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));


    }
}