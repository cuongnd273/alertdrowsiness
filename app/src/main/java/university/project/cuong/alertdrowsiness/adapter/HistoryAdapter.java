package university.project.cuong.alertdrowsiness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import university.project.cuong.alertdrowsiness.R;
import university.project.cuong.alertdrowsiness.model.History;

/**
 * Created by cuong on 4/4/2018.
 */

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<History> histories;

    public HistoryAdapter(Context context, ArrayList<History> histories) {
        this.context = context;
        this.histories = histories;
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
            viewHolder.map=v.findViewById(R.id.map);
            viewHolder.time=v.findViewById(R.id.time);
            viewHolder.duration=v.findViewById(R.id.duration);
            v.setTag(viewHolder);
        }
        ViewHolder viewHolder= (ViewHolder) v.getTag();
        viewHolder.time.setText(String.valueOf(histories.get(i).getTime()));
        return v;
    }
    class ViewHolder{
        TextView time;
        TextView duration;
        ImageView map;
    }
}
