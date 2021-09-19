package com.example.carbonfoodprint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ElementAdapter extends RecyclerView.Adapter<ElementAdapter.ElementViewHolder> implements Filterable {
    private ArrayList<ElementList> mElementLists;
    private ArrayList<ElementList> mElementListsFull;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }


    @Override
    public Filter getFilter() {
        return elementFilter;
    }
    private Filter elementFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ElementList> filteredList = new ArrayList<>();

            if (charSequence==null || charSequence.length() == 0){
                filteredList.addAll(mElementListsFull);

            }else{
                String filterPattern = charSequence.toString().toLowerCase(Locale.ROOT).trim();

                for (ElementList item : mElementListsFull){
                    if (item.getText1().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values =  filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mElementLists.clear();
            mElementLists.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public static  class ElementViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;
        public TextView mTextView2;
        public ElementViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mTextView1=itemView.findViewById(R.id.list_line_1);
            mTextView2=itemView.findViewById(R.id.list_line_2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  if (listener !=null){
                      int position = getAdapterPosition();
                      if (position != RecyclerView.NO_POSITION){
                          listener.onItemClick(position);
                      }
                  }
                }
            });
        }
    }

    public ElementAdapter(ArrayList<ElementList> elementLists){
        this.mElementLists =elementLists;
        mElementListsFull = new ArrayList<>(elementLists);
    };

    @NonNull
    @Override
    public ElementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list,parent, false);
        ElementViewHolder evh = new ElementViewHolder(v,mListener);
        return evh;
    }


    @Override
    public void onBindViewHolder(@NonNull ElementViewHolder holder, int position) {
      ElementList currentItem = mElementLists.get(position);

      holder.mTextView1.setText(currentItem.getText1());
      holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mElementLists.size();
    }



}
