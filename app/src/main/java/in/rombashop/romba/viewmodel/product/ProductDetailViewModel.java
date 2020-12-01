package in.rombashop.romba.viewmodel.product;

import in.rombashop.romba.Config;
import in.rombashop.romba.repository.product.ProductRepository;
import in.rombashop.romba.utils.AbsentLiveData;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewmodel.common.PSViewModel;
import in.rombashop.romba.viewobject.Basket;
import in.rombashop.romba.viewobject.Product;
import in.rombashop.romba.viewobject.common.Resource;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class ProductDetailViewModel extends PSViewModel {

    //for product detail list

    public String productId = "";
    public String historyFlag = "";
    public String basketFlag = "";
    public String currencySymbol = "";
    public String price = "";
    public String attributes = "";
    public String count = "";
    public String colorId = "";
    public Basket basket;
    public int basketId = 0;
    public Product productContainer;

    public Boolean isAddtoCart = false;

    private final LiveData<Resource<Product>> productDetailListData;
    private MutableLiveData<ProductDetailViewModel.TmpDataHolder> productDetailObj = new MutableLiveData<>();

    //region Constructor

    @Inject
    ProductDetailViewModel(ProductRepository productRepository) {
        //  product detail List
        productDetailListData = Transformations.switchMap(productDetailObj, obj -> {
            if (obj == null) {
                return AbsentLiveData.create();
            }
            Utils.psLog("product detail List.");
            return productRepository.getProductDetail(Config.API_KEY, obj.productId, obj.historyFlag, obj.userId);
        });


    }

    //endregion
    //region Getter And Setter for product detail List

    public void setProductDetailObj(String productId,String historyFlag, String userId) {
        if (!isLoading) {
            ProductDetailViewModel.TmpDataHolder tmpDataHolder = new ProductDetailViewModel.TmpDataHolder();
            tmpDataHolder.productId = productId;
            tmpDataHolder.historyFlag = historyFlag;
            tmpDataHolder.userId = userId;
            productDetailObj.setValue(tmpDataHolder);

            // start loading
            setLoadingState(true);
        }
    }

    public LiveData<Resource<Product>> getProductDetailData() {
        return productDetailListData;
    }

    //endregion

    //region Holder
    class TmpDataHolder {
        public String offset = "";
        public String productId = "";
        public String historyFlag = "";
        public String userId = "";
        public String shopId = "";
        public Boolean isConnected = false;
    }
    //endregion
}
