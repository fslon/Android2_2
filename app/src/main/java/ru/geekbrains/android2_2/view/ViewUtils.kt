package ru.geekbrains.android2_2.view

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_LONG
) {
    Snackbar.make(this, text, length).setAction(actionText, action).show()
}

fun View.showSnackBarWithoutAction(
    text: String,
    length: Int = Snackbar.LENGTH_LONG
) {
    Snackbar.make(this, text, length).show()
}

fun View.showSnackBarFromResources(
    resourceId: Int,
    length: Int = Snackbar.LENGTH_LONG

) {
    Snackbar.make(this, resourceId, length).show()
}



