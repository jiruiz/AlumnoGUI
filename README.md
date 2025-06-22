## Funcionalidades Implementadas

🔁 **Reutilización de conexiones (TXT y SQL)**  
- Si ya se ha establecido la conexión, no debe solicitarse ni establecerse nuevamente. (**SE REALIZÓ EN EL SQL**)

👁️ **Ver Eliminados**  
- Por defecto, esta opción no debe estar seleccionada.  (**OK, SE VEN LOS ELIMINADOS SELECCIONANDO EL CHECK SOLAMENTE**)
- Al activarse, debe recargar la grilla para mostrar todos los alumnos: tanto activos como eliminados. (**OK, SE RECARGA LA GRILLA CON LA CONDICION INDICADA DE MOSTRAR TODOS**)

📋 **Grilla de Alumnos**  
- Debe mostrar al menos las siguientes columnas:  
  - DNI  
  - Apellido  
  - Nombre  
  - Estado  
- Para visualizar los datos completos de un alumno, se utilizará la acción `<Consultar>`. (**OK, SE MUESTRAN DNI,NOMBRE,APELLIDO Y ESTADO, TAMBIEN EN EL CONSULTAR**)

✏️ **Acción `<Modificar>`**  
- Todos los campos deben poder modificarse, excepto el DNI (clave primaria). (**OK, SE MODIFICA TODO MENOS EL DNI**)

❌ **Acción `<Eliminar>`**  (**OK, SE PRESENTA EL BOTON DE CONFIRMAR LA ELIMINACION TAMBIEN ES UNA BAJA LOGICA, CAMBIA EL ESTADO**)
- Al presionar esta acción, debe mostrarse un mensaje de confirmación.  
- Tanto para TXT como SQL, debe realizarse una baja lógica (cambio de estado).

✅ **Validaciones en el formulario de carga de alumno**  (**OK, VALIDA CAMPOS, MUETRA MENSAJES EN JOPTIONPANE EN CADA EXCEPTIONS**)
- Validar la obligatoriedad de los campos.  
- Mostrar los mensajes de error en un popup.  
- Las validaciones pueden implementarse mediante `Exceptions` lanzadas desde el backend, y luego capturadas y mostradas en la GUI.

⚠️ **Manejo de errores**  (**OK, REALIZADO**)
- Todos los errores, ya sean generados por `exceptions` en el backend o por la interfaz gráfica, deben mostrarse al usuario mediante un popup.
