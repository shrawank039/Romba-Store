package in.rombashop.romba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import in.rombashop.romba.helper.PhotoView;
import in.rombashop.romba.net.ServiceNames;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewobject.Image;

public class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.MyViewHolder> {

    private final Context ctx;

    // Array of images
    List<Image> moviesList;

    // Layout Inflater
    LayoutInflater mLayoutInflater;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        PhotoView imageView;


        MyViewHolder(View view) {
            super(view);

            imageView  = (PhotoView) view.findViewById(R.id.image);
        }
    }


    public ViewPager2Adapter(Context context, List<Image> moviesList) {
        ctx = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_image, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Image ongoing = moviesList.get(position);

        Utils.psLog("image : "+ moviesList.size());

        Glide.with(ctx)
                .load(ServiceNames.IMAGE_URL+ongoing.imgPath)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
