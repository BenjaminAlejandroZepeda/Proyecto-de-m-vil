package com.example.legendarymotorsport_app.model

data class SupportTopic(
    val title: String,
    val description: String
)

val supportTopics = listOf(
    SupportTopic("¿Cómo realizar un pedido?", "Explora el catálogo, presiona en 'Ver Detalles' y presiona en 'Comprar'."),
    SupportTopic("¿Cómo contactar soporte?", "Puedes escribirnos a soporte@legendarymotorsport.com."),
    SupportTopic("¿Cómo ver tus favoritos?", "Ve al menú lateral y selecciona 'Favoritos'."),
    SupportTopic("¿Cómo cerrar sesión?", "Presiona el botón 'Cerrar sesión' en el menú.")
)
