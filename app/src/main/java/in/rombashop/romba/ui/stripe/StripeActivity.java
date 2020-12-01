package in.rombashop.romba.ui.stripe;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import in.rombashop.romba.R;
import in.rombashop.romba.databinding.ActivityStripeBinding;
import in.rombashop.romba.ui.common.PSAppCompactActivity;

public class StripeActivity extends PSAppCompactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityStripeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_stripe);

        initUI(binding);

    }

    private void initUI(ActivityStripeBinding binding) {

        // Toolbar
        initToolbar(binding.toolbar, getResources().getString(R.string.checkout__stripe));

        // setup Fragment
        setupFragment(new StripeFragment());

    }
}

