package com.example.zsq.easyshop.me.personInfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zsq.easyshop.R;
import com.example.zsq.easyshop.model.ItemShow;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZSQ on 2016/11/23.
 */

public class PersonAdapter extends BaseAdapter{
  private List<ItemShow> list = new ArrayList<>();

  public PersonAdapter(List<ItemShow> list){
    this.list = list;
  }
  @Override public int getCount() {
    return list.size();
  }

  @Override public Object getItem(int i) {
    return list.get(i);
  }

  @Override public long getItemId(int i) {
    return i;
  }

  @Override public View getView(int i, View view, ViewGroup viewGroup) {
    ViewHolder viewHolder;
    if (view == null){
      view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_person_info,viewGroup,false);
      viewHolder = new ViewHolder(view);
      view.setTag(viewHolder);
    }else {
      viewHolder = (ViewHolder) view.getTag();
    }

    viewHolder.tv_item_name.setText(list.get(i).getItem_title());
    viewHolder.tv_person.setText(list.get(i).getItem_content());
    return view;
  }

  class ViewHolder{
    @BindView(R.id.tv_item_name) TextView tv_item_name;
    @BindView(R.id.tv_person) TextView tv_person;

    public ViewHolder(View view){
      ButterKnife.bind(this,view);
    }
  }
}
