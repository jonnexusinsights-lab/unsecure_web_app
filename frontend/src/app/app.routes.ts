import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ExpedienteListComponent } from './components/expediente-list/expediente-list.component';
import { ExpedienteDetailComponent } from './components/expediente-detail/expediente-detail.component';
import { ExpedienteFormComponent } from './components/expediente-form/expediente-form.component';

export const routes: Routes = [
  { path: '', redirectTo: 'expedientes', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'expedientes', component: ExpedienteListComponent },
  { path: 'expediente/:id', component: ExpedienteDetailComponent },
  { path: 'nuevo-expediente', component: ExpedienteFormComponent },
  { path: '**', redirectTo: 'expedientes' }
];

