
package com.example.misgastos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.size
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

data class Gasto(
    val descripcion: String,
    val monto: Double,
    val categoria: String
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PantallaLogin()
        }
    }
}

@Composable
fun PantallaLogin() {

    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var sesionIniciada by remember { mutableStateOf(false) }

    if (sesionIniciada) {
        PantallaBienvenida(
            usuario = usuario,
            cerrarSesion = {
                sesionIniciada = false
                usuario = ""
                contrasena = ""
                mensaje = ""
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE3F2FD))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
            painter = painterResource(id = R.drawable.logo_gastos),
            contentDescription = "Logo de Mis Gastos",
            modifier = Modifier.size(140.dp),
            contentScale = ContentScale.Fit
        )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mis Gastos",
                style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFF1565C0)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Iniciar sesion")

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = usuario,
                onValueChange = { usuario = it },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contrasena") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (usuario == "admin" && contrasena == "1234") {
                        sesionIniciada = true
                    } else {
                        mensaje = "Usuario o contrasena incorrectos"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = mensaje,
                color = Color.Red
            )
        }
    }
}
@Composable
fun PantallaBienvenida(
    usuario: String,
    cerrarSesion: () -> Unit
) {
    var presupuesto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var mostrarResumen by remember { mutableStateOf(false) }
    var fotoComprobante by remember {
        mutableStateOf<Bitmap?>(null)
    }
    var categoria by remember { mutableStateOf("Alimentacion") }
    var menuExpandido by remember { mutableStateOf(false) }
    val categorias = listOf(
        "Alimentacion",
        "Transporte",
        "Compras",
        "Salud",
        "Otros"
    )
    val abrirCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { foto ->
        if (foto != null) {
            fotoComprobante = foto
            mensaje = "Foto tomada correctamente"
        } else {
            mensaje = "No se tomo ninguna foto"
        }
    }

    val listaGastos = remember { mutableStateListOf<Gasto>() }
    val totalGastado = listaGastos.sumOf { it.monto }
    val dineroDisponible = (presupuesto.toDoubleOrNull() ?: 0.0) - totalGastado

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
        painter = painterResource(id = R.drawable.logo_gastos),
        contentDescription = "Control de gastos",
        modifier = Modifier.size(70.dp),
        contentScale = ContentScale.Fit
    )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Bienvenido, $usuario",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFF1565C0)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = cerrarSesion,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesion")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Control de Gastos",
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                mostrarResumen = !mostrarResumen
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (mostrarResumen) "Ocultar resumen"
                else "Ver resumen"
            )
        }

        if (mostrarResumen) {

            val totalAlimentacion = listaGastos
                .filter { it.categoria == "Alimentacion" }
                .sumOf { it.monto }

            val totalTransporte = listaGastos
                .filter { it.categoria == "Transporte" }
                .sumOf { it.monto }

            val totalCompras = listaGastos
                .filter { it.categoria == "Compras" }
                .sumOf { it.monto }

            val totalSalud = listaGastos
                .filter { it.categoria == "Salud" }
                .sumOf { it.monto }

            val totalOtros = listaGastos
                .filter { it.categoria == "Otros" }
                .sumOf { it.monto }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = "Resumen de gastos",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Presupuesto: Gs. ${presupuesto.toDoubleOrNull() ?: 0.0}")
                    Text("Total gastado: Gs. $totalGastado")
                    Text("Dinero restante: Gs. $dineroDisponible")

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Alimentacion: Gs. $totalAlimentacion")
                    Text("Transporte: Gs. $totalTransporte")
                    Text("Compras: Gs. $totalCompras")
                    Text("Salud: Gs. $totalSalud")
                    Text("Otros: Gs. $totalOtros")

                    Spacer(modifier = Modifier.height(8.dp))

                    if (dineroDisponible < 0) {
                        Text(
                            text = "Superaste tu presupuesto",
                            color = Color.Red
                        )
                    } else {
                        Text(
                            text = "Tu presupuesto esta controlado",
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = presupuesto,
            onValueChange = { presupuesto = it },
            label = { Text("Presupuesto disponible") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripcion del gasto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = monto,
            onValueChange = { monto = it },
            label = { Text("Monto del gasto") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Text("Categoria del gasto")

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { menuExpandido = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(categoria)
                }

                DropdownMenu(
                    expanded = menuExpandido,
                    onDismissRequest = { menuExpandido = false }
                ) {
                    categorias.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                categoria = opcion
                                menuExpandido = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val valor = monto.toDoubleOrNull()
                val presupuestoValor = presupuesto.toDoubleOrNull()

                if (
                    descripcion.isNotBlank() &&
                    valor != null &&
                    valor > 0 &&
                    presupuestoValor != null &&
                    presupuestoValor > 0
                ) {
                    listaGastos.add(
                        Gasto(descripcion, valor, categoria)
                    )

                    descripcion = ""
                    monto = ""
                    mensaje = "Gasto registrado correctamente"
                } else {
                    mensaje = "Completa los datos correctamente"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar gasto")
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                abrirCamara.launch(null)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tomar foto del comprobante")
        }
        if (fotoComprobante != null) {

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                bitmap = fotoComprobante!!.asImageBitmap(),
                contentDescription = "Foto del comprobante",
                modifier = Modifier.size(100.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = mensaje,
            color = Color(0xFF1565C0)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Total gastado: Gs. $totalGastado")

                Spacer(modifier = Modifier.height(8.dp))

                Text("Dinero disponible: Gs. $dineroDisponible")

                if (dineroDisponible < 0) {
                    Text(
                        text = "Atencion: Superaste tu presupuesto",
                        color = Color.Red
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Gastos registrados",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            items(listaGastos) { gasto ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text("Descripcion: ${gasto.descripcion}")
                        Text("Monto: Gs. ${gasto.monto}")
                        Text("Categoria: ${gasto.categoria}")

                        OutlinedButton(
                            onClick = {
                                listaGastos.remove(gasto)
                                mensaje = "Gasto eliminado"
                            }
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}