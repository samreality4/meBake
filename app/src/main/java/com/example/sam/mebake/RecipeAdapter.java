package com.example.sam.mebake;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.sam.mebake.Model.Recipes;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sam on 11/29/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Recipes> recipeLists;
    public recipeClickListener listener;

    public interface recipeClickListener{
        void onRecipeClickListener(View v, int position);}


    public RecipeAdapter(Context context, ArrayList<Recipes> list) {
        this.mContext = context;
        this.listener = (RecipeAdapter.recipeClickListener) context;
        this.recipeLists = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_main, parent, false);
        final RecipeAdapter.Myholder myholder = new Myholder(view);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(listener !=null){
                    listener.onRecipeClickListener(v, myholder.getAdapterPosition());
                }
            }
        });

        return myholder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecipeAdapter.Myholder myholder = (RecipeAdapter.Myholder) holder;
        Recipes current = recipeLists.get(position);
        myholder.recipeMain.setText(current.getName());


        if (current.getImage().isEmpty()) {

        } else {
            Picasso.with(mContext)
                    .load(current.getImage())
                    .resize(185, 278)
                    .error(R.drawable.ic_action_name)
                    .into(myholder.recipeImage);

        }
    }

    @Override
    public int getItemCount() {
        return recipeLists.size();
    }


class Myholder extends RecyclerView.ViewHolder{
    @BindView(R.id.recipe_name) TextView recipeMain;
    @BindView(R.id.recipe_image)
    ImageView recipeImage;

    public Myholder(View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

}
