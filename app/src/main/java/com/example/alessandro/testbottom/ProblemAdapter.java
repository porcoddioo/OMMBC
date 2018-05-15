package com.example.alessandro.testbottom;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import java.util.List;

public class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.MyViewHolder>{
    private List<Problema> problemList;
    AdapterView.OnItemClickListener mItemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //cambiar por valores de problemas:
        public TextView Name, Description;

        public MyViewHolder(View view) {
            super(view);
            Name = (TextView) view.findViewById(R.id.Name);
            Description = (TextView) view.findViewById(R.id.Description);
        }
    }
    public ProblemAdapter(List<Problema> problemList){
        this.problemList = problemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.problem_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Problema q = problemList.get(position);

        //Cambiar por getters de clase Problema para llenar los textviews:

        holder.Name.setText(q.getName());
        holder.Description.setText(q.getDesc());

    }
    @Override
    public int getItemCount() {
        return problemList.size();
    }
}
