package in.rombashop.romba.viewobject;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity(primaryKeys = "id")
public class City {

    @NonNull
    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("country_id")
    public String countryId;

    @SerializedName("status")
    public String status;

    @SerializedName("added_date")
    public String addedDate;

    @SerializedName("added_user_id")
    public String addedUserId;

    @SerializedName("updated_date")
    public String updatedDate;

    @SerializedName("updated_user_id")
    public String updatedUserId;

    @SerializedName("updated_flag")
    public String updatedFlag;

    @SerializedName("added_date_str")
    public String addedDateStr;


    public City(@NonNull String id, String name, String countryId, String status, String addedDate, String addedUserId, String updatedDate, String updatedUserId, String updatedFlag, String addedDateStr) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
        this.status = status;
        this.addedDate = addedDate;
        this.addedUserId = addedUserId;
        this.updatedDate = updatedDate;
        this.updatedUserId = updatedUserId;
        this.updatedFlag = updatedFlag;
        this.addedDateStr = addedDateStr;
    }
}
