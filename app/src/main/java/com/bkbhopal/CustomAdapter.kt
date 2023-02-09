package com.bkbhopal

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CustomAdapter(private val mList: java.util.ArrayList<ItemsViewModel>,var listener: clickItemListener) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    var mAList:ArrayList<ItemsViewModel> = mList
    var itemListener: clickItemListener = listener

    interface clickItemListener {
        public fun onItemClick(itemsViewModel: ItemsViewModel)
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.inflator_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mAList[position]

        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageResource(ItemsViewModel.image)
       var ndate = formatStringToDate(ItemsViewModel.lastSubbmitDate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "UTC")
        // sets the text to the textview from our itemHolder class
        holder.username.text = ItemsViewModel.name
        holder.address.text = ItemsViewModel.address
        holder.phone.text = ItemsViewModel.phone
        holder.date.text = formatDateToString(ndate, "EEE, dd MMM hh:mm a", "")

        holder.itemView.setOnClickListener {
            itemListener.onItemClick(ItemsViewModel)
        }

    }

    //DATE FUNCTIONS
    fun formatDateToString(
        date: Date?, format: String?,
        timeZone: String?
    ): String? {
// null check
        var timeZone = timeZone
        if (date == null) return null
        // create SimpleDateFormat object with input format
        val sdf = SimpleDateFormat(format)
        // default system timezone if passed null or empty
        if (timeZone == null || "".equals(timeZone.trim { it <= ' ' }, ignoreCase = true)) {
            timeZone = Calendar.getInstance().timeZone.id
        }
        // set timezone to SimpleDateFormat
        sdf.timeZone = TimeZone.getTimeZone(timeZone)
        // return Date in required format with timezone as String
        return sdf.format(date)
    }

    fun formatStringToDate(
        datestring: String?, format: String?,
        timeZone: String?
    ): Date? {
// null check
        var timeZone = timeZone
        if (datestring == null) return null
        // create SimpleDateFormat object with input format
        val sdf = SimpleDateFormat(format)
        // default system timezone if passed null or empty
        if (timeZone == null || "".equals(timeZone.trim { it <= ' ' }, ignoreCase = true)) {
            timeZone = Calendar.getInstance().timeZone.id
        }
        // set timezone to SimpleDateFormat
        sdf.timeZone = TimeZone.getTimeZone(timeZone)
        var date: Date? = null
        try {
            date = sdf.parse(datestring)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

// return Date in required format with timezone as String
        return date
    }
    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mAList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val username: TextView = itemView.findViewById(R.id.username)
        val address: TextView = itemView.findViewById(R.id.address)
        val phone: TextView = itemView.findViewById(R.id.phone)
        val date: TextView = itemView.findViewById(R.id.date)
    }
}