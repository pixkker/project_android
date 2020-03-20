package com.example.firebase_authen


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.fragment_recycle_view.*
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 */
class recycle_view : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycle_view, container, false)
        // Inflate the layout for this fragment

        val jsonString : String = loadJsonFromAsset("recipes.json", activity!!.baseContext).toString()
        val json = JSONObject(jsonString)
        val jsonArray = json.getJSONArray("recipes")

        val recyclerView: RecyclerView = view.findViewById(R.id.recyLayout)

        //ตั้งค่า Layout
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!.baseContext)
        recyclerView.layoutManager = layoutManager

        //ตั้งค่า Adapter
        val adapter = MyRecyclerAdapter(activity!!.baseContext,activity!!.supportFragmentManager,jsonArray)
        recyclerView.adapter = adapter

        val dialog = view.findViewById(R.id.button_open_dialog) as Button


        dialog.setOnClickListener{
            val builder = AlertDialog.Builder(this.context)
            builder.setMessage("Do you like Haikyuu?")
            builder.setPositiveButton("No") { dialog, id ->
                Toast.makeText(
                    this.context,
                    "Thank you", Toast.LENGTH_SHORT
                ).show()
                LoginManager.getInstance().logOut()
                activity!!.supportFragmentManager.popBackStack()
            }
            builder.setNegativeButton("Yes",
                DialogInterface.OnClickListener{ dialog, which ->
                    dialog.dismiss();
                    Toast.makeText(
                        this.context,
                        "Thank you for watching", Toast.LENGTH_SHORT
                    ).show()
                })

            builder.show()
        }

        return view
    }
        private fun loadJsonFromAsset(filename: String, context: Context): String? {
            val json: String?

            try {
                val inputStream = context.assets.open(filename)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = String(buffer, Charsets.UTF_8)
            } catch (ex: java.io.IOException) {
                ex.printStackTrace()
                return null
            }

            return json
        }


}
