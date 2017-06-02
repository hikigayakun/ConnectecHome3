package com.example.android.connectechome3.adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.FitWindowsFrameLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.connectechome3.ItemClickListener;
import com.example.android.connectechome3.R;
import com.example.android.connectechome3.model.Category;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

/**
 * Created by caglar on 28.05.2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    List<Category> categoryList;
    Context context;
    private ItemClickListener clickListener;


    public CategoryAdapter(Context context, List<Category> categoryList){
        this.categoryList=categoryList;
        this.context=context;
    }

    //kategorilerin görüntüsünü oluştur
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView categoryName; //görüntülenmesini istediğin category özellikleri
        public ImageView optionMenu;

        public ViewHolder(View view){
            super(view);

            categoryName = (TextView) view.findViewById(R.id.categoryName);
            optionMenu = (ImageView) view.findViewById(R.id.optionMenu);
            view.setTag(view);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(clickListener !=null) clickListener.onClick(v,getAdapterPosition());
        }
    }

    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new ViewHolder(itemView);
    }

    //itemlerin içini doldur
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.categoryName.setText(categoryList.get(position).getCategoryName());
        holder.optionMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, holder.optionMenu);
                popup.inflate(R.menu.menu_items);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit:
                                //TODO category düzenleme fonksiyou
                                //updateCategory(categoryList.get(position));
                                break;
                            case R.id.delete:
                                //TODO category silme fonksiyou
                                //updateCategory(categoryList.get(position));
                                break;
                        }
                        return false;
                    }
                });
                //popup menüyü göster
                popup.show();
            }
        });


    }

    private Category updateCategory;
    private void updateCategory(Category category){
        updateCategory = category;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName=(EditText) dialogView.findViewById(R.id.editTextName);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateCustomer);
        //diğer butonlar vs buraya

        editTextName.setText(updateCategory.getCategoryName());


        dialogBuilder.setTitle("Müsteri Duzenleme");
        final AlertDialog b = dialogBuilder.create();
        b.show();
        //Buton  Güncelle komutu
        buttonUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (editTextName.getText().length()>0){
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference().child(updateCategory.getCategoryID());
                    Category category = new Category(updateCategory.getCategoryID(),updateCategory.getUstCatID(),
                            updateCategory.getUserID(),updateCategory.getCategoryName(),updateCategory.getAygitMi());
                    dR.setValue(category);
                    b.dismiss();
                    Toast.makeText(context,"Kategori Güncellendi",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //kategory sil
    private void deleteCategory(String id){
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("category").child(id);
        dR.removeValue();
        //TODO alt kategorilerindekileri de sil butonu ekele

        Toast.makeText(context,"Kategori Silindi",Toast.LENGTH_SHORT).show();
    }

    public void setClickListener(ItemClickListener itemClickListner){
        this.clickListener = itemClickListner;
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
