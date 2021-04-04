package in.rombashop.romba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.rombashop.romba.net.MySingleton;
import in.rombashop.romba.net.PrefManager;

public class cartPayAddressListAdapter extends RecyclerView.Adapter<cartPayAddressListAdapter.MyViewHolder> {

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

    public cartPayAddressListAdapter(Context context, List<AddressList> moviesList) {
        ctx = context;
        this.moviesList = moviesList;

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

        holder.name.setText(ongoing.getFirstname()+" "+ ongoing.getLastlast());
        holder.address.setText(ongoing.getAdd1()+", "+ongoing.getCity());
        holder.pincode.setText(ongoing.getPincode());
        if (prf.getString("payment_id").equals(ongoing.getId()))
            selectedPosition = position;

        holder.checkBox.setChecked(selectedPosition == position);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                prf.setString("payment_id", String.valueOf(ongoing.getId()));
                makeDefault(String.valueOf(ongoing.getId()));
                notifyDataSetChanged();
            }
        });

    }

    private void makeDefault(String key) {

        JSONObject data= new JSONObject();
        try {
            data.put("address_id", key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, "ServiceNames.EXISTING_PAY_ADDRESS", data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                   //     pDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   pDialog.dismiss();
                Toast.makeText(ctx, "error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("X-Oc-Merchant-Id", prf.getString("s_key"));
                headers.put("X-Oc-Session", prf.getString("session"));
                return headers;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(ctx).addToRequestQueue(stringRequest);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
