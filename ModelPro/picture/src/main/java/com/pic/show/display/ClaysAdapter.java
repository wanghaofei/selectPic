package com.pic.show.display;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pic.show.Clay;
import com.pic.show.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by wanghaofei on 17/11/14.
 */

public class ClaysAdapter extends RecyclerView.Adapter<ClaysAdapter.Holder> {


    private Context context;
    private List<Clay> arrayList = new ArrayList<>();
    private SingleBlock singleBlock;

    public ClaysAdapter(Context context) {
        this.context = context;
    }

    public SingleBlock getSingleBlock() {
        return singleBlock;
    }

    public void setSingleBlock(SingleBlock singleBlock) {
        this.singleBlock = singleBlock;
    }

    @Override
    public ClaysAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lib_pandora_adapter_album_item, null);
        return new ClaysAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(ClaysAdapter.Holder holder,final int position) {



        final Clay var = arrayList.get(position);

        Glide.with(context).load(var.getData()).apply(new RequestOptions().fitCenter().placeholder(R.color.lib_pandora_817F7F)).thumbnail(0.1f).into(holder.imageView);



        holder.name.setText(var.getName());

        holder.count.setText(String.format(Locale.CHINA, context.getResources().getString(R.string.lib_pandora_number), var.getCount()));
        if (var.isCheck()) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleBlock.block(var);
                if(!var.isCheck()){
                    var.setCheck(true);
                    highlightItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ImageView checkBox;
        TextView name;
        TextView count;

        public Holder(View view) {
            super(view);
            imageView = view.findViewById(R.id.lib_pandora_adapter_album_image_view);
            checkBox = view.findViewById(R.id.lib_pandora_adapter_album_check_box);
            name = view.findViewById(R.id.lib_pandora_adapter_album_name);
            count = view.findViewById(R.id.lib_pandora_adapter_album_count);
        }
    }


    public void update(List<Clay> arrayList) {
        this.arrayList.clear();
        this.arrayList.addAll(arrayList);
        notifyDataSetChanged();
    }

    /**
     * 高亮指定条目
     */
    private void highlightItem(int position) {

        for (Clay clay : arrayList) {
            clay.setCheck(false);
        }
        arrayList.get(position).setCheck(true);

        notifyDataSetChanged();
    }


   public interface SingleBlock {
        void block(Clay clay);
    }


}
