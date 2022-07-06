package medshub.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.medshub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import medshub.adapters.MyCartAdapter;
import medshub.models.MyCartModel;

public class CartActivity extends AppCompatActivity {

    int overAllTotalAmount;
    TextView overAllAmount;

    Toolbar toolbar;
    RecyclerView recyclerView;
    List<MyCartModel> cartModelList;
    MyCartAdapter cartAdapter;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        toolbar=findViewById(R.id.my_cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get data from my cart adapter
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver,new IntentFilter("MyTotalAmount"));

        overAllAmount=findViewById(R.id.textView3);
        recyclerView =findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartModelList=new ArrayList<>();
        cartAdapter=new MyCartAdapter(this,cartModelList);
        recyclerView.setAdapter(cartAdapter);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        MyCartModel myCartModel = doc.toObject(MyCartModel.class);
                        cartModelList.add(myCartModel);
                        cartAdapter.notifyDataSetChanged();

                    }
                }
            }
        });

    }

    public BroadcastReceiver mMessageReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int totalBill = intent.getIntExtra("totalAmount",0);
            overAllAmount.setText("Total Amount :"+totalBill+"$");

        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
        }
        return true;
    }
}