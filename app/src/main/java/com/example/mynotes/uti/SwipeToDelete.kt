
package com.example.mynotes.uti


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mynotes.data.db.Note
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SwipeToDeleteNote(
    note: Note,
    onDelete: (Note) -> Unit
) {
    val dismissState = rememberDismissState()

    SwipeToDismiss(
        state = dismissState,
        background = {
            val backgroundColor by animateColorAsState(
                targetValue = if (dismissState.dismissDirection != null) Color.Red else Color.Transparent
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
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
                colors = CardDefaults.cardColors(containerColor = Color(android.graphics.Color.parseColor(note.color))),
                elevation = CardDefaults.cardElevation(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = note.title,
                        fontSize = 20.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = note.description, fontSize = 16.sp)
                }
            }
        },
        directions = setOf(DismissDirection.EndToStart) // تأكد من استخدام EndToStart هنا
    )

    // تنفيذ عملية الحذف عند اكتمال السحب
    LaunchedEffect(dismissState) {
        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
            onDelete(note)
        }
    }
}