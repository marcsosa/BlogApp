package com.itla.apiblog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itla.apiblog.entity.Comment;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AdaptadorComentarios extends RecyclerView.Adapter<AdaptadorComentarios.MyViewHolder>
{
    private List<Comment> comments;

    public AdaptadorComentarios(List<Comment> comments) {
        this.comments = comments;


    }

    ;    @Override
public AdaptadorComentarios.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comentarios,null,false);

    return new MyViewHolder(view);
}
    @Override
    public void onBindViewHolder(@NonNull AdaptadorComentarios.MyViewHolder myViewHolder, int position) {
        Comment currentdata = comments.get(position);
       myViewHolder.Cuerpo.setText(currentdata.getBody());
        Date creacion = new Date(currentdata.getCreatedAt());
       myViewHolder.date.setText(String.valueOf(creacion));
       myViewHolder.Username.setText(currentdata.getUserName());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }



    static class MyViewHolder extends RecyclerView.ViewHolder {
        //util para layout, body,created,username

        TextView Cuerpo,Username, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Cuerpo = (TextView) itemView.findViewById(R.id.Cdescripcion);
            Username = (TextView) itemView.findViewById(R.id.Cname);
            date = (TextView) itemView.findViewById(R.id.Cdate);

        }

    }

}