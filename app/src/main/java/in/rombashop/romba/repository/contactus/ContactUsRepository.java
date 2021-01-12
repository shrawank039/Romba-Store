package in.rombashop.romba.repository.contactus;

import in.rombashop.romba.AppExecutors;
import in.rombashop.romba.api.ApiResponse;
import in.rombashop.romba.api.PSApiService;
import in.rombashop.romba.db.PSCoreDb;
import in.rombashop.romba.repository.common.PSRepository;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewobject.ApiStatus;
import in.rombashop.romba.viewobject.common.Resource;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Response;

/**
 * Created by matrixdeveloper on 7/2/18.
 * Contact Email : matrixdeveloper.business@gmail.com
 * Website : http://www.matrixdeveloper.com
 */

public class ContactUsRepository extends PSRepository {

    @Inject
    ContactUsRepository(PSApiService psApiService, AppExecutors appExecutors, PSCoreDb db) {
        super(psApiService, appExecutors, db);

    }

    /**
     * Post Contact Us
     * @param apiKey APIKey to access Web Service
     * @param contactName Name
     * @param contactEmail Email
     * @param contactDesc Desc
     * @return Status of Post
     */
    public LiveData<Resource<Boolean>> postContactUs(String apiKey, String contactName, String contactEmail, String contactDesc, String contactPhone) {

        final MutableLiveData<Resource<Boolean>> statusLiveData = new MutableLiveData<>();
        appExecutors.networkIO().execute(() -> {
            try {

                // Call the API Service
                Response<ApiStatus> response = psApiService.rawPostContact(apiKey, contactName, contactEmail, contactDesc, contactPhone).execute();

                // Wrap with APIResponse Class
                ApiResponse<ApiStatus> apiResponse = new ApiResponse<>(response);

                Utils.psLog("apiResponse " + apiResponse);
                // If response is successful
                if (apiResponse.isSuccessful()) {

                    statusLiveData.postValue(Resource.success(true));
                } else {

                    statusLiveData.postValue(Resource.error(apiResponse.errorMessage, true));
                }
            } catch (Exception e) {
                statusLiveData.postValue(Resource.error(e.getMessage(), true));
            }

        });

        return statusLiveData;

    }

}
