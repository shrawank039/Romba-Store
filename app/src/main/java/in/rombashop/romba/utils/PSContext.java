package in.rombashop.romba.utils;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

/**
 * Created by matrixdeveloper on 1/10/18.
 * Contact Email : matrixdeveloper.business@gmail.com
 */


public class PSContext {

    public Context context;

    @Inject
    PSContext(Application App) {
        this.context = App.getApplicationContext();
    }
}
