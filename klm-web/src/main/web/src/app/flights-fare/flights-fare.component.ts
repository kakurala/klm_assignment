import { Component, OnInit, OnDestroy } from '@angular/core';
import { FetchDataService } from '../service/fetch-data.service';
import { LoadingService } from '../service/loading.service';

@Component({
  selector: 'app-flights-fare',
  templateUrl: './flights-fare.component.html',
  styleUrls: ['./flights-fare.component.scss']
})
export class FlightsFareComponent implements OnInit, OnDestroy {

  public data: any;

  constructor(
    private fetchDataService: FetchDataService,
    private loadingService: LoadingService
    ) { }

  ngOnInit() {
    this.fetchDataService.fareData$.subscribe(data => {
      this.data = data;
      this.loadingService.isLoading.next(false);
    });
  }

  ngOnDestroy(){
    this.fetchDataService.fareData$.unsubscribe();
  }

}
