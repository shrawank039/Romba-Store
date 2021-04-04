package in.rombashop.romba;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.rombashop.romba.net.PrefManager;

public class SearchActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    EditText inputSearch;
    private static PrefManager prf;
    private RecyclerView recyclerView;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        inputSearch = (EditText) findViewById(R.id.inputSearch);
        prf = new PrefManager(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                searchProductList(String.valueOf(cs));
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    private void searchProductList(String valueOf) {
//        productLists.clear();
//        for (int i=0; i<Global.allProductLists.size(); i++){
//            if (Pattern.compile(Pattern.quote(valueOf), Pattern.CASE_INSENSITIVE).matcher(Global.allProductLists.get(i).getName()).find()){
//                productLists.add(Global.allProductLists.get(i));
//            }
//        }
//        mAdapter.notifyDataSetChanged();
    }


    public void backPress(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}