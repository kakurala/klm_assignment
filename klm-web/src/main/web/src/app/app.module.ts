import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FlightsSelectorComponent } from './flights-selector/flights-selector.component';
import { HttpClientModule } from '@angular/common/http';
import { FlightsFareComponent } from './flights-fare/flights-fare.component';
import { FormsModule } from '@angular/forms';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { NgxLoadingModule } from 'ngx-loading';
import { HealthDashboardComponent } from './health-dashboard/health-dashboard.component';

@NgModule({
  declarations: [
    AppComponent,
    FlightsSelectorComponent,
    FlightsFareComponent,
    HealthDashboardComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    TypeaheadModule.forRoot(),
    NgxLoadingModule.forRoot({})
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
