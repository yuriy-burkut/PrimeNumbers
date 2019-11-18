package com.yuriy.primenumbers

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_upper_limit.view.*

class InputDialog(private val callback: InputDialogListener) : DialogFragment() {

    interface InputDialogListener {
        fun onDialogStartClick(dialog: DialogFragment, inputData: Int)
    }

    private lateinit var mListener: InputDialogListener
    var inputData: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater: LayoutInflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_upper_limit, null)

        val builder = AlertDialog.Builder(requireActivity())
            .setView(view)
            .setPositiveButton(getString(R.string.start)) { _, _ ->
                inputData = view.id_upper_limit_text_edit.text.toString().toInt()
                callback.onDialogStartClick(this, inputData)
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }

        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = requireActivity() as InputDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(requireActivity().toString() + "must implement InputDialogListener")
        }
    }

}
