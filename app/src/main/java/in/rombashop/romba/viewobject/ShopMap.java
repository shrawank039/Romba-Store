package in.rombashop.romba.viewobject;

import androidx.annotation.NonNull;
import androidx.room.Entity;

/**
 * Created by matrixdeveloper on 3/19/19.
 * Contact Email : matrixdeveloper.business@gmail.com
 */

@Entity(primaryKeys = "id")
public class ShopMap {

    @NonNull
    public final String id;

    public final String mapKey;

    public final String productId;

    public final int sorting;

    public final String addedDate;

    public ShopMap(@NonNull String id, String mapKey, String productId, int sorting, String addedDate) {
        this.id = id;
        this.mapKey = mapKey;
        this.productId = productId;
        this.sorting = sorting;
        this.addedDate = addedDate;
    }
}

