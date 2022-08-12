package vn.aptech.smartstudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.aptech.smartstudy.R;
import vn.aptech.smartstudy.entity.Resource;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.ResourceHolder> {
    private List<Resource> data;
    private Context mContext;

    public ResourceAdapter(List<Resource> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ResourceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.resource_item,parent,false);
        return new ResourceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceHolder holder, int position) {
        final Resource resource = data.get(position);

        holder.dataBind(resource);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ResourceHolder extends RecyclerView.ViewHolder{

        private RelativeLayout layout;
        private TextView tvTeacherName,tvContent,tvSubject;
        public ResourceHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvResourceContent);
            tvTeacherName = itemView.findViewById(R.id.tvTeacherName);
            tvSubject = itemView.findViewById(R.id.tvSubjectName);
        }
        public void dataBind(Resource resource){
            tvContent.setText(resource.getContent());
            tvSubject.setText(resource.getSubject().getSubject());
            tvTeacherName.setText(resource.getTeacher_name());
        }
    }
}
