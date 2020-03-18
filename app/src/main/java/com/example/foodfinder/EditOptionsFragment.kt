package com.example.foodfinder

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditOptionsFragment : Fragment(), AdapterView.OnItemSelectedListener {
    lateinit var building: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_options, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner: Spinner = view.findViewById(R.id.spinnerBuilding)
        spinner.onItemSelectedListener = this
        ArrayAdapter.createFromResource(
            view.context,
            R.array.buildings,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        val db = Firebase.firestore
        val submitButton = view.findViewById<Button>(R.id.buttonSubmit)
        submitButton.setOnClickListener(){
            val roomNum = (view.findViewById<EditText>(R.id.editTextRoom)).text.toString()
            val provider = (view.findViewById<EditText>(R.id.editTextProvider)).text.toString()
            val foodType = (view.findViewById<EditText>(R.id.editTextFoodTyoe)).text.toString()
            val entry = hashMapOf(
                "roomNum" to roomNum,
                "provider" to provider,
                "foodType" to foodType,
                "building" to building
            )
            db.collection("entries")
                .add(entry)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            building = parent.getItemAtPosition(position) as String
    }


}