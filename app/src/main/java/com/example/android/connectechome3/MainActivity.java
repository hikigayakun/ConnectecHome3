package com.example.android.connectechome3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.connectechome3.adapters.CategoryAdapter;
import com.example.android.connectechome3.model.Category;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements ItemClickListener {

    //Firebase ve class değişkenleri tanımlama test
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mUsername; //sonradan kaldır
    private String mPhotoUrl; //sonradan kaldır
    private static final String TAG = "MainActivity"; //Bulunduğun sayfa
    private ProgressBar mProgressBar; //processbar a bak
    private LinearLayoutManager mLinearLayoutManager; // bu ne?
    private String ustCatID; // hangi kategori seçildi onu takip et

    Context context;


    //kategori değikenleri
    private EditText editText; // isim değiştirmek için
    private Spinner spinner; //şimdilik kalsın bakıcam
    private FloatingActionButton floatingActionButton;// yapışan buton / kategori ekleme
    private DatabaseReference databaseReferenceCategory; //kategorilerin database  referansı
    private List<Category> categories; //listelenecek kategoriler
    private RecyclerView mRecyclerView; //Recyclemanager
    private RecyclerView.LayoutManager mLayoutManager; //Recycler manager

    //private RecyclerView Adapter
    private CategoryAdapter categoryAdapter;


    @Override
    protected void onStart() {
        super.onStart();
        databaseReferenceCategory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categories.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Category category = postSnapshot.getValue(Category.class);
                    categories.add(category);
                }

                mRecyclerView.setHasFixedSize(true);


                //use a linear layout manager
                mLayoutManager = new LinearLayoutManager(MainActivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);

                categoryAdapter = new CategoryAdapter(MainActivity.this, categories);
                categoryAdapter.setClickListener(MainActivity.this);
                mRecyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbarı oluşturr
        //Toolbar mToolbar = (Toolbar) findViewById(R.id.menuToolbar);
        //setSupportActionBar(mToolbar);
        //Üstteki Toolbar Ayarları
        //ActionBar mActionBar = getSupportActionBar();
        //mActionBar.setDisplayShowHomeEnabled(true);
        //mActionBar.setDisplayShowTitleEnabled(true);


        //  Firebase girişi başlat/kontrol et
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, FirebaseUI.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();

        }


        // Firebase  database değişkenleri tanımla

        databaseReferenceCategory = FirebaseDatabase.getInstance().getReference();
        //Spinner kategori seçmek için
        editText = (EditText)findViewById(R.id.editText);
        spinner = (Spinner)findViewById(R.id.spinnerUstCategory);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.addCategory);
        mRecyclerView = (RecyclerView)findViewById(R.id.categoryRecyclerView);

        categories = new ArrayList<>();

        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addCategory();
            }
        });

        /*String id = databaseReferenceCategory.push().getKey();
        //TODO deneme database ata

        Category category = new Category(id,"0",mFirebaseUser.getUid(),"deneme",false);
        databaseReferenceCategory.child(id).setValue(category);
        */



    }

    private void addCategory(){
        if (editText.getText().length()>0){
            String id = databaseReferenceCategory.push().getKey();
            ustCatID = "1";
            //TODO ust kategori bilgisini ustCatID ye ata
            Category category = new Category(id,ustCatID,mFirebaseUser.getUid(),editText.getText().toString(),true);
            databaseReferenceCategory.child(id).setValue(category);
        }
        else {
            showAlertDialog();
        }

    }

    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Hata");
        builder.setMessage("lütfen kategori adını giriniz");

        String possitiveText = "Tamam";
        builder.setPositiveButton(possitiveText,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //positive buton tanımlaması
                    }
                });
        AlertDialog dialog = builder.create();
        //display dialog
        dialog.show();

    }
    //alt kategoriye git
    @Override
    public void onClick(View view, int position) {
        //TODO kategoriye tıklayınca içini göster
    }

    //Ayarlar Menüsü
    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.ayarlar, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {

                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    }

