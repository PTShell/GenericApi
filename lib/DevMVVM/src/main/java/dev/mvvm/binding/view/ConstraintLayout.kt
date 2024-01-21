package dev.mvvm.binding.view

import androidx.constraintlayout.widget.Guideline
import androidx.databinding.BindingAdapter

// ===================================
// = ConstraintLayout BindingAdapter =
// ===================================

@BindingAdapter("binding_layoutConstraintGuideBegin")
fun Guideline.bindingLayoutConstraintGuideBegin(margin: Int) {
    this.setGuidelineBegin(margin)
}