package in.rombashop.romba.ui.gallery.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import in.rombashop.romba.R;
import in.rombashop.romba.databinding.ItemGalleryAdapterBinding;
import in.rombashop.romba.ui.common.DataBoundListAdapter;
import in.rombashop.romba.utils.Objects;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewobject.Image;

import androidx.databinding.DataBindingUtil;

/**
 * Created by matrixdeveloper on 12/6/17.
 * Contact Email : matrixdeveloper.business@gmail.com
 */


public class GalleryAdapter extends DataBoundListAdapter<Image, ItemGalleryAdapterBinding> {

    private DataBoundListAdapter.DiffUtilDispatchedInterface diffUtilDispatchedInterface;
    private final androidx.databinding.DataBindingComponent dataBindingComponent;
    private final ImageClickCallback callback;

    public GalleryAdapter(androidx.databinding.DataBindingComponent dataBindingComponent,
                          ImageClickCallback callback,
                          DiffUtilDispatchedInterface diffUtilDispatchedInterface) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
        this.diffUtilDispatchedInterface = diffUtilDispatchedInterface;
    }

    @Override
    protected ItemGalleryAdapterBinding createBinding(ViewGroup parent) {
        ItemGalleryAdapterBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_gallery_adapter, parent, false,
                        dataBindingComponent);

        binding.getRoot().setOnClickListener(v -> {
            Image image = binding.getImage();
            if (image != null && callback != null) {
                Utils.psLog("Clicked Image" + image.imgDesc);

                callback.onClick(image);
            }
        });
        return binding;
    }

    @Override
    protected void dispatched() {
        if (diffUtilDispatchedInterface != null) {
            diffUtilDispatchedInterface.onDispatched();
        }
    }

    @Override
    protected void bind(ItemGalleryAdapterBinding binding, Image item) {
        binding.setImage(item);
    }

    @Override
    protected boolean areItemsTheSame(Image oldItem, Image newItem) {
        return Objects.equals(oldItem.imgId, newItem.imgId);
    }

    @Override
    protected boolean areContentsTheSame(Image oldItem, Image newItem) {
        return Objects.equals(oldItem.imgId, newItem.imgId);
    }

    public interface ImageClickCallback {
        void onClick(Image item);
    }
}