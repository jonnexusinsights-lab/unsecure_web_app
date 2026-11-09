import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { ExpedienteService } from '../../services/expediente.service';
import { AuthService } from '../../services/auth.service';
import { Expediente } from '../../models/expediente.model';

@Component({
  selector: 'app-expediente-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './expediente-list.component.html'
})
export class ExpedienteListComponent implements OnInit {
  expedientes: Expediente[] = [];
  terminoBusqueda = '';
  loading = false;
  errorInfo = '';

  constructor(
    public authService: AuthService,
    private expedienteService: ExpedienteService
  ) {}

  ngOnInit(): void {
    this.cargarExpedientes();
  }

  cargarExpedientes(): void {
    this.loading = true;
    this.errorInfo = '';
    this.expedienteService.listarTodos().subscribe({
      next: (data) => {
        this.expedientes = data;
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        this.errorInfo = 'Error al cargar expedientes: ' + (err.error?.message || err.message);
      }
    });
  }

  // [VULNERABILIDAD INTENCIONADA: SQL Injection Trigger]
  ejecutarBusqueda(): void {
    if (!this.terminoBusqueda.trim()) {
      this.cargarExpedientes();
      return;
    }

    this.loading = true;
    this.errorInfo = '';
    this.expedienteService.buscar(this.terminoBusqueda).subscribe({
      next: (data) => {
        this.expedientes = data;
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        // [VULNERABILIDAD INTENCIONADA: Information Disclosure]
        // Muestra el mensaje de error de sintaxis SQL al usuario
        this.errorInfo = 'Fallo de Base de Datos expuesto: ' + (err.error?.message || err.error?.trace || err.message);
      }
    });
  }

  probarPayloadSql(): void {
    this.terminoBusqueda = "' OR '1'='1";
    this.ejecutarBusqueda();
  }
}

