package in.rombashop.romba;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import in.rombashop.romba.ui.transaction.detail.TransactionActivity;
import in.rombashop.romba.ui.transaction.list.TransactionListActivity;

public class CancelActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private static PrefManager prf;
    private String returnReasonId="Delayed On Arrival";
    EditText edtMessage;
    String orderId, userId;
    private boolean pactStatus = false;
    private boolean returnReason = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel);

        prf = new PrefManager(this);
        orderId = getIntent().getStringExtra("order_id");
        userId = getIntent().getStringExtra("user_id");
        edtMessage = findViewById(R.id.edt_message);

    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        if (checked)
            returnReason = true;
        switch(view.getId()) {
            case R.id.one:
                if (checked)
                    returnReasonId = "Delayed On Arrival";
                break;
            case R.id.two:
                if (checked)
                    returnReasonId = "Ordered Wrong Item";
                break;
            case R.id.three:
                if (checked)
                    returnReasonId = "Order Error";
                break;
            case R.id.four:
                if (checked)
                    returnReasonId = "Other, please supply details";
                break;
        }
    }

    public void makeReturnReq(View view) {
            if (returnReason){
                alertClicked();
            }else {
                Toast.makeText(this, "Please select any reason to return", Toast.LENGTH_LONG).show();
            }
    }

    private void alertClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CancelActivity.this);
        builder.setTitle("Are you sure to cancel this order?")
                .setCancelable(true)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        submitRequest();
                      //  Toast.makeText(getApplicationContext(), "This option is coming soon", Toast.LENGTH_LONG).show();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        alert.show();
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
            data.put("cancel_reason", returnReasonId);
            data.put("comment", edtMessage.getText());
            data.put("user_id", userId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, ServiceNames.CANCEL, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        pDialog.dismiss();

                            Toast.makeText(CancelActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), TransactionListActivity.class));
                            finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(CancelActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
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