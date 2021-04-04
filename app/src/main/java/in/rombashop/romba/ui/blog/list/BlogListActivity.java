package in.rombashop.romba.ui.blog.list;

import android.os.Bundle;

import in.rombashop.romba.R;
import in.rombashop.romba.databinding.ActivityBlogListBinding;
import in.rombashop.romba.ui.common.PSAppCompactActivity;

import androidx.databinding.DataBindingUtil;

public class BlogListActivity extends PSAppCompactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityBlogListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_blog_list);

        initUI(binding);

    }

    private void initUI(ActivityBlogListBinding binding) {

        // Toolbar
        initToolbar(binding.toolbar, getResources().getString(R.string.blog_list__title));

        // setup Fragment

        setupFragment(new BlogListFragment());

    }
}
