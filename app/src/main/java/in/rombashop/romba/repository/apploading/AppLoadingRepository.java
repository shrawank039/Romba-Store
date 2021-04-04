package in.rombashop.romba.repository.apploading;

import in.rombashop.romba.AppExecutors;
import in.rombashop.romba.Config;
import in.rombashop.romba.api.ApiResponse;
import in.rombashop.romba.api.PSApiService;
import in.rombashop.romba.db.PSCoreDb;
import in.rombashop.romba.repository.common.PSRepository;
import in.rombashop.romba.utils.Constants;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewobject.DeletedObject;
import in.rombashop.romba.viewobject.PSAppInfo;
import in.rombashop.romba.viewobject.common.Resource;

import java.io.IOException;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Response;

public class AppLoadingRepository extends PSRepository {

    @Inject
    AppLoadingRepository(PSApiService psApiService, AppExecutors appExecutors, PSCoreDb db) {
        super(psApiService, appExecutors, db);
    }

    public LiveData<Resource<PSAppInfo>> deleteTheSpecificObjects(String startDate, String endDate) {

        final MutableLiveData<Resource<PSAppInfo>> statusLiveData = new MutableLiveData<>();

        appExecutors.networkIO().execute(() -> {

            Response<PSAppInfo> response;

            try {
                response = psApiService.getDeletedHistory(Config.API_KEY, startDate, endDate).execute();

                ApiResponse<PSAppInfo> apiResponse = new ApiResponse<>(response);

                if (apiResponse.isSuccessful()) {

                    try {
                        db.beginTransaction();

                        if (apiResponse.body != null) {

                            if (apiResponse.body.deletedObjects.size() > 0) {
                                for (DeletedObject deletedObject: apiResponse.body.deletedObjects)
                                {
                                    switch (deletedObject.typeName) {
                                        case Constants.APPINFO_NAME_SHOP:
                                            db.shopDao().deleteShopById(deletedObject.id);

                                            break;
                                        case Constants.APPINFO_NAME_PRODUCT:
                                            db.productDao().deleteProductById(deletedObject.id);
                                            db.historyDao().deleteHistoryProductById(deletedObject.id);

                                            break;
                                        case Constants.APPINFO_NAME_CATEGORY:
                                            db.categoryDao().deleteCategoryById(deletedObject.id);
                                            break;
                                    }
                                }
                            }
                        }

                        db.setTransactionSuccessful();
                    } catch (NullPointerException ne) {
                        Utils.psErrorLog("Null Pointer Exception : ", ne);
                    } catch (Exception e) {
                        Utils.psErrorLog("Exception : ", e);
                    } finally {
                        db.endTransaction();
                    }

                    statusLiveData.postValue(Resource.success(apiResponse.body));

                } else {
                    statusLiveData.postValue(Resource.error(apiResponse.errorMessage, null));
                }

            } catch (IOException e) {
                statusLiveData.postValue(Resource.error(e.getMessage(), null));
            }

        });

        return statusLiveData;
    }
}
