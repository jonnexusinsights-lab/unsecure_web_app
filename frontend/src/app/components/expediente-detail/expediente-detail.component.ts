import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { ExpedienteService } from '../../services/expediente.service';
import { AuthService } from '../../services/auth.service';
import { Expediente } from '../../models/expediente.model';

@Component({
  selector: 'app-expediente-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './expediente-detail.component.html'
})
export class ExpedienteDetailComponent implements OnInit {
  expediente: Expediente | null = null;
  observacionesSeguras: SafeHtml = '';
  loading = false;
  errorMsg = '';
  currentId = 1;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private expedienteService: ExpedienteService,
    public authService: AuthService,
    // [VULNERABILIDAD INTENCIONADA: A03:2021 - Bypass de Proteccion XSS en Angular]
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const idParam = params.get('id');
      if (idParam) {
        this.currentId = Number(idParam);
        this.cargarExpediente(this.currentId);
      }
    });
  }

  cargarExpediente(id: number): void {
    this.loading = true;
    this.errorMsg = '';
    this.expedienteService.obtenerPorId(id).subscribe({
      next: (data) => {
        this.expediente = data;
        // [VULNERABILIDAD INTENCIONADA: XSS]
        // Se anula intencionalmente el sanitizador nativo de Angular
        if (data.observacionesHtml) {
          this.observacionesSeguras = this.sanitizer.bypassSecurityTrustHtml(data.observacionesHtml);
        } else {
          this.observacionesSeguras = '';
        }
        this.loading = false;
      },
      error: (err) => {
        this.loading = false;
        this.errorMsg = 'Error al cargar expediente #' + id + ': ' + (err.error?.message || err.message);
      }
    });
  }

  navegarAId(nuevoId: number): void {
    this.router.navigate(['/expediente', nuevoId]);
  }
}

