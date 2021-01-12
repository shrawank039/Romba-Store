package in.rombashop.romba.viewmodel.common;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import android.content.Context;
import in.rombashop.romba.repository.aboutus.AboutUsRepository;
import in.rombashop.romba.ui.common.BackgroundTaskHandler;
import in.rombashop.romba.ui.common.NotificationTaskHandler;
import in.rombashop.romba.utils.Utils;

import javax.inject.Inject;

/**
 * Created by matrixdeveloper on 1/4/18.
 * Contact Email : matrixdeveloper.business@gmail.com
 */

public class NotificationViewModel extends ViewModel {

    private final NotificationTaskHandler backgroundTaskHandler;

    public boolean pushNotificationSetting = false;
    public boolean isLoading = false;

    @Inject
    NotificationViewModel(AboutUsRepository repository) {
        Utils.psLog("Inside NewsViewModel");

        backgroundTaskHandler = new NotificationTaskHandler(repository);
    }

    public void registerNotification(Context context, String platform, String token, String loginUserId) {

        if(token == null || platform == null) return;

        if(platform.equals("")) return;

        backgroundTaskHandler.registerNotification(context, platform, token, loginUserId);
    }

    public void unregisterNotification(Context context, String platform, String token, String loginUserId) {

        if(token == null || platform == null) return;

        if(platform.equals("")) return;

        backgroundTaskHandler.unregisterNotification(context, platform, token, loginUserId);
    }

    public LiveData<BackgroundTaskHandler.LoadingState> getLoadingStatus() {
        return backgroundTaskHandler.getLoadingState();
    }



}
