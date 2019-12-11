package com.itla.apiblog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itla.apiblog.entity.Post;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AdaptadorPostUser extends RecyclerView.Adapter<AdaptadorPostUser.MyViewHolder>
{
    private Context context;
    private List<Post> posts;

    public AdaptadorPostUser(Context context, List<Post> posts) {
        this.posts = posts;
        this.context = context;

    }


    ;    @Override
public AdaptadorPostUser.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,null,false);

    return new MyViewHolder(view);
}
    @Override
    public void onBindViewHolder(@NonNull AdaptadorPostUser.MyViewHolder myViewHolder, int position) {
        Post currentdata = posts.get(position);
        myViewHolder.TituloEst.setText(currentdata.getTitle());
        myViewHolder.DescripcionEst.setText(currentdata.getBody());
        myViewHolder.Username.setText(currentdata.getUserName());
        myViewHolder.Tags.setText(Arrays.toString(currentdata.getTags()));
        Date creacion = new Date(currentdata.getCreatedAt());
        myViewHolder.date.setText(String.valueOf(creacion));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }



    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TituloEst,DescripcionEst,Tags,Username, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            TituloEst = (TextView) itemView.findViewById(R.id.titulo);
            DescripcionEst = (TextView) itemView.findViewById(R.id.descripcion);
            Tags = (TextView) itemView.findViewById(R.id.tags);
            Username = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);

        }

    }

}