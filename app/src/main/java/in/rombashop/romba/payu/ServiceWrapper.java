package in.rombashop.romba.payu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import in.rombashop.romba.Config;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceWrapper {

    private final ServiceInterface mServiceInterface;

    public ServiceWrapper(Interceptor mInterceptorheader) {
        mServiceInterface = getRetrofit(mInterceptorheader).create(ServiceInterface.class);
    }

    public Retrofit getRetrofit(Interceptor mInterceptorheader) {


        Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Config.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit;
    }

    public Call<String> newHashCall(String key, String txtid, String amount, String productinfo,
                                    String fullname, String email){
       return mServiceInterface.getHashCall(
               convertPlainString(key),   convertPlainString(txtid),convertPlainString(amount),
               convertPlainString(productinfo), convertPlainString( fullname),  convertPlainString(email));
    }

      // convert aa param into plain text
    public RequestBody convertPlainString(String data){
        RequestBody plainString = RequestBody.create(data, MediaType.parse("text/plain"));//(MediaType.parse("text/plain"), data);
        return  plainString;
    }
}


