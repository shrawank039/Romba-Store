package in.rombashop.romba.ui.product.detail.adapter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.rombashop.romba.R;
import in.rombashop.romba.databinding.ItemNewProductColorAdapterBinding;
import in.rombashop.romba.databinding.ItemProductCollectionRowAdapterBinding;
import in.rombashop.romba.ui.common.DataBoundListAdapter;
import in.rombashop.romba.ui.product.adapter.ProductCollectionRowAdapter;
import in.rombashop.romba.utils.Objects;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewobject.ProductAttributeDetail;
import in.rombashop.romba.viewobject.ProductCollectionHeader;
import okhttp3.internal.Util;

public class newProductColorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final androidx.databinding.DataBindingComponent dataBindingComponent;
    private AttributeClickCallBack callback;
    private int selectedPosition=-1;
    private List<ProductAttributeDetail> detail;
   // private DiffUtilDispatchedInterface diffUtilDispatchedInterface = null;


    public newProductColorAdapter(DataBindingComponent dataBindingComponent, List<ProductAttributeDetail> detail, AttributeClickCallBack colorClickCallback) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = colorClickCallback;
        this.detail =detail;
    }

//    @Override
//    protected ItemNewProductColorAdapterBinding createBinding(ViewGroup parent) {
//        ItemNewProductColorAdapterBinding binding = DataBindingUtil
//                .inflate(LayoutInflater.from(parent.getContext()),
//                        R.layout.item_new_product_color_adapter, parent, false,
//                        dataBindingComponent);
//
//        binding.getRoot().setOnClickListener((View v) -> {
//            ProductAttributeDetail productColor = binding.getNewProductColor();
//            if (productColor != null && callback != null) {
//
//                callback.onClick(productColor, productColor.id, productColor.name);
//            }
//        });
//
//        return binding;
//    }
//
//
//    @Override
//    protected void dispatched() {
//        if (diffUtilDispatchedInterface != null) {
//            diffUtilDispatchedInterface.onDispatched();
//        }
//    }
//
//    @Override
//    protected void bind(ItemNewProductColorAdapterBinding binding, ProductAttributeDetail item) {
//        binding.setNewProductColor(item);
//
////        Bitmap b = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888);
////        Canvas canvas = new Canvas(b);
////        canvas.drawColor(Color.parseColor("#000"));
//
//     //   dataBindingComponent.getFragmentBindingAdapters().bindCircleBitmap(binding.color1BgImageView, b);
//        Utils.psLog("data : "+ item.name);
//        binding.textVieww.setText(item.name);
//
////        if (item.isColorSelect) {
////            binding.color1ImageView.setVisibility(View.VISIBLE);
////        } else {
////            binding.color1ImageView.setVisibility(View.GONE);
////        }
//    }

//    @Override
//    protected boolean areItemsTheSame(ProductAttributeDetail oldItem, ProductAttributeDetail newItem) {
//        return Objects.equals(oldItem.id, newItem.id)
//                && oldItem.productId.equals(newItem.productId)
//                && oldItem.isColorSelect == newItem.isColorSelect;
//    }
//
//    @Override
//    protected boolean areContentsTheSame(ProductAttributeDetail oldItem, ProductAttributeDetail newItem) {
//        return Objects.equals(oldItem.id, newItem.id)
//                && oldItem.productId.equals(newItem.productId)
//                && oldItem.isColorSelect == newItem.isColorSelect;
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemNewProductColorAdapterBinding binding;

        private MyViewHolder(ItemNewProductColorAdapterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ProductAttributeDetail productCollectionHeader) {
            binding.setNewProductColor(productCollectionHeader);
            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewProductColorAdapterBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_new_product_color_adapter, parent, false,
                        dataBindingComponent);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ProductAttributeDetail item = detail.get(position);

        ((MyViewHolder) holder).binding.textVieww.setText(item.name);

        if (selectedPosition == position) {
            ((MyViewHolder) holder).binding.color1ImageView.setVisibility(View.VISIBLE);
        } else {
            ((MyViewHolder) holder).binding.color1ImageView.setVisibility(View.GONE);
        }

        ((MyViewHolder) holder).binding.getRoot().setOnClickListener((View v) -> {
                Utils.psLog("callback : "+ selectedPosition);
                selectedPosition = position;
                callback.onClick(item, item.id, item.name);
                notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        if (detail != null && detail.size() > 0) {
            return detail.size();
        } else {
            return 0;
        }
    }

    public interface AttributeClickCallBack {
        void onClick(ProductAttributeDetail productColor, String selectedColorId, String selectedColorValue);
    }

}
