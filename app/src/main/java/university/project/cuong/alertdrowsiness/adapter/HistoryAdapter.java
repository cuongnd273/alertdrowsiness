package university.project.cuong.alertdrowsiness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import university.project.cuong.alertdrowsiness.R;
import university.project.cuong.alertdrowsiness.model.History;

/**
 *
 */

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private List<History> histories;
    private int layout;

    public HistoryAdapter(Context context, int layout, List<History> histories) {
        this.context = context;
        this.histories = histories;
        this.layout=layout;
    }

    @Override
    public int getCount() {
        return histories.size();
    }

    @Override
    public Object getItem(int i) {
        return histories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=view;
        if(v==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.item_history,viewGroup,false);
            ViewHolder viewHolder=new ViewHolder();
            viewHolder.time=v.findViewById(R.id.time);
            viewHolder.duration=v.findViewById(R.id.duration);
            v.setTag(viewHolder);
        }
        ViewHolder viewHolder= (ViewHolder) v.getTag();
        viewHolder.time.setText(convertTime(histories.get(i).getTime()));
        viewHolder.duration.setText(String.valueOf(histories.get(i).getDuration()));
        return v;
    }
    class ViewHolder{
        TextView time;
        TextView duration;

    }
    public void refresh(ArrayList<History> histories){
        this.histories=histories;
        this.notifyDataSetChanged();
    }
    public String convertTime(long time){
        Date date=new Date(time);
        SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return df2.format(date);
    }
}
