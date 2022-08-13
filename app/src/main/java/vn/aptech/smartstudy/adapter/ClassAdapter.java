package vn.aptech.smartstudy.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.aptech.smartstudy.FillMarkActivity;
import vn.aptech.smartstudy.R;
import vn.aptech.smartstudy.entity.ClassName;
import vn.aptech.smartstudy.entity.ReviewClass;

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
        return new ClassAdapter.ClassHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassHolder holder, int position) {
        final ClassName className = list.get(position);
        holder.dataBind(className);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(mContext, FillMarkActivity.class);
                it.putExtra("class",className.getName());
                mContext.startActivity(it);
            }
        });


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
            layout = itemView.findViewById(R.id.class_item);
        }
        public void dataBind(ClassName className) {
            tvClass.setText(className.getName());
        }
    }
}
