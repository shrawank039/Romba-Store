package in.rombashop.romba.utils;

import android.content.SharedPreferences;

import in.rombashop.romba.Config;

import javax.inject.Inject;

/**
 * Created by matrixdeveloper on 10/16/18.
 * Contact Email : matrixdeveloper.business@gmail.com
 */

//@Singleton
public class AppLanguage {

    private String currentLanguage;
    private SharedPreferences sharedPreferences;

    @Inject
    public AppLanguage(SharedPreferences sharedPreferences) {
        if(sharedPreferences != null) {
            this.sharedPreferences = sharedPreferences;
            try {
                this.currentLanguage = sharedPreferences.getString("Language", Config.DEFAULT_LANGUAGE);
            } catch (Exception e) {
                this.currentLanguage = Config.DEFAULT_LANGUAGE;
            }
        }
    }

    public AppLanguage() {

    }

    public String getLanguage(boolean isLatest) {

        if(isLatest) {
            try {
                this.currentLanguage = sharedPreferences.getString("Language", Config.DEFAULT_LANGUAGE);
            } catch (Exception e) {
                this.currentLanguage = Config.DEFAULT_LANGUAGE;
            }
        }
        return currentLanguage;
    }
}
