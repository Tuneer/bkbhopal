package com.bkbhopal

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bkbhopal.databinding.FragmentSecondBinding
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var itemsViewModel: ItemsViewModel? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        itemsViewModel = Gson().fromJson(arguments?.getString("item"), ItemsViewModel::class.java)


        return binding.root

    }
    fun onClickApp(pack: String?, bitmap: Bitmap) {
        val pm = this.requireActivity()!!.packageManager
        try {
            val bytes = ByteArrayOutputStream()
            bitmap.compress(CompressFormat.JPEG, 100, bytes)
            val path = MediaStore.Images.Media.insertImage(
                this.requireActivity()!!.contentResolver,
                bitmap,
                "Title",
                null
            )
            val imageUri = Uri.parse(path)
            val info = pm.getPackageInfo(pack!!, PackageManager.GET_META_DATA)
            val waIntent = Intent(Intent.ACTION_SEND)
            waIntent.type = "image/*"
            waIntent.setPackage(pack)
            waIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
            waIntent.putExtra(Intent.EXTRA_TEXT, pack)
            waIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            this.requireActivity().startActivity(Intent.createChooser(waIntent, "Share with"))
        } catch (e: Exception) {
            Log.e("Error on sharing", "$e ")
            Toast.makeText(context, "App not Installed", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (itemsViewModel!=null){
            val ndate = formatStringToDate(itemsViewModel!!.lastSubbmitDate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "UTC")
            // sets the text to the textview from our itemHolder class
            binding.username.text = itemsViewModel!!.name
            binding.vehiclemake.text = itemsViewModel!!.address
            binding.vehiclemodel.text = itemsViewModel!!.phone
            binding.rides.text = formatDateToString(ndate, "EEE, dd MMM hh:mm a", "")
        }

        binding.buttonSecond.setOnClickListener {
            val v = binding.pp

            val bitmap = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
            val c = Canvas(bitmap)
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c)
            onClickApp("com.whatsapp",bitmap)
           // findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}