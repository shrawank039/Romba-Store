package in.rombashop.romba;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.rombashop.romba.net.MySingleton;
import in.rombashop.romba.net.PrefManager;
import in.rombashop.romba.net.ServiceNames;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder> {

    private final Context ctx;

    private List<AddressList> moviesList;
    int priceA;
    int priceB;
    private static PrefManager prf;

    // private ProgressDialog pDialog;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView name;
        final TextView address;
        final TextView pincode;
        final TextView defaultTxt, txtMenu;

        MyViewHolder(View view) {
            super(view);

            prf = new PrefManager(ctx);
            name = view.findViewById(R.id.txt_name);
            address = view.findViewById(R.id.txt_address);
            pincode = view.findViewById(R.id.txt_pincode);
            defaultTxt = view.findViewById(R.id.txt_default);
            txtMenu = view.findViewById(R.id.textViewOptions);

        }
    }

    public AddressListAdapter(Context context, List<AddressList> moviesList) {
        ctx = context;
        this.moviesList = moviesList;

       // pageViewModel = ViewModelProviders.of((FragmentActivity) context).get(PageViewModel.class);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_address_list, parent, false);

        return new MyViewHolder(itemView);
    }

    public void setTasks(List<AddressList> personList) {
        moviesList = personList;
        notifyDataSetChanged();
    }

    public List<AddressList> getTasks() {

        return moviesList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AddressList ongoing = moviesList.get(position);

        holder.name.setText(ongoing.getFirstname()+" "+ ongoing.getLastlast());
        holder.address.setText(ongoing.getAdd1()+", "+ongoing.getCity());
        holder.pincode.setText(ongoing.getPincode());
        if (prf.getString("shipping_id").equals(ongoing.getId()))

            holder.defaultTxt.setVisibility(View.VISIBLE);
        else
            holder.defaultTxt.setVisibility(View.GONE);


        holder.txtMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(ctx, holder.txtMenu);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_address);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                view.getContext().startActivity(new Intent(view.getContext(), AddAdress.class)
                                        .putExtra("address_id", ongoing.getAddress_id())
                                        .putExtra("firstname", ongoing.getFirstname())
                                        .putExtra("lastname", ongoing.getLastlast())
                                        .putExtra("phone", ongoing.getPhone())
                                        .putExtra("alt_phone", ongoing.getAlt_phone())
                                        .putExtra("address_1", ongoing.getAdd1())
                                        .putExtra("address_2", ongoing.getAdd2())
                                        .putExtra("postcode", ongoing.getPincode())
                                        .putExtra("city", ongoing.getCity())
                                        .putExtra("country_id", ongoing.getId())
                                        .putExtra("type","2")
                                );
                                return true;
                            case R.id.delete:
                                moviesList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, moviesList.size());
                                deleteItem(String.valueOf(ongoing.getAddress_id()));
                                return true;
//                            case R.id.make_default:
//                                prf.setString("shipping_id", String.valueOf(ongoing.getId()));
//                                prf.setString("shipping_name",ongoing.getFirstname()+" "+ongoing.getLastlast());
//                                prf.setString("shipping_address",ongoing.getAdd1());
//                                prf.setString("shipping_postcode",ongoing.getPincode());
//                                holder.defaultTxt.setVisibility(View.VISIBLE);
//                                makeDefault(String.valueOf(ongoing.getId()));
//                                notifyDataSetChanged();
//                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

            }
        });
    }

    private void deleteItem(final String key) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServiceNames.DELETE_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                            JSONObject jsonObject = new JSONObject(responseBody);
                            String message = jsonObject.optString("message");
                            Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            //Handle a malformed json response
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> data = new HashMap<String, String>();
                data.put("address_id",key);
                data.put("user_id",prf.getString("user_id"));
                return data;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(ctx).addToRequestQueue(stringRequest);
    }

    private void makeDefault(String key) {

        JSONObject data= new JSONObject();
        try {
            data.put("address_id", key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, "ServiceNames.EXISTING_ADDRESS", data,
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
