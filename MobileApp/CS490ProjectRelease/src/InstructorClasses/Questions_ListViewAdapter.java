package InstructorClasses;

import ExamQuestionClasses.QuestionObject;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.malan.cs490project.R;
 

import android.content.Context;
import android.widget.ArrayAdapter;

public class Questions_ListViewAdapter extends ArrayAdapter<QuestionObject> {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    List<QuestionObject> questionlist;
    private SparseBooleanArray mSelectedItemsIds;
 
    public Questions_ListViewAdapter(Context context, int resourceId,List<QuestionObject> questionlist) {
        super(context, resourceId, questionlist);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.questionlist = questionlist;
        inflater = LayoutInflater.from(context);
    }
 
    private class ViewHolder {
        TextView question;
    }
 
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.question_list_item, null);
            holder.question = (TextView) view.findViewById(R.id.question);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.question.setText(questionlist.get(position).getQuestion());
        return view;
    }
 
    @Override
    public void remove(QuestionObject object) {
    	questionlist.remove(object);
        notifyDataSetChanged();
    }
 
    public List<QuestionObject> getWorldPopulation() {
        return questionlist;
    }
 
    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }
 
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }
 
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }
 
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }
 
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}