package in.rombashop.romba.repository.common;

import androidx.lifecycle.LiveData;
import in.rombashop.romba.AppExecutors;
import in.rombashop.romba.Config;
import in.rombashop.romba.api.PSApiService;
import in.rombashop.romba.db.PSCoreDb;
import in.rombashop.romba.utils.AbsentLiveData;
import in.rombashop.romba.utils.Connectivity;
import in.rombashop.romba.utils.RateLimiter;
import in.rombashop.romba.utils.Utils;
import in.rombashop.romba.viewobject.common.Resource;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * Parent Class of All Repository Class in this project
 * Created by matrixdeveloper on 12/5/17.
 * Contact Email : matrixdeveloper.business@gmail.com
 */

public abstract class PSRepository {


    //region Variables

    protected final PSApiService psApiService;
    protected final AppExecutors appExecutors;
    protected final PSCoreDb db;
    @Inject
    protected Connectivity connectivity;
    protected RateLimiter<String> rateLimiter = new RateLimiter<>( Config.API_SERVICE_CACHE_LIMIT, TimeUnit.MINUTES);

    //endregion


    //region Constructor

    /**
     * Constructor of PSRepository
     * @param psApiService matrixdeveloper API Service Instance
     * @param appExecutors Executors Instance
     * @param db matrixdeveloper DB
     */
    protected PSRepository(PSApiService psApiService, AppExecutors appExecutors, PSCoreDb db) {
        Utils.psLog("Inside NewsRepository");
        this.psApiService = psApiService;
        this.appExecutors = appExecutors;
        this.db = db;
    }

    //endregion


    //region public Methods

    public LiveData<Resource<Boolean>> save(Object obj) {

        if(obj == null) {
            return AbsentLiveData.create();
        }

        SaveTask saveTask = new SaveTask(psApiService, db, obj);
        appExecutors.diskIO().execute(saveTask);
        return saveTask.getStatusLiveData();
    }


    public LiveData<Resource<Boolean>> delete(Object obj) {

        if(obj == null) {
            return AbsentLiveData.create();
        }

        DeleteTask deleteTask = new DeleteTask(psApiService, db, obj);
        appExecutors.diskIO().execute(deleteTask);
        return deleteTask.getStatusLiveData();
    }
    //endregion

}
