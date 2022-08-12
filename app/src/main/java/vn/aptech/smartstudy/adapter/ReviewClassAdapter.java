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
import vn.aptech.smartstudy.entity.ReviewClass;

public class ReviewClassAdapter extends RecyclerView.Adapter<ReviewClassAdapter.ResourceHolder> {
    private List<ReviewClass> list;
    private Context mContext;

    public ReviewClassAdapter(List<ReviewClass> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ResourceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.reviewclass_item,parent,false);
        return new ResourceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceHolder holder, int position) {
        final ReviewClass reviewClass = list.get(position);
        holder.dataBind(reviewClass);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ResourceHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layout;
        private TextView tvClass,tvReviewContent,tvDate;
        public ResourceHolder(@NonNull View itemView) {
            super(itemView);
            tvClass = itemView.findViewById(R.id.tvClass);
            tvReviewContent = itemView.findViewById(R.id.tvReviewContent);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
        public void dataBind(ReviewClass reviewClass) {
            tvClass.setText(reviewClass.getClassName().getName());
            tvReviewContent.setText(reviewClass.getContent());
            tvDate.setText(reviewClass.getDatetime());
        }
    }
}
