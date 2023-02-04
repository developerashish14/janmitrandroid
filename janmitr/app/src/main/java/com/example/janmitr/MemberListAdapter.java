package com.example.janmitr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MemberListAdapter  extends RecyclerView.Adapter<MemberListAdapter.ViewHolder>{
    private final List<MemberList> memberList;
    private MemberListAdapter.OnRecyclerViewClickListener listener;

    public interface OnRecyclerViewClickListener{
        void OnItemClick(int position,int Coordinator_id);
    }

    public void OnRecyclerViewClickListener(MemberListAdapter.OnRecyclerViewClickListener listener){
        this.listener = listener;
    }

    public MemberListAdapter(List<MemberList> memberList){

        this.memberList = memberList;

    }
    public MemberListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_list,parent,false);
        return new ViewHolder(view);
    }


    public void onBindViewHolder(@NonNull MemberListAdapter.ViewHolder holder, int position) {
        String name = memberList.get(position).getMemberName();
        String memberId = memberList.get(position).getMemberId();
        String CoordinatorId = memberList.get(position).getCoordinatorId();

        holder.setData(name,memberId,CoordinatorId);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int member_id=Integer.parseInt(memberId);
                int Coordinator_id=Integer.parseInt(CoordinatorId);
                //Log.i("VALUE OF BUTTON", buttonId);
                listener.OnItemClick(member_id,Coordinator_id);
            }
        });

    }



    public int getItemCount(    ) {
        return memberList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.lblMemberName);
            button = itemView.findViewById(R.id.add_member);


        }
        public void setData(String name,String memberId,String CoordinatorId) {
            textViewName.setText(name);
        }
    }

}
