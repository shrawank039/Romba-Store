package in.rombashop.romba.di;

import in.rombashop.romba.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by matrixdeveloper on 11/15/17.
 * Contact Email : matrixdeveloper.business@gmail.com
 */

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        MainActivityModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(android.app.Application application);
        AppComponent build();
    }
    void inject(Application MShop);

}
