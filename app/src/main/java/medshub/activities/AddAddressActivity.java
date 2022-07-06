package medshub.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medshub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    EditText name,address,city,postalCode,phoneNumber;
    Toolbar toolbar;
    Button addAddressBtn;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        toolbar=findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        name=findViewById(R.id.ad_name);
        address=findViewById(R.id.ad_address);
        city =findViewById(R.id.ad_city);
        phoneNumber=findViewById(R.id.ad_phone);
        postalCode=findViewById(R.id.ad_code);
        addAddressBtn=findViewById(R.id.ad_add_address);

        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName=name.getText().toString();
                String userCity=city.getText().toString();
                String userAddress=address.getText().toString();
                String userCode=postalCode.getText().toString();
                String userNumber=phoneNumber.getText().toString();

                String final_address="";

                if(!userName.isEmpty()){
                    final_address+=userName;
                }
                if(!userCity.isEmpty()){
                    final_address+=userCity;
                }
                if(!userAddress.isEmpty()){
                    final_address+=userAddress;
                }
                if(!userCode.isEmpty()){
                    final_address+=userCode;
                }
                if(!userNumber.isEmpty()){
                    final_address+=userNumber;
                }
                if(!userName.isEmpty() && !userCode.isEmpty() && !userCity.isEmpty() && !userAddress.isEmpty() && !userNumber.isEmpty()){

                    Map<String,String> map=new HashMap<>();
                    map.put("userAddress",final_address );

                    firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                           if(task.isSuccessful()){

                               Toast.makeText(AddAddressActivity.this,"Address Added",Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
                }
                else{
                    Toast.makeText(AddAddressActivity.this,"Kindly Fill All Field",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
        }
        return true;
    }
}