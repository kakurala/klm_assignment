import { Injectable, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';
import { LoadingService } from './loading.service';

@Injectable({
  providedIn: 'root'
})
export class FetchDataService implements OnInit {

  public fareData$ = new BehaviorSubject<any | null>(null);
  public dashboardData$ = new BehaviorSubject<any | null>(null);

  constructor(
    private http: HttpClient,
    private loadingService: LoadingService
  ) { }

  ngOnInit() {
  }

  public getOrigin(origin: string) {
    return this.http.get(`${environment.apiURL}/airports/${origin}`);
  }

  public searchgOrigin(term: string) {
    return this.http.get(`${environment.apiURL}/airports?term=${term}`);
  }

  public getDashboardData() {

    this.http.get(`${environment.dashboardAPI}`).subscribe(data => {

      const length = data['traces'].length;
      let arr = [];
      let minTime = 0;
      let maxTime = 0;
      let totalTime = 0;

      minTime = data['traces'][0].timeTaken;
      maxTime = data['traces'][0].timeTaken;

      data['traces'].forEach(obj => {
        const statusCode = obj.response.status;

        if (arr[statusCode] && arr[statusCode].count) {
          arr[statusCode].count = arr[statusCode].count + 1;
          minTime = arr[statusCode].minTime;
          maxTime = arr[statusCode].maxTime;
          arr[statusCode].totalTime += obj.timeTaken;
        } else {
          arr[statusCode] = { count: 1, totalTime: data['traces'][0].timeTaken};
        }

        minTime = minTime > obj.timeTaken ? obj.timeTaken : minTime;
        maxTime = maxTime < obj.timeTaken ? obj.timeTaken : maxTime;

        arr[statusCode].minTime = minTime;
        arr[statusCode].maxTime = maxTime;
        arr[statusCode].code = statusCode;
        // arr[statusCode].totalTime += obj.timeTaken;
      });

      const statsData = { length: length, stats: arr };

      this.dashboardData$.next(statsData);
    });
  }


  public getFareDetails(origin: string, dest: string) {
    this.http.get(`${environment.apiURL}/fares/${origin}/${dest}`).subscribe(
      data => {
        this.fareData$.next(data);
      },
      error => {
        // stop loading overlay incase of error
        this.loadingService.isLoading.next(false);
      });
  }
}
