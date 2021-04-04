package in.rombashop.romba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.rombashop.romba.net.PrefManager;
import in.rombashop.romba.net.ServiceNames;

public class cartAddressListAdapter extends RecyclerView.Adapter<cartAddressListAdapter.MyViewHolder> {

    private final Context ctx;

    private final List<AddressList> moviesList;
    private int selectedPosition;
    private static PrefManager prf;
    // private ProgressDialog pDialog;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView name;
        final TextView address;
        final TextView pincode;
        final CheckBox checkBox;

        MyViewHolder(View view) {
            super(view);

            prf = new PrefManager(ctx);
            name = view.findViewById(R.id.txt_name);
            address = view.findViewById(R.id.txt_address);
            pincode = view.findViewById(R.id.txt_pincode);
            checkBox = view.findViewById(R.id.radio_btn);

        }
    }

    public cartAddressListAdapter(Context context, List<AddressList> moviesList) {
        ctx = context;
        this.moviesList = moviesList;

        // pageViewModel = ViewModelProviders.of((FragmentActivity) context).get(PageViewModel.class);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_cart_address_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AddressList ongoing = moviesList.get(position);

        holder.name.setText(ongoing.getFirstname() + " " + ongoing.getLastlast());
        holder.address.setText(ongoing.getAdd1() + ", " + ongoing.getCity());
        holder.pincode.setText(ongoing.getPincode());
        if (prf.getString("shipping_id").equals(ongoing.getId()))
            selectedPosition = position;

        if (selectedPosition == position) {
            holder.checkBox.setChecked(true);
            ServiceNames.addressList = ongoing;
//            prf.setString("shipping_id", String.valueOf(ongoing.getId()));
//            prf.setString("shipping_name", ongoing.getFirstname() + " " + ongoing.getLastlast());
//            prf.setString("shipping_address", ongoing.getAdd1());
//            prf.setString("shipping_postcode", ongoing.getPincode());
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                ServiceNames.addressList = ongoing;
//                prf.setString("shipping_id", String.valueOf(ongoing.getId()));
//                prf.setString("shipping_name", ongoing.getFirstname() + " " + ongoing.getLastlast());
//                prf.setString("shipping_address", ongoing.getAdd1());
//                prf.setString("shipping_postcode", ongoing.getPincode());
              //  makeDefault(String.valueOf(ongoing.getId()));
                notifyDataSetChanged();
            }
        });

    }


    private void makeDefault(final String key) {


//        JSONObject data = new JSONObject();
//        try {
//            data.put("address_id", key);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, ServiceNames.EXISTING_ADDRESS, data,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        makePayDefault(key);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //   pDialog.dismiss();
//                Toast.makeText(ctx, "error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                headers.put("X-Oc-Merchant-Id", prf.getString("s_key"));
//                headers.put("X-Oc-Session", prf.getString("session"));
//                return headers;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        stringRequest.setShouldCache(false);
//        MySingleton.getInstance(ctx).addToRequestQueue(stringRequest);
//    }
//
//    private void makePayDefault(String key) {
//
//        JSONObject data= new JSONObject();
//        try {
//            data.put("address_id", key);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, ServiceNames.EXISTING_PAY_ADDRESS, data,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//                        //     pDialog.dismiss();
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //   pDialog.dismiss();
//                Toast.makeText(ctx, "error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                headers.put("X-Oc-Merchant-Id", prf.getString("s_key"));
//                headers.put("X-Oc-Session", prf.getString("session"));
//                return headers;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        stringRequest.setShouldCache(false);
//        MySingleton.getInstance(ctx).addToRequestQueue(stringRequest);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
