package com.example.carbonfoodprint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TotalItemsAdapter extends RecyclerView.Adapter <TotalItemsAdapter.TotalItemsViewHolder>{
    private ArrayList<TotalItem> mTotalList;
    public static class TotalItemsViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;
        public TextView mTextView4;
        public TotalItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView1=itemView.findViewById(R.id.textView3);
            mTextView2=itemView.findViewById(R.id.textView4);
            mTextView3=itemView.findViewById(R.id.textView5);
            mTextView4=itemView.findViewById(R.id.textView6);
        }
    }

    public TotalItemsAdapter(ArrayList<TotalItem> totalList){
        mTotalList=totalList;
    }

    @NonNull
    @Override
    public TotalItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.total_item,parent,false);
        TotalItemsViewHolder tvh = new TotalItemsViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(@NonNull TotalItemsViewHolder holder, int position) {
        TotalItem currentItem = mTotalList.get(position);

        holder.mTextView1.setText(currentItem.getmText1());
        holder.mTextView2.setText(currentItem.getmText2());
        holder.mTextView3.setText(currentItem.getmText3());
        holder.mTextView4.setText(currentItem.getmText4());
    }

    @Override
    public int getItemCount() {
        return mTotalList.size();
    }


}
