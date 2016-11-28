package com.example.zsq.easyshop.me.goodsupload;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.zsq.easyshop.R;
import com.example.zsq.easyshop.model.ImageItem;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/28 0028.
 */

public class GoodsUpLoadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ImageItem> list = new ArrayList<>();
    private LayoutInflater inflater;

    //alt + insert--->const。。。
    public GoodsUpLoadAdapter(ArrayList<ImageItem> list, LayoutInflater inflater) {
        this.list = list;
        this.inflater = inflater;
    }

    //##########################   逻辑：模式的选择    #####################
    //表示编辑时的模式，1为普通，2为可选
    public static final int MODE_NORMAL = 1;
    public static final int MODE_MULTI_SELECT = 2;
    //代表图片编辑模式
    public int mode;

    //用枚举，表示item类型，有图或者无图（待添加的加号）
    public enum ITEM_TYPE {
        ITEM_NORMAL, ITEM_ADD
    }

    //模式设置
    public void changeMode(int mode) {
        this.mode = mode;
        notifyDataSetChanged();
    }

    //获取当前模式
    public int getMode() {
        return mode;
    }
    //##########################   逻辑：模式的选择    #####################

    //添加图片
    public void add(ImageItem photo){
        list.add(photo);
    }

    public int getSize(){
        return list.size();
    }

    //刷新数据
    public void notifyData(){
        notifyDataSetChanged();
    }

    public void notifyDataSet(){
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //判断当前显示的item类型，有图或者待添加，从而选择不同viewholder，不同的布局
        if (viewType == ITEM_TYPE.ITEM_NORMAL.ordinal()){
            //有图的viewholder
            return new ItemSelectViewHolder(inflater.inflate(R.layout.layout_item_recyclerview,parent,false));
        }else {
            //没图，显示加号的viewholder
            return new ItemAddViewHolder(inflater.inflate(R.layout.layout_item_recyclerviewlast,parent,false));
        }
    }

    //获取item类型方法中，去判断item类型，从而加载不同viewholder
    @Override
    public int getItemViewType(int position) {
        //当position与图片数量相同时，则为加号的布局
        if (position == list.size()) return ITEM_TYPE.ITEM_ADD.ordinal();
        return ITEM_TYPE.ITEM_NORMAL.ordinal();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    //图片布局
    public static class ItemSelectViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.cb_check_photo)
        CheckBox checkBox;
        ImageItem photo;

        public ItemSelectViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    //添加按钮布局
    public static class ItemAddViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ib_recycle_add)
        ImageButton ib_add;

        public ItemAddViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
