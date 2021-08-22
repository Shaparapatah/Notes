package com.shaparapatah.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    private String[] dataSource;

    public NoteAdapter(String[] dataSource) {
        this.dataSource = dataSource;
    }

    public void setData(String[] dataSource) {
        this.dataSource = dataSource;
    }


    private MyOnClickListener listener;

    public void setOnMyClickListener(MyOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(dataSource[position]);

    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        //    ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            //  imageView = itemView.findViewById(R.id.imageView);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMyClick(v, getAdapterPosition());
                }
            });
        }
    }
}