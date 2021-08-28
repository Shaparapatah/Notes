package com.shaparapatah.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.shaparapatah.notes.data.CardData;
import com.shaparapatah.notes.data.MyOnClickListener;
import com.shaparapatah.notes.R;
import com.shaparapatah.notes.data.CardSource;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {

    private CardSource dataSource;
    private Fragment fragment;
    private int menuContextClickPosition;

    public int getMenuContextClickPosition() {
        return menuContextClickPosition;
    }

    public void setDataSource(CardSource dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    public NoteAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    private MyOnClickListener listener;

    public void setOnMyClickListener(MyOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view, parent, false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(dataSource.getCardData(position));

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView notesList;
        private TextView toDoList;
        private TextView date;


        public MyViewHolder(View itemView) {
            super(itemView);
            notesList = itemView.findViewById(R.id.notesList);
            toDoList = itemView.findViewById(R.id.toDoList);
            date = itemView.findViewById(R.id.date);


            fragment.registerForContextMenu(notesList);

            notesList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMyClick(v, getAdapterPosition());
                }
            });

            notesList.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    menuContextClickPosition = getAdapterPosition();
                    notesList.showContextMenu();
                    return true;
                }
            });
        }

        public void setData(CardData cardData) {
            notesList.setText(cardData.getListNote());
            date.setText(cardData.getDate().toString());
            toDoList.setText(cardData.getListTodo());
        }
    }
}