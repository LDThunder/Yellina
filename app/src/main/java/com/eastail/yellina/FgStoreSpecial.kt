package com.eastail.yellina

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.eastail.yellina.Database.DbHelper
import com.google.android.material.button.MaterialButton
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.xml.KonfettiView
import java.util.concurrent.TimeUnit

class FgStoreSpecial : Fragment(R.layout.fg_store_special) {
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = FgStoreSpecial()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.fgStoreRecSpecial)
        initAdapter()
    }

    private fun initAdapter() {
        val dbHelper = DbHelper(context)
        val storeList = dbHelper.getStore()
        val adapter = StoreRecAdapter(storeList.takeLast(8))
        adapter.setListener(object : OnStoreClickListener {
            override fun onStoreClick(
                cost: Int,
                position: Int,
                checking: Boolean,
                view: View
            ): Boolean {
                (activity as MainActivity).soundBuy()
                val coins = requireActivity().findViewById<TextView>(R.id.tvMoney)
                val tvPlus = requireActivity().findViewById<TextView>(R.id.tvPlus)
                tvPlus.text = "-$cost"
                val curCoins = Integer.parseInt(coins.text.toString())
                if ((curCoins - cost) > 0) {
                    dbHelper.updateStore(position.toLong(), 0, 0)
                    coins.text = (curCoins - cost).toString()
                    tvPlus.visibility = View.VISIBLE
                    YoYo.with(Techniques.Pulse)
                        .duration(350)
                        .interpolate(DecelerateInterpolator())
                        .playOn(coins)
                    YoYo.with(Techniques.BounceInDown)
                        .duration(450)
                        .interpolate(DecelerateInterpolator())
                        .playOn(tvPlus)
                    YoYo.with(Techniques.FadeOut)
                        .duration(451)
                        .interpolate(AccelerateDecelerateInterpolator())
                        .playOn(tvPlus)
                    if (position == 13) {
                        (activity as MainActivity).wolfSound()
                        /* Dialog inflation */
                        val dialogBinding = layoutInflater.inflate(R.layout.dialog_alime, null)
                        val dialog = context?.let { x -> Dialog(x) }
                        /* Dialog logic */
                        with(dialog) {
                            /* Init parameters */
                            this?.setContentView(dialogBinding)
                            this?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            this?.setCancelable(false)
                            this?.show()
                            /* Find */
                            val buttonPrimenit =
                                this?.findViewById<MaterialButton>(R.id.primenitCel)
                            /* Применение */
                            buttonPrimenit?.setOnClickListener {
                                Toast.makeText(
                                    context,
                                    "Шанс появления при нажатии - 3%",
                                    Toast.LENGTH_SHORT
                                ).show()
                                this?.dismiss()
                            }

                        }
                    }
                    if (position == 18) {
                        context?.deleteDatabase("Yellina")
                        requireActivity().findViewById<KonfettiView>(R.id.konfetti).start(
                            listOf(
                                Party(
                                    emitter = Emitter(
                                        duration = 100,
                                        TimeUnit.MILLISECONDS
                                    ).max(100), position = Position.Relative(0.5, 0.3)
                                )
                            )
                        )
                        /* Dialog inflation */
                        val dialogBinding = layoutInflater.inflate(R.layout.dialog_win, null)
                        val dialog = context?.let { x -> Dialog(x) }
                        /* Dialog logic */
                        with(dialog) {
                            /* Init parameters */
                            this?.setContentView(dialogBinding)
                            this?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                            this?.setCancelable(false)
                            this?.show()
                            /* Find */
                            val buttonPrimenit =
                                this?.findViewById<MaterialButton>(R.id.primenitCel)
                            /* Применение */
                            buttonPrimenit?.setOnClickListener {
                                (activity as MainActivity).finish()
                                this?.dismiss()
                            }

                        }
                    }
                    if (position == 14){
                        Toast.makeText(
                            context,
                            "Зажмите главную картинку, чтобы сменить её",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return true
                } else {
                    return false
                }
            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }
}