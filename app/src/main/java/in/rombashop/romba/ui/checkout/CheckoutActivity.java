package in.rombashop.romba.ui.checkout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.rombashop.romba.Config;
import in.rombashop.romba.MainActivity;
import in.rombashop.romba.PincodeActivity;
import in.rombashop.romba.R;
import in.rombashop.romba.databinding.ActivityCheckoutBinding;
import in.rombashop.romba.net.MySingleton;
import in.rombashop.romba.net.PrefManager;
import in.rombashop.romba.net.ServiceNames;
import in.rombashop.romba.payu.StartPaymentActivity;
import in.rombashop.romba.ui.basket.BasketListActivity;
import in.rombashop.romba.ui.common.PSAppCompactActivity;
import in.rombashop.romba.ui.common.PSFragment;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.MyContextWrapper;
import in.rombashop.romba.utils.PSDialogMsg;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewobject.TransactionObject;
import in.rombashop.romba.viewobject.User;
import in.rombashop.romba.viewobject.holder.TransactionValueHolder;


public class CheckoutActivity extends PSAppCompactActivity {

    public int number = 1;
    private int maxNumber = 4;
    User user;
    PSFragment fragment;
    public ActivityCheckoutBinding binding;
    public ProgressDialog progressDialog;
    private PSDialogMsg psDialogMsg;
    public TransactionValueHolder transactionValueHolder;
    public TransactionObject transactionObject;
    private static PrefManager prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout);
        prf = new PrefManager(CheckoutActivity.this);
        // Init all UI
        initUI(binding);

        progressDialog = new ProgressDialog(this);

        progressDialog.setCancelable(false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE__MAIN_ACTIVITY
                && resultCode == Constants.RESULT_CODE__RESTART_MAIN_ACTIVITY) {

            finish();
            startActivity(new Intent(this, MainActivity.class));

        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        String CURRENT_LANG_CODE = preferences.getString(Constants.LANGUAGE_CODE, Config.DEFAULT_LANGUAGE);
        String CURRENT_LANG_COUNTRY_CODE = preferences.getString(Constants.LANGUAGE_COUNTRY_CODE, Config.DEFAULT_LANGUAGE_COUNTRY_CODE);

        super.attachBaseContext(MyContextWrapper.wrap(newBase, CURRENT_LANG_CODE, CURRENT_LANG_COUNTRY_CODE, true));
    }

    private void initUI(ActivityCheckoutBinding binding) {

        transactionValueHolder = new TransactionValueHolder();

        psDialogMsg = new PSDialogMsg(this, false);

        // click close image button
        binding.closeImageButton.setOnClickListener(view -> finish());

        // fragment1 default initialize
        navigateFragment(binding, number);

        binding.nextButton.setOnClickListener(view -> {
            if (number < maxNumber) {
                number++;
                if (number == 3) {

                    if (((CheckoutFragment2) fragment).clicked) {
                        navigateFragment(binding, number);
                    }else {
                        number--;
                        psDialogMsg.showErrorDialog(getString(R.string.checkout__choose_ship_method), getString(R.string.app__ok));
                        psDialogMsg.show();
                    }


                } else if (number == 2) {

//                    if (user.city.id.isEmpty()) {
//                        psDialogMsg.showErrorDialog(getString(R.string.error_message__select_city), getString(R.string.app__ok));
//                        psDialogMsg.show();
//                        number--;
//                    }else
//                        if (((CheckoutFragment1)fragment).checkShippingAddressEditTextIsEmpty()) {
//                        psDialogMsg.showErrorDialog(getString(R.string.shipping_address_one_error_message), getString(R.string.app__ok));
//                        psDialogMsg.show();
//                        number--;
//                    } else if (((CheckoutFragment1)fragment).checkBillingAddressEditTextIsEmpty()) {
//                        psDialogMsg.showErrorDialog(getString(R.string.billing_address_one_error_message), getString(R.string.app__ok));
//                        psDialogMsg.show();
//                        number--;
//                    }
//                        else if (((CheckoutFragment1)fragment).checkUserEmailEditTextIsEmpty()) {
//                        psDialogMsg.showErrorDialog(getString(R.string.checkout__user_email_empty), getString(R.string.app__ok));
//                        psDialogMsg.show();
//                        number--;
//                    }
//                    else {

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ServiceNames.PINCODE,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        JSONObject jsonObject = null;
                                        try {
                                            jsonObject = new JSONObject(response);
                                            if (jsonObject.optString("status").equalsIgnoreCase("success")) {

                                                JSONObject msgObject = jsonObject.optJSONObject("message");
                                                JSONObject shippingObj = msgObject.optJSONObject("shipping");
                                                prf.setString("pincode", ServiceNames.addressList.getPincode());
                                                assert shippingObj != null;
                                                prf.setInt("shipping_cost",Integer.parseInt(shippingObj.optString("shipping_cost")));

                                                ((AddressSelectionFragment) fragment).updateUserProfile();

                                            } else {
                                                Utils.psLog("pincode not available");
                                                psDialogMsg.showErrorDialog("This pincode area is not serviceable.", getString(R.string.app__ok));
                                                psDialogMsg.show();
                                                number --;
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                psDialogMsg.showErrorDialog("This pincode area is not serviceable.", getString(R.string.app__ok));
                                psDialogMsg.show();
                                number --;
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("pincode", ServiceNames.addressList.getPincode());
                                params.put("shop_id", "shopd8b59013d41510b0b78483e477286803");
                                return params;
                            }
                        };
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                15000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        stringRequest.setShouldCache(false);
                        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


                } else if (number == 4) {

                    if (((CheckoutFragment3) fragment).binding.get().checkBox.isChecked()) {

                        number--;

                        if (((CheckoutFragment3) fragment).clicked) {

                            psDialogMsg.showConfirmDialog(getString(R.string.confirm_to_Buy), getString(R.string.app__ok), getString(R.string.app__cancel));
                            psDialogMsg.show();

                            psDialogMsg.okButton.setOnClickListener(v -> {

                                psDialogMsg.cancel();

                                switch (((CheckoutFragment3) fragment).paymentMethod) {
                                    case Constants.PAYMENT_PAYPAL:

                                        ((CheckoutFragment3) fragment).getToken();

                                        break;

                                    case Constants.PAYMENT_CASH_ON_DELIVERY:

                                        ((CheckoutFragment3) fragment).sendData();

                                        break;

                                    case Constants.PAYMENT_STRIPE:

                                        navigationController.navigateToStripeActivity(CheckoutActivity.this);

                                        break;

                                    case Constants.PAYMENT_BANK:
                                        ((CheckoutFragment3) fragment).payU();
                                        break;
                                }

                            });

                            psDialogMsg.cancelButton.setOnClickListener(v -> psDialogMsg.cancel());
                        } else {

                            psDialogMsg.showErrorDialog(getString(R.string.checkout__choose_a_method), getString(R.string.app__ok));
                            psDialogMsg.show();
                        }

                    } else {

                        number--;

                        psDialogMsg.showInfoDialog(getString(R.string.not_checked), getString(R.string.app__ok));
                        psDialogMsg.show();

                    }

                } else {

                    navigateFragment(binding, number);
                }

            }
        });

        binding.prevButton.setOnClickListener(view -> {
            if (number > 1) {
                number--;
                binding.shippingImageView.setImageResource(R.drawable.baseline_circle_line_uncheck_24);
                binding.paymentImageView.setImageResource(R.drawable.baseline_circle_black_uncheck_24);
                navigateFragment(binding, number);
            }

        });

        binding.keepShoppingButton.setOnClickListener(v -> {

            navigationController.navigateBackToBasketActivity(CheckoutActivity.this);
            CheckoutActivity.this.finish();

        });

    }


    public void navigateFragment(ActivityCheckoutBinding binding, int position) {
        updateCheckoutUI(binding);

        if (position == 1) {

         //   CheckoutFragment1 fragment = new CheckoutFragment1();
            AddressSelectionFragment fragment = new AddressSelectionFragment();
            this.fragment = fragment;
            setupFragment(fragment);

        } else if (position == 2) {

            CheckoutFragment2 fragment = new CheckoutFragment2();
            this.fragment = fragment;
            setupFragment(fragment);

        } else if (position == 3) {

            CheckoutFragment3 fragment = new CheckoutFragment3();
            this.fragment = fragment;
            setupFragment(fragment);

        } else if (position == 4) {
            setupFragment(new CheckoutStatusFragment());
        }
    }

    private void updateCheckoutUI(ActivityCheckoutBinding binding) {
        if (number == 1) {
            binding.nextButton.setVisibility(View.VISIBLE);
            binding.prevButton.setVisibility(View.GONE);
            binding.keepShoppingButton.setVisibility(View.GONE);
            binding.step2View.setBackgroundColor(getResources().getColor(R.color.md_grey_300));
            binding.step3View.setBackgroundColor(getResources().getColor(R.color.md_grey_300));
            binding.nextButton.setText(R.string.checkout__next);

        } else if (number == 2) {

            binding.nextButton.setVisibility(View.VISIBLE);
            binding.prevButton.setVisibility(View.VISIBLE);
            binding.step2View.setBackgroundColor(getResources().getColor(R.color.global__primary));
            binding.step3View.setBackgroundColor(getResources().getColor(R.color.md_grey_300));
            binding.keepShoppingButton.setVisibility(View.GONE);
            binding.paymentImageView.setImageResource(R.drawable.baseline_circle_line_uncheck_24);
            binding.shippingImageView.setImageResource(R.drawable.baseline_circle_line_check_24);

            binding.nextButton.setText(R.string.checkout__next);
            binding.prevButton.setText(R.string.back);

        } else if (number == 3) {
            binding.nextButton.setVisibility(View.VISIBLE);
            binding.prevButton.setVisibility(View.VISIBLE);
            binding.keepShoppingButton.setVisibility(View.GONE);
            binding.step3View.setBackgroundColor(getResources().getColor(R.color.global__primary));
            binding.paymentImageView.setImageResource(R.drawable.baseline_circle_line_check_24);
            binding.successImageView.setImageResource(R.drawable.baseline_circle_line_uncheck_24);

            binding.nextButton.setText(R.string.checkout);
            binding.prevButton.setText(R.string.back);
        } else if (number == 4) {
            binding.constraintLayout25.setVisibility(View.GONE);
            binding.nextButton.setVisibility(View.GONE);
            binding.prevButton.setVisibility(View.GONE);
            binding.keepShoppingButton.setVisibility(View.VISIBLE);
            binding.paymentImageView.setImageResource(R.drawable.baseline_circle_line_check_24);
            binding.successImageView.setImageResource(R.drawable.baseline_circle_line_check_24);
        }

    }

    public void setCurrentUser(User user) {
        this.user = user;
    }

    public User getCurrentUser() {
        return this.user;
    }

    public void goToFragment4() {
        navigateFragment(binding, 4);
        number = 4;
    }

    @Override
    public void onBackPressed() {
    }
}
