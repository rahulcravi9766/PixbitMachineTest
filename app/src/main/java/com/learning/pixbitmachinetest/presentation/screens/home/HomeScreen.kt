package com.learning.pixbitmachinetest.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navigateToAddEmployee: () -> Unit, navigateToProfile: () -> Unit) {

    val employees = listOf(
        Employee("John Doe", "123-456-7890", Icons.Default.Person),
        Employee("Rahul", "123-456-7890", Icons.Default.Person),
        Employee("Arun", "123-456-7880", Icons.Default.Person),
        Employee("Mohan", "123-456-7230", Icons.Default.Person),
    )
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Employees") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToAddEmployee() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, "Add Employee")
            }
        }
    ) { paddingValues ->

        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)){
            items(employees.size) { index ->
                EmployeeListItem(employee = employees[index]){
                    navigateToProfile()
                }
            }
        }
    }
}


@Composable
fun EmployeeListItem(employee: Employee, navigateToProfile: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp).clickable{
                navigateToProfile()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageVector = employee.image ?: Icons.Default.Person,
            contentDescription = "Employee Image",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = employee.name, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
            Text(text = employee.phone, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
    }
}

data class Employee(
    val name: String,
    val phone: String,
    val image: ImageVector?
)

@Composable
@Preview(showSystemUi = true)
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    HomeScreen(navigateToAddEmployee = {}){

    }
}
