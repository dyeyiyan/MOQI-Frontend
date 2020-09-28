package com.ardeidei.moqi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ardeidei.moqi.R;
import com.ardeidei.moqi.model.CompanyModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.MyHolder> {

    private Context context;
    private List<CompanyModel> lcompany;

    public CompanyAdapter(Context context, List<CompanyModel> lcompany) {
        this.context = context;
        this.lcompany = lcompany;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row item
        View view = LayoutInflater.from(context).inflate(R.layout.row_company_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //get data
        String cID = lcompany.get(position).getId();
        String cName = lcompany.get(position).getName();
        String cAddress = lcompany.get(position).getAddress();

        //set data
        //holder.uTVName.setText(uName);
        holder.tvName.setText(cName);
        holder.tvAddress.setText(cAddress);


    }

    @Override
    public int getItemCount() {
        return lcompany.size();
    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder {
        CircleImageView civCompany;
        TextView tvName, tvAddress;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //init views
            civCompany = itemView.findViewById(R.id.rci_civ_company);
            tvAddress = itemView.findViewById(R.id.rci_tv_address);
            tvName = itemView.findViewById(R.id.rci_tv_name);
        }
    }

}
