package in.rombashop.romba.viewmodel.product;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import in.rombashop.romba.Config;
import in.rombashop.romba.repository.product.ProductRepository;
import in.rombashop.romba.utils.AbsentLiveData;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewmodel.common.PSViewModel;
import in.rombashop.romba.viewobject.Product;
import in.rombashop.romba.viewobject.common.Resource;

import java.util.List;

import javax.inject.Inject;

public class ProductFavouriteViewModel extends PSViewModel {

    //for product favourite list
    private final LiveData<Resource<List<Product>>> productFavouriteData;
    private MutableLiveData<ProductFavouriteViewModel.TmpDataHolder> productFavouriteListObj = new MutableLiveData<>();

    private final LiveData<Resource<Boolean>> nextPageFavouriteLoadingData;
    private MutableLiveData<ProductFavouriteViewModel.TmpDataHolder> nextPageLoadingFavouriteObj = new MutableLiveData<>();

    private final LiveData<Resource<Boolean>> sendFavouritePostData;
    private MutableLiveData<TmpDataHolder> sendFavouriteDataPostObj = new MutableLiveData<>();

    //endregion
    //region Constructor

    @Inject
    public ProductFavouriteViewModel(ProductRepository productRepository) {
        //  product detail List
        productFavouriteData = Transformations.switchMap(productFavouriteListObj, obj -> {
            if (obj == null) {
                return AbsentLiveData.create();
            }
            Utils.psLog("productFavouriteData");
            return productRepository.getFavouriteList(Config.API_KEY, obj.loginUserId, obj.offset);
        });

        nextPageFavouriteLoadingData = Transformations.switchMap(nextPageLoadingFavouriteObj, obj -> {
            if (obj == null) {
                return AbsentLiveData.create();
            }
            Utils.psLog("nextPageFavouriteLoadingData");
            return productRepository.getNextPageFavouriteProductList(obj.loginUserId, obj.offset);
        });

        sendFavouritePostData = Transformations.switchMap(sendFavouriteDataPostObj, obj -> {

            if (obj == null) {
                return AbsentLiveData.create();
            }
            return productRepository.uploadFavouritePostToServer(obj.productId, obj.userId);
        });

    }

    //endregion

    //region Getter And Setter for product detail List
    public void setProductFavouriteListObj(String loginUserId, String offset) {
        if (!isLoading) {
            ProductFavouriteViewModel.TmpDataHolder tmpDataHolder = new ProductFavouriteViewModel.TmpDataHolder();
            tmpDataHolder.loginUserId = loginUserId;
            tmpDataHolder.offset = offset;
            productFavouriteListObj.setValue(tmpDataHolder);

            // start loading
            setLoadingState(true);
        }
    }

    public LiveData<Resource<List<Product>>> getProductFavouriteData() {
        return productFavouriteData;
    }
    //endregion


    //Get Favourite Next Page
    public void setNextPageLoadingFavouriteObj(String offset, String loginUserId) {

        if (!isLoading) {
            ProductFavouriteViewModel.TmpDataHolder tmpDataHolder = new ProductFavouriteViewModel.TmpDataHolder();
            tmpDataHolder.loginUserId = loginUserId;
            tmpDataHolder.offset = offset;
            nextPageLoadingFavouriteObj.setValue(tmpDataHolder);

            // start loading
            setLoadingState(true);
        }
    }

    public LiveData<Resource<Boolean>> getNextPageFavouriteLoadingData() {
        return nextPageFavouriteLoadingData;
    }

    //fav post
    public void setFavouritePostDataObj(String product_id, String userId) {

        if(!isLoading) {
            TmpDataHolder tmpDataHolder = new TmpDataHolder();
            tmpDataHolder.productId = product_id;
            tmpDataHolder.userId = userId;

            sendFavouriteDataPostObj.setValue(tmpDataHolder);

            // start loading
            setLoadingState(true);
        }
    }

    public LiveData<Resource<Boolean>> getFavouritePostData() {
        return sendFavouritePostData;
    }

    class TmpDataHolder {
        public String productId = "";
        public String userId = "";
        public String shopId = "";
        public String loginUserId = "";
        public String offset = "";
        public String limit = "";
        public Boolean isConnected = false;
    }
    //endregion

}
