package ni.edu.uam.practicasemana6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ni.edu.uam.practicasemana6.ui.theme.PracticaSemana6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticaSemana6Theme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    var textoInput by remember { mutableStateOf("") }
    var textoMostrado by remember { mutableStateOf("") }
    val imagenes = listOf(R.drawable.img1, R.drawable.img2, R.drawable.img3)
    var indiceImagen by remember { mutableStateOf(0) }
    var contadorCambios by remember { mutableStateOf(0) }

    val cardShape = RoundedCornerShape(16.dp)
    val cardBg = MaterialTheme.colorScheme.surface
    val cardBorder = MaterialTheme.colorScheme.outlineVariant

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
            .padding(top = 56.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título
        Text(
            text = "Galería interactiva",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Escribe tu nombre y explora las imágenes",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
        )

        // Card: Nombre
        Surface(
            shape = cardShape,
            color = cardBg,
            tonalElevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .border(0.5.dp, cardBorder, cardShape)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "TU NOMBRE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.08.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = textoInput,
                        onValueChange = { textoInput = it },
                        placeholder = { Text("¿Cómo te llamas?", fontSize = 15.sp) },
                        singleLine = true,
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Button(
                        onClick = {
                            textoMostrado = if (textoInput.isBlank()) "" else "Hola, ${textoInput.trim()}!"
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text("Saludar", fontSize = 14.sp)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(10.dp))
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = textoMostrado.ifEmpty { "Ingresa tu nombre arriba" },
                        fontSize = if (textoMostrado.isEmpty()) 14.sp else 16.sp,
                        fontWeight = if (textoMostrado.isEmpty()) FontWeight.Normal else FontWeight.Medium,
                        color = if (textoMostrado.isEmpty())
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else
                            MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Card: Galería
        Surface(
            shape = cardShape,
            color = cardBg,
            modifier = Modifier
                .fillMaxWidth()
                .border(0.5.dp, cardBorder, cardShape)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "GALERÍA",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.08.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 14.dp)
                )

                // Imagen
                Image(
                    painter = painterResource(id = imagenes[indiceImagen]),
                    contentDescription = "Imagen ${indiceImagen + 1}",
                    modifier = Modifier
                        .size(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                Spacer(Modifier.height(12.dp))

                // Dots
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    imagenes.indices.forEach { i ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (i == indiceImagen)
                                        MaterialTheme.colorScheme.onBackground
                                    else
                                        MaterialTheme.colorScheme.outlineVariant
                                )
                                .clickable {
                                    if (i != indiceImagen) {
                                        indiceImagen = i
                                        contadorCambios++
                                    }
                                }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Controles
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(onClick = {
                        indiceImagen = (indiceImagen - 1 + imagenes.size) % imagenes.size
                        contadorCambios++
                    }) { Text("←", fontSize = 18.sp) }

                    Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = if (contadorCambios == 1) "1 cambio" else "$contadorCambios cambios",
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(onClick = {
                        indiceImagen = (indiceImagen + 1) % imagenes.size
                        contadorCambios++
                    }) { Text("→", fontSize = 18.sp) }
                }

                Divider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = MaterialTheme.colorScheme.outlineVariant,
                    thickness = 0.5.dp
                )

                // Stats
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        Pair("$contadorCambios", "Cambios de imagen"),
                        Pair("${indiceImagen + 1}", "Imagen actual")
                    ).forEach { (val_, lbl) ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Text(val_, fontSize = 22.sp, fontWeight = FontWeight.Medium)
                            Text(lbl, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Botón reiniciar
        OutlinedButton(
            onClick = {
                textoInput = ""
                textoMostrado = ""
                indiceImagen = 0
                contadorCambios = 0
            },
            modifier = Modifier.fillMaxWidth().height(44.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Reiniciar todo", fontSize = 14.sp)
        }
    }
}