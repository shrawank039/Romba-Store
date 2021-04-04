package in.rombashop.romba.ui.privacyandpolicy;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import in.rombashop.romba.R;
import in.rombashop.romba.databinding.ActivityPrivacyPolicyBinding;
import in.rombashop.romba.ui.common.PSAppCompactActivity;

public class PrivacyPolicyActivity extends PSAppCompactActivity {


    //region Override Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityPrivacyPolicyBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_policy);

        // Init all UI
        initUI(binding);

    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
//        String CURRENT_LANG_CODE = preferences.getString(Constants.LANGUAGE_CODE, Config.DEFAULT_LANGUAGE);
//        String CURRENT_LANG_COUNTRY_CODE = preferences.getString(Constants.LANGUAGE_COUNTRY_CODE, Config.DEFAULT_LANGUAGE_COUNTRY_CODE);
//
//        super.attachBaseContext(MyContextWrapper.wrap(newBase, CURRENT_LANG_CODE, CURRENT_LANG_COUNTRY_CODE, true));
//    }
    //endregion


    //region Private Methods

    private void initUI(ActivityPrivacyPolicyBinding binding) {

        // Toolbar
        initToolbar(binding.toolbar, getResources().getString(R.string.terms_and_conditions__title));

        // setup Fragment
        setupFragment(new PrivacyPolicyFragment());

    }
}

//endregion
