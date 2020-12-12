package in.rombashop.romba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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
import in.rombashop.romba.net.MySingleton;
import in.rombashop.romba.net.PrefManager;
import in.rombashop.romba.net.ServiceNames;


public class PaymentAddressFragment extends Fragment {

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
    private RecyclerView recyclerView;
    private PayAddressListAdapter mAdapter;
    private Button addBtn;

    public PaymentAddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentAddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentAddressFragment newInstance(String param1, String param2) {
        PaymentAddressFragment fragment = new PaymentAddressFragment();
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
        View root = inflater.inflate(R.layout.fragment_payment_address, container, false);
        // Inflate the layout for this fragment
        prf = new PrefManager(getContext());
        addressLists = new ArrayList<>();
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        addBtn = root.findViewById(R.id.btn_add_address);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddAdress.class)
                        .putExtra("type","1"));
                requireActivity().finish();
            }
        });

        mAdapter = new PayAddressListAdapter(getContext(), addressLists);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        loadAddress();
        return root;
    }
    private void loadAddress() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, ServiceNames.GET_ADDRESS+prf.getString("user_id"), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        pDialog.dismiss();
                        Log.d("TAG", "response : " + jsonObject);
                        try {
                            if (jsonObject.optString("status").equals("success")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("message");
                                try {
                                    JSONArray jsonarray = jsonObject1.getJSONArray("billing");
                                //    Global.addressCount = jsonarray.length();

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
}