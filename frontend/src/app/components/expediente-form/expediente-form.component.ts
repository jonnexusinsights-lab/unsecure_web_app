import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ExpedienteService } from '../../services/expediente.service';
import { AuthService } from '../../services/auth.service';
import { Expediente } from '../../models/expediente.model';

@Component({
  selector: 'app-expediente-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './expediente-form.component.html'
})
export class ExpedienteFormComponent {
  expediente: Expediente = {
    usuarioId: 3, // Por defecto Carlos Mora
    diagnostico: '',
    tratamiento: '',
    observacionesHtml: '',
    confidencial: true
  };

  loading = false;
  errorMsg = '';

  constructor(
    private expedienteService: ExpedienteService,
    public authService: AuthService,
    private router: Router
  ) {
    const user = this.authService.currentUser();
    if (user && user.usuarioId) {
      this.expediente.usuarioId = user.usuarioId;
    }
  }

  onSubmit(): void {
    if (!this.expediente.diagnostico || !this.expediente.tratamiento) {
      this.errorMsg = 'Complete el diagnóstico y el tratamiento.';
      return;
    }

    this.loading = true;
    this.errorMsg = '';

    this.expedienteService.crear(this.expediente).subscribe({
      next: (creado) => {
        this.loading = false;
        // Redirige directamente al detalle para observar la ejecucion del XSS
        this.router.navigate(['/expediente', creado.id]);
      },
      error: (err) => {
        this.loading = false;
        this.errorMsg = 'Error al registrar expediente: ' + (err.error?.message || err.message);
      }
    });
  }

  inyectarPayloadXss(tipo: string): void {
    if (tipo === 'img') {
      this.expediente.observacionesHtml = `<img src="invalido" onerror="alert('Vulnerabilidad XSS Almacenado Confirmada en InsecureCare!')">`;
    } else if (tipo === 'estilo') {
      this.expediente.observacionesHtml = `<div style="background-color: #fee2e2; border: 2px solid #ef4444; padding: 12px; border-radius: 6px;">` +
        `<h4 style="color: #991b1b; margin: 0;">Nota Inyectada mediante HTML Arbitrario</h4>` +
        `<p style="color: #7f1d1d; margin: 4px 0 0 0;">Esta etiqueta fue renderizada sin sanitización context-aware.</p></div>`;
    }
  }
}

