package com.qfxl.samples.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qfxl.samples.R;
import com.qfxl.view.banner.Banner;
import com.qfxl.view.banner.BannerDefaultAdapter;
import com.qfxl.view.banner.IBannerImageLoader;

import java.util.ArrayList;
import java.util.List;

public class IndexAdapter extends RecyclerView.Adapter<IndexViewHolder> {
    private final int TYPE_HEADER = 1;
    private List<String> resources = new ArrayList<>();

    public IndexAdapter() {
        resources.add("https://i0.hdslb.com/bfs/archive/9fff0328cbfec3e16a8de0af570399e4870f0fa8.jpg");
        resources.add("https://i0.hdslb.com/bfs/archive/b8efabf3031888db680f39dda98a41a7f987e4a9.jpg");
        resources.add("https://i0.hdslb.com/bfs/archive/a6be3a5d4a4adc4193a09b748d2d8266c56b118b.jpg");
        resources.add("https://i0.hdslb.com/bfs/sycp/tmaterial/201807/26c8f387a0777961a9ed804bd88accb5.png");
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public IndexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(viewType == TYPE_HEADER ? R.layout.item_index_header : R.layout.item_index, parent, false);
        return new IndexViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IndexViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER && !holder.banner.isReady()) {
            holder.banner
                    .setLoopInterval(1000)
                    .autoReady(resources, new IBannerImageLoader() {
                        @Override
                        public void displayImage(Context context, ImageView imageView, Object path) {
                            Glide.with(context).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.img_placeholder)).load(path).into(imageView);
                        }

                        @Override
                        public ImageView createImageViewView(Context context) {
                            return null;
                        }
                    }, new BannerDefaultAdapter.OnBannerClickListener() {
                        @Override
                        public void onBannerClick(int position, Object resource) {

                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}

class IndexViewHolder extends RecyclerView.ViewHolder {
    Banner banner;

    public IndexViewHolder(View itemView) {
        super(itemView);
        banner = itemView.findViewById(R.id.banner_index);
    }
}


