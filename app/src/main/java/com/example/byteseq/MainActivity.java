package com.example.byteseq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button login;
    FirebaseAuth auth;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email =findViewById(R.id.EmailAddressL);
        password=findViewById(R.id.PasswordL);
        login=findViewById(R.id.Login1);

        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd= new ProgressDialog(MainActivity.this);
                pd.setMessage("Please wait....");
                pd.show();

                String str_email=email.getText().toString();
                String str_password=password.getText().toString();

                if ( TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
                    Toast.makeText(MainActivity.this, "All Fields are Required", Toast.LENGTH_SHORT).show();
                }else{
                    auth.signInWithEmailAndPassword(str_email,str_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()){

                                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users")
                                                .child(auth.getCurrentUser().getUid());
                                        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                pd.dismiss();
                                                Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(MainActivity.this, SecondActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();



                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                pd.dismiss();
                                            }
                                        });

                                    }else {
                                        pd.dismiss();
                                        Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                }

            }
        });


    }
}