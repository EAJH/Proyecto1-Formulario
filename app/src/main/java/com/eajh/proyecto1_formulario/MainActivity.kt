package com.eajh.proyecto1_formulario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eajh.proyecto1_formulario.ui.theme.BackgroundApp
import com.eajh.proyecto1_formulario.ui.theme.Black
import com.eajh.proyecto1_formulario.ui.theme.DarkBlue
import com.eajh.proyecto1_formulario.ui.theme.GrayTextField
import com.eajh.proyecto1_formulario.ui.theme.Neutral
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
                MainScreen(
                    modifier = Modifier.fillMaxSize()
                )

            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {

    // Estados TextFields
    // Los estados se los quiero pasar a main screen
    var name by rememberSaveable {
        mutableStateOf("") // Cadena vacía porque los TextFields siempre tienen texto
    }

    var lastName by rememberSaveable {
        mutableStateOf("") // Cadena vacía porque los TextFields siempre tienen texto
    }

    var birthday by rememberSaveable {
        mutableStateOf("") // Cadena vacía porque los TextFields siempre tienen texto
    }

    var gender by rememberSaveable {
        mutableStateOf("")
    }

    /*
    * El Scaffold es el esqueleto de la pantalla. Tiene como función organizar el espacio para
    * que los elementos comunes de una app no se amontonen. Poniendo un Scaffold aquí permite
    * colocar la TopBar fácilmente y dejar el MainActivity más limpio.
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
                },
                modifier = Modifier
                    .fillMaxWidth()  // Solo ocupa el ancho
                    .background(TopBarColor)
                    .statusBarsPadding() // Para no sobreponer la TopBar con la de notificaciones
                    .height(64.dp)
                    .padding(horizontal = 8.dp)
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ){
                // Espacio entre TopBar e imagen de perfil
                Spacer(
                    modifier = Modifier.size(30.dp)
                )

                // Composable que genera foto de perfil
                FotoPerfil(
                    modifier = Modifier
                        .size(200.dp)
                )

                // Espacio entre imagen de perfil y título del formulario
                Spacer(
                    modifier = Modifier.size(30.dp)
                )

                // Título
                Text(
                    // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                    text = "Ingresa tus datos",
                    fontFamily = FontFamily(Font(R.font.manrope_regular)),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )

                // Mensaje introductorio
                Text(
                    // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                    text = "Completa los siguientes campos para iniciar",
                    fontFamily = FontFamily(Font(R.font.inter_regular)),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Black,
                    textAlign = TextAlign.Center                     // Centra el texto multilínea
                )

                // Espacio entre texto introductorio y sección 1
                Spacer(
                    modifier = Modifier.size(30.dp)
                )

                // ================== SECCIÓN 1. INFORMACIÓN PERSONAL ===============
                /*
                    * Pasando el Modifier como parámetro puedo cambiar ligeramente
                    * el aspecto de este componente si decido reusarlo sin necesidad
                    * de reescribir toda la función
                    * */
                TituloSeccion(
                    // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                    "Información personal",
                    modifier = Modifier
                        .fillMaxWidth()
                )

                // Espacio entre el título de la sección 1 y los campos del formulario en sí
                Spacer(
                    modifier = Modifier.size(5.dp)
                )

                // Sección de nombre en parte 1 del formulario
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // ================== COLUMNA 1: NOMBRE ==================
                    Column(
                        modifier = Modifier
                            .weight(1f) // Ocupa una parte proporcional del Row (1 mitad porque
                                        // solo uso esto dos veces
                    ){
                        Text(
                            // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                            text = "Nombre",
                            fontFamily = FontFamily(Font(R.font.inter_regular)),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = Black,
                            textAlign = TextAlign.Center       // Centra el texto multilínea
                        )
                        TextFieldCommon(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 5.dp),
                            name = name,
                            // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                            placeHolderText = "Nombre"
                        ) { newName ->
                            // Definimos un máximo de líneas
                            if(newName.length <= 10 && newName.all{it.isLetter()}){
                                name = newName
                            }
                        }
                    }

                    // ================== COLUMNA 2: APELLIDO ==================
                    Column(
                        modifier = Modifier
                            .weight(1f) // Ocupa la otra mitad exacta del Row
                    ){
                        Text(
                            // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                            text = "Apellido",
                            fontFamily = FontFamily(Font(R.font.inter_regular)),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = Black,
                            textAlign = TextAlign.Center       // Centra el texto multilínea
                        )
                        TextFieldCommon(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 5.dp),
                            name = lastName,
                            // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                            placeHolderText = "Apellido"
                        ) { newLastName ->
                            // Definimos un máximo de líneas
                            if(newLastName.length <= 10 && newLastName.all{it.isLetter()}){
                                lastName = newLastName
                            }
                        }
                    }
                }

                // Espacio entre el nombre y apellido y la fecha de nacimiento
                Spacer(
                    modifier = Modifier.size(10.dp)
                )
                // Sección de fecha de nacimiento
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Text(
                            // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                            text = "Fecha de nacimiento",
                            fontFamily = FontFamily(Font(R.font.inter_regular)),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = Black,
                            textAlign = TextAlign.Center       // Centra el texto multilínea
                        )

                        TextFieldBirthday(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 5.dp),
                            date = birthday
                        ) { newBirthday ->
                            birthday = newBirthday
                        }
                    }
                }

                // Espacio entre la fecha de nacimiento y la selección de género
                Spacer(
                    modifier = Modifier.size(10.dp)
                )
                // TextField con el menú desplegable de la sección de elegir género
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        Text(
                            // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                            text = "Género",
                            fontFamily = FontFamily(Font(R.font.inter_regular)),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = Black,
                            textAlign = TextAlign.Center       // Centra el texto multilínea
                        )

                        TextFieldGender(
                            modifier = Modifier,
                            selectedGender = gender
                        ) { newGender ->
                            gender = newGender
                        }
                    }
                }



            }

        }
    }
}

// ============================= TOP BAR =============================
// Función que se encarga de generar el Composable de mi TopBar
@Composable
fun TopBar(
    content: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
    ){
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart) // Alinea el botón a la izquierda
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.back_arrow),
                // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                contentDescription = "Atrás",
                tint = DarkBlue
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

// ============================= TÍTULOS DE SECCIONES DE FORMULARIO =============================
@Composable
fun TituloSeccion(texto: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1. Línea vertical azul
        Box(
            modifier = Modifier
                .width(4.dp)       // Grosor de la línea
                .height(24.dp)      // Altura de la línea
                .clip(CircleShape)  // Orillas redondas
                .background(DarkBlue)
        )

        // 2. Espacio entre la línea y el texto
        Spacer(modifier = Modifier.width(12.dp))

        // 3. El texto del título
        Text(
            text = texto,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        )
    }
}


// ============================= FOTO DE PERFIL =============================
@Composable
fun FotoPerfil(modifier: Modifier = Modifier){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // 1. Imagen de perfil
        Image(
            painter = painterResource(id = R.drawable.foto_perfil),
            // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .border(
                    BorderStroke(2.dp, DarkBlue),
                    CircleShape
                )
                .padding(2.dp)
                .clip(CircleShape)
        )

        // 2. Ícono de cambiar imagen (Usar IconButton)
        IconButton(
            onClick = {},
            modifier = Modifier
                .size(32.dp)
                .background(color = DarkBlue, shape = CircleShape)
                .align(Alignment.BottomEnd) // Alinea el botón en la esquina inferior derecha
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.cambiar_foto_lapiz),
                // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                contentDescription = "Cambiar foto de perfil",
                tint = TopBarColor,
                modifier = Modifier.size(20.dp)
            )
        }

    }
}


// ============================= CAMPOS DEL FORMULARIO =============================
@Composable
fun TextFieldCommon(
    modifier: Modifier = Modifier,
    // Ya tengo la manera de recibir el estado y la manera de modificarlo
    name: String, // Nombre formulario. Primera sección
    placeHolderText: String,
    onNameChange: (String) -> Unit
){
    TextField(
        value = name,
        onValueChange = { newName ->
            onNameChange(newName)
        },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(
                text = placeHolderText,
                color = Neutral
            )
        },
        colors = colors(
            // Quita la línea inferior cuando está y no está seleccionado
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            // Define el color de fondo
            focusedContainerColor = GrayTextField,
            unfocusedContainerColor = GrayTextField,
            // Define el color de las letras que escribimos
            focusedTextColor = Black,
            unfocusedTextColor = Black
        )
    )
}

// ===============!!!!!!!!!!!!!!!!! MODIFICACIÓN!!!!!!!!!!!!!!!!! ===============
// Agregar que funcione el ícono de calendario para que no puedan escribir en este campo
@Composable
fun TextFieldBirthday(
    modifier: Modifier = Modifier,
    date: String,
    onDateChange: (String) -> Unit
){
    TextField(
        value = date,
        onValueChange = { newDate ->
            onDateChange(newDate)
        },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(
                text = "mm/dd/yyyy",
                color = Neutral
            )
        },
        // Para agregar un botón que después abrirá un calendario
        trailingIcon = {
            IconButton(onClick = {
                // !!!!!!!!!!! ========== Agregar funcionalidad ========= !!!!!!!!!!!
                // DatePicker?? Investigar
            }) {
                Icon(
                    // Ícono por defecto dentro de la biblioteca de Material
                    imageVector = Icons.Default.DateRange,
                    // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                    contentDescription = "Seleccionar fecha de nacimiento",
                    tint = Neutral
                )
            }
        },
        // Teclado numérico si el usuario decide escribir la fecha a mano
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        // Colores para quitar la línea inferior del TextField y definir el fondo de este
        colors = colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = GrayTextField,
            unfocusedContainerColor = GrayTextField,
            focusedTextColor = Black,
            unfocusedTextColor = Black
        )
    )
}

@Composable
fun TextFieldGender(
    modifier: Modifier = Modifier,
    selectedGender: String,
    onGenderChange: (String) -> Unit
){
    // Estado local para saber si la lista está desplegada o no
    var expanded by remember{
        mutableStateOf(false)
    }

    // Opciones del menú
    val opciones = listOf("Hombre", "Mujer")

    // Box principal para que el menú sepa dónde aparecer
    Box(
        modifier = modifier
    )
    {
        // El TextField visual
        TextField(
            value = selectedGender,
            onValueChange = {},
            readOnly = true, // Evita que se escriba
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            placeholder = {
                // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                Text("Selecciona tu género", color = Neutral)
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                    contentDescription = "Desplegar menú",
                    tint = Neutral
                )
            },
            colors = colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = GrayTextField,
                unfocusedContainerColor = GrayTextField,
                focusedTextColor = Black,
                unfocusedTextColor = Black
            )
        )
        // Este Box invisible se pone encima del TextField y atrapa el clic
        // para abrir el menú. Es la forma más segura de hacer un campo clickable.
        Box(
            modifier = Modifier
                .matchParentSize() // Toma el tamaño exacto del TextField anterior
                .clickable {
                    expanded = true
                }
        )
        DropdownMenu(
            expanded = expanded,
            // Cierra al tocar fuera
            onDismissRequest = {
                expanded = false
            }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onGenderChange(opcion)
                        expanded = false
                    }
                )
            }
        }
    }
}

//@Composable
//fun TextFieldNumber(){
//
//}
//
//@Composable
//fun TextFieldEmail(){
//
//}

// ======= Función necesaria para previsualizar la app dentro del IDE =======
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Proyecto1FormularioTheme {
        MainScreen()
    }
}