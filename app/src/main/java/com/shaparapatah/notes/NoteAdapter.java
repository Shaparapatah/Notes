package com.shaparapatah.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    private CardSource dataSource;

    public NoteAdapter(CardSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setData(CardSource dataSource) {
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
        holder.notesList.setText(dataSource.getCardData(position).getListNote());
        holder.toDoList.setText(dataSource.getCardData(position).getListTodo());

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView notesList;
        TextView toDoList;
        //    ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            notesList = itemView.findViewById(R.id.notesList);
            toDoList = itemView.findViewById(R.id.toDoList);
            //  imageView = itemView.findViewById(R.id.imageView);

  /*         notesList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMyClick(v, getAdapterPosition());
                }
            }); */
        }
    }
}