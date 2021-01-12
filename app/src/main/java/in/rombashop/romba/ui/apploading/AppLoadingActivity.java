package in.rombashop.romba.ui.apploading;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.rombashop.romba.Config;
import in.rombashop.romba.R;
import in.rombashop.romba.databinding.ActivityAppLoadingBinding;
import in.rombashop.romba.ui.common.PSAppCompactActivity;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.MyContextWrapper;
import in.rombashop.romba.utils.PSDialogMsg;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewmodel.apploading.AppLoadingViewModel;
import in.rombashop.romba.viewmodel.clearalldata.ClearAllDataViewModel;
import in.rombashop.romba.viewobject.PSAppInfo;

public class AppLoadingActivity extends PSAppCompactActivity {

    private PSDialogMsg psDialogMsg;
    private String startDate = Constants.ZERO;
    private String endDate = Constants.ZERO;

    private AppLoadingViewModel appLoadingViewModel;
    private ClearAllDataViewModel clearAllDataViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAppLoadingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_app_loading);
        psDialogMsg = new PSDialogMsg(this, false);
        appLoadingViewModel = ViewModelProviders.of(this, viewModelFactory).get(AppLoadingViewModel.class);
        clearAllDataViewModel = ViewModelProviders.of(this, viewModelFactory).get(ClearAllDataViewModel.class);

        initData();

    }

    private void initData() {

        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            Config.APP_VERSION = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (connectivity.isConnected()) {
            if (startDate.equals(Constants.ZERO)) {

                startDate = getDateTime();
                Utils.setDatesToShared(startDate, endDate, pref);
            }

            endDate = getDateTime();
            appLoadingViewModel.setDeleteHistoryObj(startDate, endDate);

        } else {
            navigationController.navigateToMainActivity(AppLoadingActivity.this);

            if (getApplicationContext() != null) {
                finish();
            }
        }

        appLoadingViewModel.getDeleteHistoryData().observe(this, result -> {

            if (result != null) {
                switch (result.status) {

                    case SUCCESS:

                        if (result.data != null) {
                            appLoadingViewModel.psAppInfo = result.data;
                            checkVersionNumber(result.data);
                            startDate = endDate;
                        }
                        break;

                    case ERROR:

                        break;
                }
            }

        });

        clearAllDataViewModel.getDeleteAllDataData().observe(this, result -> {

            if (result != null) {
                switch (result.status) {

                    case ERROR:
                        break;

                    case SUCCESS:
                        checkForceUpdate(appLoadingViewModel.psAppInfo);
                        break;
                }
            }
        });

    }

    private void checkForceUpdate(PSAppInfo psAppInfo) {

        if (psAppInfo.psAppVersion.versionForceUpdate.equals(Constants.ONE)) {

            pref.edit().putString(Constants.APPINFO_PREF_VERSION_NO, psAppInfo.psAppVersion.versionNo).apply();
            pref.edit().putBoolean(Constants.APPINFO_PREF_FORCE_UPDATE, true).apply();
            pref.edit().putString(Constants.APPINFO_FORCE_UPDATE_TITLE, psAppInfo.psAppVersion.versionTitle).apply();
            pref.edit().putString(Constants.APPINFO_FORCE_UPDATE_MSG, psAppInfo.psAppVersion.versionMessage).apply();

            navigationController.navigateToForceUpdateActivity(this, psAppInfo.psAppVersion.versionTitle, psAppInfo.psAppVersion.versionMessage);

        } else if (psAppInfo.psAppVersion.versionForceUpdate.equals(Constants.ZERO)) {

            pref.edit().putBoolean(Constants.APPINFO_PREF_FORCE_UPDATE, false).apply();

            psDialogMsg.showAppInfoDialog(getString(R.string.update), getString(R.string.app__cancel), psAppInfo.psAppVersion.versionTitle, psAppInfo.psAppVersion.versionMessage);
            psDialogMsg.show();
            psDialogMsg.okButton.setOnClickListener(v -> {
                psDialogMsg.cancel();
                navigationController.navigateToMainActivity(AppLoadingActivity.this);
                navigationController.navigateToPlayStore(AppLoadingActivity.this);
                if (getApplicationContext() != null) {
                    finish();
                }

            });

            psDialogMsg.cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    psDialogMsg.cancel();

                    navigationController.navigateToMainActivity(AppLoadingActivity.this);
                    if (getApplicationContext() != null) {
                        finish();
                    }
                }
            });

            psDialogMsg.getDialog().setCancelable(false);
        }else {
            navigationController.navigateToMainActivity(AppLoadingActivity.this);
            if (getApplicationContext() != null) {
                finish();
            }
        }
    }

    private void checkVersionNumber(PSAppInfo psAppInfo) {

        if (!Config.APP_VERSION.equals(psAppInfo.psAppVersion.versionNo)) {

            if (psAppInfo.psAppVersion.versionNeedClearData.equals(Constants.ONE)) {
                psDialogMsg.cancel();
                clearAllDataViewModel.setDeleteAllDataObj();
            } else {
                checkForceUpdate(appLoadingViewModel.psAppInfo);
            }
        } else {
            pref.edit().putBoolean(Constants.APPINFO_PREF_FORCE_UPDATE, false).apply();
            navigationController.navigateToMainActivity(AppLoadingActivity.this);
            if (getApplicationContext() != null) {
                finish();
            }
        }

    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        String CURRENT_LANG_CODE = preferences.getString(Constants.LANGUAGE_CODE, Config.DEFAULT_LANGUAGE);
        String CURRENT_LANG_COUNTRY_CODE = preferences.getString(Constants.LANGUAGE_COUNTRY_CODE, Config.DEFAULT_LANGUAGE_COUNTRY_CODE);

        super.attachBaseContext(MyContextWrapper.wrap(newBase, CURRENT_LANG_CODE, CURRENT_LANG_COUNTRY_CODE, true));
    }

}
//endregion


