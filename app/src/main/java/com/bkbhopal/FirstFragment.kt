package com.bkbhopal

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bkbhopal.CustomAdapter.clickItemListener
import com.bkbhopal.databinding.FragmentFirstBinding
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.model.Drive
import com.google.api.services.forms.v1.Forms
import com.google.api.services.forms.v1.FormsScopes
import com.google.api.services.forms.v1.model.ListFormResponsesResponse
import com.google.auth.oauth2.GoogleCredentials
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.security.GeneralSecurityException
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    var progressDialog: ProgressDialog? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val APPLICATION_NAME = "Alampata"
    private var driveService: Drive? = null
    private var formsService: Forms? = null
    private  var recyclerview :RecyclerView? = null
    private var  listOfItems =  java.util.ArrayList<ItemsViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }


    @SuppressLint("StaticFieldLeak")
   private class AsyncTaskExample(private var fragment:  FirstFragment?) : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            //activity?.MyprogressBar?.visibility = View.VISIBLE
            fragment!!.showProgressDialog("onPreExecute")
        }

        override fun doInBackground(vararg p0: String?): String {

            var result = ""

            val token = fragment!!.getAccessToken()
            if (token != null) {
                fragment!!.readResponses("1E1ou-YmdVLO6_-hxzdR_hVqW9cB2fioSD8zbSmiNvSk", token)
            };
            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            fragment!!.dismissProgressDialog("onPostExecute")
            val adapter = CustomAdapter(fragment!!.listOfItems, fragment!!.itemListener)
               fragment!!.recyclerview!!.adapter = adapter



//            // ArrayList of class ItemsViewModel
//            val data = ArrayList<ItemsViewModel>()
//
//            // This loop will create 20 Views containing
//            // the image with the count of view
////            for (i in 1..20) {
////                data.add(ItemsViewModel(R.drawable.ic_baseline_folder_24, "Item " + i))
////            }
//
//            // This will pass the ArrayList to our Adapter
//            val adapter = CustomAdapter(data)
//
//            // Setting the Adapter with the recyclerview

        }
    }


    var itemListener: clickItemListener = object : clickItemListener {
        override fun onItemClick(itemsViewModel: ItemsViewModel) {
            Toast.makeText(activity, itemsViewModel.name,Toast.LENGTH_SHORT).show()
            val gson = Gson().toJson(itemsViewModel)
            val bundle = Bundle()
            bundle.putString("item", gson)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
        }
    }

    private fun readResponses(formId: String, token: String) : ListFormResponsesResponse? {
        try {
            val response =
                formsService!!.forms().responses().list(formId).setOauthToken(token).execute()
            Log.d("readResponses",response.toPrettyString())

            try {
                var obj = JSONObject(response.toPrettyString())
                val answers: JSONArray = obj.getJSONArray("responses")
//            InstitutionLocations = java.util.ArrayList<InstitutionLocation>()
                for (i in 0 until answers.length()) {
                    val inans: JSONObject = answers.getJSONObject(i)
                    Log.d("question",inans.toString())
                   var ans = inans.getJSONObject("answers");
                    var createTime = inans.getString("createTime");
                    var lastSubmittedTime = inans.getString("lastSubmittedTime");
                    var responseId = inans.getString("responseId");

                    // second Question 1

                    var question1 = ans.getJSONObject("3f7b522a");
                    var questionid1 = question1.getString("questionId");
                    var textAnswers1 = question1.getJSONObject("textAnswers");
                    var finalanswers1 = textAnswers1.getJSONArray("answers");
                    var finalanswersValue1=""
                    for (j in 0 until finalanswers1.length()) {
                        var temp = finalanswers1.getJSONObject(j);
                        finalanswersValue1 = temp.getString("value");
                    }

                    // second Question 2

                    var question2 = ans.getJSONObject("458e9ec2");
                    var questionid2 = question2.getString("questionId");
                    var textAnswers2 = question2.getJSONObject("textAnswers");
                    var finalanswers2 = textAnswers2.getJSONArray("answers");
                    var finalanswersValue2=""
                    for (j in 0 until finalanswers2.length()) {
                        var temp: JSONObject = finalanswers2.getJSONObject(j);
                        finalanswersValue2 = temp.getString("value");
                    }

                    // second Question 3

                    var question3 = ans.getJSONObject("3e555b2b");
                    var questionid3 = question3.getString("questionId");
                    var textAnswers3 = question3.getJSONObject("textAnswers");
                    var finalanswers3 = textAnswers3.getJSONArray("answers");
                    var finalanswersValue3=""
                    for (j in 0 until finalanswers3.length()) {
                        var temp = finalanswers3.getJSONObject(j);
                        finalanswersValue3 = temp.getString("value");
                    }

                    // second Question 4

                    var question4 = ans.getJSONObject("778b574a");
                    var questionid4 = question4.getString("questionId");
                    var textAnswers4 = question4.getJSONObject("textAnswers");
                    var finalanswers4 = textAnswers4.getJSONArray("answers");
                    var finalanswersValue4=""
                    for (j in 0 until finalanswers4.length()) {
                        var temp = finalanswers4.getJSONObject(j);
                        finalanswersValue4 = temp.getString("value");
                    }
                    var itemsViewModel = ItemsViewModel(finalanswersValue4,finalanswersValue1, finalanswersValue2,finalanswersValue3,createTime,lastSubmittedTime,responseId)
                    this.listOfItems.add(itemsViewModel)
                    Log.d("itemsViewModel", itemsViewModel.toString())
                }


               // val adapter = CustomAdapter(listOfItems)
              //  recyclerview!!.adapter = adapter
            }
            catch (e: Exception)
            {
                e.printStackTrace()
            }



            return response

        }
        catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            this.recyclerview = binding.userlist
            listOfItems =  java.util.ArrayList<ItemsViewModel>()

            // this creates a vertical layout Manager
            this.recyclerview!!.layoutManager = LinearLayoutManager(this.requireActivity())



            val jsonFactory: JsonFactory = JacksonFactory.getDefaultInstance()
//            driveService = Builder(
//                GoogleNetHttpTransport.newTrustedTransport(),
//                jsonFactory, null
//            )
            //  .setApplicationName(APPLICATION_NAME).build()
            formsService = Forms.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                jsonFactory, null
            )
                .setApplicationName(APPLICATION_NAME).build()
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
         AsyncTaskExample(this).execute();

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    @Throws(IOException::class)
    fun getAccessToken(): String? {

//        val ins : InputStream = resources.openRawResource(
//           R.raw.bkbhopal
//        )
        try {
            val ins1 : InputStream = this.requireActivity().getAssets().open("bk.json")
            //val ins1 : InputStream = this.javaClass.classLoader.getResourceAsStream("bkbhopal.json")
            // val url : URL = URL.parse("android.resource://com.bkbhopal/raw/bkbhopal")
            //val strm: InputStream = Main::class.java.getResourceAsStream("bkbhopal.json")
            val credential: GoogleCredentials = GoogleCredentials.fromStream(
                ins1
            ).createScoped(FormsScopes.all())
            return if (credential.getAccessToken() != null) credential.getAccessToken()
                .getTokenValue() else credential.refreshAccessToken().getTokenValue()
        }
        catch (e : Exception){
            return e.message
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun showProgressDialog(FuntionName: String) {
        if (progressDialog == null) {
            Log.d("ProgressDialog", "progress null created $FuntionName")
            progressDialog = ProgressDialog(requireActivity(), R.drawable.spinner)
            progressDialog!!.show()
        }
        else {
            Log.d(
                "ProgressDialog",
                "progress not null not created $FuntionName"
            )
        }
    }

    fun dismissProgressDialog(funtionName: String) {
        // if (apiCallCount<=0) {
        if (progressDialog != null) {
            Log.d("ProgressDialog", "progress not null dismiss $funtionName")
            progressDialog?.dismiss()
            progressDialog = null
        } else {
            Log.d("ProgressDialog", "progress null not dismiss $funtionName")
        }
        // }
    }

}