package in.rombashop.romba.ui.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import in.rombashop.romba.R;
import in.rombashop.romba.binding.FragmentDataBindingComponent;
import in.rombashop.romba.databinding.CheckoutFragment2Binding;
import in.rombashop.romba.net.PrefManager;
import in.rombashop.romba.ui.basket.BasketListActivity;
import in.rombashop.romba.ui.checkout.adapter.CheckoutBasketAdapter;
import in.rombashop.romba.ui.checkout.adapter.ShippingMethodsAdapter;
import in.rombashop.romba.ui.common.DataBoundListAdapter;
import in.rombashop.romba.ui.common.PSFragment;
import in.rombashop.romba.utils.AutoClearedValue;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.PSDialogMsg;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewmodel.coupondiscount.CouponDiscountViewModel;
import in.rombashop.romba.viewmodel.product.BasketViewModel;
import in.rombashop.romba.viewmodel.shippingmethod.ShippingMethodViewModel;
import in.rombashop.romba.viewobject.Basket;
import in.rombashop.romba.viewobject.ShippingCostContainer;
import in.rombashop.romba.viewobject.ShippingMethod;
import in.rombashop.romba.viewobject.ShippingProductContainer;
import in.rombashop.romba.viewobject.common.Status;

public class CheckoutFragment2 extends PSFragment implements DataBoundListAdapter.DiffUtilDispatchedInterface {

    private final androidx.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    private BasketViewModel basketViewModel;
    private ShippingMethodViewModel shippingMethodViewModel;
    private CouponDiscountViewModel couponDiscountViewModel;
    private AutoClearedValue<CheckoutBasketAdapter> checkoutBasketAdapter;
    private static PrefManager prf;

    @VisibleForTesting
    private AutoClearedValue<CheckoutFragment2Binding> binding;
    private AutoClearedValue<ShippingMethodsAdapter> adapter;

    private PSDialogMsg psDialogMsg;
    boolean clicked = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CheckoutFragment2Binding dataBinding = DataBindingUtil.inflate(inflater, R.layout.checkout_fragment_2, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);

        return binding.get().getRoot();
    }

    @Override
    public void onDispatched() {

    }

    @Override
    protected void initUIAndActions() {

        psDialogMsg = new PSDialogMsg(this.getActivity(), false);
        prf = new PrefManager(getContext());

        if (getActivity() instanceof CheckoutActivity) {
            ((CheckoutActivity) getActivity()).progressDialog.setMessage((Utils.getSpannableString(getContext(), getString(R.string.message__please_wait), Utils.Fonts.MM_FONT)));
            ((CheckoutActivity) getActivity()).progressDialog.setCancelable(false);
        }

        binding.get().couponDiscountButton.setOnClickListener(v -> {
            if ((CheckoutFragment2.this.getActivity()) != null) {
                ((CheckoutActivity) CheckoutFragment2.this.getActivity()).transactionValueHolder.couponDiscountText = binding.get().couponDiscountValueEditText.getText().toString();
            }
            couponDiscountViewModel.setCouponDiscountObj(binding.get().couponDiscountValueEditText.getText().toString());

            if (getActivity() != null && getActivity() instanceof CheckoutActivity) {
                ((CheckoutActivity) getActivity()).progressDialog.setMessage(getString(R.string.check_coupon));
                ((CheckoutActivity) getActivity()).progressDialog.show();
            }


        });

        if (!overAllTaxLabel.isEmpty()) {
            binding.get().overAllTaxTextView.setText(getString(R.string.tax));
        }

        if (!shippingTaxLabel.isEmpty()) {
            binding.get().shippingTaxTextView.setText(getString(R.string.shipping_tax));
        }

    }

    @Override
    protected void initViewModels() {
        basketViewModel = ViewModelProviders.of(this, viewModelFactory).get(BasketViewModel.class);
        shippingMethodViewModel = ViewModelProviders.of(this, viewModelFactory).get(ShippingMethodViewModel.class);
        couponDiscountViewModel = ViewModelProviders.of(this, viewModelFactory).get(CouponDiscountViewModel.class);
    }

    @Override
    protected void initAdapters() {

        if (getActivity() != null) {

            ShippingMethodsAdapter nvAdapter = new ShippingMethodsAdapter(dataBindingComponent, shippingMethod -> {

                if (CheckoutFragment2.this.getActivity() != null) {

                    ((CheckoutActivity) CheckoutFragment2.this.getActivity()).transactionValueHolder.shipping_cost = Utils.round(shippingMethod.price, 2);
                    ((CheckoutActivity) CheckoutFragment2.this.getActivity()).transactionValueHolder.shippingMethodName = shippingMethod.name;
                    ((CheckoutActivity) CheckoutFragment2.this.getActivity()).transactionValueHolder.selectedShippingId = shippingMethod.id;

                    CheckoutFragment2.this.calculateTheBalance();
                }
            }, shippingId, ((CheckoutActivity) CheckoutFragment2.this.getActivity()).transactionValueHolder.selectedShippingId);

            adapter = new AutoClearedValue<>(this, nvAdapter);
            binding.get().shippingMethodsRecyclerView.setAdapter(adapter.get());

        }

        CheckoutBasketAdapter basketAdapter1 = new CheckoutBasketAdapter(dataBindingComponent, new CheckoutBasketAdapter.BasketClickCallBack() {
            @Override
            public void onMinusClick(Basket basket) {
              //  basketViewModel.setUpdateToBasketListObj(basket.id, basket.count);

            }

            @Override
            public void onAddClick(Basket basket) {
              //  basketViewModel.setUpdateToBasketListObj(basket.id, basket.count);
            }

            @Override
            public void onDeleteConfirm(Basket basket) {

//                psDialogMsg.showConfirmDialog(getString(R.string.delete_item_from_basket), getString(R.string.app__ok), getString(R.string.app__cancel));
//
//                psDialogMsg.show();
//
//                psDialogMsg.okButton.setOnClickListener(view -> {
//                    basketViewModel.setDeleteToBasketListObj(basket.id);
//                    psDialogMsg.cancel();
//                });
//                psDialogMsg.cancelButton.setOnClickListener(view -> psDialogMsg.cancel());

            }

            @Override
            public void onClick(Basket basket) {
               // navigationController.navigateToProductDetailActivity(getActivity(), basket);
            }

        }, this);
        checkoutBasketAdapter = new AutoClearedValue<>(this, basketAdapter1);
        bindingBasketAdapter(checkoutBasketAdapter.get());

    }

    private void bindingBasketAdapter(CheckoutBasketAdapter nvbasketAdapter) {
        this.checkoutBasketAdapter = new AutoClearedValue<>(this, nvbasketAdapter);
//        binding.get().basketRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.get().basketRecycler.setAdapter(checkoutBasketAdapter.get());
    }

    @Override
    protected void initData() {

        Utils.psLog("shipping : " + shopStandardShippingEnable + shopNoShippingEnable);

        if (shopNoShippingEnable.equals(Constants.ONE)) {
            binding.get().shippingMethodsCardView.setVisibility(View.GONE);
        }

        if (shopStandardShippingEnable.equals(Constants.ONE)) {
            binding.get().shippingMethodsCardView.setVisibility(View.VISIBLE);
            shippingMethodViewModel.setShippingMethodsObj();
        }

        shippingMethodViewModel.getshippingCostByCountryAndCityData().observe(this, result -> {

            if (result != null) {
                if (result.status == Status.SUCCESS) {
//                    progressDialog.get().cancel();
                    if (getActivity() != null && getActivity() instanceof CheckoutActivity) {
                        ((CheckoutActivity) getActivity()).progressDialog.hide();
                    }

                    if (result.data != null) {

                        Utils.psLog("shippingMethods"+result.data);

                        if (CheckoutFragment2.this.getActivity() != null) {
                            ((CheckoutActivity) CheckoutFragment2.this.getActivity()).transactionValueHolder.shippingMethodName = result.data.shippingZone.shippingZonePackageName;
                            ((CheckoutActivity) CheckoutFragment2.this.getActivity()).transactionValueHolder.shipping_cost = Float.parseFloat(result.data.shippingZone.shippingCost);//prf.getInt("shipping_cost"));

                            calculateTheBalance();
                        }
                    }
                } else if (result.status == Status.ERROR) {
                    if (getActivity() != null && getActivity() instanceof CheckoutActivity) {
                        ((CheckoutActivity) getActivity()).progressDialog.hide();
                    }

                }
            }

        });

        shippingMethodViewModel.getShippingMethodsData().observe(this, result -> {

            if (result.data != null) {
                switch (result.status) {

                    case SUCCESS:
                        CheckoutFragment2.this.replaceShippingMethods(result.data);


                        for (ShippingMethod shippingMethod : result.data) {

                            if ((CheckoutFragment2.this.getActivity()) != null) {
                                if (!shippingId.isEmpty()) {
                                    if (shippingMethod.id.equals(shippingId) && ((CheckoutActivity) CheckoutFragment2.this.getActivity()).transactionValueHolder.selectedShippingId.isEmpty()) {
                                        if (CheckoutFragment2.this.getActivity() != null) {
                                            if (shopNoShippingEnable.equals(Constants.ONE)) {
                                                ((CheckoutActivity) CheckoutFragment2.this.getActivity()).transactionValueHolder.shipping_cost = 0;
                                            } else {
                                                ((CheckoutActivity) CheckoutFragment2.this.getActivity()).transactionValueHolder.shipping_cost = Utils.round(shippingMethod.price, 2);
                                            }
                                            CheckoutFragment2.this.calculateTheBalance();
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                        break;

                    case LOADING:
                        CheckoutFragment2.this.replaceShippingMethods(result.data);

                        for (ShippingMethod shippingMethod : result.data) {

                            if ((CheckoutFragment2.this.getActivity()) != null) {
                                if (!shippingId.isEmpty()) {
                                    if (shippingMethod.id.equals(shippingId) && ((CheckoutActivity) CheckoutFragment2.this.getActivity()).transactionValueHolder.selectedShippingId.isEmpty()) {
                                        if (CheckoutFragment2.this.getActivity() != null) {
                                            if (shopNoShippingEnable.equals(Constants.ONE))
                                                ((CheckoutActivity) CheckoutFragment2.this.getActivity()).transactionValueHolder.shipping_cost = 0;
                                            else {
                                                ((CheckoutActivity) CheckoutFragment2.this.getActivity()).transactionValueHolder.shipping_cost = Utils.round(shippingMethod.price, 2);
                                            }
                                            CheckoutFragment2.this.calculateTheBalance();
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        });

        couponDiscountViewModel.getCouponDiscountData().observe(this, result -> {

            if (result != null) {
                switch (result.status) {
                    case ERROR:

                        if (getActivity() != null && getActivity() instanceof CheckoutActivity) {
                            ((CheckoutActivity) getActivity()).progressDialog.hide();
                        }

                        psDialogMsg.showErrorDialog(getString(R.string.error_coupon), getString(R.string.app__ok));
                        psDialogMsg.show();

                        break;

                    case SUCCESS:

                        if (result.data != null) {

                            if (getActivity() != null && getActivity() instanceof CheckoutActivity) {
                                ((CheckoutActivity) getActivity()).progressDialog.hide();
                            }

                            psDialogMsg.showSuccessDialog(getString(R.string.checkout_detail__claimed_coupon), getString(R.string.app__ok));
                            psDialogMsg.show();

                            if (getActivity() != null) {
                                ((CheckoutActivity) getActivity()).transactionValueHolder.coupon_discount = Utils.round(Float.parseFloat(result.data.couponAmount), 2);
                                Utils.psLog("coupon5" + ((CheckoutActivity) getActivity()).transactionValueHolder.coupon_discount + "");
                            }

                            calculateTheBalance();
                        }

                        break;
                }
            }
        });

        basketViewModel.setBasketListWithProductObj();
        basketViewModel.getAllBasketWithProductList().observe(this, baskets -> {
            if (baskets != null && baskets.size() > 0) {

                if (getActivity() != null) {
                    ((CheckoutActivity) getActivity()).transactionValueHolder.resetValues();

                    for (Basket basket : baskets) {

                        ((CheckoutActivity) getActivity()).transactionValueHolder.total_item_count += basket.count;
                        ((CheckoutActivity) getActivity()).transactionValueHolder.total += Utils.round((basket.basketOriginalPrice) * basket.count, 2);

                        ((CheckoutActivity) getActivity()).transactionValueHolder.discount += Utils.round(basket.product.discountAmount * basket.count, 2);
                        ((CheckoutActivity) getActivity()).transactionValueHolder.currencySymbol = basket.product.currencySymbol;


                        ShippingProductContainer shippingProductContainer = new ShippingProductContainer(
                                basket.product.id,
                                basket.count
                        );
                        shippingMethodViewModel.shippingProductContainer.add(shippingProductContainer);

                    }

                    if (shopZoneShippingEnable.equals(Constants.ONE)) {
                        binding.get().shippingMethodsCardView.setVisibility(View.GONE);

                        if (getActivity() != null) {
                            //progressDialog.get().show();
                            if (getActivity() != null && getActivity() instanceof CheckoutActivity) {
                                ((CheckoutActivity) getActivity()).progressDialog.show();
                                Utils.psLog(((CheckoutActivity) getActivity()).user.country.id + " - " + ((CheckoutActivity) getActivity()).user.city.id + " - " + shopId);


                                shippingMethodViewModel.setshippingCostByCountryAndCityObj(new ShippingCostContainer(
                                        ((CheckoutActivity) getActivity()).user.country.id, ((CheckoutActivity) getActivity()).user.city.id, shopId,
                                        shippingMethodViewModel.shippingProductContainer));
                            }
                        }
                    }
                }
                calculateTheBalance();
            }
        });

        basketViewModel.getWholeBasketDeleteData().observe(this, result -> {
            if (result != null) {
                if (result.status == Status.SUCCESS) {
                    Utils.psLog("Success");
                } else if (result.status == Status.ERROR) {
                    Utils.psLog("Fail");
                }
            }
        });

        LoadData();
    }

    private void LoadData() {
        //load basket

        basketViewModel.setBasketListWithProductObj();
        LiveData<List<Basket>> basketData = basketViewModel.getAllBasketWithProductList();
        if (basketData != null) {
            basketData.observe(this, listResource -> {
                if (listResource != null) {
                    if (listResource.size() > 0) {

//                        binding.get().noItemConstraintLayout.setVisibility(View.GONE);
//                        binding.get().checkoutConstraintLayout.setVisibility(View.VISIBLE);

                    } else {

//                        binding.get().checkoutConstraintLayout.setVisibility(View.GONE);
//                        binding.get().noItemConstraintLayout.setVisibility(View.VISIBLE);

                        if (getActivity() instanceof BasketListActivity) {
                            getActivity().finish();
                        }

                    }

                    replaceProductSpecsData(listResource);

                } else {
                    if(basketViewModel.getAllBasketWithProductList() != null) {
                        if (basketViewModel.getAllBasketWithProductList().getValue() != null) {
                            if (basketViewModel.getAllBasketWithProductList().getValue().size() == 0) {
//                                binding.get().checkoutConstraintLayout.setVisibility(View.GONE);
//                                binding.get().noItemConstraintLayout.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

            });
        }

        basketViewModel.getBasketUpdateData().observe(this, resourse -> {
            if (resourse != null) {
                if (resourse.status == Status.SUCCESS) {

                    basketViewModel.totalPrice = 0;
                    basketViewModel.basketCount = 0;

                    basketViewModel.setBasketListWithProductObj();

                }
            }
        });

        basketViewModel.getBasketDeleteData().observe(this, resource -> {
            if (resource != null) {
                if (resource.status == Status.SUCCESS) {

                    basketViewModel.totalPrice = 0;
                    basketViewModel.basketCount = 0;

                    basketViewModel.setBasketListWithProductObj();

                }
            }
        });


    }

    private void replaceProductSpecsData(List<Basket> basketList) {

        checkoutBasketAdapter.get().replace(basketList);

        if (basketList != null) {
            checkoutBasketAdapter.get().replace(basketList);

            if (basketList.size() > 0) {
                basketViewModel.totalPrice = 0;

                for (int i = 0; i < basketList.size(); i++) {
                    basketViewModel.totalPrice += basketList.get(i).basketPrice * basketList.get(i).count;
                }

                basketViewModel.basketCount = 0;

                for (int i = 0; i < basketList.size(); i++) {
                    basketViewModel.basketCount += basketList.get(i).count;
                }

                String totalPriceString = basketList.get(0).product.currencySymbol + Constants.SPACE_STRING + Utils.format(Utils.round(basketViewModel.totalPrice, 2));

//                binding.get().totalPriceTextView.setText(totalPriceString);
//                binding.get().countTextView.setText(String.valueOf(basketViewModel.basketCount));
            } else {
//                binding.get().totalPriceTextView.setText(Constants.ZERO);
//                binding.get().countTextView.setText(Constants.ZERO);

                basketViewModel.totalPrice = 0;
                basketViewModel.basketCount = 0;

                basketList.size();

                binding.get().executePendingBindings();

            }
        }
    }

    private void replaceShippingMethods(List<ShippingMethod> shippingMethods) {
        this.adapter.get().replace(shippingMethods);
        this.binding.get().executePendingBindings();
    }

    private void calculateTheBalance() {

        if (getActivity() != null) {

            ((CheckoutActivity) getActivity()).transactionValueHolder.sub_total = Utils.round(((CheckoutActivity) getActivity()).transactionValueHolder.total - (((CheckoutActivity) getActivity()).transactionValueHolder.discount), 2);

            if (((CheckoutActivity) getActivity()).transactionValueHolder.coupon_discount > 0) {
                ((CheckoutActivity) getActivity()).transactionValueHolder.sub_total = Utils.round(((CheckoutActivity) getActivity()).transactionValueHolder.sub_total - ((CheckoutActivity) getActivity()).transactionValueHolder.coupon_discount, 2);
            }

            if (!overAllTax.isEmpty()) {
                ((CheckoutActivity) getActivity()).transactionValueHolder.tax = Utils.round(((CheckoutActivity) getActivity()).transactionValueHolder.sub_total * Float.parseFloat(overAllTax), 2);
            }

            if (!shippingTax.isEmpty() && ((CheckoutActivity) getActivity()).transactionValueHolder.shipping_cost > 0) {
                ((CheckoutActivity) getActivity()).transactionValueHolder.shipping_tax = Utils.round(((CheckoutActivity) getActivity()).transactionValueHolder.shipping_cost * Float.parseFloat(shippingTax), 2);
            }

            ((CheckoutActivity) getActivity()).transactionValueHolder.final_total = Utils.round(((CheckoutActivity) getActivity()).transactionValueHolder.sub_total + ((CheckoutActivity) getActivity()).transactionValueHolder.tax +
                    ((CheckoutActivity) getActivity()).transactionValueHolder.shipping_tax + ((CheckoutActivity) getActivity()).transactionValueHolder.shipping_cost, 2);
            updateUI();
        }

    }

    private void updateUI() {

        if (getActivity() != null) {

            if (((CheckoutActivity) getActivity()).transactionValueHolder.total_item_count > 0) {
                binding.get().totalItemCountValueTextView.setText(String.valueOf(((CheckoutActivity) getActivity()).transactionValueHolder.total_item_count));
            }

            if (((CheckoutActivity) getActivity()).transactionValueHolder.total > 0) {
                String totalValueString = ((CheckoutActivity) getActivity()).
                        transactionValueHolder.currencySymbol + " " + Utils.format(((CheckoutActivity) getActivity()).transactionValueHolder.total);
                binding.get().totalValueTextView.setText(totalValueString);
            }

            if (((CheckoutActivity) getActivity()).transactionValueHolder.coupon_discount > 0) {
                String couponDiscountValueString = ((CheckoutActivity) getActivity()).transactionValueHolder.currencySymbol + " " + Utils.format(((CheckoutActivity) getActivity()).transactionValueHolder.coupon_discount);
                binding.get().couponDiscountValueTextView.setText(couponDiscountValueString);
            }

            if (((CheckoutActivity) getActivity()).transactionValueHolder.discount > 0) {
                String discountValueString = ((CheckoutActivity) getActivity()).transactionValueHolder.currencySymbol + " " + Utils.format(((CheckoutActivity) getActivity()).transactionValueHolder.discount);
                binding.get().discountValueTextView.setText(discountValueString);
            }

            if (!((CheckoutActivity) getActivity()).transactionValueHolder.couponDiscountText.isEmpty()) {
                String couponDiscountValueString = ((CheckoutActivity) getActivity()).transactionValueHolder.couponDiscountText;
                binding.get().couponDiscountValueEditText.setText(couponDiscountValueString);
            }

            if (((CheckoutActivity) getActivity()).transactionValueHolder.sub_total > 0) {
                String subTotalValueString = ((CheckoutActivity) getActivity()).transactionValueHolder.currencySymbol + " " + Utils.format(((CheckoutActivity) getActivity()).transactionValueHolder.sub_total);
                binding.get().subtotalValueTextView.setText(subTotalValueString);
            }

            if (((CheckoutActivity) getActivity()).transactionValueHolder.tax > 0) {
                String taxValueString = ((CheckoutActivity) getActivity()).transactionValueHolder.currencySymbol + " " + Utils.format(((CheckoutActivity) getActivity()).transactionValueHolder.tax);
                binding.get().taxValueTextView.setText(taxValueString);
            }

            if (!shippingTax.equals("0.0") && !shippingTax.equals(Constants.RATING_ZERO)) {
                String shippingTaxValueString = ((CheckoutActivity) getActivity()).transactionValueHolder.currencySymbol + " " + Utils.format(Utils.round((((CheckoutActivity) getActivity()).transactionValueHolder.shipping_tax), 2));
                binding.get().shippingTaxValueTextView.setText(shippingTaxValueString);
            }

            if (((CheckoutActivity) getActivity()).transactionValueHolder.final_total > 0.0) {
                String finalTotalValueString = ((CheckoutActivity) getActivity()).transactionValueHolder.currencySymbol + " " + Utils.format(((CheckoutActivity) getActivity()).transactionValueHolder.final_total);
                binding.get().finalTotalValueTextView.setText(finalTotalValueString);
            }

            if (((CheckoutActivity) getActivity()).transactionValueHolder.shipping_cost > -1) {
                String shippingCostValueString = ((CheckoutActivity) getActivity()).transactionValueHolder.currencySymbol + " " + Utils.format(((CheckoutActivity) getActivity()).transactionValueHolder.shipping_cost);
                binding.get().shippingCostValueTextView.setText(shippingCostValueString);

                Utils.psLog("shippingMethods"+((CheckoutActivity) getActivity()).transactionValueHolder.shipping_cost);

                clicked =true;
            }

        }

    }

}
