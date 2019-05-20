import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FlightsSelectorComponent } from './flights-selector/flights-selector.component';
import { HealthDashboardComponent } from './health-dashboard/health-dashboard.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/home' },
  { path: 'home', component: FlightsSelectorComponent },
  { path: 'dashboard', component: HealthDashboardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
