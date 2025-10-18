package com.example.mywishlistapp

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    innerPadding: PaddingValues
) {
    val context = LocalContext.current

    var wishList by remember { mutableStateOf(listOf<String>()) }

    var showAddDialog by remember { mutableStateOf(false) }
    var newWish by remember { mutableStateOf(TextFieldValue("")) }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            AppBarView(title = "WishList")
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (wishList.isEmpty()) {
                item {
                    Text(
                        text = "No items yet. Tap + to add something!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }
            } else {
                items(wishList) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFEDE7F6)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item,
                                style = MaterialTheme.typography.bodyLarge
                            )

                            IconButton(
                                onClick = {
                                    itemToDelete = item
                                    showDeleteDialog = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }

        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text("Add New Wish") },
                text = {
                    OutlinedTextField(
                        value = newWish,
                        onValueChange = { newWish = it },
                        label = { Text("What do you wish for?") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        val wish = newWish.text.trim()
                        if (wish.isNotEmpty()) {
                            wishList = wishList + wish
                            newWish = TextFieldValue("")
                            showAddDialog = false
                            Toast.makeText(context, "Added to WishList", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Please enter something!", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showAddDialog = false
                        newWish = TextFieldValue("")
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (showDeleteDialog && itemToDelete != null) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Remove Item") },
                text = { Text("Are you sure you want to remove \"${itemToDelete}\"?") },
                confirmButton = {
                    TextButton(onClick = {
                        wishList = wishList - itemToDelete!!
                        showDeleteDialog = false
                        Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Delete", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
