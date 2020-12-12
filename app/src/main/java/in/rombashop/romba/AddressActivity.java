package in.rombashop.romba;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import javax.inject.Inject;

import in.rombashop.romba.ui.user.phonelogin.RombaAppCompactActivity;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.MyContextWrapper;

public class AddressActivity extends RombaAppCompactActivity {

    @Inject
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        Fragment fragment;
        fragment = new ShippingAddressFragment();
        loadFragment(fragment);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("SHIPPING"));
        tabLayout.addTab(tabLayout.newTab().setText("PAYMENT"));
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        assert tab != null;
        tab.select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                switch(tab.getPosition()) {
                    case 0:
                        fragment = new ShippingAddressFragment();
                        loadFragment(fragment);
                        break;
                    case 1:
                        fragment = new PaymentAddressFragment();
                        loadFragment(fragment);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
       // transaction.addToBackStack(null);
        transaction.commit();
    }

    public void backPress(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}