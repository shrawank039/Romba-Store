package in.rombashop.romba.ui.product.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;

import com.like.LikeButton;

import in.rombashop.romba.R;
import in.rombashop.romba.databinding.ItemProductSearchListAdapterBinding;
import in.rombashop.romba.databinding.ItemProductSearchListAdapterBinding;
import in.rombashop.romba.ui.common.DataBoundListAdapter;
import in.rombashop.romba.ui.common.DataBoundViewHolder;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.Objects;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewobject.Product;

/**
 * Created by matrixdeveloper on 9/18/18.
 * Contact Email : matrixdeveloper.business@gmail.com
 */


public class ProductSearchListAdapter extends DataBoundListAdapter<Product, ItemProductSearchListAdapterBinding> {

    private final DataBindingComponent dataBindingComponent;
    private final NewsClickCallback callback;
    private DiffUtilDispatchedInterface diffUtilDispatchedInterface;

    public ProductSearchListAdapter(DataBindingComponent dataBindingComponent,
                                    NewsClickCallback callback, DiffUtilDispatchedInterface diffUtilDispatchedInterface) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
        this.diffUtilDispatchedInterface = diffUtilDispatchedInterface;
    }

    @Override
    protected ItemProductSearchListAdapterBinding createBinding(ViewGroup parent) {
        ItemProductSearchListAdapterBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_product_search_list_adapter, parent, false,
                        dataBindingComponent);
        binding.getRoot().setOnClickListener(v -> {
            Product product = binding.getProduct();
            if (product != null && callback != null) {
                callback.onClick(product);
            }
        });

//        binding.heartButton.setOnLikeListener(new OnLikeListener() {
//            @Override
//            public void liked(LikeButton likeButton) {
//
//                Product product = binding.getProduct();
//                if (product != null && callback != null) {
//                    callback.onFavLikeClick(product, binding.heartButton);
//                }
//
//            }
//
//            @Override
//            public void unLiked(LikeButton likeButton) {
//
//                Product product = binding.getProduct();
//                if (product != null && callback != null) {
//                    callback.onFavUnlikeClick(product, binding.heartButton);
//                }
//            }
//        });

        return binding;
    }

    // For general animation
    @Override
    public void bindView(DataBoundViewHolder<ItemProductSearchListAdapterBinding> holder, int position) {
        super.bindView(holder, position);


        //setAnimation(holder.itemView, position);
    }

    @Override
    protected void dispatched() {
        if (diffUtilDispatchedInterface != null) {
            diffUtilDispatchedInterface.onDispatched();
        }
    }

    @Override
    protected void bind(ItemProductSearchListAdapterBinding binding, Product product) {
        binding.setProduct(product);

        binding.ratingBar.setRating(product.ratingDetails.totalRatingValue);

        binding.ratingBarTextView.setText((binding.getRoot().getResources().getString(R.string.discount__rating5, String.valueOf(product.ratingDetails.totalRatingValue), String.valueOf(product.ratingDetails.totalRatingCount))));

        binding.priceTextView.setText(String.valueOf(Utils.format(product.unitPrice)));
        String originalPriceStr = product.currencySymbol + Constants.SPACE_STRING + Utils.format(product.originalPrice);
        binding.originalPriceTextView.setText(originalPriceStr);

        if (product.isDiscount.equals(Constants.ZERO)) {
            binding.originalPriceTextView.setVisibility(View.GONE);
            binding.discountTextView.setVisibility(View.GONE);
        } else {
            binding.originalPriceTextView.setPaintFlags(binding.originalPriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            binding.originalPriceTextView.setVisibility(View.VISIBLE);
            binding.discountTextView.setVisibility(View.VISIBLE);
            int discountValue = (int) product.discountPercent;
          //  String discountValueStr = "-" + discountValue + "%";
            String discountValueStr = "-" + product.discountAmount;
            binding.discountTextView.setText(discountValueStr);
        }

        if (product.isFeatured.equals(Constants.ZERO)) {
            binding.featuredIconImageView.setVisibility(View.GONE);
        } else {
            binding.featuredIconImageView.setVisibility(View.VISIBLE);
        }

//        if (product.isFavourited.equals(Constants.RATING_ONE)) {
//            binding.heartButton.setLiked(true);
//        } else {
//            binding.heartButton.setLiked(false);
//        }
    }

    @Override
    protected boolean areItemsTheSame(Product oldItem, Product newItem) {
        return Objects.equals(oldItem.id, newItem.id)
                && oldItem.name.equals(newItem.name)
                && oldItem.isFavourited.equals(newItem.isFavourited)
                && oldItem.likeCount == newItem.likeCount
                && oldItem.ratingDetails.totalRatingValue == newItem.ratingDetails.totalRatingValue
                && oldItem.ratingDetails.totalRatingCount == newItem.ratingDetails.totalRatingCount;
    }

    @Override
    protected boolean areContentsTheSame(Product oldItem, Product newItem) {
        return Objects.equals(oldItem.id, newItem.id)
                && oldItem.name.equals(newItem.name)
                && oldItem.isFavourited.equals(newItem.isFavourited)
                && oldItem.likeCount == newItem.likeCount
                && oldItem.ratingDetails.totalRatingValue == newItem.ratingDetails.totalRatingValue
                && oldItem.ratingDetails.totalRatingCount == newItem.ratingDetails.totalRatingCount;
    }

    public interface NewsClickCallback {
        void onClick(Product product);

        void onFavLikeClick(Product product, LikeButton likeButton);

        void onFavUnlikeClick(Product product, LikeButton likeButton);
    }


}
