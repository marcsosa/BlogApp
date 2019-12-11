package com.itla.apiblog;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itla.apiblog.entity.Post;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Callback;

public class AdaptadorPost<onNoteListener> extends RecyclerView.Adapter<AdaptadorPost.MyViewHolder> {
    private static final String TAG = "Blog";

    private List<Post> posts;
    private OnNoteListener mOnNoteListener;

    public AdaptadorPost( List<Post> posts,OnNoteListener onNoteListener) {
        this.posts = posts;
        this.mOnNoteListener = onNoteListener;

    }


    @Override
    public AdaptadorPost.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, null, false);

        return new MyViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPost.MyViewHolder myViewHolder, int position) {
        Post currentdata = posts.get(position);
        myViewHolder.TituloEst.setText(currentdata.getTitle());
        myViewHolder.DescripcionEst.setText(currentdata.getBody());
        myViewHolder.Username.setText(currentdata.getUserName());
        myViewHolder.Tags.setText(Arrays.toString(currentdata.getTags()));
        Date creacion = new Date(currentdata.getCreatedAt());
        myViewHolder.date.setText(String.valueOf(creacion));
        myViewHolder.comentarios.setText(currentdata.getComments() + " Comentarios");
        myViewHolder.visitas.setText(currentdata.getViews() + " Visitas");
        myViewHolder.likes.setText(currentdata.getLikes() + " Likes");
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView TituloEst, DescripcionEst, Tags, Username, date, visitas, likes, comentarios;

        OnNoteListener onNoteListener;
        public MyViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            TituloEst = (TextView) itemView.findViewById(R.id.titulo);
            DescripcionEst = (TextView) itemView.findViewById(R.id.descripcion);
            Tags = (TextView) itemView.findViewById(R.id.tags);
            Username = (TextView) itemView.findViewById(R.id.name);
            date = (TextView) itemView.findViewById(R.id.date);
            visitas = (TextView) itemView.findViewById(R.id.vistas);
            likes = (TextView) itemView.findViewById(R.id.likes);
            comentarios = (TextView) itemView.findViewById(R.id.comentarios);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: " + getAdapterPosition());
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);

    }


}