package com.learning.pixbitmachinetest.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learning.pixbitmachinetest.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeProfileScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile Details", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                // Use a placeholder if you don't have the image
                // Image(painter = painterResource(id = R.drawable.profile_placeholder), ...)
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer) // Placeholder background
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("John Joy", style = MaterialTheme.typography.headlineSmall)
                Text("Supervisor", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            InfoColumn(title = "Contact Number", subtitle = "+91 8412094567", modifier = Modifier.weight(1f))
                            InfoColumn(title = "Email", subtitle = "johnjoy@mail.com", modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            InfoColumn(title = "Date of birth", subtitle = "12-12-2022", modifier = Modifier.weight(1f))
                            InfoColumn(title = "Gender", subtitle = "Male", modifier = Modifier.weight(1f))
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        InfoColumn(title = "Address", subtitle = "12 street, The Marine World, los angeles")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.pdf),
                                contentDescription = "Resume File",
                                tint = Color.Unspecified, // Use original colors of the drawable
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text("Resume File", style = MaterialTheme.typography.titleMedium)
                                Text("Updated 01-01-2020", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        Button(
                            onClick = { /* Handle view resume */ },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                        ) {
                            Text("View", color = MaterialTheme.colorScheme.onSecondaryContainer)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Salary Scheme", style = MaterialTheme.typography.titleMedium)
                        Text("3 Months  ₹60,000", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }

            item { SalaryMonthDetail("Month 1", "12-12-2024", "12,000", "20%", "First Salary") }
            item { SalaryMonthDetail("Month 2", "12-01-2025", "30,000", "50%", "Secondary Salary") }
            item { SalaryMonthDetail("Month 3", "12-02-2025", "18,000", "30%", "Final Salary") }
        }
    }
}

@Composable
fun InfoColumn(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = title, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = subtitle, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun SalaryMonthDetail(month: String, date: String, amount: String, percentage: String, remarks: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(text = month, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(4.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    InfoColumn(title = "Payment Date", subtitle = date)
                    InfoColumn(title = "Payment Amount ($percentage)", subtitle = "₹ $amount")
                }
                Spacer(modifier = Modifier.height(16.dp))
                InfoColumn(title = "Remarks", subtitle = remarks)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {

    EmployeeProfileScreen()

}
