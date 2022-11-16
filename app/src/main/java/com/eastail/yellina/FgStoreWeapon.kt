package com.eastail.yellina

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.eastail.yellina.Database.DbHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FgStoreWeapon : Fragment(R.layout.fg_store_weapon) {
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = FgStoreWeapon()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.fgStoreRecWeapon)
        initAdapter()

    }

    private fun initAdapter() {
        val dbHelper = DbHelper(context)
        val storeList = dbHelper.getStore()
        val adapter = StoreRecAdapter(storeList.take(11))
        adapter.setListener(object : OnStoreClickListener {
            override fun onStoreClick(
                cost: Int,
                position: Int,
                checking: Boolean,
                view: View
            ): Boolean {
                val trueCost = dbHelper.getStore()[position].cost
                if (!checking) {
                    val coins = requireActivity().findViewById<TextView>(R.id.tvMoney)
                    val curCoins = Integer.parseInt(coins.text.toString())
                    if ((curCoins - trueCost) >= 0) {
                        (activity as MainActivity).soundBuy()
                        val tvPlus = requireActivity().findViewById<TextView>(R.id.tvPlus)
                        tvPlus.text = "-$trueCost"

                       GlobalScope.launch {
                            dbHelper.updateStore(
                                position.toLong(),
                                Integer.parseInt(
                                    view.findViewById<TextView>(R.id.tvBought).text.drop(1)
                                        .toString()
                                ),
                                1
                            )
                            dbHelper.updateCost(
                                position.toLong(),
                                Integer.parseInt(view.findViewById<TextView>(R.id.tvCost).text.toString())
                            )
                            Log.d("meMare", "u")
                        }

                        coins.text = (curCoins - trueCost).toString()
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
                    }
                    return ((curCoins - trueCost) - trueCost) >= 0
                } else {
                    val coins = requireActivity().findViewById<TextView>(R.id.tvMoney)
                    val curCoins = Integer.parseInt(coins.text.toString())
                    return (curCoins - trueCost) >= 0
                }
            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }
}