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
import vn.aptech.smartstudy.entity.ClassName;


public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassHolder> {

    private List<ClassName> list;
    private Context mContext;

    public ClassAdapter(List<ClassName> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ClassHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.class_item,parent,false);
        return new ClassHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassHolder holder, int position) {
        final ClassName className = list.get(position);
        holder.dataBind(className);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ClassHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layout;
        private TextView tvClass;
        public ClassHolder(@NonNull View itemView) {
            super(itemView);
            tvClass = itemView.findViewById(R.id.tvClassItem);
        }
        public void dataBind(ClassName className) {
            tvClass.setText(className.getClass().getName());
        }
    }
}
