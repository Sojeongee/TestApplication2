package com.example.sj.testapplication2;

/**
 * Created by SJ on 2016-11-07.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JUNED on 6/16/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<GetDataAdapter> getDataAdapter;

    public RecyclerViewAdapter(List<GetDataAdapter> getDataAdapter, Context context){

        super();

        this.getDataAdapter = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GetDataAdapter getDataAdapter1 =  getDataAdapter.get(position);

        holder.NameTextView.setText(getDataAdapter1.getName());


        holder.PhoneNumberTextView.setText(getDataAdapter1.getAddress());

        holder.SubjectTextView.setText(getDataAdapter1.getSubject());

    }

    @Override
    public int getItemCount() {

        return getDataAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView IdTextView;
        public TextView NameTextView;
        public TextView PhoneNumberTextView;
        public TextView SubjectTextView;


        public ViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);

            IdTextView = (TextView) itemView.findViewById(R.id.textView2) ;
            NameTextView = (TextView) itemView.findViewById(R.id.textView4) ;
            PhoneNumberTextView = (TextView) itemView.findViewById(R.id.textView6) ;
            SubjectTextView = (TextView) itemView.findViewById(R.id.textView8) ;

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext() , DetailActivity.class);
            v.getContext().startActivity(intent);
        }
    }
}
