package in.rombashop.romba.ui.product.history.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import in.rombashop.romba.R;
import in.rombashop.romba.databinding.ItemHistoryAdapterBinding;
import in.rombashop.romba.ui.common.DataBoundListAdapter;
import in.rombashop.romba.utils.Objects;
import in.rombashop.romba.viewobject.HistoryProduct;

public class HistoryAdapter extends DataBoundListAdapter<HistoryProduct, ItemHistoryAdapterBinding> {

    private final androidx.databinding.DataBindingComponent dataBindingComponent;
    private HistoryClickCallback callback;
    private DataBoundListAdapter.DiffUtilDispatchedInterface diffUtilDispatchedInterface = null;

    public HistoryAdapter(androidx.databinding.DataBindingComponent dataBindingComponent, HistoryClickCallback historyClickCallback) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = historyClickCallback;
    }

    @Override
    protected ItemHistoryAdapterBinding createBinding(ViewGroup parent) {
        ItemHistoryAdapterBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_history_adapter, parent, false,
                        dataBindingComponent);

        binding.getRoot().setOnClickListener(v -> {
            HistoryProduct historyProduct = binding.getHistory();
            if (historyProduct != null && callback != null) {
                callback.onClick(historyProduct);
            }

        });


        return binding;
    }

    @Override
    protected void dispatched() {
        if (diffUtilDispatchedInterface != null) {
            diffUtilDispatchedInterface.onDispatched();
        }
    }

    @Override
    protected void bind(ItemHistoryAdapterBinding binding, HistoryProduct item) {
        binding.setHistory(item);
        binding.historyNameTextView.setText(item.historyName);
    }

    @Override
    protected boolean areItemsTheSame(HistoryProduct oldItem, HistoryProduct newItem) {
        return Objects.equals(oldItem.id, newItem.id)
                && oldItem.historyName.equals(newItem.historyName);
    }

    @Override
    protected boolean areContentsTheSame(HistoryProduct oldItem, HistoryProduct newItem) {
        return Objects.equals(oldItem.id, newItem.id)
                && oldItem.historyName.equals(newItem.historyName);
    }

    public interface HistoryClickCallback {
        void onClick(HistoryProduct historyProduct);
    }
}
