package com.example.expensestracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.expensestracker.ui.theme.ExpensesTrackerTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.expensestracker.data.Expense
import com.example.expensestracker.data.ExpenseViewModel
import com.example.expensestracker.ui.theme.AddExpenseScreen


class MainActivity : ComponentActivity() {
    private val expenseViewModel: ExpenseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpensesTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExpenseApp(expenseViewModel = expenseViewModel)
                }
            }
        }
    }
}

enum class ExpenseScreen(@StringRes val title: Int) {
    Home(title = R.string.app_name),
    AddExpense(title = R.string.add_expense)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesTrackerAppBar(
    currentScreen: ExpenseScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(currentScreen.title),
                    textAlign = TextAlign.Center // Centraliza o texto horizontalmente
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}


// Top Bar antiga
/*@Composable
fun ExpensesTrackerTopBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0, 102, 137, 255))
            .padding(start = 16.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
    ) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}*/

@Composable
fun PriceBox(
    totalExpenses: Double,
    totalIncome: Double,
    totalBalance: Double,
    modifier: Modifier = Modifier
) {
    val mediumPadding = 16.dp
    Card(
        modifier = modifier.padding(bottom = mediumPadding),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(mediumPadding))
            Text(
                text = "Total Balance",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = String.format("%.2f €", totalBalance),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = if (totalBalance >= 0) Color.Black else Color.Red
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = String.format("Income: %.2f €", totalIncome),
                    fontSize = 16.sp,
                    color = Color.Green
                )
                Text(
                    text = String.format("Expenses: %.2f €", totalExpenses),
                    fontSize = 16.sp,
                    color = Color.Red
                )
            }
            Spacer(modifier = Modifier.height(mediumPadding))
        }
    }
}

@Composable
fun TransactionList(expenses: List<Expense>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
    ) {
        Text(text = "Recent Transactions", fontSize = 25.sp)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f) // Garante que a LazyColumn ocupa o espaço disponível
        ) {
            items(expenses) { expense ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)) {
                    Text(text = expense.name, fontSize = 25.sp, modifier = Modifier.weight(1f))
                    Text(
                        text = String.format("%+.2f €", if (expense.isPositive) expense.amount.toDouble() else -expense.amount.toDouble()),
                        fontSize = 25.sp,
                        color = if (expense.isPositive) Color.Green else Color.Red
                    )
                }
            }
        }
    }
}
@Composable
fun AddExpenseButton(onClick: () -> Unit = {}, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(16.dp),
    ) {
        Text(text = "+ ADD EXPENSE")
    }
}

@Composable
fun HomePage(
    expenseViewModel: ExpenseViewModel,
    onAddExpenseButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalExpenses by remember { derivedStateOf { expenseViewModel.totalExpenses } }
    val totalIncome by remember { derivedStateOf { expenseViewModel.totalIncome } }
    val totalBalance by remember { derivedStateOf { expenseViewModel.totalBalance } }


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PriceBox(
            totalExpenses = totalExpenses,
            totalIncome = totalIncome,
            totalBalance = totalBalance,
            )
        TransactionList(expenses = expenseViewModel.expenses, modifier = Modifier.weight(1f))
        AddExpenseButton(onClick = onAddExpenseButtonClicked)
    }
}



@Composable
fun ExpenseApp(
    expenseViewModel: ExpenseViewModel,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ExpenseScreen.valueOf(
        backStackEntry?.destination?.route ?: ExpenseScreen.Home.name
    )

    Scaffold(
        topBar = {
            ExpensesTrackerAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // Define a cor de fundo do conteúdo
        ) {
            // Adiciona a imagem de fundo
            Image(
                painter = painterResource(R.drawable.elegantbg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            // Adiciona o conteúdo sobre a imagem de fundo
            NavHost(
                navController = navController,
                startDestination = ExpenseScreen.Home.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = ExpenseScreen.Home.name) {
                    HomePage(
                        expenseViewModel = expenseViewModel,
                        onAddExpenseButtonClicked = {
                            navController.navigate(ExpenseScreen.AddExpense.name)
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.padding_medium))
                    )
                }
                composable(route = ExpenseScreen.AddExpense.name) {
                    AddExpenseScreen(
                        onCancelButtonClicked = { navController.navigateUp() },
                        onSaveButtonClicked = { expenseName, amount, isPositive ->
                            expenseViewModel.addExpense(Expense(expenseName,
                                amount, isPositive))
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ExpensesTrackerPreview() {

    val sampleViewModel = ExpenseViewModel().apply {
        addExpense(Expense(name = "Groceries", amount = 50.00, isPositive = false))
        addExpense(Expense(name = "Transport", amount = 20.00, isPositive = false))
        addExpense(Expense(name = "Utilities", amount = 100.00, isPositive = false))
        addExpense(Expense(name = "Salary", amount = 1500.00, isPositive = true))
        addExpense(Expense(name = "Dining Out", amount = 30.00, isPositive = false))
        addExpense(Expense(name = "Bonus", amount = 200.00, isPositive = true))
        addExpense(Expense(name = "Savings", amount = 300.00, isPositive = true))
        addExpense(Expense(name = "Miscellaneous", amount = 10.00, isPositive = false))
    }


    ExpensesTrackerTheme {
        ExpenseApp(expenseViewModel = sampleViewModel)
    }
}