package in.rombashop.romba.utils;

/**
 * Created by matrixdeveloper on 7/25/15.
 * Contact Email : matrixdeveloper.business@gmail.com
 */


public interface SelectListener {
    void Select(int position, CharSequence text);
    void Select(int position, CharSequence text, String id);
    /*void Select(View view, int position, CharSequence text, String id, float additional_price);*/
}
