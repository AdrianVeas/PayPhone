package com.example.payphone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter constructor(transactionId: List<String>,
                                 email: List<String>,
                                 phoneNumber: List<String>,
                                 transactionStatus: List<String>,
                                 amount: List<String>,
                                 date: List<String>,
                                 reference: List<String>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

    val itemtransactionId = transactionId
    val itememail=email
    val itemphoneNumber=phoneNumber
    val item_transactionStatus=transactionStatus
    val itemamount=amount
    val itemdate=date
    val itemreference=reference


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): HistoryAdapter.ViewHolder {
        val v =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.cdhistory, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: HistoryAdapter.ViewHolder, i: Int) {

        viewHolder.transactionId.text = "Pago Nª ${itemtransactionId.get(i)}"
        viewHolder.email.text="Email: ${itememail.get(i)}"
        viewHolder.phoneNumber.text="Celular: ${itemphoneNumber.get(i)}"
        viewHolder.transactionStatus.text="Estado: ${item_transactionStatus.get(i)}"
        viewHolder.amount.text="Monto: ${itemamount.get(i)}"
        viewHolder.amount.text="Fecha: ${itemdate.get(i)}"
        viewHolder.reference.text="Referencia: ${itemreference.get(i)}"
    }

    override fun getItemCount(): Int {
        return itemtransactionId.count()
    }



    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var transactionId : TextView
        var email : TextView
        var phoneNumber : TextView
        var transactionStatus : TextView
        var amount : TextView
        var date : TextView
        var reference : TextView

        init {
            transactionId=itemView.findViewById(R.id.txttransactionIdcd)
            email=itemView.findViewById(R.id.txtemailcd)
            phoneNumber=itemView.findViewById(R.id.txtphonecd)
            transactionStatus=itemView.findViewById(R.id.txttransactionStatuscd)
            amount=itemView.findViewById(R.id.txtamountcd)
            date=itemView.findViewById(R.id.txtdatecd)
            reference=itemView.findViewById(R.id.txtreferencecd)
        }
    }
}