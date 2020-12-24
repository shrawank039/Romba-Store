package in.rombashop.romba.ui.checkout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.rombashop.romba.AddressActivity;
import in.rombashop.romba.AddressList;
import in.rombashop.romba.R;
import in.rombashop.romba.cartAddressListAdapter;
import in.rombashop.romba.cartPayAddressListAdapter;
import in.rombashop.romba.net.MySingleton;
import in.rombashop.romba.net.PrefManager;
import in.rombashop.romba.net.ServiceNames;
import in.rombashop.romba.ui.common.PSFragment;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewmodel.user.UserViewModel;
import in.rombashop.romba.viewobject.City;
import in.rombashop.romba.viewobject.Country;
import in.rombashop.romba.viewobject.User;

public class AddressSelectionFragment extends PSFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static PrefManager prf;
    private ProgressDialog pDialog;
    private List<AddressList> addressLists;
    private List<AddressList> payAddressLists;
    private RecyclerView rv_shipping,rv_payment;
    private cartAddressListAdapter mAdapter;
    private TextView addAddress;
    private UserViewModel userViewModel;

    public AddressSelectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShippingAddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddressSelectionFragment newInstance(String param1, String param2) {
        AddressSelectionFragment fragment = new AddressSelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_address_selection, container, false);
        // Inflate the layout for this fragment
        prf = new PrefManager(getContext());
        addressLists = new ArrayList<>();
        rv_shipping = (RecyclerView) root.findViewById(R.id.rv_shipping);
        addAddress = root.findViewById(R.id.addAddress);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), AddressActivity.class));
            }
        });

        Utils.psLog("onCreate Address");
        mAdapter = new cartAddressListAdapter(getContext(), addressLists);

        rv_shipping.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv_shipping.setLayoutManager(mLayoutManager);
        rv_shipping.setItemAnimator(new DefaultItemAnimator());
        rv_shipping.setAdapter(mAdapter);
      //  loadAddress();
        return root;
    }

    void updateUserProfile() {
        String user_name;
        if (userViewModel.user.userName.isEmpty())
            user_name = "username";
        else
            user_name = userViewModel.user.userName;
      //  checkUserShippingEmailAndBillingEmail();
        User user = new User(userViewModel.user.userId,
                userViewModel.user.userIsSysAdmin,
                userViewModel.user.isShopAdmin,
                userViewModel.user.facebookId,
                userViewModel.user.googleId,
                user_name,
                ServiceNames.addressList.getAlt_phone(),
                ServiceNames.addressList.getPhone(),
                userViewModel.user.userPassword,
                userViewModel.user.userAboutMe,
                userViewModel.user.userCoverPhoto,
                userViewModel.user.userProfilePhoto,
                userViewModel.user.roleId,
                userViewModel.user.status,
                userViewModel.user.isBanned,
                userViewModel.user.addedDate,
                ServiceNames.addressList.getFirstname(),
                ServiceNames.addressList.getLastlast(),
                "",
                ServiceNames.addressList.getAdd1(),
                ServiceNames.addressList.getAdd2(),
                ServiceNames.addressList.getCountry(),
                ServiceNames.addressList.getState(),
                ServiceNames.addressList.getCity(),
                ServiceNames.addressList.getPincode(),
                ServiceNames.addressList.getAlt_phone(),
                ServiceNames.addressList.getPhone(),
                ServiceNames.addressList.getFirstname(),
                ServiceNames.addressList.getLastlast(),
                "",
                ServiceNames.addressList.getAdd1(),
                ServiceNames.addressList.getAdd2(),
                ServiceNames.addressList.getCountry(),
                ServiceNames.addressList.getState(),
                ServiceNames.addressList.getCity(),
                ServiceNames.addressList.getPincode(),
                ServiceNames.addressList.getAlt_phone(),
                ServiceNames.addressList.getPhone(),
                userViewModel.user.deviceToken,
                userViewModel.user.code,
                userViewModel.user.verifyTypes,
                userViewModel.user.addedDateStr,
                new Country(userViewModel.countryId,
                        ServiceNames.addressList.getCountry(),
                        null, null, null, null,
                        null, null, null),
                new City(userViewModel.cityId,
                        ServiceNames.addressList.getCity(),
                        userViewModel.countryId, null, null,
                        null, null, null, null, null)
        );

        // TODO
        if (getActivity() != null && getActivity() instanceof CheckoutActivity) {
            ((CheckoutActivity) getActivity()).progressDialog.show();
        }

        userViewModel.setUpdateUserObj(user);
    }

    @Override
    public void onResume() {
        super.onResume();
        Utils.psLog("onResume Address");
    //    addressLists = new ArrayList<>();
        addressLists.clear();
        loadAddress();
    }

    private void loadAddress() {

     //   Utils.psLog("user : "+pref.getString(Constants.USER_ID,""));
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, ServiceNames.GET_ADDRESS+pref.getString(Constants.USER_ID,""), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        pDialog.dismiss();
                        Log.d("TAG", "response : " + jsonObject);
//                        try {
//                            if (jsonObject.optString("status").equals("success")) {
//                                JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                                try {
                                    JSONArray jsonarray = jsonObject.getJSONArray("shipping");
                                   // Global.addressCount = jsonarray.length();

                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject c = null;
                                        try {
                                            c = jsonarray.getJSONObject(i);
                                            AddressList addressList = new AddressList(
                                                    c.optString("address_id"),
                                                    c.optString("first_name"),
                                                    c.optString("last_name"),
                                                    c.optString("phone"),
                                                    c.optString("alt_phone"),
                                                    c.optString("email"),
                                                    c.optString("address_1"),
                                                    c.optString("address_1"),
                                                    c.optString("postal_code"),
                                                    c.optString("city"),
                                                    c.optString("state"),
                                                    c.optString("country"),
                                                    c.optString("type")

                                            );

                                            addressLists.add(addressList);
                                            mAdapter.notifyDataSetChanged();

//                                            if (Global.addressCount == 1){
//                                                makeDefault(c.optString("address_id"));
//                                            }

//                                            if (defaultAddress.equals(c.optString("address_id"))){
//                                                String name = c.optString("firstname")+" "+c.optString("lastname");
//                                                prf.setString("shipping_id",defaultAddress);
//                                                prf.setString("shipping_name",name);
//                                                prf.setString("shipping_address",c.optString("address_1"));
//                                                prf.setString("shipping_postcode",c.optString("postcode"));
//                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(getContext(), "01error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


    @Override
    protected void initUIAndActions() {

    }

    @Override
    protected void initViewModels() {
        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
    }

    @Override
    protected void initAdapters() {

    }

    @Override
    protected void initData() {
        userViewModel.setLoginUser();
        userViewModel.getLoginUser().observe(this, listResource -> {
            // we don't need any null checks here for the adapter since LiveData guarantees that
            // it won't call us if fragment is stopped or not started.
            if (listResource != null && listResource.size() > 0) {

                //fadeIn Animation
              //  AddressSelectionFragment.this.fadeIn(binding.get().getRoot());

                userViewModel.user = listResource.get(0).user;
                //Utils.psLog("ADDRESS :" +userViewModel.user.shippingAddress1);
                userViewModel.countryId = userViewModel.user.country.id;
                if (getActivity() != null) {

                    ((CheckoutActivity) AddressSelectionFragment.this.getActivity()).setCurrentUser(userViewModel.user);
                    AddressSelectionFragment.this.initUIAndActions();
                }

            }
        });

        userViewModel.getUpdateUserData().observe(this, listResource -> {

            if (listResource != null) {

                switch (listResource.status) {
                    case SUCCESS:
                        // Success State
                        // Data are from Server

                        if (listResource.data != null) {

                            userViewModel.setLoadingState(false);
                            // TODO
                            if (AddressSelectionFragment.this.getActivity() != null && AddressSelectionFragment.this.getActivity() instanceof CheckoutActivity) {
                                ((CheckoutActivity) AddressSelectionFragment.this.getActivity()).progressDialog.hide();
                            }
                            userViewModel.setLoginUser();
                            //Toast.makeText(getActivity(), getString(R.string.success), Toast.LENGTH_SHORT).show();
                            if (AddressSelectionFragment.this.getActivity() != null) {
                                ((CheckoutActivity) AddressSelectionFragment.this.getActivity()).navigateFragment(((CheckoutActivity) AddressSelectionFragment.this.getActivity()).binding, 2);
                            }

                        }
                        break;

                    case ERROR:
                        // Error State

                        if (AddressSelectionFragment.this.getActivity() != null) {
                            ((CheckoutActivity) AddressSelectionFragment.this.getActivity()).number = 1;
                        }

//                        psDialogMsg.showErrorDialog(listResource.message, AddressSelectionFragment.this.getString(R.string.app__ok));
//                        psDialogMsg.show();

                      //  psDialogMsg.okButton.setOnClickListener(v -> psDialogMsg.cancel());

                        // TODO
                        if (AddressSelectionFragment.this.getActivity() != null && AddressSelectionFragment.this.getActivity() instanceof CheckoutActivity) {
                            ((CheckoutActivity) AddressSelectionFragment.this.getActivity()).progressDialog.hide();
                        }

                        userViewModel.setLoadingState(false);
                        break;
                    default:
                        break;
                }

            }
        });
    }
}