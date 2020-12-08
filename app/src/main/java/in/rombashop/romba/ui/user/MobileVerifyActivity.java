package in.rombashop.romba.ui.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import in.rombashop.romba.Config;
import in.rombashop.romba.MainActivity;
import in.rombashop.romba.R;
import in.rombashop.romba.net.MySingleton;
import in.rombashop.romba.net.PrefManager;
import in.rombashop.romba.net.ServiceNames;
import in.rombashop.romba.ui.user.phonelogin.LoginActivity;
import in.rombashop.romba.ui.user.phonelogin.RombaAppCompactActivity;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.MyContextWrapper;
import in.rombashop.romba.utils.PSDialogMsg;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewmodel.user.UserViewModel;
import in.rombashop.romba.viewobject.User;

public class MobileVerifyActivity extends RombaAppCompactActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USERNAME = "firstname";
    private static final String TAG_LASTNAME = "lastname";
    private static final String TAG_EMAIL = "user_email";
    private static final String TAG_MOBILE = "USER_PHONE";
    private static final String TAG_PASSWORD = "password";

    //Textbox
    private String firstname;
    private String lastname;
    private String email;
    private final String TAG="MobileVerifyAct";
    private String mobile;
    private String password;

    private String success;

    private boolean ispass;

    private TextInputEditText newPass;
    private Button resetPassButton;
    private TextInputEditText retypeNewPass;
    private static PrefManager prf;
    private static final String TAG_USERID = "user_id";

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private CountryCodePicker ccp;
    private TextInputEditText phoneed;
    private TextInputEditText codeed;
    private FloatingActionButton fabbutton;
    private String mVerificationId;
    private TextView timertext;
    private Timer timer;
    private ImageView verifiedimg;
    private Boolean mVerified = false;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private UserViewModel userViewModel;
    private PSDialogMsg psDialogMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verify);

        prf = new PrefManager(this);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        phoneed = (TextInputEditText) this.findViewById(R.id.numbered);

        codeed = (TextInputEditText) this.findViewById(R.id.verificationed);
        fabbutton = (FloatingActionButton) findViewById(R.id.sendverifybt);
        timertext = (TextView) findViewById(R.id.timertv);
        verifiedimg = (ImageView) findViewById(R.id.verifiedsign);

        pDialog = new ProgressDialog(MobileVerifyActivity.this);
        pDialog.setMessage("Loading Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        newPass = (TextInputEditText) findViewById(R.id.newpass);
        retypeNewPass = (TextInputEditText) findViewById(R.id.retypeNewPass);
        resetPassButton = (Button) findViewById(R.id.changePassBtn);

        psDialogMsg = new PSDialogMsg(this, false);

        try {
            ispass = Objects.requireNonNull(getIntent().getStringExtra(Constants.USER_PASSWORD)).contains(Constants.USER_PASSWORD);
            if(!ispass) {
//                firstname = getIntent().getStringExtra(TAG_USERNAME);
//                lastname = getIntent().getStringExtra(TAG_LASTNAME);
//                email = getIntent().getStringExtra(TAG_EMAIL);
                mobile = getIntent().getStringExtra(Constants.USER_PHONE);
                password = getIntent().getStringExtra(Constants.USER_PASSWORD);
            } else {
                fabbutton.setTag("reset");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        if(!ispass) {
            phoneed.setText(mobile);
        } else {
            phoneed.setEnabled(true);
        }

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Snackbar snackbar = Snackbar
                            .make((ConstraintLayout) findViewById(R.id.parentlayout), "Verification Failed !! Invalied verification Code", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
                else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar snackbar = Snackbar
                            .make((ConstraintLayout) findViewById(R.id.parentlayout), "Verification Failed !! Too many request. Try after some time. ", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
        fabbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          //      Toast.makeText(MobileVerifyActivity.this, fabbutton.getTag().toString(), Toast.LENGTH_SHORT).show();
                if (fabbutton.getTag().equals("send")) {
                    if (!phoneed.getText().toString().trim().isEmpty() && phoneed.getText().toString().trim().length() >= 10) {
                      //  Toast.makeText(MobileVerifyActivity.this, ccp.getSelectedCountryCodeWithPlus()+phoneed.getText().toString().trim(), Toast.LENGTH_SHORT).show();
//                        startPhoneNumberVerification(ccp.getSelectedCountryCodeWithPlus()+phoneed.getText().toString().trim());
//                        mVerified = false;
//                        starttimer();
//                        codeed.setVisibility(View.VISIBLE);
//                        fabbutton.setImageResource(R.drawable.ic_arrow_forward_white_24dp);
//                        fabbutton.setTag("verify");
                        checkNumber();
                    }
                    else {
                        phoneed.setError("Please enter valid mobile number");
                    }
                }

                if (fabbutton.getTag().equals("reset")) {
                    if (!phoneed.getText().toString().trim().isEmpty() && phoneed.getText().toString().trim().length() >= 10) {
                        checkNumberAvailability();
                    }
                    else {
                        phoneed.setError("Please enter valid mobile number");
                    }
                }

                if (fabbutton.getTag().equals("verify")) {
                    if (!Objects.requireNonNull(codeed.getText()).toString().trim().isEmpty() && !mVerified) {
                        Snackbar snackbar = Snackbar
                                .make((ConstraintLayout) findViewById(R.id.parentlayout), "Please wait...", Snackbar.LENGTH_LONG);

                        snackbar.show();
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, codeed.getText().toString().trim());
                        signInWithPhoneAuthCredential(credential);
                    }
                    if (mVerified) {
                        if(!ispass) {
                           // new OneLoadAllProducts().execute();
                            Log.d(TAG,"Verified : registerSubmit()");
                            registerSubmit();
                        } else {
                            ((LinearLayout) findViewById(R.id.entermobile)).setVisibility(View.GONE);
                            ((LinearLayout) findViewById(R.id.resetpass)).setVisibility(View.VISIBLE);
                        }
                    }

                }


            }
        });

        resetPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newPass.getText().length()>1 && retypeNewPass.getText().length()>1) {
                    if (newPass.getText().toString().equals(retypeNewPass.getText().toString())) {
                        forgotPost();
                    } else {
                        Toast.makeText(MobileVerifyActivity.this, "NewPassword And RetypePass is not Same", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MobileVerifyActivity.this, "Please enter value for all field", Toast.LENGTH_SHORT).show();
                }

            }
        });

        timertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!phoneed.getText().toString().trim().isEmpty() && phoneed.getText().toString().trim().length() == 10) {
                    resendVerificationCode(phoneed.getText().toString().trim(), mResendToken);
                    mVerified = false;
                    starttimer();
                    codeed.setVisibility(View.VISIBLE);
                    fabbutton.setImageResource(R.drawable.ic_arrow_forward_white_24dp);
                    fabbutton.setTag("verify");
                    Snackbar snackbar = Snackbar
                            .make((ConstraintLayout) findViewById(R.id.parentlayout), "Resending verification code...", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }
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
    protected void initUIAndActions() {

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
        userViewModel.getRegisterUser().observe(this, listResource -> {

            if (listResource != null) {

                Utils.psLog("Got Data" + listResource.message + listResource.toString());

                switch (listResource.status) {
                    case LOADING:
                        // Loading State
                        // Data are from Local DB
                        pDialog.show();
                        break;
                    case SUCCESS:
                        // Success State
                        // Data are from Server

                        if(listResource.data != null) {
                            try {
                                Utils.registerUserLoginData(pref,listResource.data, password);
                                Utils.navigateAfterUserRegister(this,navigationController);

                            } catch (NullPointerException ne) {
                                Utils.psErrorLog("Null Pointer Exception.", ne);
                            } catch (Exception e) {
                                Utils.psErrorLog("Error in getting notification flag data.", e);
                            }

                            userViewModel.isLoading = false;
pDialog.dismiss();

                        }

                        break;
                    case ERROR:
                        // Error State

                        psDialogMsg.showWarningDialog(listResource.message, getString(R.string.app__ok));
                        psDialogMsg.show();

                        userViewModel.isLoading = false;
                        pDialog.dismiss();

                        break;
                    default:
                        // Default

                        break;
                }

            } else {

                // Init Object or Empty Data
                Utils.psLog("Empty Data");


            }


            // we don't need any null checks here for the adapter since LiveData guarantees that
            // it won't call us if fragment is stopped or not started.
            if (listResource != null) {
                Utils.psLog("Got Data Of About Us.");

            } else {
                //noinspection Constant Conditions
                Utils.psLog("No Data of About Us.");
            }
        });
    }

    private void forgotPost() {
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServiceNames.FORGOT_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.d(TAG, "response : " + response);

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if (jsonObject.optString("status").equals("success")) {

                                JSONObject jsonObj = jsonObject.optJSONObject("message");
                                Toast.makeText(MobileVerifyActivity.this, jsonObj.optString("msg"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class)
                                        .putExtra("activity","MainActivity"));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "03 error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", phoneed.getText().toString().trim());
                params.put("password", newPass.getText().toString().trim());
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

    private void checkNumberAvailability() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServiceNames.USER_CHECK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.d(TAG, "response : " + response);

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if (jsonObject.optString("status").equals("success")) {

                                if (fabbutton.getTag().equals("reset")) {
                                    startPhoneNumberVerification(ccp.getSelectedCountryCodeWithPlus() + phoneed.getText().toString().trim());
                                    mVerified = false;
                                    starttimer();
                                    codeed.setVisibility(View.VISIBLE);
                                    fabbutton.setImageResource(R.drawable.ic_arrow_forward_white_24dp);
                                    fabbutton.setTag("verify");

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
                Toast.makeText(getApplicationContext(), "This user is not registered!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone", phoneed.getText().toString().trim());
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

    private void checkNumber() {
        pDialog.show();

    StringRequest stringRequest = new StringRequest(Request.Method.POST, ServiceNames.USER_CHECK,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    Log.d(TAG, "response : " + response);

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        if (jsonObject.optString("status").equals("success")) {
                            Toast.makeText(MobileVerifyActivity.this, "This number already exists. Please user different phone.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            pDialog.dismiss();
            startPhoneNumberVerification(ccp.getSelectedCountryCodeWithPlus()+phoneed.getText().toString().trim());
            mVerified = false;
            starttimer();
            codeed.setVisibility(View.VISIBLE);
            fabbutton.setImageResource(R.drawable.ic_arrow_forward_white_24dp);
            fabbutton.setTag("verify");
          //  Toast.makeText(getApplicationContext(), "This user is not registered!", Toast.LENGTH_SHORT).show();
        }
    }) {
        @Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();
            params.put("phone", phoneed.getText().toString().trim());
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

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            mVerified = true;
                            timer.cancel();
                            verifiedimg.setVisibility(View.VISIBLE);
                            timertext.setVisibility(View.INVISIBLE);
                            phoneed.setEnabled(false);
                            ((TextInputLayout) findViewById(R.id.enterotp)).setVisibility(View.GONE);
                            codeed.setVisibility(View.INVISIBLE);
                            Snackbar snackbar = Snackbar
                                    .make((ConstraintLayout) findViewById(R.id.parentlayout), "Successfully Verified", Snackbar.LENGTH_LONG);

                            snackbar.show();
                            if (mVerified) {
                                if(!ispass) {
                                    Log.d(TAG,"Verified : registerSubmit()");
                                 //   registerSubmit();
                                    registerUser();
                                } else {
                                    ((LinearLayout) findViewById(R.id.entermobile)).setVisibility(View.GONE);
                                    ((LinearLayout) findViewById(R.id.resetpass)).setVisibility(View.VISIBLE);
                                }
                            }
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Snackbar snackbar = Snackbar
                                        .make((ConstraintLayout) findViewById(R.id.parentlayout), "Invalid OTP ! Please enter correct OTP", Snackbar.LENGTH_LONG);

                                snackbar.show();
                            }
                        }
                    }
                });
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

    }

    public void starttimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {

            int second = 60;

            @Override
            public void run() {
                if (second <= 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timertext.setText("RESEND CODE");
                            timer.cancel();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timertext.setText("00:" + second--);
                        }
                    });
                }

            }
        }, 0, 1000);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void registerSubmit() {
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServiceNames.USER_REGISTRATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            success = jsonObject.optString("status");
                            if (success.equals("success")) {
                                JSONObject data = jsonObject.optJSONObject("message");
                                // preference and set username for session

                                prf.setString(TAG_USERID, data.optString(TAG_USERID));
                                prf.setString(TAG_USERNAME, data.optString(TAG_USERNAME));
                                prf.setString(TAG_EMAIL, data.optString(TAG_EMAIL));
                                prf.setString(TAG_MOBILE, data.optString("user_phone"));

                                startActivity(new Intent(getApplicationContext(), MainActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                finish();

                                  }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                Map<String, String> params = new HashMap<String, String>();
                params.put(TAG_MOBILE, mobile);
                params.put(TAG_PASSWORD, password);
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

    private void resetSubmit() {
        pDialog = new ProgressDialog(MobileVerifyActivity.this);
        pDialog.setMessage("Loading Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "ServiceNames.CHANGE_PASS",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        if (success.equals("1")) {
                            // offers found
                            // Getting Array of offers

                            Intent intent = new Intent(MobileVerifyActivity.this, LoginActivity.class);
                            intent.putExtra("activity","MainActivity");
                            startActivity(intent);

                            Toast.makeText(MobileVerifyActivity.this,"Password changed Succsessfully", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(MobileVerifyActivity.this,"Something went wrong.Please try again!", Toast.LENGTH_LONG).show();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put(TAG_MOBILE, phoneed.getText().toString().trim());
                params.put(TAG_PASSWORD, newPass.getText().toString());
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void registerUser() {

        Utils.hideKeyboard(this);


      //  String userEmail = binding.get().emailEditText.getText().toString().trim();
        if (mobile.equals("")) {

            psDialogMsg.showWarningDialog( getString(R.string.error_message__blank_email),getString(R.string.app__ok));

            psDialogMsg.show();
            return;
        }

      //  String userPassword = binding.get().passwordEditText.getText().toString().trim();
        if (password.equals("")) {

            psDialogMsg.showWarningDialog(getString(R.string.error_message__blank_password), getString(R.string.app__ok));

            psDialogMsg.show();
            return;
        }


        userViewModel.isLoading = true;
      //  updateRegisterBtnStatus();

        String token = pref.getString(Constants.NOTI_TOKEN,Constants.USER_NO_DEVICE_TOKEN);
        userViewModel.setRegisterUser(new User(
                "",
                "",
                "",
                "",
                "",
                "",
                mobile,
                mobile,
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
                token,
                "",
                "",
                "",null,null));

    }


}
