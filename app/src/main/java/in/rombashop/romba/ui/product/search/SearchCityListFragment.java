package in.rombashop.romba.ui.product.search;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.ads.AdRequest;

import java.util.List;

import in.rombashop.romba.Config;
import in.rombashop.romba.R;
import in.rombashop.romba.binding.FragmentDataBindingComponent;
import in.rombashop.romba.databinding.FragmentSearchCityListBinding;
import in.rombashop.romba.ui.common.PSFragment;
import in.rombashop.romba.ui.product.search.adapter.SearchCityAdapter;
import in.rombashop.romba.utils.AutoClearedValue;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewmodel.city.CityViewModel;
import in.rombashop.romba.viewobject.City;
import in.rombashop.romba.viewobject.common.Resource;
import in.rombashop.romba.viewobject.common.Status;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchCityListFragment extends PSFragment {

    private final androidx.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    private CityViewModel cityViewModel;
    public String cityId;

    @VisibleForTesting
    private AutoClearedValue<FragmentSearchCityListBinding> binding;
    private AutoClearedValue<SearchCityAdapter> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentSearchCityListBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_city_list, container, false, dataBindingComponent);

        binding = new AutoClearedValue<>(this, dataBinding);

        setHasOptionsMenu(true);

        if (getActivity() != null) {
            Intent intent = getActivity().getIntent();
            this.cityId = intent.getStringExtra(Constants.CITY_ID);
        }
        return binding.get().getRoot();
    }

    @Override
    protected void initUIAndActions() {

        if (Config.SHOW_ADMOB && connectivity.isConnected()) {
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            binding.get().adView.loadAd(adRequest);
        } else {
            binding.get().adView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.clear_button, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.clear) {
            this.cityId = "";

            initAdapters();

            initData();

            navigationController.navigateBackToSearchFragmentFromCity(SearchCityListFragment.this.getActivity(), this.cityId, "");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViewModels() {

        cityViewModel = ViewModelProviders.of(this, viewModelFactory).get(CityViewModel.class);
    }

    @Override
    protected void initAdapters() {

        SearchCityAdapter nvadapter = new SearchCityAdapter(dataBindingComponent,
                (city, id) -> {

                    navigationController.navigateBackToSearchFragmentFromCity(SearchCityListFragment.this.getActivity(), city.id, city.name);

                    if (SearchCityListFragment.this.getActivity() != null) {
                        SearchCityListFragment.this.getActivity().finish();
                    }
                }, this.cityId);
        this.adapter = new AutoClearedValue<>(this, nvadapter);
        binding.get().searchCategoryRecyclerView.setAdapter(nvadapter);

    }

    @Override
    protected void initData() {

        if (getActivity() != null) {
            cityViewModel.countryId = getActivity().getIntent().getStringExtra(Constants.COUNTRY_ID);
        }

        loadCity();
    }

    private void loadCity() {

        // Load City List

        cityViewModel.setCityListObj(shopId, cityViewModel.countryId, String.valueOf(Config.LIST_CATEGORY_COUNT), String.valueOf(cityViewModel.offset));

        LiveData<Resource<List<City>>> news = cityViewModel.getCityListData();

        if (news != null) {

            news.observe(this, listResource -> {
                if (listResource != null) {

                    Utils.psLog("Got Data" + listResource.message + listResource.toString());

                    switch (listResource.status) {
                        case LOADING:
                            // Loading State
                            // Data are from Local DB

//                            if (listResource.data != null) {
//                                //fadeIn Animation
//                                fadeIn(binding.get().getRoot());
//
//                                // Update the data
//                                replaceData(listResource.data);
//
//                            }

                            break;

                        case SUCCESS:
                            // Success State
                            // Data are from Server

                            if (listResource.data != null) {
                                // Update the data
                                replaceData(listResource.data);
                            }

                            cityViewModel.setLoadingState(false);

                            break;

                        case ERROR:
                            // Error State

                            cityViewModel.setLoadingState(false);

                            break;
                        default:
                            // Default

                            break;
                    }

                } else {

                    // Init Object or Empty Data
                    Utils.psLog("Empty Data");

                    if (cityViewModel.offset > 1) {
                        // No more data for this list
                        // So, Block all future loading
                        cityViewModel.forceEndLoading = true;
                    }

                }

            });
        }

        cityViewModel.getNextPageCityListData().observe(this, state -> {
            if (state != null) {
                if (state.status == Status.ERROR) {
                    Utils.psLog("Next Page State : " + state.data);

                    cityViewModel.setLoadingState(false);
                    cityViewModel.forceEndLoading = true;
                }
            }
        });

    }

    private void replaceData(List<City> cityList) {

        adapter.get().replace(cityList);
        binding.get().executePendingBindings();

    }
}
