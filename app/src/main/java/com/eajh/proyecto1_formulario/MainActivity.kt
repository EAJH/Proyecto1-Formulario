package com.eajh.proyecto1_formulario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eajh.proyecto1_formulario.ui.theme.BackgroundApp
import com.eajh.proyecto1_formulario.ui.theme.Black
import com.eajh.proyecto1_formulario.ui.theme.Proyecto1FormularioTheme
import com.eajh.proyecto1_formulario.ui.theme.TopBarColor

/*
* En el MainActivity solo debe de ir la configuración global (temas, navegación). La
* estructura visual de como se ve la pantalla le pertenece a MainScreen, por eso
* movemos el Scaffold ahí.
* */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proyecto1FormularioTheme {
                // Este Scaffold nos ayuda a obtener el padding necesario de la barra de
                // estados de android y este se lo pasamos a MainScreen para que comience a
                // dibujar la app partiendo debajo de esta barra.
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    /*
    * El Scaffold es el esqueleto de la pantalla. Tiene como función organizar el espacio para
    * que los elementos comunes de una app no se amontonen. Poniendo un Scaffold aquí permite
    * colocar la TopBar fácilmente.
    * */
    Scaffold(
        modifier = modifier, // Aplica el padding que viene del MainActivity para colocarnos
                             // debajo de la barra de estado de Android (hora, notificaciones)
        topBar = {
            // Mandamos a llamar a la función TopBar
            TopBar(
                // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                content = "Creación de perfil",
                onBackClick = {
                    // Lógica de que al dar en el botón, este se regrese uno en el backstack
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                // Usa el padding obtenido del Scaffold dentro de este MainScreen para no chocar
                // con la TopBar
                .padding(innerPadding)
                .background(BackgroundApp)
        ) {
            Text(
                text = "Prueba",
                fontFamily = FontFamily(Font(R.font.inter_regular)),
                color = Black
            )
        }
    }
}

// Función que se encarga de generar el composable de mi topBar
@Composable
fun TopBar(
    content: String,
    onBackClick: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()  // Solo ocupa el ancho
            .height(64.dp)
            .background(TopBarColor)
            .padding(horizontal = 8.dp)
    ){
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart) // Alinea el botón a la izquierda
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.back_arrow),
                // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                contentDescription = "Atrás",
                tint = Black
            )
        }

        Text(
            text = content,
            fontFamily = FontFamily(Font(R.font.manrope_regular)),
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.Center), // Centra el texto
            fontWeight = FontWeight.Bold,
            color = Black
        )
    }
}



// ======= Función necesaria para previsualizar la app dentro del IDE =======
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Proyecto1FormularioTheme {
        MainScreen()
    }
}