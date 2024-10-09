package com.example.mynotes

import com.example.mynotes.presentation.viewModel.NoteViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import com.example.mynotes.presentation.view.MainScreen
import dagger.hilt.android.AndroidEntryPoint

//  val noteViewModel2 : com.example.mynotes.presentation.viewModel.NoteViewModel = hiltViewModel()
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    val noteViewModel : NoteViewModel by viewModels()
        setContent {
                Navigator(screen = MainScreen(noteViewModel))
        }
    }
}

@Composable
fun AddNoteDialog(
    onSaveClick: (String, String, Color) -> Unit,
    onDismissRequest: () -> Unit
) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val selectedColor = remember { mutableStateOf(Color(0xffE4080A)) } // Default color

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        icon = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDismissRequest() }
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onSaveClick(title.value, description.value, selectedColor.value)
            }) {
                Text(text = "Save Note")
            }
        },
        title = { Text(text = "Add New Note") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    placeholder = { Text(text = "Add Title") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.height(100.dp),
                    value = description.value,
                    onValueChange = { description.value = it },
                    placeholder = { Text(text = "Add Description") }
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Add more colors to this list
                    val colors = listOf(
                        Color(0xFF860A8F),
                        Color(0xff5DE2E7),
                        Color(0xffEFC752),
                        Color(0xff8BC34A),
                        Color(0xFFDB1873)
                    )

                    colors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(color)
                                .clickable { selectedColor.value = color }
                                .border(
                                    5.dp,
                                    if (selectedColor.value == color) Color.Black else Color.Transparent
                                )
                        )
                    }
                }


            }
        }
    )
}
