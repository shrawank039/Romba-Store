package in.rombashop.romba;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import in.rombashop.romba.ui.user.phonelogin.RombaAppCompactActivity;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.MyContextWrapper;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewmodel.image.ImageViewModel;
import in.rombashop.romba.viewobject.Image;
import in.rombashop.romba.viewobject.common.Resource;
import me.relex.circleindicator.CircleIndicator3;


public class ImageViewActivity extends RombaAppCompactActivity {

    private List<Image> imageList = new ArrayList<>();
    ViewPager2 mViewPager;
    ViewPager2Adapter mViewPagerAdapter;
    private ImageViewModel imageViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imageList = new ArrayList<>();
        mViewPager = findViewById(R.id.viewPagerMain);

        mViewPagerAdapter = new ViewPager2Adapter(ImageViewActivity.this, imageList);
        mViewPager.setAdapter(mViewPagerAdapter);

        CircleIndicator3 indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
        mViewPagerAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());


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
        imageViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImageViewModel.class);
    }

    @Override
    protected void initAdapters() {

    }

    @Override
    protected void initData() {

        imageViewModel.imgType = getIntent().getStringExtra(Constants.IMAGE_TYPE);

        imageViewModel.id = getIntent().getStringExtra(Constants.IMAGE_PARENT_ID);

        LiveData<Resource<List<in.rombashop.romba.viewobject.Image>>> imageListLiveData = imageViewModel.getImageListLiveData();
        imageViewModel.setImageParentId(imageViewModel.imgType, imageViewModel.id);
        imageListLiveData.observe(this, listResource -> {
            // we don't need any null checks here for the adapter since LiveData guarantees that
            // it won't call us if fragment is stopped or not started.
            if (listResource != null && listResource.data != null) {
                Utils.psLog("Got Data ");
                imageList.clear();
                imageList.addAll(listResource.data);
                mViewPagerAdapter.notifyDataSetChanged();

            } else {
                //noinspection ConstantConditions
                Utils.psLog("Empty Data");
            }
        });
    }

    public void backPress(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}