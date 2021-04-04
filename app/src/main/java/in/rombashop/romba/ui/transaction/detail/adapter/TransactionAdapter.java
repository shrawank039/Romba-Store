package in.rombashop.romba.ui.transaction.detail.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import in.rombashop.romba.Config;
import in.rombashop.romba.R;
import in.rombashop.romba.ReturnActivity;
import in.rombashop.romba.databinding.ItemTransactionAdapterBinding;
import in.rombashop.romba.net.ServiceNames;
import in.rombashop.romba.ui.common.DataBoundListAdapter;
import in.rombashop.romba.ui.common.NavigationController;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.Objects;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewobject.TransactionDetail;

public class TransactionAdapter extends DataBoundListAdapter<TransactionDetail, ItemTransactionAdapterBinding> {

    protected NavigationController navigationController;

    private final androidx.databinding.DataBindingComponent dataBindingComponent;
    private TransactionClickCallback callback;
    private DataBoundListAdapter.DiffUtilDispatchedInterface diffUtilDispatchedInterface;
    private String transactionIsZoneShipping;


    public TransactionAdapter(androidx.databinding.DataBindingComponent dataBindingComponent,
                              TransactionClickCallback callback,
                              DiffUtilDispatchedInterface diffUtilDispatchedInterface) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
        this.diffUtilDispatchedInterface = diffUtilDispatchedInterface;
    }

    public void setZoneShipping(String transactionIsZoneShipping) {
        this.transactionIsZoneShipping = transactionIsZoneShipping;
    }

    @Override
    protected ItemTransactionAdapterBinding createBinding(ViewGroup parent) {
        ItemTransactionAdapterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_transaction_adapter, parent, false, dataBindingComponent);
        binding.getRoot().setOnClickListener(v -> {
            TransactionDetail transactionDetail = binding.getTransactionDetail();
            if (transactionDetail != null && callback != null) {
                callback.onClick(transactionDetail);
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
    protected void bind(ItemTransactionAdapterBinding binding, TransactionDetail item) {
        binding.setTransactionDetail(item);
        setDataToBalanceAndSubTotalToTransactionDetailOrder(binding, item);

        if (item.productColorCode.equals("")) {
            binding.color1BgImageView.setVisibility(View.GONE);
        } else {
            binding.color1BgImageView.setVisibility(View.VISIBLE);
            Bitmap b = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(b);
            canvas.drawColor(Color.parseColor(item.productColorCode));
            dataBindingComponent.getFragmentBindingAdapters().bindCircleBitmap(binding.color1BgImageView, b);

        }

        int days=0;
        int daysdiff = 0;
        if (!item.returnTitle.isEmpty()) {
            try {
                days = Integer.parseInt(item.returnTitle.replaceAll("[\\D]", ""));
            }catch (Exception ignored){
                Utils.psLog("error: No days found in return title!");
            }
        }


       SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date6=formatter6.parse(item.addedDate);
            Date date = new Timestamp(System.currentTimeMillis());
            assert date6 != null;
            long diff = date.getTime() - date6.getTime();
            long aa = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            daysdiff = (int) aa;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (ServiceNames.order_status.equalsIgnoreCase("Delivered")){
            int isReturnValid = days - daysdiff;
            Utils.psLog("date : " + isReturnValid);
            if (isReturnValid>0){
                binding.txtReturn.setVisibility(View.VISIBLE);
            }

            if (item.returnStatus.equalsIgnoreCase("yes")){
                binding.txtReturn.setText("Returned Successful");
            }
        }


        binding.txtReturn.setOnClickListener(view ->

                view.getContext().startActivity(new Intent(view.getContext(), ReturnActivity.class)
                .putExtra("order_id", item.transactionsHeaderId)
                .putExtra("product_id", item.id)
                .putExtra("user_id", ServiceNames.user_id))
        );

        if (item.productAttributeName.equals("")) {
            binding.attributesTextView.setVisibility(View.GONE);
        } else {
            binding.attributesTextView.setVisibility(View.VISIBLE);
            String replaceComaForAttribute = item.productAttributeName;
            String replaceString = replaceComaForAttribute.replace(Config.ATTRIBUTE_SEPARATOR, ",");
            binding.attributesTextView.setText(replaceString);
        }

        if (item.productColorCode.equals("") && item.productAttributeName.equals("")) {
            binding.attributesView.setVisibility(View.GONE);
        } else {
            binding.attributesView.setVisibility(View.VISIBLE);
        }

        if (item.taxAmount != null) {
            if (transactionIsZoneShipping.equals(Constants.ONE)) {
                binding.shippingCostTextView.setVisibility(View.VISIBLE);
                String a = item.currencySymbol + " " + item.taxAmount;
                if (item.shippingCost.equals("")) {
                    binding.shippingCostValueText.setText(a);
                } else {
                    binding.shippingCostValueText.setText(a);
                }

            } else {
                binding.shippingCostTextView.setVisibility(View.GONE);
                binding.shippingCostValueText.setVisibility(View.GONE);
            }
        } else {
            binding.shippingCostValueText.setText(("0.00"));
        }
    }

    @Override
    protected boolean areItemsTheSame(TransactionDetail oldItem, TransactionDetail newItem) {
        return Objects.equals(oldItem.id, newItem.id);

    }

    @Override
    protected boolean areContentsTheSame(TransactionDetail oldItem, TransactionDetail newItem) {
        return Objects.equals(oldItem.id, newItem.id);
    }


    public interface TransactionClickCallback {
        void onClick(TransactionDetail transactionDetail);
    }

    private void setDataToBalanceAndSubTotalToTransactionDetailOrder(ItemTransactionAdapterBinding binding, TransactionDetail item) {

        int qty = Integer.parseInt(item.qty);
        float subTotal;

        if (item.originalPrice != 0 && item.discountAvailableAmount != 0) {
            int originalPrice = (int) item.originalPrice - (int) item.discountAvailableAmount;
            String balanceString = item.currencySymbol + " " + originalPrice;
            binding.balanceValueTextView.setText(item.taxPercent + "%");
            subTotal = originalPrice * qty;
        } else {
            String balanceString = item.currencySymbol + " " + Utils.format(item.originalPrice);
            binding.balanceValueTextView.setText(item.taxPercent + "%");

            subTotal = item.originalPrice * qty;
        }

        String subTotalValueString = item.currencySymbol + Constants.SPACE_STRING + Utils.format(subTotal);
        binding.subTotalValueTextView.setText(subTotalValueString);

        String priceValue = item.currencySymbol + Constants.SPACE_STRING + Utils.format(item.originalPrice);
        binding.priceValueTextView.setText(priceValue);

        String discountAvailableAmount = item.currencySymbol + Constants.SPACE_STRING + Utils.format(item.discountAvailableAmount);
        binding.discountAvailableAmountValueTextView.setText(discountAvailableAmount);


    }
}
