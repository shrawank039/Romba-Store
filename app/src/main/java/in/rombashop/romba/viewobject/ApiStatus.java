package in.rombashop.romba.viewobject;

import com.google.gson.annotations.SerializedName;

/**
 * Created by matrixdeveloper on 11/17/17.
 * Contact Email : matrixdeveloper.business@gmail.com
 */

public class ApiStatus {

    @SerializedName("status")
    public final String status;

    @SerializedName("message")
    public final String message;

    public ApiStatus(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
