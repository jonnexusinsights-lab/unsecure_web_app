import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Expediente } from '../models/expediente.model';

@Injectable({
  providedIn: 'root'
})
export class ExpedienteService {
  private apiUrl = 'http://localhost:8080/api/expedientes';

  constructor(private http: HttpClient) {}

  listarTodos(): Observable<Expediente[]> {
    return this.http.get<Expediente[]>(this.apiUrl);
  }

  obtenerPorId(id: number): Observable<Expediente> {
    return this.http.get<Expediente>(`${this.apiUrl}/${id}`);
  }

  // [VULNERABILIDAD INTENCIONADA: SQL Injection]
  // Envia el termino directamente en la consulta
  buscar(termino: string): Observable<Expediente[]> {
    return this.http.get<Expediente[]>(`${this.apiUrl}/buscar?termino=${encodeURIComponent(termino)}`);
  }

  crear(expediente: Expediente): Observable<Expediente> {
    return this.http.post<Expediente>(this.apiUrl, expediente);
  }

  actualizar(id: number, expediente: Expediente): Observable<Expediente> {
    return this.http.put<Expediente>(`${this.apiUrl}/${id}`, expediente);
  }
}

