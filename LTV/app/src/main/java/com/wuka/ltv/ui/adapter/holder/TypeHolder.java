package com.wuka.ltv.ui.adapter.holder;

import android.util.TypedValue;
import android.view.View;

import com.wuka.ltv.bean.Type;
import com.wuka.ltv.databinding.AdapterTypeBinding;
import com.wuka.ltv.ui.adapter.PlayerAdapter;

public class TypeHolder extends BaseHolder {

	private final AdapterTypeBinding binding;

	public TypeHolder(PlayerAdapter adapter, AdapterTypeBinding binding) {
		super(binding.getRoot(), adapter);
		this.binding = binding;
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		adapter.setSelected();
		adapter.addCount();
	}

	public void setView(Type item) {
		itemView.setSelected(item.isSelect());
		binding.name.setText(item.getName());
		binding.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, item.getTextSize());
	}
}
