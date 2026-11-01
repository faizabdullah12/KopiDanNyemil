package com.kopidannyemil.app.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kopidannyemil.app.data.AppViewModel
import com.kopidannyemil.app.data.toRupiah
import com.kopidannyemil.app.ui.screens.ConfirmationScreen
import com.kopidannyemil.app.ui.screens.HomeScreen
import com.kopidannyemil.app.ui.screens.LoginScreen
import com.kopidannyemil.app.ui.screens.OrderScreen
import com.kopidannyemil.app.ui.screens.ProfileScreen
import com.kopidannyemil.app.ui.screens.RegisterScreen
import com.kopidannyemil.app.ui.screens.SplashScreen
import com.kopidannyemil.app.ui.screens.WelcomeScreen
import com.kopidannyemil.app.ui.theme.Espresso
import com.kopidannyemil.app.ui.theme.Peach

object Routes {
    const val SPLASH = "splash"
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
    const val ORDER = "order"
    const val PROFILE = "profile"
    const val CONFIRMATION = "confirmation"
}

private data class Tab(val route: String, val label: String, val icon: ImageVector)

private val tabs = listOf(
    Tab(Routes.HOME, "Home", Icons.Filled.Home),
    Tab(Routes.ORDER, "Order", Icons.Filled.ShoppingCart),
    Tab(Routes.PROFILE, "Profile", Icons.Filled.Person)
)

@Composable
fun AppNavigation(vm: AppViewModel = viewModel()) {
    val nav = rememberNavController()

    val goHome: () -> Unit = {
        nav.navigate(Routes.HOME) {
            popUpTo(Routes.WELCOME) { inclusive = true }
            launchSingleTop = true
        }
    }

    NavHost(navController = nav, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(onStart = {
                nav.navigate(Routes.WELCOME) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            })
        }

        composable(Routes.WELCOME) {
            WelcomeScreen(
                onRegister = { nav.navigate(Routes.REGISTER) },
                onLogin = { nav.navigate(Routes.LOGIN) }
            )
        }

        composable(Routes.LOGIN) {
            LoginScreen(
                vm = vm,
                onBack = { nav.popBackStack() },
                onGoRegister = {
                    nav.navigate(Routes.REGISTER) { popUpTo(Routes.WELCOME) }
                },
                onSuccess = goHome
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                vm = vm,
                onBack = { nav.popBackStack() },
                onGoLogin = {
                    nav.navigate(Routes.LOGIN) { popUpTo(Routes.WELCOME) }
                },
                onSuccess = goHome
            )
        }

        composable(Routes.HOME) {
            TabScaffold(Routes.HOME, vm, nav) { padding ->
                HomeScreen(vm = vm, padding = padding)
            }
        }

        composable(Routes.ORDER) {
            TabScaffold(Routes.ORDER, vm, nav) { padding ->
                OrderScreen(
                    vm = vm,
                    padding = padding,
                    onOrdered = {
                        nav.navigate(Routes.CONFIRMATION) {
                            popUpTo(Routes.HOME)
                        }
                    }
                )
            }
        }

        composable(Routes.PROFILE) {
            TabScaffold(Routes.PROFILE, vm, nav) { padding ->
                ProfileScreen(
                    vm = vm,
                    padding = padding,
                    onLogout = {
                        vm.logout()
                        nav.navigate(Routes.WELCOME) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(Routes.CONFIRMATION) {
            ConfirmationScreen(
                vm = vm,
                onOk = {
                    nav.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }
    }
}

/** Kerangka halaman utama dengan bottom navigation (Home / Order / Profile). */
@Composable
private fun TabScaffold(
    current: String,
    vm: AppViewModel,
    nav: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val openTab: (String) -> Unit = { route ->
        nav.navigate(route) {
            popUpTo(Routes.HOME) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Column {
                if (current == Routes.HOME && vm.cartCount > 0) {
                    CartBar(
                        count = vm.cartCount,
                        total = vm.cartTotal,
                        onClick = { openTab(Routes.ORDER) }
                    )
                }
                NavigationBar(containerColor = Color.White) {
                    tabs.forEach { tab ->
                        NavigationBarItem(
                            selected = current == tab.route,
                            onClick = { if (current != tab.route) openTab(tab.route) },
                            icon = { Icon(tab.icon, contentDescription = tab.label) },
                            label = { Text(tab.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = Espresso,
                                indicatorColor = Espresso
                            )
                        )
                    }
                }
            }
        },
        content = content
    )
}

@Composable
private fun CartBar(count: Int, total: Int, onClick: () -> Unit) {
    Surface(
        color = Espresso,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("$count item di keranjang", color = Color(0xFFD9D2CA), fontSize = 12.sp)
                Text(total.toRupiah(), color = Color.White, fontWeight = FontWeight.Bold)
            }
            Text("Lanjut ke Order ›", color = Peach, fontWeight = FontWeight.Bold)
        }
    }
}
