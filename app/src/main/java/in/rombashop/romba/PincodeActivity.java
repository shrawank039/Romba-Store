package in.rombashop.romba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.rombashop.romba.net.MySingleton;
import in.rombashop.romba.net.PrefManager;
import in.rombashop.romba.net.ServiceNames;
import in.rombashop.romba.ui.basket.BasketListActivity;

public class PincodeActivity extends AppCompatActivity {

    EditText pinEdt;
    private ProgressDialog pDialog;
    private static PrefManager prf;
    LinearLayout llnoService;
    private final String TAG ="PincodeAct";
    String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincode);
        llnoService = findViewById(R.id.ll_noservice);
        pinEdt = findViewById(R.id.edt_pincode);
        pinEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llnoService.setVisibility(View.GONE);
            }
        });
        prf = new PrefManager(PincodeActivity.this);
        activity = getIntent().getStringExtra("activity");
    }

    public void pinSubmit(View view) {
        final String pin= pinEdt.getText().toString().trim();

            pDialog = new ProgressDialog(PincodeActivity.this);
            pDialog.setMessage("Checking Service...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServiceNames.PINCODE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pDialog.dismiss();
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                                Log.d(TAG, "response : "+response);
                                if (jsonObject.optString("status").equalsIgnoreCase("success")) {

                                    JSONObject msgObject = jsonObject.optJSONObject("message");
                                    JSONObject shippingObj = msgObject.optJSONObject("shipping");
                                    prf.setString("pincode", pin);
                                    prf.setInt("shipping_cost",shippingObj.optInt("shipping_cost"));

                                    if (activity.equalsIgnoreCase("MainActivity")) {
                                        Intent intent = new Intent(PincodeActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }else {
                                        startActivity(new Intent(getApplicationContext(), BasketListActivity.class));
                                        finish();
                                    }

                                } else {
                                    llnoService.setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    llnoService.setVisibility(View.VISIBLE);
                   // Toast.makeText(PincodeActivity.this, "error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("pincode", pin);
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

    }

}