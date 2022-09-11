package com.example.payphone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() ,AdapterView.OnItemClickListener{
    private var token = "SV8SkcxKFyBWmsB3ZsN-2dpfNK1WGZSw2Y-2p6QETUIE9B7qnb0oOU_xvhTnO_fxtjxpmp" +
            "XXNxrGuL5xgdyf3LzcGUd2-O1DCrBn8R-COcFE3gDKu9xPzXDJIk9Y83Q003X-Cf-nnyeHG0gMZK966UMmd_" +
            "O0qeaE2GvwwSwrt-5OV-OTx0suoZnPQlI9HVGhShW9vxqUx6lrnjN7f0Mo9a-3bqaWj1VWOPhOOrm69wyy8svw" +
            "LayhsCE_C_i3mJfTtTDCxic1Cm56K5ak0KYBXOoUqgv_GTcMlu_2JThRzkv-NlyDgvI-pYsfgZsNHa1InaM9fulzh" +
            "-vnR2vAduOcwxcqayc"
    private var endPoiniapiRegions="https://pay.payphonetodoesposible.com/api/Regions"
    private var endPointapiSale="https://pay.payphonetodoesposible.com/api/Sale"
    private var codes: ArrayList<String> = ArrayList()
    private lateinit var txtcode : AutoCompleteTextView
    private var adapters: ArrayAdapter<String>? = null

    private lateinit var txtreference: TextInputEditText
    private lateinit var txtphone: TextInputEditText
    private lateinit var txtamount: TextInputEditText
    private var selectcode:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RecyclerView()
        txtreference = findViewById(R.id.txtreference)
        txtphone=findViewById(R.id.txtphonenumber)
        txtamount=findViewById(R.id.txtamount)


        Regions()

        adapters= ArrayAdapter(this,R.layout.list_item,codes)
        txtcode.setAdapter(adapters)
        txtcode.setOnItemClickListener(this)
        RecyclerView()
    }


    private fun Regions() {
        txtcode =findViewById(R.id.txtcode)
        val queue = Volley.newRequestQueue(this)
        val JsonArrayRq = object: JsonArrayRequest(Method.GET,endPoiniapiRegions,null,
            { response->
                for (i in 0  until response.length()) {
                    val Jbject: JSONObject = response.getJSONObject(i)
                    val Id: String = Jbject.getString("prefixNumber")
                    codes.add(Id)
                }
            },
            Response.ErrorListener { error-> MessageLong(error.toString())
                Log.d("TAG", error.toString())
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                headers.put("Authorization", "Bearer $token")
                return headers
            }
        }
        queue.add(JsonArrayRq)
    }

    private fun MessageLong (message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun PayPayphone(v: View?) {

        v.toString()
        val phone: String = txtphone.text.toString()
        val countryCode: String? = selectcode
        val reference: String = txtreference.text.toString()
        val transactionId: String = UUID.randomUUID().toString()
        val currency = "USD"
        val amount = txtamount.text.toString().toFloat()
        val valor = (amount * 100).toInt()


        val queue = Volley.newRequestQueue(this)

        val JsonPost = JSONArray(
            """[{"amount":$valor,"amountWithoutTax":$valor,
                "clientTransactionId":"$transactionId", "countryCode":"$countryCode",
                "currency":"$currency", "phoneNumber":"$phone",
                "reference":"$reference"}]""".trimMargin()
        )
        val JsonArrayRq: JsonObjectRequest = object : JsonObjectRequest(
            Method.POST,
            endPointapiSale,
            JsonPost.getJSONObject(0),  // info del pago
            Response.Listener { response ->
                try {
                    MessageLong("Payment Requested")
                    RecyclerView()
                } catch (e: JSONException) {
                    println(e.toString())
                }
            },
            Response.ErrorListener { error -> println(error.toString()) }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = java.util.HashMap()
                params["Authorization"] = "Bearer $token"
                return params
            }
        }
        queue.add(JsonArrayRq)
    }
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val item: String = p0?.getItemAtPosition(p2).toString()
        selectcode=item
    }

    fun RecyclerView()
    {
        val queue = Volley.newRequestQueue(this)
        val url = "https://mocki.io/v1/0667e56e-d410-4d78-9e92-37362a2ee898"

        val stringReq = StringRequest(
            Request.Method.GET, url,
            { response ->
                val str= JSONArray(response)

                var aux=0
                val n=str.length()


                val transactionId= arrayListOf<String>()
                val email= arrayListOf<String>()
                val phoneNumber= arrayListOf<String>()
                val transactionStatus= arrayListOf<String>()
                val amount = arrayListOf<String>()
                val date = arrayListOf<String>()
                val reference = arrayListOf<String>()


                while (aux<n)
                {
                    val obj: JSONObject =str.getJSONObject(aux)

                    transactionId.add(obj.getString("transactionId"))
                    email.add(obj.getString("email"))
                    phoneNumber.add(obj.getString("phoneNumber"))
                    transactionStatus.add(obj.getString("transactionStatus"))
                    amount.add(obj.getString("amount"))
                    date.add(obj.getString("date"))
                    reference.add(obj.getString("reference"))
                    aux++

                }
                val recyclerView_ : RecyclerView =findViewById(R.id.RcviewHistorial)
                val adapter_=HistoryAdapter(transactionId,email, phoneNumber,
                    transactionStatus, amount, date, reference)

                recyclerView_.layoutManager= LinearLayoutManager(this)
                recyclerView_.adapter=adapter_
            },
            { Log.d("API", "that didn't work") })
        queue.add(stringReq)
    }
}