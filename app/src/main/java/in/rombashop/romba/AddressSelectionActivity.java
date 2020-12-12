package in.rombashop.romba;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

public class AddressSelectionActivity extends AppCompatActivity {

    private static PrefManager prf;
    private ProgressDialog pDialog;
    private List<AddressList> addressLists;
    private List<AddressList> payAddressLists;
    private RecyclerView rv_shipping,rv_payment;
    private cartAddressListAdapter mAdapter;
    private cartPayAddressListAdapter payAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_selection);

        prf = new PrefManager(this);
        addressLists = new ArrayList<>();
        payAddressLists = new ArrayList<>();
        rv_shipping =  findViewById(R.id.rv_shipping);
        rv_payment =  findViewById(R.id.rv_payment);

        mAdapter = new cartAddressListAdapter(getApplicationContext(), addressLists);
        payAdapter = new cartPayAddressListAdapter(getApplicationContext(), addressLists);

        rv_shipping.setHasFixedSize(true);
        rv_payment.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager payLayoutManager = new LinearLayoutManager(this);
        rv_shipping.setLayoutManager(mLayoutManager);
        rv_shipping.setItemAnimator(new DefaultItemAnimator());
        rv_shipping.setAdapter(mAdapter);
        rv_payment.setLayoutManager(payLayoutManager);
        rv_payment.setItemAnimator(new DefaultItemAnimator());
        rv_payment.setAdapter(payAdapter);
        loadAddress();

    }

    private void loadAddress() {
        pDialog = new ProgressDialog(this);
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
                                    JSONArray jsonarray = jsonObject1.getJSONArray("shipping");
                               //     Global.addressCount = jsonarray.length();

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

//
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
                Toast.makeText(getApplicationContext(), "01error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    public void backPress(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void nextClick(View view) {
     //   startActivity(new Intent(getApplicationContext(), ShippingMethod.class));
    }
}