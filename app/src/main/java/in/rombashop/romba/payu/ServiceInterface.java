package in.rombashop.romba.payu;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import static in.rombashop.romba.Config.SERVER_main_folder;


public interface ServiceInterface {

    @Multipart
    @POST(SERVER_main_folder+"new_hash.php")
    Call<String> getHashCall(
            @Part("key") RequestBody key,
            @Part("txnid") RequestBody txnid,
            @Part("amount") RequestBody amount,
            @Part("productinfo") RequestBody producinfo,
            @Part("firstname") RequestBody firstname,
            @Part("email") RequestBody email
    );

}
