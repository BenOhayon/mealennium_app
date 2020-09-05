package com.benohayon.meallennium.framework.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.benohayon.meallennium.R

object MealenniumPopupManager {

    private fun getInflatedView(context: Context, title: String, message: String): View? {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_popup, null)
        val titleText: TextView = view.findViewById(R.id.popupTitleText)
        val messageText: TextView = view.findViewById(R.id.popupMessageText)
        titleText.text = title
        messageText.text = message

        return view
    }

    fun showInformationPopup(context: Context, title: String, message: String) {
        val alert = AlertDialog.Builder(context, R.style.CustomAlertDialog)
                .setView(getInflatedView(context, title, message))
                .setPositiveButton(context.resources.getString(R.string.mealennium_alert_ok_button_text), null)
                .create()

        alert.setOnShowListener {
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.brilliantBlue))
        }
        alert.show()
    }

    private fun showConfirmationPopup(context: Context, title: String, message: String, onYesClick: (dialog: DialogInterface, which: Int) -> Unit, onNoClick: (dialog: DialogInterface, which: Int) -> Unit) {
        val alert = AlertDialog.Builder(context, R.style.CustomAlertDialog)
                .setView(getInflatedView(context, title, message))
                .setPositiveButton(context.resources.getString(R.string.mealennium_alert_yes_button_text), onYesClick)
                .setNegativeButton(context.resources.getString(R.string.mealennium_alert_no_button_text), onNoClick)
                .create()

        alert.setCanceledOnTouchOutside(false)
        alert.setOnShowListener {
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.brilliantBlue))
            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.brilliantBlue))
        }
        alert.show()

    }

    fun showConfirmationPopup(context: Context, title: String, message: String, onYesClick: (dialog: DialogInterface, which: Int) -> Unit) {
        showConfirmationPopup(context, title, message, onYesClick, { _, _ ->  })
    }

    fun showDiscardDataMessage(context: Context, onYesClick: () -> Unit) {
        showConfirmationPopup(context, context.getString(R.string.mealennium_alert_discard_post_title), context.getString(R.string.mealennium_alert_discard_post_message)) { _: DialogInterface, _: Int ->
            onYesClick()
        }
    }
}