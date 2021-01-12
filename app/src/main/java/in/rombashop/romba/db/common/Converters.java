package in.rombashop.romba.db.common;

import androidx.room.TypeConverter;
import java.util.Date;

/**
 * Created by matrixdeveloper on 12/27/17.
 * Contact Email : matrixdeveloper.business@gmail.com
 */


public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
