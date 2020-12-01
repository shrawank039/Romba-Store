package in.rombashop.romba.viewmodel.product;

import in.rombashop.romba.repository.product.ProductRepository;
import in.rombashop.romba.utils.AbsentLiveData;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewmodel.common.PSViewModel;
import in.rombashop.romba.viewobject.ProductColor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class ProductColorViewModel extends PSViewModel {
    //for product color list

    public boolean isColorData = false;
    private final LiveData<List<ProductColor>> productColorListData;
    private MutableLiveData<ProductColorViewModel.TmpDataHolder> productColorObj = new MutableLiveData<>();

    public List<ProductColor> proceededColorListData = new ArrayList<>();
    public String colorSelectId = "";
    public String colorSelectValue = "";

    //region Constructor

    @Inject
    ProductColorViewModel(ProductRepository productRepository) {
        //  product color List
        productColorListData = Transformations.switchMap(productColorObj, (TmpDataHolder obj) -> {
            if (obj == null) {
                return AbsentLiveData.create();
            }
            Utils.psLog("product color List.");
            return productRepository.getProductColor(obj.productId);
        });

    }

    //endregion
    //region Getter And Setter for product color List

    public void setProductColorListObj(String productId) {
        ProductColorViewModel.TmpDataHolder tmpDataHolder = new ProductColorViewModel.TmpDataHolder();
        tmpDataHolder.productId = productId;
        tmpDataHolder.colorSelectedId = colorSelectId;
        tmpDataHolder.colorSelectedValue = colorSelectValue;

        productColorObj.setValue(tmpDataHolder);

        // start loading
        setLoadingState(true);

    }

    public LiveData<List<ProductColor>> getProductColorData() {
        return productColorListData;
    }

    //endregion

    //region Holder
    class TmpDataHolder {
        public String offset = "";
        public String productId = "";
        public String colorSelectedId = "";
        public String colorSelectedValue = "";
        public Boolean isConnected = false;
    }
    //endregion
}
