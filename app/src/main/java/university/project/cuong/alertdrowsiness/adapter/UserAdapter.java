package university.project.cuong.alertdrowsiness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import university.project.cuong.alertdrowsiness.R;
import university.project.cuong.alertdrowsiness.model.User;

public class UserAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<User> userList;

    public UserAdapter(Context context, int layout, List<User> userList) {
        this.context = context;
        this.layout = layout;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder
    {
         TextView tvname;
         TextView tvemail;
         TextView tvaddress;
         TextView tvtelephone;
         TextView tvidentityCard;
         TextView tvsex;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view== null)
        {
            holder= new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view= layoutInflater.inflate(layout,null);

            holder.tvname= (TextView) view.findViewById(R.id.tvname2);
            holder.tvemail=(TextView) view.findViewById(R.id.tvemail2);
            holder.tvaddress=(TextView) view.findViewById(R.id.tvaddress2);
            holder.tvtelephone=(TextView) view.findViewById(R.id.tvtelephone2);
            holder.tvidentityCard=(TextView) view.findViewById(R.id.tvidentityCard2);
            holder.tvsex=(TextView)view.findViewById(R.id.tvsex2);

            view.setTag(holder);
        }
        else
        {
            holder=(UserAdapter.ViewHolder) view.getTag();
        }
        //
        final User user= userList.get(i);
        //
        holder.tvname.setText(user.getUserName());
        holder.tvemail.setText(user.getEmail());
        holder.tvaddress.setText(user.getAddress());
        holder.tvtelephone.setText(user.getTelephone());
        holder.tvidentityCard.setText(user.getIdentityCard());
        holder.tvsex.setText(user.getSex());


        return view;
    }

}
