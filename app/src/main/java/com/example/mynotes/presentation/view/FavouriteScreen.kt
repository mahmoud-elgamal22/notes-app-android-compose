package com.example.mynotes.presentation.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.mynotes.presentation.viewModel.NoteViewModel



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavouriteScreen(noteViewModel: NoteViewModel) {
    val allNotes = noteViewModel.allNotes.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 100.dp, bottom = 130.dp)) {

        items(allNotes.value?.filter { it.isFavorite } ?: emptyList(), key = { it.id }) { note ->

            val dismissState = rememberDismissState()

            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                noteViewModel.deleteNotes(note)
            }

            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    val color by animateColorAsState(
                        if (dismissState.dismissDirection != null) Color.Red else Color.Transparent
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(16.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White
                        )
                    }
                },
                dismissContent = {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color(
                                android.graphics.Color.parseColor(note.color)
                            )
                        ),
                        elevation = CardDefaults.cardElevation(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navigator.push(NoteDetailsScreen(note, noteViewModel)) }
                            .padding(12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                        ) {
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        noteViewModel.updateNotes(note.copy(isFavorite = !note.isFavorite))
                                    }
                                    .align(Alignment.End)
                                    .size(30.dp),
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = if (note.isFavorite) Color.Blue else Color.White
                            )
                            Text(
                                text = note.title,
                                color = Color.Black,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                text = note.description,
                                fontSize = 20.sp,
                                modifier = Modifier.align(Alignment.Start)
                            )
                        }
                    }
                }
            )
        }
    }
}

