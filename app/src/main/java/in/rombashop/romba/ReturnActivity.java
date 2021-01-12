package in.rombashop.romba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.rombashop.romba.net.MySingleton;
import in.rombashop.romba.net.PrefManager;
import in.rombashop.romba.net.ServiceNames;
import in.rombashop.romba.ui.transaction.list.TransactionListActivity;

public class ReturnActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static PrefManager prf;
    private String returnReasonId="1";
    private String packetOpened="1";
    EditText edtMessage;
    private boolean pactStatus = false;
    private boolean returnReason = false;
    private static final String TAG_USERID = "customer_id";
    private static final String TAG_USERNAME = "firstname";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_MOBILE = "telephone";
    String orderId, userId, productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        prf = new PrefManager(this);
        orderId = getIntent().getStringExtra("order_id");
        userId = getIntent().getStringExtra("user_id");
        productId = getIntent().getStringExtra("product_id");

        Log.d("MATRIXDEV", orderId+" - "+ userId +" - "+ productId);
        edtMessage = findViewById(R.id.edt_message);

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        if (checked)
            returnReason = true;
        switch(view.getId()) {
            case R.id.one:
                if (checked)
                    returnReasonId = "Dead On Arrival";
                    break;
            case R.id.two:
                if (checked)
                    returnReasonId = "Received Wrong Item";
                    break;
            case R.id.three:
                if (checked)
                    returnReasonId = "Order Error";
                break;
            case R.id.four:
                if (checked)
                    returnReasonId = "Faulty, please supply details";
                break;
            case R.id.five:
                if (checked)
                    returnReasonId = "Other, please supply details";
                break;
        }
    }

    public void onRadioStatusClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        if (checked)
            pactStatus = true;
        switch(view.getId()) {
            case R.id.open:
                if (checked)
                    packetOpened = "yes";
                break;
            case R.id.packed:
                if (checked)
                    packetOpened = "no";
                break;

        }
    }

    public void makeReturnReq(View view) {
        if (pactStatus){
            if (returnReason){
                submitRequest();
            }else {
                Toast.makeText(this, "Please select any reason to return", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "Please select packet status", Toast.LENGTH_LONG).show();
        }
    }

    private void submitRequest() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        JSONObject data= new JSONObject();
        try {
            data.put("order_id", orderId);
            data.put("product_id", productId);
            data.put("return_reason", returnReasonId);
            data.put("opened", packetOpened);
            data.put("comment", edtMessage.getText());
            data.put("user_id", userId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, ServiceNames.RETURN, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        pDialog.dismiss();

                        Toast.makeText(ReturnActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), TransactionListActivity.class));
                        finish();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(ReturnActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
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

}