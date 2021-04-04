package in.rombashop.romba.viewmodel.category;

import in.rombashop.romba.repository.category.CategoryRepository;
import in.rombashop.romba.utils.AbsentLiveData;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewmodel.common.PSViewModel;
import in.rombashop.romba.viewobject.Category;
import in.rombashop.romba.viewobject.common.Resource;
import in.rombashop.romba.viewobject.holder.CategoryParameterHolder;
import in.rombashop.romba.viewobject.holder.ProductParameterHolder;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

/**
 * Created by matrixdeveloper on 11/25/17.
 * Contact Email : matrixdeveloper.business@gmail.com
 */

public class CategoryViewModel extends PSViewModel {


    //region Variables

    private final LiveData<Resource<List<Category>>> categoryListData;
    private MutableLiveData<CategoryViewModel.TmpDataHolder> categoryListObj = new MutableLiveData<>();

    private final LiveData<Resource<Boolean>> nextPageLoadingStateData;
    private MutableLiveData<CategoryViewModel.TmpDataHolder> nextPageLoadingStateObj = new MutableLiveData<>();

    public ProductParameterHolder productParameterHolder = new ProductParameterHolder();

    public CategoryParameterHolder categoryParameterHolder = new CategoryParameterHolder();

    //endregion

    //region Constructors

    @Inject
    CategoryViewModel(CategoryRepository repository) {

        Utils.psLog("CategoryViewModel");

        categoryListData = Transformations.switchMap(categoryListObj, obj -> {
            if (obj == null) {
                return AbsentLiveData.create();
            }
            Utils.psLog("CategoryViewModel : categories");
            return repository.getAllSearchCategory(obj.categoryParameterHolder, obj.limit, obj.offset);
        });

        nextPageLoadingStateData = Transformations.switchMap(nextPageLoadingStateObj, obj -> {
            if (obj == null) {
                return AbsentLiveData.create();
            }
            Utils.psLog("Category List.");
            return repository.getNextSearchCategory(obj.limit, obj.offset, obj.categoryParameterHolder);
        });

    }

    //endregion

    public void setCategoryListObj(String loginUserId, CategoryParameterHolder categoryParameterHolder, String limit, String offset) {
        if (!isLoading) {
            TmpDataHolder tmpDataHolder = new TmpDataHolder();
            tmpDataHolder.loginUserId = loginUserId;
            tmpDataHolder.offset = offset;
            tmpDataHolder.limit = limit;
            tmpDataHolder.categoryParameterHolder = categoryParameterHolder;
            categoryListObj.setValue(tmpDataHolder);

            // start loading
            setLoadingState(true);
        }
    }

    public LiveData<Resource<List<Category>>> getCategoryListData() {
        return categoryListData;
    }

    //Get Latest Category Next Page
    public void setNextPageLoadingStateObj(String limit, String offset, CategoryParameterHolder categoryParameterHolder) {

        if (!isLoading) {
            TmpDataHolder tmpDataHolder = new TmpDataHolder();
            tmpDataHolder.offset = offset;
            tmpDataHolder.limit = limit;
            tmpDataHolder.categoryParameterHolder = categoryParameterHolder;
            nextPageLoadingStateObj.setValue(tmpDataHolder);

            // start loading
            setLoadingState(true);
        }
    }

    public LiveData<Resource<Boolean>> getNextPageLoadingStateData() {
        return nextPageLoadingStateData;
    }

    class TmpDataHolder {
        public String loginUserId = "";
        public String limit = "";
        public String offset = "";
        public Boolean isConnected = false;
        public String shopId = "";
        public String orderBy = "";
        public CategoryParameterHolder categoryParameterHolder;

    }

}
