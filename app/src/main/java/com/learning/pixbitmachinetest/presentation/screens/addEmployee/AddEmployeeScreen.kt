package com.learning.pixbitmachinetest.presentation.screens.addEmployee

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Slider
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.learning.pixbitmachinetest.common.utils.Validator
import com.learning.pixbitmachinetest.data.model.Designation
import com.learning.pixbitmachinetest.presentation.model.MonthlyPayment
import com.learning.pixbitmachinetest.presentation.state.DesignationState
import com.learning.pixbitmachinetest.presentation.state.SaveEmployeeState
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEmployeeScreen(
    onBackPress: () -> Unit,
    onSaveSuccess: () -> Unit
) {

    val viewModel: AddEmployeeViewModel = hiltViewModel()
    var currentStep by remember { mutableIntStateOf(1) }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var designation by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var profilePicUri by remember { mutableStateOf<Uri?>(null) }
    var resumeUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> profilePicUri = uri }
    )

    val resumePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> resumeUri = uri }
    )

    var mobileNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    var contractPeriod by remember { mutableStateOf("3 Months") }
    var totalSalary by remember { mutableFloatStateOf(30000f) }

    val designationState by viewModel.designationState.collectAsState()
    val saveEmployeeState by viewModel.saveEmployeeState.collectAsState()

    LaunchedEffect(saveEmployeeState) {
        if (saveEmployeeState is SaveEmployeeState.Success) {
            onSaveSuccess()
        }
    }

    val designations = remember(designationState) {
        if (designationState is DesignationState.Success) {
            (designationState as DesignationState.Success).data
        } else {
            emptyList<Designation>()
        }
    }
    val designationNames = remember(designations) {
        designations.map { it.name }
    }

    val isStep1Valid = remember(firstName, lastName, dob, designation, gender) {
        firstName.isNotBlank() && lastName.isNotBlank() && dob.isNotBlank() && designation.isNotBlank() && gender.isNotBlank()
    }
    val isStep2Valid = remember(mobileNumber, email, address) {
        Validator.isPhoneValid(mobileNumber) && Validator.isEmailValid(email) && address.isNotBlank()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Add an employee") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (currentStep > 1) currentStep-- else onBackPress()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Step indicator
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Step $currentStep/3", fontWeight = FontWeight.Bold)
                    Text(
                        when (currentStep) {
                            1 -> "Basic Details"
                            2 -> "Contact Details"
                            else -> "Salary Scheme"
                        },
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (currentStep) {
                1 -> {
                    BasicDetailsStep(
                        firstName = firstName,
                        lastName = lastName,
                        dob = dob,
                        designation = designation,
                        gender = gender,
                        designationList = designationNames,
                        onFirstNameChange = { firstName = it },
                        onLastNameChange = { lastName = it },
                        onDobChange = { dob = it },
                        onDesignationChange = { designation = it },
                        onGenderChange = { gender = it },
                        profilePicUri = profilePicUri,
                        onEditProfilePicClick = { imagePickerLauncher.launch("image/*") },
                        resumeUri = resumeUri,
                        onUploadResumeClick = { resumePickerLauncher.launch("*/*") }
                    )
                }

                2 -> {
                    var step2ButtonClicked by remember { mutableStateOf(false) }
                    ContactDetailsStep(
                        mobileNumber,
                        email,
                        address,
                        onMobileNumberChange = { mobileNumber = it },
                        onEmailChange = { email = it },
                        onAddressChange = { address = it },
                        showError = step2ButtonClicked
                    )
                }

                3 -> {
                    val context = LocalContext.current
                    SalarySchemeStep(
                        contractPeriod,
                        totalSalary,
                        onContractPeriodChange = { contractPeriod = it },
                        onTotalSalaryChange = { totalSalary = it },
                        onSave = { monthlyPayments ->
                            val designationId = designations.find { it.name == designation }?.id
                            val profilePicFile = profilePicUri?.let { uri -> uriToFile(context, uri) }
                            val resumeFile = resumeUri?.let { uri -> uriToFile(context, uri) }
                            viewModel.saveEmployee(
                                firstName = firstName,
                                lastName = lastName,
                                dob = dob,
                                designation = designationId?.toString() ?: "",
                                gender = gender,
                                mobile = mobileNumber,
                                email = email,
                                address = address,
                                contractPeriod = contractPeriod,
                                totalSalary = totalSalary.toString(),
                                profilePic = profilePicFile,
                                resume = resumeFile,
                                monthlyPayments = monthlyPayments
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))


            if (currentStep < 3) {
                Button(
                    onClick = {
                        when (currentStep) {
                            1 -> {
                                if (isStep1Valid) currentStep++
                            }
                            2 -> {
                                if (isStep2Valid) currentStep++
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = when(currentStep){
                        1 -> isStep1Valid
                        2 -> isStep2Valid
                        else -> false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Next")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun BasicDetailsStep(
    firstName: String,
    lastName: String,
    dob: String,
    designation: String,
    gender: String,
    designationList: List<String>,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onDobChange: (String) -> Unit,
    onDesignationChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    profilePicUri: Uri?,
    onEditProfilePicClick: () -> Unit,
    resumeUri: Uri?,
    onUploadResumeClick: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    var designationExpanded by remember { mutableStateOf(false) }

    val genders = listOf("Male", "Female", "Other")
    var genderExpanded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Box(contentAlignment = Alignment.BottomEnd) {
            if (profilePicUri != null) {
                GlideImage(
                    model = profilePicUri,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(120.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        .padding(24.dp),
                    contentScale = ContentScale.Fit
                )
            }
            SmallFloatingActionButton(
                onClick = onEditProfilePicClick,
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit profile picture", tint = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = dob,
            onValueChange = onDobChange,
            label = { Text("Date of birth") },
            placeholder = { Text("yyyy-mm-dd") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Select Date")
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(expanded = designationExpanded, onExpandedChange = { designationExpanded = !designationExpanded }) {
            OutlinedTextField(
                value = designation,
                onValueChange = onDesignationChange,
                label = { Text("Designation") },
                placeholder = { Text("Select One") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = designationExpanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )
            ExposedDropdownMenu(expanded = designationExpanded, onDismissRequest = { designationExpanded = false }) {
                designationList.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onDesignationChange(selectionOption)
                            designationExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(expanded = genderExpanded, onExpandedChange = { genderExpanded = !genderExpanded }) {
            OutlinedTextField(
                value = gender,
                onValueChange = onGenderChange,
                label = { Text("Gender") },
                placeholder = { Text("Select One") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                )
            )
            ExposedDropdownMenu(expanded = genderExpanded, onDismissRequest = { genderExpanded = false }) {
                genders.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onGenderChange(selectionOption)
                            genderExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onUploadResumeClick() },
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Upload Resume", fontWeight = FontWeight.Bold)
                    val resumeName = resumeUri?.let { getFileName(context.contentResolver, it) }
                    Text(
                        text = resumeName ?: "upload PDF file or image",
                        fontSize = 12.sp,
                        color = if (resumeName != null) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                }
            }
        }
    }

    if (showDatePicker) {
        PastDatePicker(
            onDateSelected = {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                onDobChange(sdf.format(Date(it)))
            },
            onDismiss = { showDatePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailsStep(
    mobileNumber: String,
    email: String,
    address: String,
    onMobileNumberChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    showError: Boolean
) {
    val isEmailValid = Validator.isEmailValid(email)
    val isPhoneValid = Validator.isPhoneValid(mobileNumber)

    Column {
        OutlinedTextField(
            value = mobileNumber,
            onValueChange = { if (it.length <= 10) onMobileNumberChange(it) },
            label = { Text("Mobile Number") },
            placeholder = { Text("Enter Mobile Number") },
            modifier = Modifier.fillMaxWidth(),
            isError = !isPhoneValid && showError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            supportingText = {
                if (!isPhoneValid && showError) {
                    Text(text = "Invalid phone number")
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            placeholder = { Text("Enter Mail Address") },
            modifier = Modifier.fillMaxWidth(),
            isError = !isEmailValid && showError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            supportingText = {
                if (!isEmailValid && showError) {
                    Text(text = "Invalid email address")
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = address,
            onValueChange = onAddressChange,
            label = { Text("Address") },
            placeholder = { Text("Enter Address") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalarySchemeStep(
    contractPeriod: String,
    totalSalary: Float,
    onContractPeriodChange: (String) -> Unit,
    onTotalSalaryChange: (Float) -> Unit,
    onSave: (List<MonthlyPayment>) -> Unit
) {
    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale("en", "IN")) }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var monthlyPayments by remember { mutableStateOf<List<MonthlyPayment>>(emptyList()) }
    val sdf = remember {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC")
        format
    }
    val disabledDates = remember(monthlyPayments) {
        monthlyPayments.mapNotNull {
            try {
                sdf.parse(it.date)?.time
            } catch (e: Exception) {
                null
            }
        }
    }


    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Contract Period", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val contractOptions = listOf("3 Months", "6 Months", "12 Months")
            contractOptions.forEach { option ->
                OutlinedButton(
                    onClick = { onContractPeriodChange(option) },
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, if (contractPeriod == option) MaterialTheme.colorScheme.primary else Color.LightGray),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (contractPeriod == option) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                        contentColor = if (contractPeriod == option) MaterialTheme.colorScheme.primary else Color.Gray
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(option)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Total Salary", fontWeight = FontWeight.Bold)
            Text(
                currencyFormat.format(totalSalary),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
        Slider(
            value = totalSalary,
            onValueChange = onTotalSalaryChange,
            valueRange = 10000f..200000f,
            steps = 0
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("₹10,000", fontSize = 12.sp, color = Color.Gray)
            Text("₹2,00,000", fontSize = 12.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        val contractMonths = contractPeriod.split(" ").first().toInt()
        Button(
            onClick = { showBottomSheet = true },
            modifier = Modifier.fillMaxWidth(),
            enabled = monthlyPayments.size < contractMonths,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Monthly Payment (${monthlyPayments.size}/$contractMonths)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        monthlyPayments.forEachIndexed { index, payment ->
            MonthlyPaymentItem(month = index + 1, payment = payment, onDelete = {
                monthlyPayments = monthlyPayments.filterIndexed { i, _ -> i != index }
            })
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        val remainingSalary = totalSalary - monthlyPayments.sumOf { it.amount.toDouble() }.toFloat()
        val remainingPercentage = if (totalSalary > 0) (remainingSalary / totalSalary) * 100 else 0
        val remainingMonths = contractMonths - monthlyPayments.size


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Remaining", color = Color.Gray, fontSize = 14.sp)
            Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Text(currencyFormat.format(remainingSalary), modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 12.sp)
            }
            Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Text("${String.format("%.0f", remainingPercentage)}%", modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 12.sp)
            }
            Card(shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Text("$remainingMonths Month", modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (monthlyPayments.isNotEmpty()) {
            Button(onClick = { onSave(monthlyPayments) }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                Text("Save")
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            MonthlyPaymentBottomSheetContent(
                disabledDates = disabledDates,
                onSave = { date, percentage, remark ->
                    val amount = (totalSalary * percentage.toFloat() / 100).toString()
                    monthlyPayments = monthlyPayments + MonthlyPayment(date, amount, percentage, remark)
                    showBottomSheet = false
                }, onCancel = {
                    showBottomSheet = false
                }
            )
        }
    }
}

@Composable
fun MonthlyPaymentItem(
    month: Int,
    payment: MonthlyPayment,
    onDelete: () -> Unit
) {
    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale("en", "IN")) }

    Column {
        Text("Month $month", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Payment Date", fontSize = 12.sp, color = Color.Gray)
                        Text(payment.date, fontWeight = FontWeight.Medium)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Payment Amount (${payment.percentage}%)", fontSize = 12.sp, color = Color.Gray)
                        Text(currencyFormat.format(payment.amount.toFloat()), fontWeight = FontWeight.Medium)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Remarks", fontSize = 12.sp, color = Color.Gray)
                        Text(payment.remark, fontWeight = FontWeight.Medium)
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Payment", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthlyPaymentBottomSheetContent(
    disabledDates: List<Long>,
    onSave: (String, String, String) -> Unit,
    onCancel: () -> Unit
) {
    var paymentDate by remember { mutableStateOf("") }
    var amountPercentage by remember { mutableStateOf("") }
    var remark by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    val isSaveEnabled = paymentDate.isNotBlank() && amountPercentage.isNotBlank() && remark.isNotBlank()

    var isAmountPercentageError by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Add Monthly Payment", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = paymentDate,
            onValueChange = { },
            readOnly = true,
            label = { Text("Payment Date") },
            placeholder = { Text("yyyy-mm-dd") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Select Date")
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = amountPercentage,
            onValueChange = { value ->
                if (value.isEmpty() || value.toIntOrNull() != null) {
                    amountPercentage = value
                    isAmountPercentageError = value.toIntOrNull()?.let { it < 1 || it > 100 } ?: false
                }
            },
            label = { Text("Amount Percentage") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            isError = isAmountPercentageError,
            supportingText = {
                if (isAmountPercentageError) {
                    Text(text = "Percentage must be between 1 and 100")
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = remark,
            onValueChange = { remark = it },
            label = { Text("Remark") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { onSave(paymentDate, amountPercentage, remark) },
                enabled = isSaveEnabled && !isAmountPercentageError,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Save")
            }
        }
    }

    if (showDatePicker) {
        FutureDatePicker(
            onDateSelected = {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                sdf.timeZone = TimeZone.getTimeZone("UTC")
                paymentDate = sdf.format(Date(it))
            },
            onDismiss = { showDatePicker = false },
            disabledDates = disabledDates
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PastDatePicker(
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit,
    disabledDates: List<Long> = emptyList()
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {

                val today = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
                return utcTimeMillis <= today && utcTimeMillis !in disabledDates
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let(onDateSelected)
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FutureDatePicker(
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit,
    disabledDates: List<Long>
) {
    val latestDisabledDate = remember(disabledDates) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        disabledDates.maxOrNull()?.let {
            calendar.timeInMillis = it
            calendar.set(Calendar.HOUR_OF_DAY, 23)
            calendar.set(Calendar.MINUTE, 59)
            calendar.set(Calendar.SECOND, 59)
            calendar.set(Calendar.MILLISECOND, 999)
            calendar.timeInMillis
        }
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = (latestDisabledDate ?: System.currentTimeMillis()) + 86400000, // a day after
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val minimumDate = latestDisabledDate ?: Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
                return utcTimeMillis > minimumDate
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let(onDateSelected)
                    onDismiss()
                },
                enabled = datePickerState.selectedDateMillis != null
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


fun uriToFile(context: Context, uri: Uri): File? {
    val contentResolver = context.contentResolver
    val fileName = getFileName(contentResolver, uri) ?: return null
    val file = File(context.cacheDir, fileName)

    try {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return file
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}


private fun getFileName(contentResolver: ContentResolver, uri: Uri): String? {
    var name: String? = null
    val cursor = contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                name = it.getString(nameIndex)
            }
        }
    }
    return name
}


@Preview(showBackground = true)
@Composable
fun AddEmployeeScreenPreview() {
    AddEmployeeScreen(onBackPress = {}, onSaveSuccess = {})
}
