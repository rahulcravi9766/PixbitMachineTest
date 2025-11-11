package com.learning.pixbitmachinetest.presentation.screens.signInSignUp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.learning.pixbitmachinetest.common.utils.Validator
import com.learning.pixbitmachinetest.presentation.theme.PixbitMachineTestTheme
import com.learning.pixbitmachinetest.presentation.theme.textFieldBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit) {

    val viewModel: SignInSIgnUpViewModel = hiltViewModel()
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    val isNameValid = Validator.isNameValid(name)
    val isEmailValid = Validator.isEmailValid(email)
    val isPasswordValid = Validator.isPasswordValid(password)
    val isConfirmPasswordValid = password == confirmPassword

    val isFormValid = isNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Registration")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }) { paddingValues ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(text = "Name", fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Enter Name")
                    },
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    isError = !isNameValid,
                    supportingText = {
                                     if (!isNameValid) {
                                         Text(text = "Name should contain only characters")
                                     }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = textFieldBackground,
                        focusedContainerColor = textFieldBackground,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Email", fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Enter Mail Address")
                    },
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    isError = !isEmailValid,
                    supportingText = {
                                     if (!isEmailValid) {
                                         Text(text = "Invalid email address")
                                     }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = textFieldBackground,
                        focusedContainerColor = textFieldBackground,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Password", fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Enter Password")
                    },
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = !isPasswordValid,
                    supportingText = {
                                     if (!isPasswordValid) {
                                         Text(text = "Password should be at least 5 characters long and contain uppercase, lowercase, number, and special character (@, !, ?, _)")
                                     }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = textFieldBackground,
                        focusedContainerColor = textFieldBackground,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Confirm Password", fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Enter Password")
                    },
                    trailingIcon = {
                        IconButton(onClick = { isConfirmPasswordVisible = !isConfirmPasswordVisible }) {
                            Icon(
                                imageVector = if (isConfirmPasswordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = !isConfirmPasswordValid,
                    supportingText = {
                                     if (!isConfirmPasswordValid) {
                                         Text(text = "Passwords do not match")
                                     }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = textFieldBackground,
                        focusedContainerColor = textFieldBackground,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        viewModel.registerUser(name, email, password, confirmPassword)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    enabled = isFormValid,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Register")
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun RegistrationScreenPreview() {
    PixbitMachineTestTheme {
        RegistrationScreen(onBackClick = {})
    }
}
