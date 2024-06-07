package com.example.expensestracker.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.expensestracker.R


@Composable
fun AddExpenseScreen(modifier: Modifier = Modifier) {
    var amountInput by remember { mutableStateOf("") }
    var expenseNameInput by remember { mutableStateOf("") }
    val selectedValue by rememberSaveable { mutableStateOf("") }


    Box(
        modifier = modifier
            .fillMaxSize()  // Preencher o tamanho total da tela
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        contentAlignment = Alignment.Center  // Centralizar o conteúdo
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            InputField(
                labelText = stringResource(id = R.string.expense_name),
                value = expenseNameInput,
                onValueChange = { newValue -> expenseNameInput = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
            )
            InputField(
                labelText = stringResource(id = R.string.amount),
                value = amountInput,
                onValueChange = { newValue -> amountInput = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                verticalAlignment = Alignment.Bottom
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { /*onCancelButtonClicked*/ }
                ) {
                    Text(stringResource(R.string.cancel))
                }
                Button(
                    modifier = Modifier.weight(1f),
                    // the button is enabled when the user makes a selection
                    enabled = selectedValue.isNotEmpty(),
                    onClick = {/*onNextButtonClicked*/}
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        }
    }
}


@Composable
fun InputField(
    labelText: String,
    value: String,
    onValueChange: (String) -> Unit,
    action: ImeAction = ImeAction.Next,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = labelText) },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = action
        ),
        modifier = modifier
    )
}

@Composable
fun ExpensesTrackerContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddExpenseScreen()
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpensesTrackerPreview() {
    ExpensesTrackerTheme {
        ExpensesTrackerContent()
    }
}