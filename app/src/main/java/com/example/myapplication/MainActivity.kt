package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(db, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun App(db: FirebaseFirestore,
        modifier:Modifier){
    var nome = remember {
        mutableStateOf("")
    }
    var telefone = remember {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
        }
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Text(text = "App Firebase Firestore")
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
        }
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.3f)
            ) {
                Text(text = "Nome:")
            }
            Column(
            ) {
                TextField(
                    value = nome.value,
                    onValueChange = { nome.value = it }
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.3f)
            ) {
                Text(text = "Telefone:")
            }
            Column(
            ) {
                TextField(
                    value = telefone.value,
                    onValueChange = { telefone.value = it }
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
        }
        Row(
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ){
            Button(onClick = {
                // Create a new user with a first and last name
                var pessoas = hashMapOf(
                    "nome" to nome.value,
                    "telefone" to telefone.value,
                )

                // Add a new document with a generated ID
                db.collection("Clientes").add(pessoas)
                    .addOnSuccessListener { documentReference ->
                        Log.w("teste", "teste")
                        Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                        nome.value = "";
                        telefone.value = "";
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }

            }) {
                Text(text = "Cadastrar")
            }

            //Text("ola")

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

            }
            /*
            Row(
                Modifier
                    .fillMaxWidth()
            ){
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                ) {
                    Text(text = "Nome:")
                }
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                ) {
                    Text(text = "Telefone:")
                }
            }*/


        }
        Row(Modifier.fillMaxWidth()) {
            Column(
                Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Nome:")
            }
            Column(
                Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Telefone:")
            }
        }
        Row (Modifier.fillMaxWidth()) {
            val clientes = remember { mutableStateListOf<HashMap<String, String>>() }


            db.collection("Clientes")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val lista = hashMapOf(
                            "nome" to "${document.data.get("nome")}",
                            "telefone" to "${document.data.get("telefone")}"
                        )
                        clientes.add(lista)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("teste", "Error getting documents: ", exception)
                }



            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(clientes) { cliente ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(0.5f)) {
                            Text(text = cliente["nome"] ?: "--")
                        }
                        Column(modifier = Modifier.weight(0.5f)) {
                            Text(text = cliente["telefone"] ?: "--")
                        }
                    }
                }
            }
        }



    }
    Column(
        Modifier
            .fillMaxWidth()
    ){
        // Column(

        // ) {

        // }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MyApplicationTheme {
//        Greeting("Android")
//    }
//}