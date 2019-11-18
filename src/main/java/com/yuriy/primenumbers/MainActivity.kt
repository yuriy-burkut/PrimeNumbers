package com.yuriy.primenumbers

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), InputDialog.InputDialogListener {

    private var runningState = false
    private var lastFoundedNumber = 0
    private var upperLimit: Int = 0
    private val primeNumbers = mutableListOf(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        id_numbers_rec_view.apply {
            setHasFixedSize(false)
            layoutManager = GridLayoutManager(this@MainActivity, 5)
            adapter = NumbersAdapter(primeNumbers)
        }

        id_start_search_button.setOnClickListener {
            if (!runningState) {
                showInputDialog()
            } else {
                switchState()
            }
        }
    }

    override fun onDialogStartClick(dialog: DialogFragment, inputData: Int) {
        upperLimit = inputData
        switchState()
        findPrimes()
    }

    private fun switchState() {
        runningState = !runningState
        if (runningState) {
            id_start_search_button.setText(getString(R.string.pause))
        } else {
            when (lastFoundedNumber) {
                0 -> id_start_search_button.setText(getString(R.string.start))
                else -> id_start_search_button.setText(getString(R.string.resume))
            }
        }
    }

    private fun findPrimes() {

        val handler = Handler()
        Thread {
            val sieveArray: Array<Boolean> = Array(upperLimit) { index -> (index % 2) != 0 }

            for (n in 3..sieveArray.lastIndex) {
                if (sieveArray[n]) {
                    for (k in n..sieveArray.lastIndex step n) {
                        sieveArray[k] = false
                    }
                    primeNumbers.add(n)
                    handler.post {
                        id_last_founded_number.text =
                            getString(R.string.last_founded_number, n.toString())
                    }
                }
            }
        }.start()
    }

    private fun showInputDialog() {
        val dialog : DialogFragment = InputDialog(this)
        dialog.show(supportFragmentManager, "InputDialogFragment")
    }
}
