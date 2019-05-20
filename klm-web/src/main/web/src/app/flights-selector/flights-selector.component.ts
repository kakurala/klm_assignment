import { Component, OnInit } from '@angular/core';
import { FetchDataService } from '../service/fetch-data.service';
import { Observable } from 'rxjs';
import { LoadingService } from '../service/loading.service';

@Component({
  selector: 'app-flights-selector',
  templateUrl: './flights-selector.component.html',
  styleUrls: ['./flights-selector.component.scss']
})
export class FlightsSelectorComponent implements OnInit {

  public faredetails: any;
  public sourceModel: string;
  public destModel: string;
  public errorMessage: string;
  public dataSourceSrc: Observable<any>;
  public dataSourceDest: Observable<any>;

  constructor(
    private fetchDataService: FetchDataService,
    private loadingService: LoadingService
  ) { }

  ngOnInit() {
    this.dataSourceSrc = Observable.create((observer: any) => {
      this.fetchDataService.searchgOrigin(this.sourceModel)
        .subscribe(
          (result: any) => {
            observer.next(result._embedded.locations);
          },
          error => {
            // TODO: Handle error.
          });
    });


    this.dataSourceDest = Observable.create((observer: any) => {
      this.fetchDataService.searchgOrigin(this.destModel)
        .subscribe(
          (result: any) => {
            observer.next(result._embedded.locations);
          },
          error => {
            // TODO: Handle error.
          });
    });
  }

  public getData() {
    // trigger the loading overlay
    this.loadingService.isLoading.next(true);
    if (!this.sourceModel || this.sourceModel.trim() === '' || !this.destModel || this.destModel.trim() === '') {
      this.errorMessage = "Please fill source and destination values";
      this.loadingService.isLoading.next(false);
    } else {
      this.fetchDataService.getFareDetails(this.sourceModel, this.destModel);
    }
  }

}
