export interface Expediente {
  id?: number;
  usuarioId: number;
  nombrePaciente?: string;
  numeroExpediente?: string;
  diagnostico: string;
  tratamiento: string;
  observacionesHtml: string;
  fechaRegistro?: string;
  confidencial?: boolean;
}

