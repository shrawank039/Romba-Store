package in.rombashop.romba.ui.common;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import in.rombashop.romba.Config;
import in.rombashop.romba.R;
import in.rombashop.romba.utils.Connectivity;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.Utils;

/**
 * Parent Activity class of all activity in this project.
 * Created by Panacea-Soft on 12/2/17.
 * Contact Email : teamps.is.cool@gmail.com
 */

public abstract class PSAppCompactActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    //region Variables
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;


    @Inject
    protected NavigationController navigationController;

    @Inject
    protected Connectivity connectivity;

    @Inject
    protected SharedPreferences pref;

    protected String loginUserId;

    protected String shippingId;

    protected String shippingTax;

    protected String overAllTax;

    protected String shippingTaxLabel;

    protected String overAllTaxLabel;

    protected String versionNo;

    protected Boolean force_update = false;

    protected String force_update_msg;

    protected String force_update_title;

    private boolean isFadeIn = false;

    protected String cod, paypal, stripe, messenger, whatsappNo;

    protected String consent_status;

    protected String userEmailToVerify, userPasswordToVerify, userNameToVerify, userIdToVerify;

    protected String shopId, shopPhoneNumber, shopStandardShippingEnable, shopNoShippingEnable, shopZoneShippingEnable;
    //endregion


    //endregion


    //region Override Methods

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadLoginUserId();
    }

    //endregion

    //region Toolbar Init

    protected Toolbar initToolbar(Toolbar toolbar, String title, int color) {

        if(toolbar != null) {

            toolbar.setTitle(title);

            if(color != 0) {
                try {
                    toolbar.setTitleTextColor(getResources().getColor(color));
                }catch (Exception e){
                    Utils.psErrorLog("Can't set color.", e);
                }
            }else {
                try {
                    toolbar.setTitleTextColor(getResources().getColor(R.color.text__white));
                }catch (Exception e){
                    Utils.psErrorLog("Can't set color.", e);
                }
            }

            try {
                setSupportActionBar(toolbar);
            }catch (Exception e) {
                Utils.psErrorLog("Error in set support action bar.", e);
            }

            try {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }catch (Exception e) {
                Utils.psErrorLog("Error in set display home as up enabled.", e);
            }

            /*
               Warning :

               Set Spannable text must call after set Support Action Bar

               The problem is actually that you have a non-String title set on the Toolbar
               at the time you're calling setSupportActionBar. It will end up in ToolbarWidgetWrapper,
               where it will be used when you click the navigation menu. You can use any CharSequence (e.g. Spannable)
               after calling setSuppportActionBar.

               https://stackoverflow.com/questions/27023802/clicking-toolbar-navigation-icon-crashes-the-app/27044868

             */
            if(!title.equals("")) {
                setToolbarText(toolbar, title);
            }

        }else {
            Utils.psLog("Toolbar is null");
        }

        return toolbar;
    }

    protected Toolbar initToolbar(Toolbar toolbar, String title) {

        if(toolbar != null) {

            toolbar.setTitle(title);

            try {
                toolbar.setTitleTextColor(getResources().getColor(R.color.text__white));
            }catch (Exception e){
                Utils.psErrorLog("Can't set color.", e);
            }

            try {
                setSupportActionBar(toolbar);
            }catch (Exception e) {
                Utils.psErrorLog("Error in set support action bar.", e);
            }

            try {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }catch (Exception e) {
                Utils.psErrorLog("Error in set display home as up enabled.", e);
            }

            /*
               Warning :

               Set Spannable text must call after set Support Action Bar

               The problem is actually that you have a non-String title set on the Toolbar
               at the time you're calling setSupportActionBar. It will end up in ToolbarWidgetWrapper,
               where it will be used when you click the navigation menu. You can use any CharSequence (e.g. Spannable)
               after calling setSuppportActionBar.

               https://stackoverflow.com/questions/27023802/clicking-toolbar-navigation-icon-crashes-the-app/27044868

             */

            if(!title.equals("")) {
                setToolbarText(toolbar, title);
            }

        }else {
            Utils.psLog("Toolbar is null");
        }
        return toolbar;
    }

    public void setToolbarText(Toolbar toolbar, String text) {
        Utils.psLog("Set Toolbar Text : " + text);
        toolbar.setTitle(Utils.getSpannableString(toolbar.getContext(), text, Utils.Fonts.ROBOTO));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item != null) {
            Utils.psLog("Clicked " + item.getItemId());
            switch (item.getItemId()) {
                case android.R.id.home:
                    onBackPressed();
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion


    //region Setup Fragment

    protected void setupFragment(Fragment fragment) {
        try {
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commitAllowingStateLoss();
        }catch (Exception e){
            Utils.psErrorLog("Error! Can't replace fragment.", e);
        }
    }

    protected void loadLoginUserId() {
        try {

            if (getApplicationContext() != null && getBaseContext() != null) {
                loginUserId = pref.getString(Constants.USER_ID, Constants.EMPTY_STRING);
                shippingId = pref.getString(Constants.SHIPPING_ID, "");
                shippingTax = pref.getString(Constants.PAYMENT_SHIPPING_TAX, "");
                versionNo = pref.getString(Constants.APPINFO_PREF_VERSION_NO, "");
                force_update = pref.getBoolean(Constants.APPINFO_PREF_FORCE_UPDATE, false);
                force_update_msg = pref.getString(Constants.APPINFO_FORCE_UPDATE_MSG, "");
                force_update_title = pref.getString(Constants.APPINFO_FORCE_UPDATE_TITLE, "");
                overAllTax = pref.getString(Constants.PAYMENT_OVER_ALL_TAX, Constants.EMPTY_STRING);
                overAllTaxLabel = pref.getString(Constants.PAYMENT_OVER_ALL_TAX_LABEL, Constants.EMPTY_STRING);
                shippingTaxLabel = pref.getString(Constants.PAYMENT_SHIPPING_TAX_LABEL, Constants.EMPTY_STRING);
                cod = pref.getString(Constants.PAYMENT_CASH_ON_DELIVERY, Constants.ZERO);
                paypal = pref.getString(Constants.PAYMENT_PAYPAL, Constants.ZERO);
                stripe = pref.getString(Constants.PAYMENT_STRIPE, Constants.ZERO);
                messenger = pref.getString(Constants.MESSENGER, Constants.ZERO);
                whatsappNo = pref.getString(Constants.WHATSAPP, Constants.ZERO);
                consent_status = pref.getString(Config.CONSENTSTATUS_CURRENT_STATUS, Config.CONSENTSTATUS_CURRENT_STATUS);
                userEmailToVerify = pref.getString(Constants.USER_EMAIL_TO_VERIFY, Constants.EMPTY_STRING);
                userPasswordToVerify = pref.getString(Constants.USER_PASSWORD_TO_VERIFY, Constants.EMPTY_STRING);
                userNameToVerify = pref.getString(Constants.USER_NAME_TO_VERIFY, Constants.EMPTY_STRING);
                userIdToVerify = pref.getString(Constants.USER_ID_TO_VERIFY, Constants.EMPTY_STRING);
                shopId = pref.getString(Constants.SHOP_ID, Constants.EMPTY_STRING);
                shopPhoneNumber = pref.getString(Constants.SHOP_PHONE_NUMBER, Constants.EMPTY_STRING);
                shopNoShippingEnable = pref.getString(Constants.SHOP_NO_SHIPPING_ENABLE, Constants.EMPTY_STRING);
                shopZoneShippingEnable = pref.getString(Constants.SHOP_ZONE_SHIPPING_ENABLE, Constants.EMPTY_STRING);
                shopStandardShippingEnable = pref.getString(Constants.SHOP_STANDARD_SHIPPING_ENABLE, Constants.EMPTY_STRING);
            }

        } catch (NullPointerException ne) {
            Utils.psErrorLog("Null Pointer Exception.", ne);
        } catch (Exception e) {
            Utils.psErrorLog("Error in getting notification flag data.", e);
        }
    }

    protected void fadeIn(View view) {

        if (!isFadeIn) {
            view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
            isFadeIn = true; // Fade in will do only one time.
        }
    }

    protected void setupFragment(Fragment fragment, int frameId) {
        try {
            this.getSupportFragmentManager().beginTransaction()
                    .replace(frameId, fragment)
                    .commitAllowingStateLoss();
        }catch (Exception e){
            Utils.psErrorLog("Error! Can't replace fragment.", e);
        }
    }

    //endregion

}
