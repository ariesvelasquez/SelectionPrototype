package com.ariesvelasquez.selectionprototype

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable



data class ItemModel(

    private var _name: String? = "",
    private var _isSelected: Boolean = false,
    private var _src: String? = ""

) : BaseObservable() {

    var name: String
        @Bindable get() = _name!!
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }

    var src: String
        @Bindable get() = _src!!
        set(value) {
            _src = value
            notifyPropertyChanged(BR.src)
        }

    var isSelected: Boolean
        @Bindable get() = _isSelected
        set(value) {
            _isSelected = value
            notifyPropertyChanged(BR.selected)
        }
}