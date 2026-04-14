package com.eajh.proyecto1_formulario

import android.os.Bundle
import android.util.Patterns
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
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.eajh.proyecto1_formulario.ui.theme.BackgroundApp
import com.eajh.proyecto1_formulario.ui.theme.Black
import com.eajh.proyecto1_formulario.ui.theme.DarkBlue
import com.eajh.proyecto1_formulario.ui.theme.FondoInterest
import com.eajh.proyecto1_formulario.ui.theme.Gray
import com.eajh.proyecto1_formulario.ui.theme.GrayTextField
import com.eajh.proyecto1_formulario.ui.theme.LightBlue
import com.eajh.proyecto1_formulario.ui.theme.LightRed
import com.eajh.proyecto1_formulario.ui.theme.Neutral
import com.eajh.proyecto1_formulario.ui.theme.NormalRed
import com.eajh.proyecto1_formulario.ui.theme.Proyecto1FormularioTheme
import com.eajh.proyecto1_formulario.ui.theme.TopBarColor
import com.eajh.proyecto1_formulario.ui.theme.White
import com.joelkanyi.jcomposecountrycodepicker.component.CountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.KomposeCountryCodePicker
import com.joelkanyi.jcomposecountrycodepicker.component.rememberKomposeCountryCodePickerState
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
//                MainScreen(
//                    modifier = Modifier.fillMaxSize()
//                )
                AppNavigation()

            }
        }
    }
}

// Serializar es convertir los elementos en bits para después pasarlos hacia
// otro elemento diferente

// Pantalla 1: Principal - Formulario y sus campos
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController
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

    var phoneNumber by rememberSaveable {
        mutableStateOf("")
    }

    /*
    * Biblioteca Kompose Country Code Picker
    * */
    // Primero, debemos configurar la bandera y el código en un estado
    // específico de esta biblioteca
    val pickerState = rememberKomposeCountryCodePickerState(
        defaultCountryCode = "MX",
        showCountryCode = true,
        showCountryFlag = true
    )

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var bioText by rememberSaveable {
        mutableStateOf("")
    }


    // La variable que nos va a ayudar a cambiar de estado está vacío inicialmente y va
    // cambiando en tiempo real dependiendo de lo que haga el usuario en pantalla.
    var interesesSeleccionados by rememberSaveable {
        mutableStateOf(setOf<String>())
    }

    // Como se explica en el Composable, nos va a ayudar para ver si alguna de las
    // opciones a seleccionar está dentro del set, si no está, lo agrega. Si sí está, lo
    // quita.
    val alHacerClic = { interes: String ->
        interesesSeleccionados = if (interesesSeleccionados.contains(interes)) {
            interesesSeleccionados - interes
        } else {
            interesesSeleccionados + interes
        }
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
            TopBarInicial(
                // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                content = "Creación de perfil",
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
                    modifier = Modifier.size(70.dp)
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
                        .fillMaxWidth(),
                    color = DarkBlue
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
                            // Se permiten espacios en blanco por si son nombres compuestos o se quiere ingresar
                            // alguna sugerencia del teclado (que automáticamente agregan un espacio en blanco
                            // al final tras el autocompletado)
                            if(newName.length <= 20 && newName.all { it.isLetter() || it.isWhitespace() }){
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
                            if(newLastName.length <= 20 && newLastName.all { it.isLetter() || it.isWhitespace() }){
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

                        // Mensaje de error si la fecha no es válida
                        val edadValida = tieneMinimo13(birthday)
                        val mostrarErrorEdad = birthday.isNotBlank() && !edadValida


                        Column(modifier = Modifier.fillMaxWidth()) {
                            TextFieldBirthday(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 5.dp),
                                date = birthday
                            ) { newBirthday ->
                                birthday = newBirthday
                            }

                            // Mensaje de error si la fecha ya se llenó pero no da 13 años
                            if (mostrarErrorEdad) {
                                Text(
                                    text = "Debes tener al menos 13 años para registrarte",
                                    color = NormalRed, // Tu color rojo
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                                )
                            }
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


                // Espacio entre sección 1 y 2 del formulario
                Spacer(
                    modifier = Modifier.size(70.dp)
                )

                // ================== SECCIÓN 2. INFORMACIÓN DE CONTACTO ===============
                TituloSeccion(
                    // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                    "Información de contacto",
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = DarkBlue
                )

                // Espacio entre el título de la sección 1 y los campos del formulario en sí
                Spacer(
                    modifier = Modifier.size(5.dp)
                )

                // TextField con el campo para ingresar el número de teléfono
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
                            text = "Número telefónico",
                            fontFamily = FontFamily(Font(R.font.inter_regular)),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = Black,
                            textAlign = TextAlign.Center       // Centra el texto multilínea
                        )

                        Spacer(
                            modifier = Modifier.size(5.dp)
                        )

                        TextFieldPhoneNumber(
                            modifier = Modifier,
                            phoneNumber = phoneNumber,
                            pickerState = pickerState
                        ) { newPhoneNumber ->
                            if(newPhoneNumber.length <= 10 && newPhoneNumber.all{it.isDigit()}){
                                phoneNumber = newPhoneNumber
                            }
                        }

                    }
                }

                // Espacio entre el número de teléfono y el correo electrónico
                Spacer(
                    modifier = Modifier.size(10.dp)
                )

                // TextField con el campo para ingresar el correo electrónico
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
                            text = "Correo electrónico",
                            fontFamily = FontFamily(Font(R.font.inter_regular)),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = Black,
                            textAlign = TextAlign.Center       // Centra el texto multilínea
                        )

                        Spacer(
                            modifier = Modifier.size(5.dp)
                        )

                        TextFieldEmail(
                            modifier = Modifier,
                            email = email
                        ) { newEmail ->
                            email = newEmail
                        }


                    }
                }

                // Espacio entre sección 2 y 3 del formulario
                Spacer(
                    modifier = Modifier.size(70.dp)
                )

                // ================== SECCIÓN 3. INTERESES ===============
                TituloSeccion(
                    // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                    "Intereses",
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = DarkBlue
                )

                // Espacio entre el título de la sección 2 y los campos del formulario en sí
                Spacer(
                    modifier = Modifier.size(10.dp)
                )

                // TextField con el campo para ingresar el correo electrónico
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    // Contenedor principal de las filas (espacio vertical entre renglones)
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                        // --- PRIMER RENGLÓN ---
                        Row(
                            modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            InteresesOpcion(
                                // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                                text = "Música",
                                icon = Icons.Default.MusicNote,
                                isSelected = interesesSeleccionados.contains("Música"),
                                onClick = { alHacerClic("Música") }
                            )
                            InteresesOpcion(
                                // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                                text = "Deportes",
                                icon = Icons.Default.SportsBasketball,
                                isSelected = interesesSeleccionados.contains("Deportes"),
                                onClick = { alHacerClic("Deportes") }
                            )
                            InteresesOpcion(
                                // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                                text = "Tech",
                                icon = Icons.Default.Computer,
                                isSelected = interesesSeleccionados.contains("Tech"),
                                onClick = { alHacerClic("Tech") }
                            )
                        }
                        // --- SEGUNDO RENGLÓN ---
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            InteresesOpcion(
                                // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                                text = "Viajes",
                                icon = Icons.Default.AirplanemodeActive,
                                isSelected = interesesSeleccionados.contains("Viajes"),
                                onClick = { alHacerClic("Viajes") }
                            )

                            InteresesOpcion(
                                text = "Cocina",
                                icon = Icons.Default.Restaurant,
                                isSelected = interesesSeleccionados.contains("Cocina"),
                                onClick = { alHacerClic("Cocina") }
                            )
                        }
                    }
                }


                // Espacio entre sección 3 y 4 del formulario
                Spacer(
                    modifier = Modifier.size(70.dp)
                )

                // ================== SECCIÓN 4. DESCRIPCIÓN DEL PERFIL ===============
                TituloSeccion(
                    // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                    "Sobre mí",
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = DarkBlue
                )

                // Espacio entre el título de la sección 2 y los campos del formulario en sí
                Spacer(
                    modifier = Modifier.size(10.dp)
                )

                // TextField con sección "Sobre mí"
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
                            text = "Biografía pública",
                            fontFamily = FontFamily(Font(R.font.inter_regular)),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = Black,
                            textAlign = TextAlign.Center       // Centra el texto multilínea
                        )

                        Spacer(
                            modifier = Modifier.size(5.dp)
                        )

                        TextFieldCommon(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp),
                            name = bioText,
                            // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                            placeHolderText = "Dile al mundo tus sueños, pasiones o lo que te hace único ..."
                        ) { newBioText ->
                            // Definimos un máximo de líneas
                            if(newBioText.length <= 150){
                                bioText = newBioText
                            }
                        }
                    }
                }

                // Espacio entre sección 4 y 5 del formulario
                Spacer(
                    modifier = Modifier.size(30.dp)
                )

                // ================== SECCIÓN 5. BOTÓN DE ENVIAR ===============

                // COMPOSABLE DEL BOTÓN DE ENVIAR

                // Espacio entre el título de la sección 2 y los campos del formulario en sí
                Spacer(
                    modifier = Modifier.size(10.dp)
                )

                // Guardamos el resultado de la edad en una variable para usarla fácilmente
                val edadValida = tieneMinimo13(birthday)

                // Validación básica de que no estén vacíos los campos del formulario
                val formularioLleno = name.isNotBlank() &&
                        lastName.isNotBlank() &&
                        edadValida &&
                        gender.isNotBlank() &&
                        phoneNumber.isNotBlank() &&
                        Patterns.EMAIL_ADDRESS.matcher(email).matches() // Repetimos validación del Composable pero viendo que sí sea un formato válido

                ProfileSaveButton(
                    text = "Guardar Perfil",
                    enabled = formularioLleno,
                    onClick = {

                        val listaIntereses = interesesSeleccionados.toList()

                        // Obtenemos el número completo (con extensión)
                        val numeroCompleto = pickerState.getFullPhoneNumber()

                        // Viajamos a la siguiente pantalla pasando los datos
                        navController.navigate(
                            DataScreenDestination(
                                name = name,
                                lastName = lastName,
                                birthday = birthday,
                                gender = gender,
                                phoneNumber = numeroCompleto,
                                email = email,
                                bioText = bioText,
                                intereses = listaIntereses
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.size(20.dp)
                )

            }

        }
    }
}

// ============================= TOP BAR =============================
// Función que se encarga de generar el Composable de mi TopBarInicial y TopBar normal (DataScreen)
@Composable
fun TopBarInicial(
    content: String,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
    ){
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
fun TituloSeccion(texto: String, modifier: Modifier = Modifier, color: Color) {
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
                .background(color)
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

@Composable
fun TextFieldBirthday(
    modifier: Modifier = Modifier,
    date: String,
    onDateChange: (String) -> Unit
){
    // Estado para controlar si el calendario se ve o no
    var showModal by remember { mutableStateOf(false) }

    // Envolvemos el TextField en un Box principal
    Box(modifier = modifier) {

        // El TextField ahora es solo lectura para que la aplicación no intente abrir
        // el teclado
        TextField(
            value = date,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(), // El TextField ocupa todo el Box padre
            shape = RoundedCornerShape(12.dp),
            placeholder = {
                Text(
                    // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                    text = "MM/DD/YYYY",
                    color = Neutral
                )
            },
            trailingIcon = {
                // Ya no necesitamos el IconButton aquí, porque toda la caja será clickable,
                // así que dejamos solo el Icon
                Icon(
                    imageVector = Icons.Default.DateRange,
                    // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                    contentDescription = "Seleccionar fecha de nacimiento",
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

        //  Este Box invisible se pone encima y atrapa el clic de toda el área
        Box(
            modifier = Modifier
                .matchParentSize() // Toma el tamaño exacto del TextField
                .clickable {
                    showModal = true // Abre el calendario
                }
        )
    }

    if (showModal) {
        DatePickerModal(
            onDateSelected = { millisSeleccionados ->
                if (millisSeleccionados != null) {
                    val fechaFormateada = convertMillisToDate(millisSeleccionados)
                    onDateChange(fechaFormateada)
                }
            },
            onDismiss = {
                showModal = false
            }
        )
    }
}

// Composable encargado de seleccionar la fecha dentro del modal de calendario que
// Android despliega. Implementación de la documentación
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    // Se pone "Long?", porque el calendario de Material 3 devuelve un número de este tipo
    // Este número representa los milisegundos transcurridos desde el 1 de enero de 1970
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

// Función que permite transformar los milisegundos obtenidos desde el 1 de enero de 1970
// a la actualidad en una fecha legible con formato de fecha de calendario
fun convertMillisToDate(millis: Long): String {
                // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    // Se agrega esta línea para evitar el error del día anterior
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(Date(millis))
}

// Función que se encarga de validar que el usuario tenga mínimo 13 años cumplidos
fun tieneMinimo13(fechaNacimiento: String): Boolean {
    // Si está vacío, ni siquiera calculamos
    if (fechaNacimiento.isBlank()) return false

    return try {
        // Le decimos a Kotlin cómo está escrita nuestra fecha
        // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        val fechaNac = LocalDate.parse(fechaNacimiento, formatter)

        // Obtenemos la fecha del celular hoy
        val hoy = LocalDate.now()

        // Calculamos el periodo exacto entre las dos fechas
        val edad = Period.between(fechaNac, hoy).years

        // Retorna verdadero si tiene 13 o más
        edad >= 13

    } catch (e: DateTimeParseException) {
        // Si el usuario escribió algo que no es una fecha válida, retorna falso
        false
    }
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

@Composable
fun TextFieldPhoneNumber(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    pickerState: CountryCodePicker,
    onPhoneNumberChange: (String) -> Unit
){

    // Composable para mostrar este elemento
    KomposeCountryCodePicker(
        modifier = modifier.fillMaxWidth(),
        text = phoneNumber,
        onValueChange = { newNumber ->
            onPhoneNumberChange(newNumber)
        },
        state = pickerState,
        textStyle = TextStyle(
            color = Neutral
        ),
        shape = RoundedCornerShape(12.dp), // Bordes redondeados
        placeholder = {
            Text("55 1234 5678", color = Neutral)
        },

        // Estilo de "TextField" usado a lo largo del formulario
        colors = colors(
            focusedContainerColor = GrayTextField,
            unfocusedContainerColor = GrayTextField,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Black,
            focusedTextColor = Black,
            unfocusedTextColor = Black
        )
    )

}

@Composable
fun TextFieldEmail(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChange: (String) -> Unit
){
    // VALIDACIÓN: Es error si el campo NO está vacío y NO hace match con el formato de correo
    val isError = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    /*
    * Parámetro isError: Es como un switch. Al recibir un valor true, sobrescribe instantáneamente
    * los colores normales del TextField por la paleta de errores y le avisa al sistema operativo
    * que la entrada es inválida. Sin embargo, no bloquea la capacidad del usuario para seguir
    * escribiendo ni genera automáticamente un mensaje de error en pantalla; tse debe de programar
    * la condición lógica que activa este interruptor y dibujar manualmente cualquier texto de
    * advertencia debajo del campo.
    * */

    // Usamos un Column para poder poner el mensaje de error debajo del TextField
    Column(modifier = modifier) {
        TextField(
            value = email,
            onValueChange = { newEmail ->
                // El .trim() evita que el usuario agregue espacios al final sin querer
                onEmailChange(newEmail.trim())
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
            placeholder = { Text("ejemplo@correo.com", color = Neutral) },

            // Abre el teclado optimizado para correos, con el @ y .com de rápido acceso
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = isError,
            colors = colors(
                focusedContainerColor = GrayTextField,
                unfocusedContainerColor = GrayTextField,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Black,
                focusedTextColor = Black,
                unfocusedTextColor = Black,
                // Colores para cuando isError = true
                errorContainerColor = LightRed, // Cambia el fondo a un rojo muy sutil
                errorIndicatorColor = Color.Transparent, // Seguimos ocultando la línea inferior de los TextField
                errorTextColor = Black,
                errorCursorColor = NormalRed
            )
        )

        // Mensaje de error en lo que se escribe correctamente el correo electrónico
        if (isError) {
            Text(
                // !!!!!!!!!!! ========== HARD CODING ========= !!!!!!!!!!!
                text = "Formato de correo inválido",
                color = NormalRed,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}


// ============================= INTERESES =============================
/*
* Todo el proceso funciona como un ciclo automatizado donde la variable de estado
* (el Set envuelto en mutableStateOf) actúa como una caja de memoria que
* guarda sin duplicados los nombres de las opciones que el usuario ha elegido; al momento en
* que el usuario toca un botón, la función alHacerClic actúa como el controlador que actualiza
* esta caja metiendo la palabra si no estaba o sacándola si ya existía, lo que detona inmediatamente
* la "recomposición" de Jetpack Compose, obligando a cada componente InteresesOpcion a leer
* la nueva caja para que, mediante su propio condicional if, decida por sí solo pintarse de
* azul si encuentra su nombre adentro o de gris si no está, garantizando que la interfaz sea
* siempre un reflejo exacto y automático de los datos sin tener que programar cambios visuales
* manualmente.
*
* */
@Composable
fun InteresesOpcion(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
){
    // Definimos los colores dependiendo de si está o no seleccionada una opción de
    // los intereses
    val backgroundColor: Color
    val contentColor: Color

    // Se va actualizando este parámetro de acuerdo al set definido en el estado. Por eso
    // en el clickable, se agregan o quitan Strings de este set, para hacer efectiva estos
    // cambios de colores
    if(isSelected){
       backgroundColor = LightBlue
       contentColor = Gray
    } else{
       backgroundColor = FondoInterest
       contentColor = Neutral
    }

    /*
    * El Surface es como un Box pero con propiedades específicas para poner un
    * color de fondo más fácilmente.
    * */
    Surface(
        modifier = Modifier
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(50),
        color = backgroundColor,
        contentColor = contentColor
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre ícono y texto
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp) // Íconos pequeños
            )
            Text(
                text = text,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


// =========================== BOTÓN DE GUARDAR PERFIL ==============================
@Composable
fun ProfileSaveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    text: String
) {
    // Botón base
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            // Colores para formulario lleno
            containerColor = DarkBlue,
            contentColor = White,

            // Colores para formulario con campos faltantes
            disabledContentColor = Neutral.copy(0.5f), // Para que sea un poco transparente
            disabledContainerColor = Gray
        ),
        // Elevación sutil para el estado habilitado
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        // Padding interno del contenido
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Texto del botón
            Text(
                text = text,
                fontWeight = FontWeight.Bold
            )
            // Espaciado pequeño entre el texto y el icono
            Spacer(modifier = Modifier.width(8.dp))
            // Ícono de guardar
            Icon(
                imageVector = Icons.Default.SaveAlt,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
                //tint = White
            )
        }
    }
}


// ============================ NAVEGACIÓN ComposeNavigation 2 ====================

@Serializable
object MainScreenDestination

@Serializable
data class DataScreenDestination(
    val name: String,
    val lastName: String,
    val birthday: String,
    val gender: String,
    val phoneNumber: String,
    val email: String,
    val bioText: String,
    val intereses: List<String> // Convertiremos el Set a List al enviar
)


// Composable con el NavHost
@Composable
fun AppNavigation(){
    // Estado para recordar en que pantalla estamos
    val navController: NavHostController = rememberNavController()

    // Bloque de navegación
    NavHost(
        navController = navController,
        startDestination = MainScreenDestination
    ){
        // Aquí estamos dentro del contexto de nuestro grafo de navegación

        // Pantalla 1: Formulario
        composable<MainScreenDestination>{
            // Mandamos a llamar a mi MainScreen
            MainScreen(navController = navController)
        }

        // Pantalla 2: Datos a mostrar del formulario
        composable<DataScreenDestination>{ backStackEntry ->
            // Recuperamos los datos del formulario
            val datosFormulario = backStackEntry.toRoute<DataScreenDestination>()

            // Mandamos a llamar a mi DataScreen
            DataScreen(
                datos = datosFormulario,
                navController = navController
            )
        }
    }
}


// Componente auxiliar para mostrar cada par de "Etiqueta: Valor"
@Composable
fun DatoFila(
    etiqueta: String,
    valor: String
)
{
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth())
    {
        Text(
            text = etiqueta,
            fontWeight = FontWeight.Bold,
            color = DarkBlue,
            fontSize = 14.sp
        )
        Text(
            text = valor,
            fontSize = 16.sp,
            color = Black
        )
    }
}

// Composable correspondiente con la pantalla de datos
@Composable
fun DataScreen(
    datos: DataScreenDestination, // Recibe el paquete de datos
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopBar(
                content = "Resumen de Perfil",
                onBackClick = {
                    // Esto destruye la DataScreen y se regresa al formulario
                    // con todos los datos intactos
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TopBarColor)
                    .statusBarsPadding()
                    .height(64.dp)
                    .padding(horizontal = 8.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BackgroundApp)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()), // Habilitamos scroll por si la bio es larga
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Tus datos guardados:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Juntamos el nombre y apellido en una sola fila para que se vea mejor
            DatoFila(etiqueta = "Nombre completo", valor = "${datos.name} ${datos.lastName}")
            DatoFila(etiqueta = "Fecha de nacimiento", valor = datos.birthday)
            DatoFila(etiqueta = "Género", valor = datos.gender)
            DatoFila(etiqueta = "Teléfono", valor = datos.phoneNumber)
            DatoFila(etiqueta = "Correo", valor = datos.email)

            // Si el usuario escribió una bio, la mostramos
            if (datos.bioText.isNotBlank()) {
                DatoFila(etiqueta = "Biografía", valor = datos.bioText)
            }

            // Si el usuario seleccionó intereses (la lista no está vacía)
            if(datos.intereses.isNotEmpty()){
                val textoIntereses = datos.intereses.joinToString(separator = ", ")
                DatoFila(etiqueta = "Intereses seleccionados", valor = textoIntereses)
            }


            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

