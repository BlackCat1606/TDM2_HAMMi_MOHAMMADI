package tech.salroid.filmy.custom_adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife
import tech.salroid.filmy.R
import tech.salroid.filmy.data_classes.FavouriteData
import tech.salroid.filmy.data_classes.WatchlistData

/*
 * Filmy Application for Android
 * Copyright (c) 2016 Sajal Gupta (http://github.com/salroid).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

class WatchlistAdapter(private val fro: Context, data: List<WatchlistData>) : RecyclerView.Adapter<WatchlistAdapter.Dh>() {

    private val inflater: LayoutInflater
    private var data = ArrayList<WatchlistData>()
    private var clickListener: ClickListener? = null
    private var fav_title: String? = null
    private var fav_id: String? = null
    private var fav_poster: String? = null
    private var longClickListener: WatchlistAdapter.LongClickListener? = null

    init {
        inflater = LayoutInflater.from(fro)
        this.data = ArrayList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Dh {
        val view = inflater.inflate(R.layout.custom_row, parent, false)
        return Dh(view)
    }

    override fun onBindViewHolder(holder: WatchlistAdapter.Dh, position: Int) {

        fav_title = data[position].fav_title
        fav_id = data[position].fav_id
        fav_poster = data[position].fav_poster

        holder.movie_name!!.text = fav_title

        try {
            Glide.with(fro).load(fav_poster).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.movie_poster!!)
        } catch (e: Exception) {
            //Log.d(LOG_TAG, e.getMessage());
        }

    }

    override fun getItemCount(): Int {

        return data.size
    }

    fun setClickListener(clickListener: WatchlistAdapter.ClickListener) {
        this.clickListener = clickListener
    }

    interface ClickListener {

        fun itemClicked(favouriteData: WatchlistData, position: Int)

    }

    fun setLongClickListener(clickListener: WatchlistAdapter.LongClickListener) {
        this.longClickListener = clickListener
    }

    interface LongClickListener {

        fun itemLongClicked(watchlistData: WatchlistData, position: Int)

    }

    inner class Dh(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.title)
        var movie_name: TextView? = null
        @BindView(R.id.poster)
        var movie_poster: ImageView? = null
        @BindView(R.id.main)
        var main: FrameLayout? = null

        init {
            ButterKnife.bind(this, itemView)


            main!!.setOnClickListener {
                if (clickListener != null) {
                    clickListener!!.itemClicked(data[position], position)
                }
            }

            main!!.setOnLongClickListener {
                if (longClickListener != null) {
                    longClickListener!!.itemLongClicked(data[position], position)
                }

                true
            }
        }
    }
}
