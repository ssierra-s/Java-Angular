import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LayoutService } from 'src/app/layout/service/app.layout.service';
import { HttpClient } from '@angular/common/http';

interface Estudiante {
    numeroIdentidad: string;
    tipoDocumento: string;
    nombres: string;
    apellidos: string;
    fechaNacimiento: Date;
    edad: number;
    gradoActual: number;
    email: string;
    telefonoFijo: string;
    celular: string;
}

@Component({
    selector: 'app-landing',
    templateUrl: './landing.component.html'
})
export class LandingComponent implements OnInit {

    estudiantes: Estudiante[] = [];
    studentDialog: boolean = false;
    editMode: boolean = false;
    estudiante: Estudiante = { numeroIdentidad: "", tipoDocumento: "", nombres: "", apellidos: "", fechaNacimiento: new Date(), edad: 0, gradoActual: 0, email: "", telefonoFijo: "", celular: "" };
    numeroDocumentoFiltro: string = '';

    private apiUrl = 'http://localhost:8080/api/estudiantes'; // Cambia esto a la URL de tu API

    constructor(public layoutService: LayoutService, public router: Router, private http: HttpClient) { }

    ngOnInit() {
        this.getEstudiantes();
    }

    getEstudiantes() {
        this.http.get<Estudiante[]>(this.apiUrl).subscribe(data => {
            this.estudiantes = data;
        });
    }

    openNew() {
        this.estudiante = { numeroIdentidad: "", tipoDocumento: "", nombres: "", apellidos: "", fechaNacimiento: new Date(), edad: 0, gradoActual: 0, email: "", telefonoFijo: "", celular: "" }; 
        this.editMode = false;
        this.studentDialog = true;
    }

    editEstudiante(estudiante: Estudiante) {
        this.http.get<Estudiante>(`${this.apiUrl}/${estudiante.numeroIdentidad}`).subscribe(data => {
            this.estudiante = data;
            this.editMode = true;
            this.studentDialog = true;
        });
    }

    deleteEstudiante(numeroIdentidad: string) {
        this.http.delete(`${this.apiUrl}/${numeroIdentidad}`).subscribe(() => {
            this.estudiantes = this.estudiantes.filter((estudiante) => estudiante.numeroIdentidad !== numeroIdentidad);
        });
    }

    filtrarPorGrado11() {
        this.http.get<Estudiante[]>(`${this.apiUrl}/grado11`).subscribe(estudiantes => {
            this.estudiantes = estudiantes;
        });
    }

    // Filtrar estudiantes por edad (15-17 años)
    filtrarPorEdad() {
        this.http.get<Estudiante[]>(`${this.apiUrl}/edad15a17`).subscribe(estudiantes => {
            this.estudiantes = estudiantes;
        });
    }

    filtrarPorNumeroDocumento() {
        

        this.http.get<Estudiante[]>(`${this.apiUrl}/${this.numeroDocumentoFiltro}`).subscribe(data => {
            console.log(data); // Imprime los datos obtenidos en la consola
        
        // Verifica si la respuesta es un arreglo o un solo objeto
        if (Array.isArray(data)) {
            // Si es un arreglo, asigna directamente
            this.estudiantes = data.map(estudiante => ({
                numeroIdentidad: estudiante.numeroIdentidad,
                tipoDocumento: estudiante.tipoDocumento,
                nombres: estudiante.nombres,
                apellidos: estudiante.apellidos,
                fechaNacimiento: new Date(estudiante.fechaNacimiento), // Asegúrate de que sea un objeto Date
                edad: estudiante.edad,
                gradoActual: estudiante.gradoActual,
                email: estudiante.email,
                telefonoFijo: estudiante.telefonoFijo,
                celular: estudiante.celular,
            }));
        } else {
            // Si es un solo objeto, conviértelo en un arreglo
            this.estudiantes = [data]; // Coloca el objeto en un arreglo
        }

        console.log(this.estudiantes); // Imprime el arreglo de estudiantes para verificar que se haya asignado correctamente
    });
        
    }

    saveEstudiante() {
       
    
        if (this.editMode) {
            // Validar si el estudiante que se está editando 
            this.http.put(`${this.apiUrl}/${this.estudiante.numeroIdentidad}`, this.estudiante).subscribe(() => {
                const index = this.estudiantes.findIndex((e) => e.numeroIdentidad === this.estudiante.numeroIdentidad);
                this.estudiantes[index] = this.estudiante; // Actualizar el estudiante en la lista local
                this.studentDialog = false; // Cerrar el diálogo
            });
        } else {
             
     
            if (this.estudiantes.some(e => e.numeroIdentidad === this.estudiante.numeroIdentidad)) {
                alert('Ya existe un estudiante con el mismo número de identidad.');
                return;
            }
            this.http.post(`${this.apiUrl}/`, this.estudiante).subscribe((newEstudiante: Estudiante) => {
                this.estudiantes.push(newEstudiante); // Añadir el nuevo estudiante a la lista local
                this.studentDialog = false; // Cerrar el diálogo
            });
        }
    }

    hideDialog() {
        this.studentDialog = false;
    }
}
