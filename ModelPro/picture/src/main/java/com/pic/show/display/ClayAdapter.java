package com.pic.show.display;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pic.show.Clay;
import com.pic.show.PandoraConfig;
import com.pic.show.R;
import com.pic.show.pupil.PupilActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghaofei on 17/11/14.
 */

public class ClayAdapter extends RecyclerView.Adapter<ClayAdapter.Holder> {

    private Context context;
    private List<Clay> arrayList = new ArrayList<>();
    private int max = 9;
    /**
     * 蒙层颜色
     */
    private int colorFilter = 0x70000000;
    private int colorFilterClear = 0x00000000;

    BlockInterface blockInterface;

    public ClayAdapter(Context context,int maxCount) {
        this.context = context;
        this.max =maxCount;
    }

    public BlockInterface getBlockInterface() {
        return blockInterface;
    }

    public void setBlockInterface(BlockInterface blockInterface) {
        this.blockInterface = blockInterface;
    }

    @Override
    public ClayAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lib_pandora_adapter_photo_item, null);
        return new ClayAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(final ClayAdapter.Holder holder, final int position) {


        final Clay var = arrayList.get(position);
        Glide.with(context).load(var.getData()).apply(new RequestOptions().fitCenter().placeholder(R.color.lib_pandora_817F7F)).thumbnail(0.1f).into(holder.imageView);


        holder.checkedTextView.setChecked(var.isCheck());
        if (var.isCheck()) {
            holder.imageView.setColorFilter(colorFilter, PorterDuff.Mode.SRC_OVER);
        } else {
            holder.imageView.setColorFilter(colorFilterClear, PorterDuff.Mode.SRC_OVER);
        }

        holder.checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckedTextView) view).isChecked()) {
                    var.setCheck(false);
                    ((CheckedTextView) view).toggle();
                    blockInterface.removeBlock(var);
                    holder.imageView.setColorFilter(colorFilterClear, PorterDuff.Mode.SRC_OVER);
                } else {
                    if (max > calculatorCount()) {
                        var.setCheck(true);
                        ((CheckedTextView) view).toggle();
                        blockInterface.addBlock(var);
                        holder.imageView.setColorFilter(colorFilter, PorterDuff.Mode.SRC_OVER);
                    } else {
                        Toast.makeText(context, String.format(context.getResources().getString(R.string.lib_pandora_check_max_count), max), Toast.LENGTH_SHORT).show();
                    }
                }

                blockInterface.countBlock(var);
            }
        });


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PupilActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putSerializable("arrayList", (Serializable) arrayList);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class Holder extends RecyclerView.ViewHolder {

        private CheckedTextView checkedTextView;
        private ImageView imageView;

        public Holder(View view) {
            super(view);
            checkedTextView = (CheckedTextView) view.findViewById(R.id.lib_pandora_photo_adapter_checked_text_view);
            imageView = (ImageView) view.findViewById(R.id.lib_pandora_photo_adapter_image_view);

            int width = context.getResources().getDisplayMetrics().widthPixels;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width / PandoraConfig.countInLine, width / PandoraConfig.countInLine);
            imageView.setLayoutParams(layoutParams);
        }
    }


    public void addData(List<Clay> list){
        if(null != arrayList){
            arrayList.clear();
            arrayList.addAll(list);
            notifyDataSetChanged();
        }
    }


    public void update(List<Clay> list) {
        if (null != arrayList) {
            this.arrayList.clear();
            this.arrayList.addAll(list);
            notifyDataSetChanged();
        }
    }


    /**
     * 实时计算当前选中的个数
     */
    private int calculatorCount() {
        int count = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            Clay clay = arrayList.get(i);
            if (clay.isCheck()) {
                count++;
            }
        }
        return count;
    }

   public interface BlockInterface {
        void removeBlock(Clay clay);

        void addBlock(Clay clay);

        void countBlock(Clay clay);
    }

}
