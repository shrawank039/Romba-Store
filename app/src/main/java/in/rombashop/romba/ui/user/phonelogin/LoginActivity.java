package in.rombashop.romba.ui.user.phonelogin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import in.rombashop.romba.Config;
import in.rombashop.romba.R;
import in.rombashop.romba.net.PrefManager;
import in.rombashop.romba.ui.user.MobileVerifyActivity;
import in.rombashop.romba.ui.user.RegisterActivity;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.MyContextWrapper;
import in.rombashop.romba.utils.PSDialogMsg;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewmodel.user.UserViewModel;
import in.rombashop.romba.viewobject.User;

public class LoginActivity extends RombaAppCompactActivity {

    // Progress Dialog
    private ProgressDialog pDialog;
    String activity;

    private UserViewModel userViewModel;

    private ArrayList<HashMap<String, String>> offersList;


    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    //user
    private static final String TAG_USERID = "user_id";
    private static final String TAG_USERNAME = "user_name";
    private static final String TAG_EMAIL = "user_email";
    private static final String TAG_MOBILE = "user_phone";
    private static final String TAG_PASSWORD = "user_password";

    //balance
    private static final String TAG_USERBALANCE = "balance";

    //matchdetail
    private static final String TAG_WONAMOUNT = "wonamount";

    // products JSONArray
    private final JSONArray jsonarray = null;

    //Prefrance
    private static PrefManager prf;

    //Textbox
    private EditText phone;
    private EditText password;
    private Button signup;
    private Button signin;
    private TextView resetnow;
    private PSDialogMsg psDialogMsg;

    private String success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prf = new PrefManager(LoginActivity.this);
      //  pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Hashmap for ListView
        offersList = new ArrayList<>();


        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        resetnow = (TextView) findViewById(R.id.resetnow);
        activity = getIntent().getStringExtra("activity");

                pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Loading Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        signup = (Button) findViewById(R.id.registerFromLogin);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        signin = (Button) findViewById(R.id.signinbtn);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkdetails()) {
                    loginSubmit();
                }
            }
        });


        resetnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(LoginActivity.this,"Email us from your registed email id",Toast.LENGTH_LONG).show();
                // Loading offers in Background Thread
                Intent intent = new Intent(LoginActivity.this, MobileVerifyActivity.class);
                intent.putExtra(Constants.USER_PASSWORD, Constants.USER_PASSWORD);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initUIAndActions() {
        psDialogMsg = new PSDialogMsg(this, false);
        //prgDialog.get().setMessage(getString(R.string.message__please_wait));
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

     //   token = pref.getString(Constants.NOTI_TOKEN, Constants.USER_NO_DEVICE_TOKEN);

        userViewModel.getLoadingState().observe(this, loadingState -> {

            if (loadingState != null && loadingState) {
                pDialog.show();
            } else {
                pDialog.dismiss();
            }
//
//            updateLoginBtnStatus();

        });

        userViewModel.getUserLoginStatus().observe(this, listResource -> {

            if (listResource != null) {

                Utils.psLog("Got Data" + listResource.message + listResource.toString());

                switch (listResource.status) {
                    case LOADING:
                        // Loading State
                        // Data are from Local DB

                        break;
                    case SUCCESS:
                        // Success State
                        // Data are from Server

                        if (listResource.data != null) {
                            try {

                                Utils.updateUserLoginData(pref, listResource.data.user);
                                Utils.navigateAfterUserLogin(this,navigationController);

                            } catch (NullPointerException ne) {
                                Utils.psErrorLog("Null Pointer Exception.", ne);
                            } catch (Exception e) {
                                Utils.psErrorLog("Error in getting notification flag data.", e);
                            }

                            userViewModel.setLoadingState(false);

                        }

                        break;
                    case ERROR:
                        // Error State

                        psDialogMsg.showErrorDialog(listResource.message, getString(R.string.app__ok));
                        psDialogMsg.show();

                        userViewModel.setLoadingState(false);

                        break;
                    default:
                        // Default

                        userViewModel.setLoadingState(false);

                        break;
                }

            } else {

                // Init Object or Empty Data
                Utils.psLog("Empty Data");

            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        String CURRENT_LANG_CODE = preferences.getString(Constants.LANGUAGE_CODE, Config.DEFAULT_LANGUAGE);
        String CURRENT_LANG_COUNTRY_CODE = preferences.getString(Constants.LANGUAGE_COUNTRY_CODE, Config.DEFAULT_LANGUAGE_COUNTRY_CODE);

        super.attachBaseContext(MyContextWrapper.wrap(newBase, CURRENT_LANG_CODE, CURRENT_LANG_COUNTRY_CODE, true));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean checkdetails() {

        if (phone.getText().toString().trim().isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(phone.getText().toString().trim()).matches()) {
            Toast.makeText(LoginActivity.this, "Enter Value for E-mail", Toast.LENGTH_SHORT).show();
            phone.requestFocus();
            return false;
        } else if (password.getText().toString().trim().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Enter Value for Password", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return false;
        }


        return true;
    }


    private void loginSubmit() {

        doSubmit(phone.getText().toString().trim(), password.getText().toString().trim());

//        pDialog = new ProgressDialog(LoginActivity.this);
//        pDialog.setMessage("Loading Please wait...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServiceNames.USER_LOGIN,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        pDialog.dismiss();
//                        JSONObject data = null;
//                        try {
//                            data = new JSONObject(response);
//
//                            Gson gson = new Gson();
//                            User user;
//                            user = gson.fromJson(response,User.class);
//
//                            doSubmit(phone.getText().toString().trim(), password.getText().toString().trim());
//
//                                prf.setString(TAG_USERID, data.optString(TAG_USERID));
//                                prf.setString(TAG_MOBILE, data.optString("user_phone"));
//
//                                Utils.updateUserLoginData(pref, user);
//                                Utils.navigateAfterUserLogin(LoginActivity.this,navigationController);
//
//                       //         Toast.makeText(LoginActivity.this, "Sign in done Succsessfully", Toast.LENGTH_LONG).show();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        pDialog.dismiss();
//                        try {
//                            String responseBody = null;
//                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                                responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
//                            }
//                            JSONObject jsonObject = new JSONObject(responseBody);
//                            String message = jsonObject.optString("message");
//                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//                        } catch (JSONException e) {
//                            //Handle a malformed json response
//                        }
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put(TAG_MOBILE, phone.getText().toString().trim());
//                params.put(TAG_PASSWORD, password.getText().toString().trim());
//                return params;
//            }
//
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                15000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        stringRequest.setShouldCache(false);
//        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void doSubmit(String email, String password) {

        //prgDialog.get().show();
        userViewModel.setUserLogin(new User(
                "",
                "",
                "",
                "",
                "",
                email,
                email,
                "",
                password,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                null,
                null
        ));

        userViewModel.isLoading = true;

    }

}
