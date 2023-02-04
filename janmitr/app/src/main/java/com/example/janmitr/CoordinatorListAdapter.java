package com.example.janmitr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CoordinatorListAdapter extends RecyclerView.Adapter<CoordinatorListAdapter.ViewHolder>{


    private final List<CoordinatorList> userList;
    private OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener{
        void OnItemClick(int position);
    }

    public void OnRecyclerViewClickListener(OnRecyclerViewClickListener listener){
        this.listener = listener;
    }


    public CoordinatorListAdapter(List<CoordinatorList> userList){

        this.userList = userList;

    }
    @NonNull
    @Override
    public CoordinatorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coordinator_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = userList.get(position).getCoordinatorName();
        String mobile = userList.get(position).getCoordinatorMobile();
        String address = userList.get(position).getCoordinatorAddress();
        String buttonId = userList.get(position).getButtonId();

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int member_id=Integer.parseInt(buttonId);
                //Log.i("VALUE OF BUTTON", buttonId);
                listener.OnItemClick(member_id);


            }
        });

        holder.setData(name, mobile, address,buttonId);


    }




    @Override
    public int getItemCount(    ) {
        return userList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewAddress;
        TextView textViewMobile;

        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.lblCoordinatorName);
            textViewAddress = itemView.findViewById(R.id.lblCoordinatorAddress);
            textViewMobile = itemView.findViewById(R.id.lblCoordinatorMobile);
            button = itemView.findViewById(R.id.OnClickMember);



        }
        public void setData(String name, String mobile, String address ,String buttonId) {
            textViewName.setText(name);
            textViewMobile.setText(mobile);
            textViewAddress.setText(address);

            //button = (Button) findViewById(R.id.OnClickMember);
           // button.setText(buttonId);

        }
    }
}