package in.rombashop.romba.viewobject;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Panacea-Soft on 10/27/18.
 * Contact Email : teamps.is.cool@gmail.com
 */

@Entity(primaryKeys = "id")
public class JProductCollectionHeader {


    @SerializedName("message")
    @Expose
    private List<ProductCollectionHeader> message;


    public JProductCollectionHeader(List<ProductCollectionHeader> message) {
        this.message = message;
    }

    public List<ProductCollectionHeader> getMessage() {
        return message;
    }

    public void setMessage(List<ProductCollectionHeader> message) {
        this.message = message;
    }
}
