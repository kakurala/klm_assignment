import { Component, OnInit, OnDestroy } from '@angular/core';
import { FetchDataService } from '../service/fetch-data.service';

@Component({
  selector: 'app-health-dashboard',
  templateUrl: './health-dashboard.component.html',
  styleUrls: ['./health-dashboard.component.scss']
})
export class HealthDashboardComponent implements OnInit, OnDestroy {

  public data: [];

  constructor(
    private fetchDataService: FetchDataService
  ) { }

  ngOnInit() {
    this.fetchDataService.dashboardData$.subscribe(data => {
      if (data && data.stats) {
        this.data = data.stats.filter(x => !!x);
      }
    });

    this.fetchDataService.getDashboardData();
  }

  ngOnDestroy() {
    this.fetchDataService.dashboardData$.unsubscribe();
  }

  public trigger() {
    this.fetchDataService.getDashboardData();
  }
}
