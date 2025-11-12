package com.learning.pixbitmachinetest.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.learning.pixbitmachinetest.data.model.EmployeeListItem
import com.learning.pixbitmachinetest.presentation.state.EmployeeListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navigateToAddEmployee: () -> Unit, navigateToProfile: (Int) -> Unit) {

    val viewModel: HomeViewModel = hiltViewModel()
    val employeeListState by viewModel.employeeListState.collectAsState()

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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (employeeListState) {
                is EmployeeListState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is EmployeeListState.Success -> {
                    val employees = (employeeListState as EmployeeListState.Success).data.data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {                        items(employees) { employee ->
                            EmployeeListItem(employee = employee) {
                                navigateToProfile(employee.id)
                            }
                        }
                    }
                }
                is EmployeeListState.Error -> {
                    Text(
                        text = (employeeListState as EmployeeListState.Error).message,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {}
            }
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EmployeeListItem(employee: EmployeeListItem, navigateToProfile: (Int) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                navigateToProfile(employee.id)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = employee.profileImageUrl,
            contentDescription = "Employee Image",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.Crop,

            ) {
            it.error(Icons.Default.Person)
        }
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = "${employee.firstName} ${employee.lastName}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = employee.mobileNumber,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    HomeScreen(navigateToAddEmployee = {}){

    }
}
