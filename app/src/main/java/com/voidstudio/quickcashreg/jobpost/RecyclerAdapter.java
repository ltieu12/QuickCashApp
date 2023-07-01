package com.voidstudio.quickcashreg.jobpost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.voidstudio.quickcashreg.R;

import java.util.ArrayList;



public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

  ArrayList<String> taskList;
  LayoutInflater inflater;
  private ItemClickListener listener;

  public RecyclerAdapter(Context context, ArrayList<String> taskList) {
    this.inflater = LayoutInflater.from(context);
    this.taskList = taskList;
  }



  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.recycler_row, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    String task = this.taskList.get(position);
    holder.myItemView.setText(task);
  }

  public String getItem(int id) {
    return this.taskList.get(id);
  }

  @Override
  public int getItemCount() {
    return this.taskList.size();
  }

  public void setItemClickListener(ItemClickListener listener) {
    this.listener = listener;
  }

  public interface ItemClickListener {
    void onItemClick(View view, int position);
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView myItemView;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      myItemView = itemView.findViewById(R.id.recyclerRow);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if(listener!=null) {
            listener.onItemClick(view,getAdapterPosition());
          }
        }
      });
    }
  }



}
