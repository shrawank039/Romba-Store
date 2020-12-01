package in.rombashop.romba.ui.user.verifyemail;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.databinding.DataBindingUtil;

import in.rombashop.romba.Config;
import in.rombashop.romba.R;
import in.rombashop.romba.databinding.ActivityVerifyEmailBinding;
import in.rombashop.romba.ui.common.PSAppCompactActivity;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.MyContextWrapper;

public class VerifyEmailActivity extends PSAppCompactActivity {


        //region Override Methods
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                ActivityVerifyEmailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_email);
                // Init all UI
                initUI(binding);

        }

        @Override
        protected void attachBaseContext(Context newBase) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
                String CURRENT_LANG_CODE = preferences.getString(Constants.LANGUAGE_CODE, Config.DEFAULT_LANGUAGE);
                String CURRENT_LANG_COUNTRY_CODE = preferences.getString(Constants.LANGUAGE_COUNTRY_CODE, Config.DEFAULT_LANGUAGE_COUNTRY_CODE);

                super.attachBaseContext(MyContextWrapper.wrap(newBase, CURRENT_LANG_CODE, CURRENT_LANG_COUNTRY_CODE, true));
        }

        //endregion


        //region Private Methods

        private void initUI(ActivityVerifyEmailBinding binding) {

                // Toolbar
                initToolbar(binding.toolbar, getString(R.string.verify_email__enter_code));

                // setup Fragment
                setupFragment(new VerifyEmailFragment());

        }

}
