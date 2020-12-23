package in.rombashop.romba.ui.basket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import in.rombashop.romba.Config;
import in.rombashop.romba.R;
import in.rombashop.romba.databinding.ActivityBasketListBinding;
import in.rombashop.romba.net.PrefManager;
import in.rombashop.romba.ui.common.PSAppCompactActivity;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.MyContextWrapper;

/**
 * Created by Panacea-Soft
 * Contact Email : teamps.is.cool@gmail.com
 * Website : http://www.panacea-soft.com
 */
public class BasketListActivity extends PSAppCompactActivity {
    PrefManager prf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBasketListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_basket_list);

        prf = new PrefManager(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pincode_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_pincode);
        if (prf.getString("pincode").equals("")) {
            item.setTitle("PINCODE");
        } else {
            item.setTitle(prf.getString("pincode"));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    //region Private Methods

    private void initUI(ActivityBasketListBinding binding) {

        // Toolbar
        initToolbar(binding.toolbar, getResources().getString(R.string.basket__list));

        // setup Fragment
        setupFragment(new BasketListFragment());

    }

    //endregion
}
