package com.example.text_it.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.text_it.R
import com.example.text_it.adapater.StatusAdapter
import com.example.text_it.dataClass.Status_dataclass
import com.google.firebase.firestore.FirebaseFirestore



class Message : Fragment() {
    private lateinit var db: FirebaseFirestore
    private lateinit var statusAdapter: StatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFirestore()
        statusAdapter = StatusAdapter(mutableListOf())
        setupRecyclerView()
        getStatusFromFirestore()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }


    private fun initFirestore() {
        db = FirebaseFirestore.getInstance()
    }

    private fun setupRecyclerView() {
        // val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        // recyclerView.adapter = statusAdapter
    }

    private fun getStatusFromFirestore() {
        db.collection("status").get()
            .addOnSuccessListener { documents ->
                val statusList = mutableListOf<Status_dataclass>()

                for (document in documents) {
                    val status = document.toObject(Status_dataclass::class.java)
                    statusList.add(status)
                }
                updateAdapterWithStatusList(statusList)
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents: ", exception)
            }
    }

    private fun updateAdapterWithStatusList(statusList: List<Status_dataclass>) {
        statusAdapter.setStatusList(statusList)
    }
}