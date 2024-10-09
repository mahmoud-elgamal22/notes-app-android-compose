package com.example.mynotes.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.mynotes.AddNoteDialog
import com.example.mynotes.data.db.Note
import com.example.mynotes.presentation.viewModel.NoteViewModel
import com.example.mynotes.ui.theme.MyNotesTheme

class MainScreen(val noteViewModel: NoteViewModel) : Screen {
    @Composable
    @Override
    override fun Content() {
        val selectedIndex = remember { mutableStateOf(0) }
        val showAddDialog = remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow

        MyNotesTheme {
            Scaffold(
                floatingActionButton = {
                    IconButton(modifier = Modifier.size(60.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Black, contentColor = Color.Red),
                        onClick = {
                            showAddDialog.value = true
                        }) {
                        Icon(modifier = Modifier.size(25.dp),imageVector = Icons.Default.Add, contentDescription =null)
                    }
                },
                topBar = { MyTopAppBar() }
                , bottomBar = { BottomAppBar(containerColor = Color.Black) {
                    IconButton(modifier = Modifier.weight(1f),onClick = { selectedIndex.value = 0 }) {
                        Icon(modifier = Modifier.size(50.dp),imageVector = Icons.Filled.Home, contentDescription =null, tint = if(selectedIndex.value==0) Color.Red else  Color.White )
                    }
                    IconButton(modifier = Modifier.weight(1f),onClick = { selectedIndex.value = 1  }) {
                        Icon(modifier = Modifier.size(50.dp),imageVector = Icons.Default.Favorite, contentDescription =null, tint = if(selectedIndex.value==1) Color.Red else  Color.White )
                    }
                }
                }
                ,modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    if (showAddDialog.value) {
                        AddNoteDialog(
                            onSaveClick = { title, description, color ->
                                // Convert color to hex string if needed
                                val colorHex = String.format("#%06X", (0xFFFFFF and color.toArgb()))

                                // Save the note with color
                                noteViewModel.insertNotes(Note(
                                    title = title,
                                    description = description,
                                    color = colorHex, // Save as hex string or other format
                                    isFavorite = false
                                ))
                                showAddDialog.value = false
                            },
                            onDismissRequest = {
                                showAddDialog.value = false
                            }
                        )
                    }

                }
                    if (selectedIndex.value == 0) {
                        HomeScreen(noteViewModel = noteViewModel)
                    } else {
                        FavouriteScreen(noteViewModel = noteViewModel)
                    }
                }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        actions = {
            IconButton(onClick = {
                expanded = true
            }) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color.White
                )
            }


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(
                    { Text("Notes") },
                    onClick = {

                        // Handle the second menu item click here
                        expanded = false
                    })
                DropdownMenuItem(
                    { Text("FavouriteNotes") },
                    onClick = {
                    // Handle the second menu item click here
                        expanded = false

                    })
                // Add more options as needed
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White
        ),
        title = { Text(text = "MyNotes", fontSize = 30.sp) }
    )
}


