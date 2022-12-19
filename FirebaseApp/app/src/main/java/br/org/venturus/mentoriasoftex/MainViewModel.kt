package br.org.venturus.mentoriasoftex

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainViewModel: ViewModel() {

    private val TAG = "MentoriaSoftex"
    private val db = Firebase.firestore

    var buttonClicks by mutableStateOf(0L)
    var totalClicks by mutableStateOf(0L)

    fun sendClicksToFirebase() {
        buttonClicks = totalClicks + 1

        val data = hashMapOf("clicks" to buttonClicks)
        db.collection("buttonClicks").document("totalClicks")
            .set(data)
            .addOnSuccessListener {
                Log.d(TAG, "Document added: $data")
                getTotalClicks()
            }
            .addOnFailureListener { error ->
                Log.w(TAG, "Error while adding the document - $error")
            }
    }

    fun getTotalClicks() {
        db.collection("buttonClicks").document("totalClicks")
            .get()
            .addOnSuccessListener { document ->
                document?.let {
                    val currentValue = it.data?.get("clicks")
                    totalClicks = (currentValue as? Long) ?: 0
                    Log.d(TAG, "Total Clicks: $totalClicks")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Failure while getting the documents: $exception")
            }
    }

}