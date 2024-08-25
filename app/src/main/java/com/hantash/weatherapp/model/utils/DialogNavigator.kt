package com.hantash.weatherapp.model.utils

import androidx.fragment.app.FragmentManager
import com.hantash.weatherapp.view.dialog.ServerErrorDialogFragment

class DialogNavigator(
    private val fragmentManager: FragmentManager
) {

    fun showServerError() {
        fragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }

}