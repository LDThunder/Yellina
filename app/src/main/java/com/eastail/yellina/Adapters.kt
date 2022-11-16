package com.eastail.yellina

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.airbnb.lottie.LottieAnimationView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.eastail.yellina.Database.Location
import com.eastail.yellina.Database.Store
import kotlin.math.roundToInt

interface OnStoreClickListener {
    fun onStoreClick(cost: Int, position: Int, checking: Boolean, view: View): Boolean
}

interface OnLocationClickListener {
    fun onLocationClick(isLocated: Boolean, isTested: Boolean, position: Int)
}

// Location
@SuppressLint("SetTextI18n")
class LocationRecAdapter(private val locList: List<Location>) :
    RecyclerView.Adapter<LocationRecAdapter.LocHolder>() {
    private lateinit var locationClickListener: OnLocationClickListener

    fun setListener(listener: OnLocationClickListener) {
        locationClickListener = listener
    }

    inner class LocHolder(item: View) : RecyclerView.ViewHolder(item) {
        val igLocationSmall = item.findViewById(R.id.igLocationSmall) as ImageView
        val igLocationBig = item.findViewById(R.id.igLocationBig) as ImageView
        val tvLocation = item.findViewById(R.id.tvLocation) as TextView
        val locationCardView = item.findViewById(R.id.locationCardView) as CardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return LocHolder(view)
    }

    override fun onBindViewHolder(holder: LocHolder, position: Int) {
        /* Extensions */
        fun locationShakeAnim() {
            YoYo.with(Techniques.Shake)
                .duration(260)
                .playOn(holder.locationCardView)
            YoYo.with(Techniques.Shake)
                .duration(260)
                .playOn(holder.tvLocation)
            YoYo.with(Techniques.Shake)
                .duration(260)
                .playOn(holder.igLocationSmall)
        }
        fun locationClikAnim() {
            YoYo.with(Techniques.Pulse)
                .duration(270)
                .playOn(holder.locationCardView)
            YoYo.with(Techniques.Pulse)
                .duration(200)
                .playOn(holder.tvLocation)
            YoYo.with(Techniques.Pulse)
                .duration(200)
                .playOn(holder.igLocationSmall)
        }

        /* Apply */
        holder.itemView.apply {
            holder.igLocationSmall.setImageResource(locList[position].image)
            holder.igLocationBig.setImageResource(locList[position].imageBig)
            holder.tvLocation.text = locList[position].title
            if (locList[position].isLocated) {
                holder.locationCardView.setCardBackgroundColor(Color.parseColor("#A886E44D"))
            }
        }

        /* Click Listener */
        holder.igLocationBig.setOnClickListener {
            locationClickListener.onLocationClick(
                locList[position].isLocated,
                locList[position].isTested,
                position
            )

            /* Анимации */
            if (locList[position].isLocated) {
                locationShakeAnim()
            } else {
                locationClikAnim()
            }


        }


    }

    override fun getItemCount(): Int = locList.size
}

// Store
@SuppressLint("SetTextI18n")
class StoreRecAdapter(private var storeList: List<Store>) :
    RecyclerView.Adapter<StoreRecAdapter.StoreHolder>() {
    /* Lateinit */
    private lateinit var storeClickListener: OnStoreClickListener
    fun setListener(listener: OnStoreClickListener) {
        storeClickListener = listener
    }

    /* Holder (Объявления) */
    inner class StoreHolder(item: View) : RecyclerView.ViewHolder(item) {
        val expandableLayout: ConstraintLayout = item.findViewById(R.id.expandableLayout)
        val expansionLayout: ConstraintLayout = item.findViewById(R.id.expansion)
        val particle: LottieAnimationView = item.findViewById(R.id.clickParticle)
        val lottieTextUp: View = item.findViewById(R.id.lottieTextUp)
        val lottieArrowUp: View = item.findViewById(R.id.lottieArrowUp)
        val tvIdleUp: TextView = item.findViewById(R.id.tvIdleUp)
        val igWeapon: ImageView = item.findViewById(R.id.igWeapon)
        val igColor: ImageView = item.findViewById(R.id.igColor)
        val tvWeapon: TextView = item.findViewById(R.id.tvWeapon)
        val tvCost: TextView = item.findViewById(R.id.tvCost)
        val tvBought: TextView = item.findViewById(R.id.tvBought)
        val buttonBuy: Button = item.findViewById(R.id.buttonBuy)
        val desc: TextView = item.findViewById(R.id.tvDescription)
        val cardView: CardView = item.findViewById(R.id.cardView)
        val lottieCoin: LottieAnimationView = item.findViewById(R.id.lottieCoinDesc)
    }

    /* OnCreate (Не нужно) */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreRecAdapter.StoreHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        return StoreHolder(view)
    }

    /* Присвоение */
    override fun onBindViewHolder(holder: StoreHolder, position: Int) {
        /* Анимации */
        fun shakeBuy() {
            YoYo.with(Techniques.Shake)
                .duration(250)
                .playOn(holder.buttonBuy)
            YoYo.with(Techniques.Shake)
                .duration(180)
                .playOn(holder.tvCost)
            YoYo.with(Techniques.Shake)
                .duration(180)
                .playOn(holder.lottieCoin)
        }
        fun pulseBuy() {
            YoYo.with(Techniques.Pulse)
                .duration(300)
                .playOn(holder.buttonBuy)
            YoYo.with(Techniques.Pulse)
                .duration(200)
                .playOn(holder.tvCost)
            YoYo.with(Techniques.Pulse)
                .duration(200)
                .playOn(holder.lottieCoin)
            YoYo.with(Techniques.Pulse)
                .duration(200)
                .playOn(holder.tvBought)
        }
        fun lvlUp() {
            YoYo.with(Techniques.Pulse)
                .duration(400)
                .playOn(holder.tvWeapon)
            YoYo.with(Techniques.Pulse)
                .duration(400)
                .playOn(holder.tvIdleUp)
            YoYo.with(Techniques.Pulse)
                .duration(400)
                .playOn(holder.tvBought)
            YoYo.with(Techniques.Pulse)
                .duration(400)
                .playOn(holder.lottieArrowUp)
            YoYo.with(Techniques.Pulse)
                .duration(400)
                .playOn(holder.lottieTextUp)
            YoYo.with(Techniques.Pulse)
                .duration(400)
                .playOn(holder.igWeapon)
        }

        /* Анимации пассивные */
        YoYo.with(Techniques.Pulse)
            .duration(6000)
            .repeat(YoYo.INFINITE)
            .pivot(133F, 71F)
            .playOn(holder.igWeapon)

        /* Вид Special */
        if (storeList[position].idleUp == 0) {
            holder.tvIdleUp.text = "      Эффект"
            holder.lottieTextUp.visibility = View.GONE
            holder.lottieArrowUp.visibility = View.GONE
            holder.tvBought.visibility = View.GONE
        }

        /* Ресурсы */
        holder.itemView.apply {
            /* Отображение */
            holder.igWeapon.setImageResource(storeList[position].image)
            holder.igColor.setImageResource(storeList[position].imageColor)
            holder.tvWeapon.text = storeList[position].title
            holder.tvCost.text = when (storeList[position].cost) {
                3500 -> "3.500"
                15000 -> "15.000"
                50000 -> "50.000"
                125000 -> "125.000"
                350000 -> "350.000"
                600000 -> "600.000"
                3000000 -> "3.000.000"
                100000000 -> "100.000.000"
                else -> storeList[position].cost.toString()
            }
            if (storeList[position].idleUp != 0) holder.tvIdleUp.text =
                "${storeList[position].idleUp}"
            holder.desc.text = storeList[position].description
            holder.tvBought.text = "x" + storeList[position].isBought.toString()
            val xy = Integer.parseInt(holder.tvBought.text.drop(1).toString())
            when {
                xy in 10..24 -> {
                    holder.tvWeapon.text = holder.tvWeapon.text.toString() + "+"
                    holder.tvWeapon.setTextColor(Color.parseColor("#397676"))
                    holder.tvIdleUp.text = (storeList[position].idleUp * 1.25).toString().take(5)
                }
                xy in 25..49 -> {
                    holder.tvWeapon.text = "δ-" + holder.tvWeapon.text.toString()
                    holder.tvWeapon.setTextColor(Color.parseColor("#356D6D"))
                    holder.tvIdleUp.text = (storeList[position].idleUp * 1.5).toString().take(5)
                }
                xy in 50..74 -> {
                    holder.tvWeapon.text = "π-" + holder.tvWeapon.text.toString()
                    holder.tvWeapon.setTextColor(Color.parseColor("#2A5656"))
                    holder.tvIdleUp.text = (storeList[position].idleUp * 1.75).toString().take(5)
                }
                xy in 75..99 -> {
                    holder.tvWeapon.text = "ζ-" + holder.tvWeapon.text.toString()
                    holder.tvWeapon.setTextColor(Color.parseColor("#2A5656"))
                    holder.tvIdleUp.text = (storeList[position].idleUp * 2.0).toString().take(5)
                }
                xy in 100..249 -> {
                    holder.tvWeapon.text = "ψ-" + holder.tvWeapon.text.toString()
                    holder.tvWeapon.setTextColor(Color.parseColor("#1E4B4B"))
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#CC2ECC71"))
                    holder.tvIdleUp.text = (storeList[position].idleUp * 2.25).toString().take(5)
                }
                xy in 250..499 -> {
                    holder.tvWeapon.text = "Σ-" + holder.tvWeapon.text.toString()
                    holder.tvWeapon.setTextColor(Color.parseColor("#163737"))
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#99FFFC00"))
                    holder.tvIdleUp.text = (storeList[position].idleUp * 2.5).toString().take(5)
                }
                xy > 500 -> {
                    holder.tvWeapon.text = "Ω-" + holder.tvWeapon.text.toString()
                    holder.tvWeapon.setTextColor(Color.parseColor("#223322"))
                    holder.cardView.setCardBackgroundColor(Color.parseColor("#E1223322"))
                    holder.tvIdleUp.text = (storeList[position].idleUp * 3.0).toString().take(5)
                }
            }

            /* Отображение купленных */
            if (storeList[position].id > 10 && !storeList[position].isAble) {
                holder.buttonBuy.isEnabled = false
                holder.buttonBuy.text = "Куплено"
                holder.buttonBuy.setTextColor(Color.parseColor("#94D1D3"))
                holder.tvCost.setTextColor(Color.parseColor("#91D1D1"))
                holder.cardView.setCardBackgroundColor(Color.parseColor("#BC8BF3F3")) // Светло-морской
            }
        }

        /* Анимация-кликлисенер */
        holder.buttonBuy.setOnClickListener {
            /* Special */
            if (storeList[position].id > 10) {
                if (storeList[position].isAble && holder.buttonBuy.text == "Купить") {
                    val isEnoughChecking = storeClickListener.onStoreClick(
                        storeList[position].cost,
                        storeList[position].id,
                        true,
                        holder.itemView
                    )
                    if (isEnoughChecking) {
                        holder.particle.playAnimation()
                        pulseBuy()
                        holder.buttonBuy.isEnabled = false
                        holder.buttonBuy.text = "Куплено"
                        holder.buttonBuy.setTextColor(Color.parseColor("#94D1D3"))
                        holder.cardView.setCardBackgroundColor(Color.parseColor("#BC8BF3F3")) // Светло-морской
                        holder.tvCost.setTextColor(Color.parseColor("#BC8BF3F3")) // Светло-морской
                    } else {
                        shakeBuy()
                    }
                } else {
                    shakeBuy()
                }
            }
            /* Weapon */
            if (storeList[position].id <= 10 && holder.buttonBuy.text == "Купить") {
                val isEnoughChecking = storeClickListener.onStoreClick(
                    storeList[position].cost,
                    storeList[position].id,
                    true,
                    holder.itemView
                )
                if (isEnoughChecking) {
                    val xyx = Integer.parseInt(holder.tvBought.text.drop(1).toString()) + 1
                    val xyko = Integer.parseInt(holder.tvCost.text.toString()).toDouble() * 1.05
                    holder.tvBought.text = "x$xyx"
                    holder.tvCost.text = xyko.roundToInt().toString()
                    when (xyx) {
                        10 -> {
                            holder.tvWeapon.text = storeList[position].title + "+"
                            holder.tvWeapon.setTextColor(Color.parseColor("#397676"))
                            holder.tvIdleUp.text =
                                (storeList[position].idleUp * 1.25).toString().take(5)
                            lvlUp()

                        }
                        25 -> {
                            holder.tvWeapon.text = "δ-" + storeList[position].title
                            holder.tvWeapon.setTextColor(Color.parseColor("#356D6D"))
                            holder.tvIdleUp.text =
                                (storeList[position].idleUp * 1.5).toString().take(5)
                            lvlUp()
                        }
                        50 -> {
                            holder.tvWeapon.text = "π-" + storeList[position].title
                            holder.tvWeapon.setTextColor(Color.parseColor("#2A5656"))
                            holder.tvIdleUp.text =
                                (storeList[position].idleUp * 1.75).toString().take(5)
                            lvlUp()
                        }
                        75 -> {
                            holder.tvWeapon.text = "ζ-" + storeList[position].title
                            holder.tvWeapon.setTextColor(Color.parseColor("#2A5656"))
                            holder.tvIdleUp.text =
                                (storeList[position].idleUp * 2.0).toString().take(5)
                            lvlUp()
                        }
                        100 -> {
                            holder.tvWeapon.text = "ψ-" + storeList[position].title
                            holder.tvWeapon.setTextColor(Color.parseColor("#1E4B4B"))
                            holder.cardView.setCardBackgroundColor(Color.parseColor("#CC1E4B4B"))
                            holder.tvIdleUp.text =
                                (storeList[position].idleUp * 2.25).toString().take(5)
                            lvlUp()
                            YoYo.with(Techniques.Pulse)
                                .duration(600)
                                .playOn(holder.cardView)
                        }
                        250 -> {
                            holder.tvWeapon.text = "Σ-" + storeList[position].title
                            holder.tvWeapon.setTextColor(Color.parseColor("#163737"))
                            holder.cardView.setCardBackgroundColor(Color.parseColor("#99FFFC00"))
                            holder.tvIdleUp.text =
                                (storeList[position].idleUp * 2.5).toString().take(5)
                            lvlUp()
                            YoYo.with(Techniques.Pulse)
                                .duration(600)
                                .playOn(holder.cardView)
                        }
                        500 -> {
                            holder.tvWeapon.text = "Ω-" + storeList[position].title
                            holder.tvWeapon.setTextColor(Color.parseColor("#163737"))
                            holder.cardView.setCardBackgroundColor(Color.parseColor("#E1223322"))
                            holder.tvIdleUp.text =
                                (storeList[position].idleUp * 3.0).toString().take(5)
                            lvlUp()
                            YoYo.with(Techniques.Pulse)
                                .duration(600)
                                .playOn(holder.cardView)
                        }
                    }
                    storeClickListener.onStoreClick(
                        storeList[position].cost,
                        storeList[position].id,
                        false,
                        holder.itemView
                    )
                    pulseBuy()
                    holder.particle.playAnimation()
                } else {
                    shakeBuy()
                }
            }
        }

        /* Описание */
        holder.expandableLayout.setOnClickListener {
            if (holder.expansionLayout.visibility == View.GONE) {
                holder.expansionLayout.visibility = View.VISIBLE
            } else {
                holder.expansionLayout.visibility = View.GONE
            }
        }
    }

    /* Item Count */
    override fun getItemCount(): Int = storeList.size
}

class ViewPagerAdapter(fa: FragmentActivity, private val fragments: ArrayList<Fragment>) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}