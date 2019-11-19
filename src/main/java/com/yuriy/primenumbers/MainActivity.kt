package com.yuriy.primenumbers

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.ceil

class MainActivity : AppCompatActivity(), InputDialog.InputDialogListener {

    private var runningState = false
    private var lastFoundNumber = 0
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
                if (lastFoundNumber != 0)
                    clear()
                showInputDialog()
            } else {
                switchState(false)
            }
        }
    }

    override fun onDialogStartClick(dialog: DialogFragment, inputData: Int) {
        switchState(true)
        findPrimes(inputData)
    }

    private fun switchState(state: Boolean) {
        runningState = state
        if (runningState) {
            id_start_search_button.text = getString(R.string.stop)
        } else {
            id_start_search_button.text = getString(R.string.start)
        }
    }

    private fun findPrimes(upperLimit: Int) {
        updateUI()
        val handler = Handler()
        val halfSize = ceil(upperLimit.toDouble() / 2).toInt()
        Thread {
            val sieveArray: Array<Boolean> = Array(halfSize) { _ -> true }

            for (n in 0..sieveArray.lastIndex) {
                if (runningState) {
                    val currentStep = n * 2 + 3
                    if (sieveArray[n]) {
                        for (k in n..sieveArray.lastIndex step currentStep) {
                            if (currentStep >= sieveArray.lastIndex)
                                break
                            sieveArray[k] = false
                        }
                        primeNumbers.add(n * 2 + 3)
                        lastFoundNumber = n * 2 + 3
                    }
                } else {
                    break
                }
            }
            handler.post {
                id_last_founded_number.text =
                    getString(R.string.last_founded_number, lastFoundNumber)
                switchState(false)
            }
        }.start()
    }

    private fun updateUI() {
        val handler = Handler()
        Thread {
            while (runningState) {
                Thread.sleep(25)
                handler.post {
                    id_numbers_count.text = getString(R.string.numbers_found, primeNumbers.size)
                    id_last_founded_number.text =
                        getString(R.string.last_founded_number, lastFoundNumber)
                    id_numbers_rec_view.adapter?.notifyDataSetChanged()
                    id_numbers_rec_view.scrollToPosition(primeNumbers.lastIndex)
                }
            }
        }.start()
    }

    private fun showInputDialog() {
        val dialog : DialogFragment = InputDialog(this)
        dialog.show(supportFragmentManager, "InputDialogFragment")
    }

    private fun clear() {
        lastFoundNumber = 0
        primeNumbers.clear()
        primeNumbers.add(2)
        id_numbers_rec_view.adapter?.notifyDataSetChanged()
    }
}
