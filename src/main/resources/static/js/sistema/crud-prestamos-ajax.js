let idPrestamoEliminar=0;
let idPrestamoActualizar=0;

function obtenerPrestamos() {
    $.ajax({
        method:"GET",
        url: "/v1/api/prestamo",
        data: {},
        success: function( resultado ) {
            if(resultado.estado===1){
                let tabla=$('#example').DataTable();
                prestamos = resultado.prestamos;

                prestamos.forEach(prestamo =>{
                    let botones ='<button type="button" class="btn btn-primary mb-2" data-bs-toggle="modal" data-bs-target="#editModal" onclick="seleccionarPrestamoActualizar('+prestamo.id+');">Edit</button>';
                    botones = botones + ' <button type="button" class="btn btn-danger mb-2" data-bs-toggle="modal" data-bs-target="#deleteModal" onclick="seleccionarPrestamoEliminar('+prestamo.id+');">Delete</button>\n';
                    tabla.row.add([
                        prestamo.id,
                        prestamo.libro_id,
                        prestamo.nombre_usuario,
                        prestamo.fecha_prestamo,
                        prestamo.fecha_devolucion,
                        botones
                    ]).node().id='renglon_'+prestamo.id;
                    tabla.draw()
                })
            }
        },
        error:function (xhr,error,mensaje){

        }
    });
}

function guardarPrestamo(){
    libro_id_prestamo = document.getElementById("libro_id_prestamo").value;
    nombre_usuario_prestamo = document.getElementById("nombre_usuario_prestamo").value;
    fecha_prestamo_prestamo = document.getElementById("fecha_prestamo_prestamo").value;
    fecha_devolucion_prestamo = document.getElementById("fecha_devolucion_prestamo").value;

    if(libro_id_prestamo>0 && nombre_usuario_prestamo && fecha_prestamo_prestamo && fecha_devolucion_prestamo){
        $.ajax({
            url: "/v1/api/prestamo",
            contentType:"application/json",
            method:"POST",
            data: JSON.stringify({
                libro_id:libro_id_prestamo,
                nombre_usuario:nombre_usuario_prestamo,
                fecha_prestamo:fecha_prestamo_prestamo,
                fecha_devolucion:fecha_devolucion_prestamo
            }),
            success: function( resultado ) {
                if(resultado.estado==1){
                    let botones ='<button type="button" class="btn btn-primary mb-2" data-bs-toggle="modal" data-bs-target="#editModal" onclick="seleccionarPrestamoActualizar('+resultado.prestamo.id+');">Edit</button>';
                    botones = botones + ' <button type="button" class="btn btn-danger mb-2" data-bs-toggle="modal" data-bs-target="#deleteModal" onclick="seleccionarPrestamoEliminar('+resultado.prestamo.id+');">Delete</button>\n';

                    let tabla = $('#example').DataTable();
                    tabla.row.add([
                        resultado.prestamo.id,
                        resultado.prestamo.libro_id,
                        resultado.prestamo.nombre_usuario,
                        resultado.prestamo.fecha_prestamo,
                        resultado.prestamo.fecha_devolucion,
                        botones
                    ]).node().id='renglon_'+resultado.prestamo.id;

                    tabla.draw()
                    //Ocultar la Modal JQuery
                    $('#basicModal').hide()
                    alert(resultado.mensaje);
                }else{
                    //Todo mal
                    alert(resultado.mensaje);
                }
            },
            error:function (xhr,error,mensaje) {
                //Se dispara la funcion si no conexion al servidor
                alert("Error de comunicacion: "+error);
            }
        });
    }else{
        alert("Ingresa los datos correctamente")
    }
}

function seleccionarPrestamoActualizar(id) {
    //1.- Seleccionar el id a actualizar
    idPrestamoActualizar=id;

    $.ajax({
        method:"GET",
        url: "/v1/api/prestamo/actualizar/"+idPrestamoActualizar,
        data: {},
        success: function( resultado ) {
            if(resultado.estado===1){
                let prestamo = resultado.prestamo;
                $('#libro_id_prestamo_editar').val(prestamo.libro_id);
                $('#nombre_usuario_prestamo_editar').val(prestamo.nombre_usuario);
                $('#fecha_prestamo_prestamo_editar').val(prestamo.fecha_prestamo);
                $('#fecha_devolucion_prestamo_editar').val(prestamo.fecha_devolucion);
            }else{
                alert(resultado.mensaje);
            }
        },
        error:function (xhr,error, mensaje) {
            alert(mensaje);
        }
    });
    //3.- Mostrar los datos en el Modal
}

function actualizarPrestamo() {
    //1.- Obtener los datos que existen en el modal
    libro_id_prestamo=$('#libro_id_prestamo_editar').val();
    nombre_usuario_prestamo=$('#nombre_usuario_prestamo_editar').val();
    fecha_prestamo_prestamo=$('#fecha_prestamo_prestamo_editar').val();
    fecha_devolucion_prestamo=$('#fecha_devolucion_prestamo_editar').val();
    if(libro_id_prestamo && nombre_usuario_prestamo>0 && fecha_prestamo_prestamo && fecha_devolucion_prestamo){
        $.ajax({
            url: "/v1/api/prestamo/actualizar/"+idPrestamoActualizar,
            contentType:"application/json",
            method:"POST",
            data: JSON.stringify({
                id:idPrestamoActualizar,
                libro_id:libro_id_prestamo,
                nombre_usuario:nombre_usuario_prestamo,
                fecha_prestamo:fecha_prestamo_prestamo,
                fecha_devolucion:fecha_devolucion_prestamo
            }),
            success: function( resultado ) {
                if(resultado.estado==1){
                    let tabla = $('#example').DataTable();
                    datos = tabla.row("#renglon_"+idPrestamoActualizar).data()
                    datos[1]=libro_id_prestamo;
                    datos[2]=nombre_usuario_prestamo;
                    tabla.row("#renglon_"+idPrestamoActualizar).data(datos);
                    tabla.draw()
                    alert(resultado.mensaje);
                }else{
                    //Todo mal
                    alert(resultado.mensaje);
                }
            },
            error:function (xhr,error,mensaje) {
                //Se dispara la funcion si no conexion al servidor
                alert("Error de comunicacion: "+error);
            }
        });
    }else{
        alert("Ingresa los datos correctamente")
    }

}

function seleccionarPrestamoEliminar(id){
    let datosPrestamo=$('#example').DataTable().row('#renglon_'+id).data()
    $('#libro_id_prestamo').text(datosPrestamo[1]+' :(')
    idPrestamoEliminar=id
}

function eliminarPrestamo() {
    $.ajax({
        method: "POST",
        url: "/v1/api/prestamo/eliminar",
        contentType:"application/json",
        data:JSON.stringify({
            id:idPrestamoEliminar,
        }),
        success: function( resultado ) {
            if(resultado.estado===1){
                //Eliminar el renglon del DataTable
                $('#example').DataTable().row('#renglon_'+idPrestamoEliminar).remove().draw();
                alert(resultado.mensaje);
            }else{
                alert(resultado.mensaje)
            }
        },
        error:function (xhr,error,mensaje){
            alert("Error de comunicacion "+error)
        }
    });
}