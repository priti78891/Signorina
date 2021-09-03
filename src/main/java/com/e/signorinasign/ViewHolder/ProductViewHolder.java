package com.e.signorinasign.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import com.e.signorinasign.Interface.ItemClickListener;
import com.e.signorinasign.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtProductName,txtProductDescription,txtProductPrice;
    public ImageView imageView;
    public ItemClickListener listner;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView)itemView.findViewById(R.id.product_images);
        txtProductDescription =(TextView)itemView.findViewById(R.id.product_descriptions);
        txtProductName =(TextView)itemView.findViewById(R.id.product_names);
        txtProductPrice =(TextView)itemView.findViewById(R.id.product_prices);

    }
    public void setItemClickListner(ItemClickListener listner)
    {
        this.listner=listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);
    }

}
