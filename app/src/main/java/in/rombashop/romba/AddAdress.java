package in.rombashop.romba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import in.rombashop.romba.net.MySingleton;
import in.rombashop.romba.net.PrefManager;
import in.rombashop.romba.net.ServiceNames;
import in.rombashop.romba.ui.user.phonelogin.RombaAppCompactActivity;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.MyContextWrapper;
import in.rombashop.romba.utils.Utils;

public class AddAdress extends RombaAppCompactActivity {

    EditText edtFirstName, edtLastName, edtAdd1, edtAdd2, edtCity, edtZip, edtCountry, edtEmail, edtPhone, edtAltPhone, edtState;
    private static PrefManager prf;
    private ProgressDialog pDialog;
    String addType = "shipping";
    String address_id, zone_id;
    Button addAddress;
    int id;
    Intent intent;
    @Inject
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adress);

        prf = new PrefManager(this);
        edtFirstName = findViewById(R.id.edt_firstName);
        edtLastName = findViewById(R.id.edt_lastname);
        edtAdd1 = findViewById(R.id.edt_address_one);
        edtAdd2 = findViewById(R.id.edt_add_two);
        edtCity = findViewById(R.id.edt_city);
        edtZip = findViewById(R.id.edt_postal);
        edtCountry = findViewById(R.id.edt_country);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);
        edtState = findViewById(R.id.edt_state);
        edtAltPhone = findViewById(R.id.edt_alt_phone);

        addAddress = findViewById(R.id.btn_add_address);

        address_id = getIntent().getStringExtra("address_id");

        if (!address_id.equals("new")){

            String firstname = getIntent().getStringExtra("firstname");
            String lastname = getIntent().getStringExtra("lastname");
            String phone = getIntent().getStringExtra("phone");
            String alt_phone = getIntent().getStringExtra("alt_phone");
            String address_1 = getIntent().getStringExtra("address_1");
            String address_2 = getIntent().getStringExtra("address_2");
            String postcode = getIntent().getStringExtra("postcode");
            String city = getIntent().getStringExtra("city");
            String country_id = getIntent().getStringExtra("country_id");

            edtFirstName.setText(firstname);
            edtLastName.setText(lastname);
            edtPhone.setText(phone);
            edtAltPhone.setText(alt_phone);
            edtAdd1.setText(address_1);
            edtAdd2.setText(address_2);
            edtCity.setText(city);
            edtZip.setText(postcode);
            edtCountry.setText(country_id);
            addAddress.setText("UPDATE ADDRESS");

        }

        final String type = getIntent().getStringExtra("type");
        assert type != null;
        if (type.equals("0"))
            addType = "shipping";
        else
            addType = "billing";

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("2")){
                    update();
                }else {
                    add();
                }
            }
        });

    }

    @Override
    protected void initUIAndActions() {

    }

    @Override
    protected void initViewModels() {

    }

    @Override
    protected void initAdapters() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        String CURRENT_LANG_CODE = preferences.getString(Constants.LANGUAGE_CODE, Config.DEFAULT_LANGUAGE);
        String CURRENT_LANG_COUNTRY_CODE = preferences.getString(Constants.LANGUAGE_COUNTRY_CODE, Config.DEFAULT_LANGUAGE_COUNTRY_CODE);

        super.attachBaseContext(MyContextWrapper.wrap(newBase, CURRENT_LANG_CODE, CURRENT_LANG_COUNTRY_CODE, true));
    }

    public void backPress(View view) {
        startActivity(new Intent(getApplicationContext(), AddressActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Utils.psLog("user : "+pref.getString(Constants.USER_ID,""));
        startActivity(new Intent(getApplicationContext(), AddressActivity.class));
        finish();
    }

    public void add() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Adding Shipping Address...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServiceNames.ADD_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();

                        startActivity(new Intent(getApplicationContext(), AddressActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        try {
                            String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                            JSONObject jsonObject = new JSONObject(responseBody);
                            String message = jsonObject.optString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            //Handle a malformed json response
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String a = edtAdd2.getText().toString().trim();
                if (a.equals(""))
                    a = "none";

                Map<String, String> data = new HashMap<String, String>();
                data.put("first_name",edtFirstName.getText().toString().trim());
                data.put("last_name",edtLastName.getText().toString().trim());
                data.put("city",edtCity.getText().toString().trim());
                data.put("address_1",edtAdd1.getText().toString().trim());
                data.put("address_2",a);
                data.put("state",edtState.getText().toString().trim());
                data.put("country",edtCountry.getText().toString().trim());
                data.put("postal_code",edtZip.getText().toString().trim());
                data.put("email","example@abc.com");
                data.put("phone",edtPhone.getText().toString().trim());
                data.put("alt_phone",edtAltPhone.getText().toString().trim());
                data.put("type",addType);
                data.put("user_id",pref.getString(Constants.USER_ID,""));
                return data;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }

    public void update() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Adding Shipping Address...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServiceNames.ADD_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();

                        Intent intent =new Intent(getApplicationContext(), AddressActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        try {
                            String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                            JSONObject jsonObject = new JSONObject(responseBody);
                            String message = jsonObject.optString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            //Handle a malformed json response
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                String a = edtAdd2.getText().toString().trim();
                if (a.equals(""))
                    a = "none";

                Map<String, String> data = new HashMap<String, String>();
                data.put("address_id",address_id);
                data.put("first_name",edtFirstName.getText().toString().trim());
                data.put("last_name",edtLastName.getText().toString().trim());
                data.put("city",edtCity.getText().toString().trim());
                data.put("address_1",edtAdd1.getText().toString().trim());
                data.put("address_2",a);
                data.put("state",edtState.getText().toString().trim());
                data.put("country",edtCountry.getText().toString().trim());
                data.put("postal_code",edtZip.getText().toString().trim());
                data.put("email","example@abc.com");
                data.put("phone",edtPhone.getText().toString().trim());
                data.put("alt_phone",edtAltPhone.getText().toString().trim());
                data.put("type",addType);
                data.put("user_id",prf.getString("user_id"));
                return data;
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