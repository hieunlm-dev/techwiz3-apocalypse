package vn.aptech.smartstudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Year;
import java.util.List;

import vn.aptech.smartstudy.R;
import vn.aptech.smartstudy.entity.ScoreDetail;
import vn.aptech.smartstudy.entity.User;

public class StudentApdapter extends RecyclerView.Adapter<StudentApdapter.StudentHolder> {

    private List<User>  data;
    private Context mContext;

    public StudentApdapter(List<User> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.student_item,parent,false);
        return new StudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        final User user = data.get(position);
        holder.dataBind(user);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class StudentHolder extends RecyclerView.ViewHolder{

        private EditText edMark;
        private TextView tvStudentName,tvClass,tvStudentEmail;
        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            edMark = itemView.findViewById(R.id.edStudentMark);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvStudentEmail = itemView.findViewById(R.id.tvStudentEmail);
            tvClass = itemView.findViewById(R.id.tvClass);
        }

        public void dataBind(User user){

            tvStudentName.setText(user.getFull_name());
            tvClass.setText(user.getStudentData().getClassName());
            tvStudentEmail.setText(user.getEmail());
        }


        public ScoreDetail getScore(int semester,String selected_test,String subject_name){
            edMark = itemView.findViewById(R.id.edStudentMark);
            if(!edMark.getText().toString().isEmpty()){
                ScoreDetail scoreDetail = new ScoreDetail();
                scoreDetail.setMark(Float.parseFloat(edMark.getText().toString()));
                scoreDetail.setStudent_email("("+tvStudentName.getText().toString().replaceAll(" ", "").toLowerCase()+")"+tvStudentEmail.getText().toString());
                scoreDetail.setYear(Year.now().getValue());
                scoreDetail.setSemester(semester);
                scoreDetail.setType_test(selected_test);
                scoreDetail.setSubject_name(subject_name);

                return scoreDetail;
            }else{
                return null;
            }
        }
    }

}
